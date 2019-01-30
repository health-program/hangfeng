package com.paladin.hf.service.org;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.framework.common.QueryType;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.ExcelImportResult;
import com.paladin.framework.common.ExcelImportResult.ExcelImportError;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.excel.DefaultSheet;
import com.paladin.framework.excel.read.DefaultReadColumn;
import com.paladin.framework.excel.read.ExcelReadException;
import com.paladin.framework.excel.read.ExcelReader;
import com.paladin.framework.excel.read.ReadColumn;
import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.mapper.org.OrgUserMapper;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.model.org.OrgUserTransferLog;
import com.paladin.hf.service.org.dto.AppOrgUserSelfDTO;
import com.paladin.hf.service.org.dto.AppOrgUserSelfResumeDTO;
import com.paladin.hf.service.org.dto.ExcelUser;
import com.paladin.hf.service.org.dto.OrgUserClaimQuery;
import com.paladin.hf.service.org.dto.OrgUserDTO;
import com.paladin.hf.service.org.dto.OrgUserQuery;
import com.paladin.hf.service.org.dto.OrgUserSelfDTO;
import com.paladin.hf.service.org.vo.OrgUserVO;
import com.paladin.hf.service.syst.SysUserService;

@Service
public class OrgUserService extends ServiceSupport<OrgUser> {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private OrgUserMapper orgUserMapper;

	@Autowired
	private OrgUserTransferLogService orgUserTransferLogService;

	/**
	 * 获取某个人员具体信息
	 * 
	 * @param id
	 * @return
	 */
	public OrgUserVO getUser(String id) {
		OrgUser user = get(id);
		if (user != null) {
			OrgUserVO vo = new OrgUserVO();
			SimpleBeanCopyUtil.simpleCopy(user, vo);
			return vo;
		}
		return null;
	}

	/**
	 * 查找人员档案
	 * 
	 * @param query
	 * @return
	 */
	public PageResult<OrgUserVO> findUser(OrgUserQuery query) {
		UnitQuery unitQuery = DataPermissionUtil.getUnitQueryDouble(query.getOrgUnitId());
		query.setAgencyId(unitQuery.getAgencyId());
		query.setAgencyIds(unitQuery.getAgencyIds());
		query.setUnitId(unitQuery.getUnitId());
		query.setUnitIds(unitQuery.getUnitIds());
		query.setAssessTeamId(unitQuery.getAssessTeamId());

		return searchPage(query).convert(OrgUserVO.class);
	}

	/**
	 * 查找人员档案
	 * 
	 * @param query
	 * @return
	 */
	public PageResult<OrgUserVO> findUserForClaim(OrgUserClaimQuery query) {
		String uid = query.getOrgUnitId();
		if (uid != null && uid.length() > 0) {
			Unit unit = UnitContainer.getUnit(uid);
			if (unit != null) {
				if (unit.isAgency()) {
					query.setAgencyId(uid);
				} else if (unit.isAssessTeam()) {
					query.setAssessTeamId(uid);
				} else {
					query.setUnitId(uid);
				}
			}
		}

		return searchPage(query, true).convert(OrgUserVO.class);
	}

	/**
	 * 根据身份证号码查找人员
	 * 
	 * @param identification
	 * @return
	 */
	public List<OrgUser> findUserByIdentification(String identification) {
		return searchAll(new Condition(OrgUser.COLUMN_IDENTIFICATION, QueryType.EQUAL, identification));
	}

