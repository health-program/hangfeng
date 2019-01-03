package com.paladin.hf.service.org;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.hf.mapper.org.OrgUserMapper;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.syst.SysUserService;


@Service
public class OrgUserService extends ServiceSupport<OrgUser> {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private OrgUserMapper orgUserMapper;

	@Autowired
	private OrgUserTransferLogService orgUserTransferLogService;
	
	@Autowired
	private PersonCycAssessMapper personCycAssessMapper;

	public List<OrgUser> findUnitUser(String unitId) {
		// 判断是否拥有该单位权限
		if (UserSession.getCurrentUserSession().ownUnit(unitId)) {
			return searchAll(new Condition(OrgUser.COLUMN_ORG_UNIT_ID, QueryType.EQUAL, unitId, null));
		} else {
			return new ArrayList<>();
		}

	}
	
	public List<OrgUser> findUserByIdentification(){
		return searchAll(new Condition("identification", QueryType.EQUAL, username, null));
	}

	@Transactional
	public int saveOrUpdateUser(OrgUser orgUser) {

		int effect = 0;

		Unit unit = UnitContainer.getUnit(orgUser.getOrgUnitId());
		// 设置冗余字段 机构
		orgUser.setOrgAgencyId(unit.getAgency().getId());

		Unit assessTeam = unit.getAssessTeam();
		if (assessTeam != null) {
			orgUser.setOrgAssessTeamId(assessTeam.getId());
		}

		// 是否考评人员处理，赋予考评权限
		if (orgUser.getIsAssessor() == 0) {
			orgUser.setAssessUnitId(null);
			orgUser.setAssessRole(UserSession.DEFAULT_ROLE_SELF_ID);
		} else {
			String assessUid = orgUser.getAssessUnitId();
			if (StringUtils.isEmpty(assessUid)) {
				throw new BusinessException("考评人员必须选择考评科室");
			}

			Unit assessUnit = UnitContainer.getUnit(assessUid);
			if (assessUnit.isAgency()) {
				orgUser.setAssessRole(UserSession.DEFAULT_ROLE_AGENCY_ADMIN_ID);
			} else if (assessUnit.isAssessTeam()) {
				orgUser.setAssessRole(UserSession.DEFAULT_ROLE_ASSESS_TEAM_ADMIN_ID);
			} else {
				orgUser.setAssessRole(UserSession.DEFAULT_ROLE_DEPARTMENT_ADMIN_ID);
			}
		}

		String userId = orgUser.getId();

		String idcard = orgUser.getIdentification();
		if (idcard != null && idcard.length() != 0) {
			if (!ValidateUtil.isValidatedAllIdcard(idcard)) {
				throw new BusinessException("请输入有效的身份证号码");
			}

			if (userId == null || userId.length() == 0) {
				if (!isUniqueIdcard(idcard)) {
					throw new BusinessException("身份证号码不能重复");
				}
			} else {
				if (!isUniqueIdcardButSelf(idcard, userId)) {
					throw new BusinessException("身份证号码不能重复");
				}
			}
		}

		if (userId == null || userId.length() == 0) {
			String orgUserId = UUIDUtil.createUUID();
			orgUser.setId(orgUserId);
			if (sysUserService.createOrgUserAccount(orgUser.getAccount(), orgUserId) > 0) {
				effect = save(orgUser);
			}
		} else {
			orgUser.setAccount(null);
			effect = this.updateSelective(orgUser);

			// 需要更新UserSession
		}

		return effect;
	}

	public boolean isUniqueIdcardButSelf(String identification, String userId) {
		return orgUserMapper.countElseUserByIdentification(identification, userId) == 0;
	}

	public boolean isUniqueIdcard(String identification) {
		return orgUserMapper.countUserByIdentification(identification) == 0;
	}

	public Page<OrgUser> searchUserPage(OrgUserQuery query) {
		UnitQuery unitQuery = getUnitQueryDouble(query.getOrgUnitId());
		query.setAgencyId(unitQuery.getAgencyId());
		query.setAgencyIds(unitQuery.getAgencyIds());
		query.setUnitId(unitQuery.getUnitId());
		query.setUnitIds(unitQuery.getUnitIds());
		query.setAssessTeamId(unitQuery.getAssessTeamId());

		return searchPage(query);
	}

	@Transactional
	public int wipeByPrimaryKey(String id) {
		// 逻辑删除档案人员
		int effect = this.removeByPrimaryKey(id);
		if (effect > 0) {
			// 物理删除账号
			return orgUserMapper.wipeByPrimaryKey(id);
		}
		return effect;
	}