	/**
	 * 新增人员
	 * 
	 * @param orgUserDTO
	 * @return
	 */
	@Transactional
	public boolean addUser(OrgUserDTO orgUserDTO) {
		if (orgUserDTO == null) {
			throw new BusinessException("新增人员数据异常");
		}

		OrgUser orgUser = new OrgUser();
		SimpleBeanCopyUtil.simpleCopy(orgUserDTO, orgUser);

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
			orgUser.setAssessRole(HfUserSession.DEFAULT_ROLE_SELF_ID);
		} else {
			String assessUid = orgUser.getAssessUnitId();
			if (StringUtil.isEmpty(assessUid)) {
				throw new BusinessException("考评人员必须选择考评科室");
			}

			Unit assessUnit = UnitContainer.getUnit(assessUid);
			if (assessUnit.isAgency()) {
				orgUser.setAssessRole(HfUserSession.DEFAULT_ROLE_AGENCY_ADMIN_ID);
			} else if (assessUnit.isAssessTeam()) {
				orgUser.setAssessRole(HfUserSession.DEFAULT_ROLE_ASSESS_TEAM_ADMIN_ID);
			} else {
				orgUser.setAssessRole(HfUserSession.DEFAULT_ROLE_DEPARTMENT_ADMIN_ID);
			}
		}

		String idcard = orgUser.getIdentification();

		if (idcard != null && idcard.length() != 0) {

			// 加入身份证类型后验证需要结合身份证类型，暂时不验证
			// if (!ValidateUtil.isValidatedAllIdcard(idcard)) {
			// throw new BusinessException("请输入有效的身份证号码");
			// }

			if (!isUniqueIdcard(idcard)) {
				throw new BusinessException("身份证号码不能重复");
			}
		} else {
			throw new BusinessException("身份证号码不能为空");
		}

		String orgUserId = UUIDUtil.createUUID();
		orgUser.setId(orgUserId);
		if (sysUserService.createOrgUserAccount(orgUser.getAccount(), orgUserId) > 0) {
			save(orgUser);
		}