	@Transactional
	public int transferUser(String[] userIds, String target) {
		if (!UserSession.getCurrentUserSession().isAdmin()) {
			throw new BusinessException("只有管理员才有权限转移人员");
		}
		int effect = 0;
		if (userIds != null && userIds.length > 0) {

			String transferBy = UserSession.getCurrentUserSession().getUserId();

			Unit unit = UnitContainer.getUnit(target);
			Unit agency = unit.getAgency();
			Unit team = unit.getAssessTeam();

			for (String userId : userIds) {
				OrgUser user = get(userId);
				if (user != null) {
					String source = user.getOrgUnitId();
					int result = orgUserMapper.updateUserUnit(userId, agency.getId(), team == null ? null : team.getId(), target);
					if (result > 0) {
						addUserTransferLog(userId, source, target, OrgUserTransferLog.TRANSFER_TYPE_ADMIN, transferBy);
						effect += result;
					}
				}
			}
		}
		return effect;
	}

	@Transactional
	public int transferAsk(String[] userIds, String target) {
		Unit unit = UnitContainer.getUnit(target);
		String targetUnitId = target;
		String targetAgencyId = unit.getAgency().getId();
		int effect = 0;
		if (userIds != null && userIds.length > 0) {
			for (String userId : userIds) {
				OrgUser user = get(userId);
				if (user != null) {
					String unitId = user.getOrgUnitId();
					if (!target.equals(unitId)) {
						OrgUser model = new OrgUser();
						model.setId(userId);
						model.setTransferOriginAgencyId(user.getOrgAgencyId());
						model.setTransferOriginUnitId(user.getOrgUnitId());
						model.setTransferAgencyId(targetAgencyId);
						model.setTransferUnitId(targetUnitId);
						model.setTransferStatus(OrgUser.TRANSFER_STATUS_ASK);

						effect += updateSelective(model);
					}
				}
			}
		}
		return effect;
	}

	@Transactional
	public int agreeTransferAsk(String[] userIds) {
		int effect = 0;
		if (userIds != null && userIds.length > 0) {

			String transferBy = UserSession.getCurrentUserSession().getUserId();

			for (String userId : userIds) {
				OrgUser user = get(userId);
				if (user != null) {
					Integer status = user.getTransferStatus();
					if (status != null && status == OrgUser.TRANSFER_STATUS_ASK) {
						String target = user.getTransferUnitId();
						String source = user.getOrgUnitId();
						Unit unit = UnitContainer.getUnit(target);
						Unit agency = unit.getAgency();
						Unit team = unit.getAssessTeam();
						int result = orgUserMapper.updateUnitForTransferAsk(userId, agency.getId(), team == null ? null : team.getId(), target);
						if (result > 0) {
							addUserTransferLog(userId, source, target, OrgUserTransferLog.TRANSFER_TYPE_ASK, transferBy);
							effect += result;
						}
					}
				}
			}
		}
		return effect;
	}

	public int rejectTransferAsk(String[] userIds) {
		return orgUserMapper.rejectTransferAsk(userIds);
	}

	/**
	 * 暂时只记录转移成功
	 * 
	 * @param userId
	 * @param unitFrom
	 * @param unitTo
	 * @param result
	 * @return
	 */
	public int addUserTransferLog(String userId, String unitFrom, String unitTo, Integer type, String transferBy) {
		OrgUserTransferLog log = new OrgUserTransferLog();
		log.setUserId(userId);
		log.setUnitFrom(unitFrom);
		log.setUnitTo(unitTo);
		log.setTransferTime(new Date());
		log.setTransferType(type);
		log.setTransferBy(transferBy);

		return orgUserTransferLogService.save(log);
	}

	private List<Integer> outTransferAsk = new ArrayList<>();

	{
		outTransferAsk.add(OrgUser.TRANSFER_STATUS_ASK);
		outTransferAsk.add(OrgUser.TRANSFER_STATUS_SUCCESS);
		outTransferAsk.add(OrgUser.TRANSFER_STATUS_FAIL);
	}

	public List<OrgUser> getTransferAskOutUser() {
		UnitQuery unitQuery = getUnitQueryDouble(null);

		List<Condition> conditions = new ArrayList<>();
		if (unitQuery.getAgencyId() != null) {
			conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_ORIGIN_AGENCY_ID, QueryType.EQUAL, unitQuery.getAgencyId(), null));
		}

		if (unitQuery.getAgencyIds() != null) {
			conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_ORIGIN_AGENCY_ID, QueryType.IN, unitQuery.getAgencyIds(), null));
		}

		if (unitQuery.getUnitId() != null) {
			conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_ORIGIN_UNIT_ID, QueryType.EQUAL, unitQuery.getUnitId(), null));
		}

		if (unitQuery.getUnitId() != null) {
			conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_ORIGIN_UNIT_ID, QueryType.IN, unitQuery.getUnitIds(), null));
		}

		if (unitQuery.getAssessTeamId() != null) {
			conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_ORIGIN_TEAM_ID, QueryType.EQUAL, unitQuery.getAssessTeamId(), null));
		}

		conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.IN, outTransferAsk, null));
		return searchAll(conditions);
	}

	public List<OrgUser> getTransferAskInUser() {

		UserSession session = UserSession.getCurrentUserSession();
		if (session.isAdmin()) {
			return searchAll(new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK, null));
		}

		List<String> agencyIds = session.getOwnAgencyId();

		if (agencyIds != null) {
			int size = agencyIds.size();
			if (size == 1) {
				return searchAll(new Condition[] { new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK, null),
						new Condition(OrgUser.COLUMN_TRANSFER_AGENCY_ID, QueryType.EQUAL, agencyIds.get(0), null) });
			} else if (size > 1) {
				return searchAll(new Condition[] { new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK, null),
						new Condition(OrgUser.COLUMN_TRANSFER_AGENCY_ID, QueryType.IN, agencyIds, null) });
			}
		}

		return new ArrayList<>();
	}

	public int getCountOfTransferAskIn() {

		UserSession session = UserSession.getCurrentUserSession();
		if (session.isAdmin()) {
			return searchAllCount(new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK, null));
		}

		List<String> agencyIds = session.getOwnAgencyId();

		if (agencyIds != null) {
			int size = agencyIds.size();
			if (size == 1) {
				return searchAllCount(new Condition[] { new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK, null),
						new Condition(OrgUser.COLUMN_TRANSFER_AGENCY_ID, QueryType.EQUAL, agencyIds.get(0), null) });
			} else if (size > 1) {
				return searchAllCount(new Condition[] { new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK, null),
						new Condition(OrgUser.COLUMN_TRANSFER_AGENCY_ID, QueryType.IN, agencyIds, null) });
			}
		}

		return 0;
	}

	public int removeTransferAskUser(String[] userIds) {
		return orgUserMapper.removeTransferAsk(userIds);
	}

	public ImportUserResult importUser(List<OrgUser> users, String unitId) {

		if (users != null && users.size() > 0) {
			if (users.size() > 200) {
				throw new BusinessException("每次导入人数不能超过200");
			}

			UserSession session = UserSession.getCurrentUserSession();
			if (!session.ownUnit(unitId)) {
				throw new BusinessException("您没有权限导入到该单位");
			}

			Unit unit = UnitContainer.getUnit(unitId);

			// 设置冗余字段 机构
			String agencyId = unit.getAgency().getId();
			String assessTeamId = null;

			Unit assessTeam = unit.getAssessTeam();
			if (assessTeam != null) {
				assessTeamId = assessTeam.getId();
			}

			List<ImportUserError> errors = new ArrayList<>();

			int i = 0;

			for (OrgUser user : users) {
				i++;
				String idcard = user.getIdentification();
				if (idcard != null && idcard.length() != 0) {
					if (!ValidateUtil.isValidatedAllIdcard(idcard)) {
						errors.add(new ImportUserError(i, "无效的身份证号码"));
						continue;
					}

					if (!isUniqueIdcard(idcard)) {
						throw new BusinessException("身份证号码不能重复");
					}
				} else {
					errors.add(new ImportUserError(i, "缺少身份证号码"));
					continue;
				}

				if (StringUtils.isEmpty(user.getName())) {
					errors.add(new ImportUserError(i, "缺少姓名"));
					continue;
				}

				if (user.getRecordCreateTime() == null) {
					errors.add(new ImportUserError(i, "缺少建档日期"));
					continue;
				}

				if (user.getBirthday() == null) {
					errors.add(new ImportUserError(i, "缺少出生日期"));
					continue;
				}

				user.setAssessRole(UserSession.DEFAULT_ROLE_SELF_ID);
				user.setIsAssessor(0);
				user.setOrgAgencyId(agencyId);
				user.setOrgAssessTeamId(assessTeamId);
				user.setOrgUnitId(unitId);

				String userId = UUIDUtil.createUUID();
				user.setId(userId);
				try {
					sysUserService.createOrgUserAndCount(user.getAccount(), user);
				} catch (BusinessException e) {
					errors.add(new ImportUserError(i, e.getMessage()));
					continue;
				} catch (Exception e) {
					errors.add(new ImportUserError(i, "保存失败"));
					continue;
				}
			}

			return new ImportUserResult(users.size(), errors);
		}

		return null;
	}

	public static class ImportUserResult {

		private int successCount;
		private int errorCount;

		private List<ImportUserError> errors;

		public ImportUserResult(int totalCount, List<ImportUserError> errors) {

			if (errors != null) {
				errorCount = errors.size();
			}

			successCount = totalCount - errorCount;

			this.errors = errors;
		}

		public int getSuccessCount() {
			return successCount;
		}

		public int getErrorCount() {
			return errorCount;
		}

		public List<ImportUserError> getErrors() {
			return errors;
		}

	}

	public static class ImportUserError {

		private int index;

		private String message;

		public ImportUserError(int index, String message) {
			this.index = index;
			this.message = message;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

      public List<PersonCycAssessExt> findThisYearAssessSituationList(String orgUserId, Integer myYear) {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(Calendar.YEAR, myYear);
                Date  startTime = calendar.getTime();
                calendar.set(Calendar.YEAR, myYear + 1);
                Date endTime = calendar.getTime();
                return  personCycAssessMapper.findThisYearAssessSituationList(orgUserId,startTime,endTime);
      }
}