		return true;
	}

	/**
	 * 更新人员
	 * 
	 * @param orgUserDTO
	 * @return
	 */
	@Transactional
	public boolean updateUser(OrgUserDTO orgUserDTO) {

		String userId = orgUserDTO.getId();
		if (userId == null || userId.length() == 0) {
			throw new BusinessException("找不到需要更新的人员");
		}

		OrgUser orgUser = get(userId);

		if (orgUser == null) {
			throw new BusinessException("找不到需要更新的人员");
		}

		// 原账号
		String originAccount = orgUser.getAccount();

		SimpleBeanCopyUtil.simpleCopy(orgUserDTO, orgUser);

		Unit unit = UnitContainer.getUnit(orgUser.getOrgUnitId());
		// 设置冗余字段 机构
		orgUser.setOrgAgencyId(unit.getAgency().getId());

		Unit assessTeam = unit.getAssessTeam();
		if (assessTeam != null) {
			orgUser.setOrgAssessTeamId(assessTeam.getId());
		} else {
			orgUser.setOrgAssessTeamId(null);
		}

		// 是否考评人员处理，赋予考评权限
		if (orgUser.getIsAssessor() == 0) {
			orgUser.setAssessUnitId(null);
			orgUser.setAssessRole(HfUserSession.DEFAULT_ROLE_SELF_ID);
		} else {
			String assessUid = orgUser.getAssessUnitId();
			if (StringUtil.isEmpty(assessUid)) {
				throw new BusinessException("考评人员必须选择考评科室");
			}

			Unit assessUnit = UnitContainer.getUnit(assessUid);
			if (assessUnit.isAgency()) {
				orgUser.setAssessRole(HfUserSession.DEFAULT_ROLE_AGENCY_ADMIN_ID);
			} else if (assessUnit.isAssessTeam()) {
				orgUser.setAssessRole(HfUserSession.DEFAULT_ROLE_ASSESS_TEAM_ADMIN_ID);
			} else {
				orgUser.setAssessRole(HfUserSession.DEFAULT_ROLE_DEPARTMENT_ADMIN_ID);
			}
		}

		String idcard = orgUser.getIdentification();
		if (idcard != null && idcard.length() != 0) {
			// 加入身份证类型后验证需要结合身份证类型，暂时不验证
			// if (!ValidateUtil.isValidatedAllIdcard(idcard)) {
			// throw new BusinessException("请输入有效的身份证号码");
			// }

			if (!isUniqueIdcardButSelf(idcard, userId)) {
				throw new BusinessException("身份证号码不能重复");
			}
		} else {
			throw new BusinessException("身份证号码不能为空");
		}

		String nowAccount = orgUser.getAccount();
		if (nowAccount == null || nowAccount.length() == 0) {
			throw new BusinessException("账号不能为空");
		}

		if (!originAccount.equals(nowAccount)) {
			sysUserService.updateAccount(userId, originAccount, nowAccount);
		}

		update(orgUser);
		return true;
	}

	/**
	 * 更新自己的信息
	 * 
	 * @param orgUserDTO
	 * @return
	 */
	@Transactional
	public boolean updateUserSelf(OrgUserSelfDTO orgUserDTO) {
		String userId = HfUserSession.getCurrentUserSession().getUserId();
		if (userId == null || userId.length() == 0) {
			throw new BusinessException("找不到需要更新的人员");
		}

		OrgUser orgUser = get(userId);

		if (orgUser == null) {
			throw new BusinessException("找不到需要更新的人员");
		}

		SimpleBeanCopyUtil.simpleCopy(orgUserDTO, orgUser);
		update(orgUser);
		return true;
	}
	
	@Transactional
    public boolean appUpdateUserSelf(AppOrgUserSelfDTO orgUserDTO) {
        String userId = HfUserSession.getCurrentUserSession().getUserId();
        if (userId == null || userId.length() == 0) {
            throw new BusinessException("找不到需要更新的人员");
        }

        OrgUser orgUser = get(userId);

        if (orgUser == null) {
            throw new BusinessException("找不到需要更新的人员");
        }

        SimpleBeanCopyUtil.simpleCopy(orgUserDTO, orgUser);
        update(orgUser);
        return true;
    }

	
	public boolean appUpdateResume(AppOrgUserSelfResumeDTO dto){
	    String userId = HfUserSession.getCurrentUserSession().getUserId();
        if (userId == null || userId.length() == 0) {
            throw new BusinessException("找不到需要更新的人员");
        }

        OrgUser orgUser = get(userId);

        if (orgUser == null) {
            throw new BusinessException("找不到需要更新的人员");
        }
        
        SimpleBeanCopyUtil.simpleCopy(dto, orgUser);
        updateSelective(orgUser);
        return true;
	}
	
	/**
	 * 判断是否唯一身份证（除自己外）
	 * 
	 * @param identification
	 * @param userId
	 * @return
	 */
	public boolean isUniqueIdcardButSelf(String identification, String userId) {
		return orgUserMapper.countElseUserByIdentification(identification, userId) == 0;
	}

	/**
	 * 判断是否唯一身份证
	 * 
	 * @param identification
	 * @return
	 */
	public boolean isUniqueIdcard(String identification) {
		return orgUserMapper.countUserByIdentification(identification) == 0;
	}

	/**
	 * 移除人员，并删除账号
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public int removeUser(String id) {
		// 逻辑删除档案人员
		int effect = this.removeByPrimaryKey(id);
		if (effect > 0) {
			// 物理删除账号
			return orgUserMapper.wipeByPrimaryKey(id);
		}
		return effect;
	}

	/**
	 * 人员离岗
	 * 
	 * @param userId
	 * @return
	 */
	public int leave(String userId) {
		return orgUserMapper.updateUserStatus(userId, OrgUser.USER_STATUS_LEAVE);
	}

	/**
	 * 人员认领
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public int claim(String[] userIds) {
		HfUserSession session = HfUserSession.getCurrentUserSession();
		Unit unit = session.getOwnUnit();
		if (unit == null) {
			throw new BusinessException("您没有权限认领人员");
		}

		int effect = 0;
		String transferBy = session.getUserId();
		Unit team = unit.getAssessTeam();
		for (String userId : userIds) {
			OrgUser user = get(userId);
			if (user.getIsDelete() != OrgUser.USER_STATUS_LEAVE) {
				continue;
			}

			String source = user.getOrgUnitId();
			String target = unit.getId();

			if (orgUserMapper.updateUserForClaim(userId, unit.getAgency().getId(), team == null ? null : team.getId(), target) > 0) {
				addUserTransferLog(userId, source, target, OrgUserTransferLog.TRANSFER_TYPE_ASK, transferBy);
				effect++;
			}
		}
		return effect;
	}

	/**
	 * 转移人员到目标科室
	 * 
	 * @param userIds
	 * @param target
	 * @return
	 */
	@Transactional
	public int transferUser(String[] userIds, String target) {
		if (!HfUserSession.getCurrentUserSession().isAdminRoleLevel()) {
			throw new BusinessException("只有管理员才有权限转移人员");
		}
		int effect = 0;
		if (userIds != null && userIds.length > 0) {

			String transferBy = HfUserSession.getCurrentUserSession().getUserId();

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

	/**
	 * 转移人员申请
	 * 
	 * @param userIds
	 * @param target
	 * @return
	 */
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

	/**
	 * 同意人员转移
	 * 
	 * @param userIds
	 * @return
	 */
	@Transactional
	public int agreeTransferAsk(String[] userIds) {
		int effect = 0;
		if (userIds != null && userIds.length > 0) {

			String transferBy = HfUserSession.getCurrentUserSession().getUserId();

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

	/**
	 * 拒绝人员转移
	 * 
	 * @param userIds
	 * @return
	 */
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

	/**
	 * 查找转移出去的人员
	 * 
	 * @return
	 */
	public List<OrgUser> findTransferAskOutUser() {
		UnitQuery unitQuery = DataPermissionUtil.getUnitQueryDouble(null);

		List<Condition> conditions = new ArrayList<>();
		if (unitQuery.getAgencyId() != null) {
			conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_ORIGIN_AGENCY_ID, QueryType.EQUAL, unitQuery.getAgencyId()));
		}

		if (unitQuery.getAgencyIds() != null) {
			conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_ORIGIN_AGENCY_ID, QueryType.IN, unitQuery.getAgencyIds()));
		}

		if (unitQuery.getUnitId() != null) {
			conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_ORIGIN_UNIT_ID, QueryType.EQUAL, unitQuery.getUnitId()));
		}

		if (unitQuery.getUnitId() != null) {
			conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_ORIGIN_UNIT_ID, QueryType.IN, unitQuery.getUnitIds()));
		}

		if (unitQuery.getAssessTeamId() != null) {
			conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_ORIGIN_TEAM_ID, QueryType.EQUAL, unitQuery.getAssessTeamId()));
		}

		conditions.add(new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.IN, outTransferAsk));
		return searchAll(conditions);
	}

	/**
	 * 查找转移进来的人员
	 * 
	 * @return
	 */
	public List<OrgUser> findTransferAskInUser() {

		HfUserSession session = HfUserSession.getCurrentUserSession();
		if (session.isAdminRoleLevel()) {
			return searchAll(new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK));
		}

		List<String> agencyIds = DataPermissionUtil.getOwnAgencyId();

		if (agencyIds != null) {
			int size = agencyIds.size();
			if (size == 1) {
				return searchAll(new Condition[] { new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK),
						new Condition(OrgUser.COLUMN_TRANSFER_AGENCY_ID, QueryType.EQUAL, agencyIds.get(0)) });
			} else if (size > 1) {
				return searchAll(new Condition[] { new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK),
						new Condition(OrgUser.COLUMN_TRANSFER_AGENCY_ID, QueryType.IN, agencyIds) });
			}
		}

		return new ArrayList<>();
	}

	/**
	 * 获取转移申请数
	 * 
	 * @return
	 */
	public int getCountOfTransferAskIn() {

		HfUserSession session = HfUserSession.getCurrentUserSession();
		if (session.isAdminRoleLevel()) {
			return searchAllCount(new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK));
		}

		List<String> agencyIds = DataPermissionUtil.getOwnAgencyId();

		if (agencyIds != null) {
			int size = agencyIds.size();
			if (size == 1) {
				return searchAllCount(new Condition[] { new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK),
						new Condition(OrgUser.COLUMN_TRANSFER_AGENCY_ID, QueryType.EQUAL, agencyIds.get(0)) });
			} else if (size > 1) {
				return searchAllCount(new Condition[] { new Condition(OrgUser.COLUMN_TRANSFER_STATUS, QueryType.EQUAL, OrgUser.TRANSFER_STATUS_ASK),
						new Condition(OrgUser.COLUMN_TRANSFER_AGENCY_ID, QueryType.IN, agencyIds) });
			}
		}

		return 0;
	}

	/**
	 * 删除人员转移申请
	 * 
	 * @param userIds
	 * @return
	 */
	public int removeTransferAskUser(String[] userIds) {
		return orgUserMapper.removeTransferAsk(userIds);
	}

	private static final List<ReadColumn> userImportColumns = DefaultReadColumn.createReadColumn(ExcelUser.class);

	/**
	 * 导入人员
	 * 
	 * @param users
	 * @param unitId
	 * @return
	 */
	@SuppressWarnings("resource")
	@Transactional
	public ExcelImportResult importUser(InputStream excelInputStream, String unitId) {
		//TODO 导入科室是否加入该功能，缺省为选择科室
		
		if (!DataPermissionUtil.ownUnit(unitId)) {
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

		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(excelInputStream);
		} catch (IOException e1) {
			throw new BusinessException("导入异常");
		}

		ExcelReader<ExcelUser> reader = new ExcelReader<>(ExcelUser.class, userImportColumns, new DefaultSheet(workbook.getSheetAt(0)), 2);
		List<ExcelImportError> errors = new ArrayList<>();

		int i = 0;

		while (reader.hasNext()) {
			i++;
			if (i > 500) {
				break;
			}

			ExcelUser excelUser = null;
			try {
				excelUser = reader.readRow();
			} catch (ExcelReadException e) {
				errors.add(new ExcelImportError(i, e));
				continue;
			}

			OrgUser user = new OrgUser();
			SimpleBeanCopyUtil.simpleCopy(excelUser, user);

			Integer idcardType = user.getIdentificationType();
			if(idcardType == null) {
				errors.add(new ExcelImportError(i, "缺少身份证类型"));
				continue;
			}
			
			String idcard = user.getIdentification();
			if (idcard != null && idcard.length() != 0) {
				if (!isUniqueIdcard(idcard)) {
					errors.add(new ExcelImportError(i, "身份证号码不能重复"));
					continue;
				}
			} else {
				errors.add(new ExcelImportError(i, "缺少身份证号码"));
				continue;
			}

			if (StringUtil.isEmpty(user.getName())) {
				errors.add(new ExcelImportError(i, "缺少姓名"));
				continue;
			}

			user.setAssessRole(HfUserSession.DEFAULT_ROLE_SELF_ID);
			user.setIsAssessor(0);
			user.setOrgAgencyId(agencyId);
			user.setOrgAssessTeamId(assessTeamId);
			user.setOrgUnitId(unitId);

			String userId = UUIDUtil.createUUID();
			user.setId(userId);
			try {
				sysUserService.createOrgUserAndCount(user.getAccount(), user);
			} catch (BusinessException e) {
				errors.add(new ExcelImportError(i, e.getMessage()));
				continue;
			} catch (Exception e) {
				errors.add(new ExcelImportError(i, "保存失败"));
				continue;
			}
		}

		return new ExcelImportResult(i, errors);
	}

	// TODO 登记打印功能
}
