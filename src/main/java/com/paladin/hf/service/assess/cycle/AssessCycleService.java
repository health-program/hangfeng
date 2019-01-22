package com.paladin.hf.service.assess.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.framework.common.Condition;
import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.StringUtil;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.mapper.assess.cycle.AssessCycleMapper;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.model.assess.quantificate.AssessCycleTemplate;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.assess.cycle.dto.AssessCycleSelectQuery;
import com.paladin.hf.service.assess.quantificate.AssessCycleTemplateService;
import com.paladin.hf.service.org.OrgUserService;


/**
 * @author jisanjie
 * @version 2018年1月11日 下午2:38:11
 */

@Service
public class AssessCycleService extends ServiceSupport<AssessCycle> {

	@Autowired
	private AssessCycleMapper assessCycleMapper;

	@Autowired
	private AssessCycleTemplateService assessCycleTemplateService;

	@Autowired
	private OrgUserService orgUserService;

	public boolean startAssessCycle(String id) {
		if(assessCycleMapper.countByAssessCycleId(id) == 0) {
			throw new BusinessException("周期考核模板还未配置完成，不能启用");
		}
		return assessCycleMapper.startAssessCycle(id) >0;
	}

	public int stopAssessCycle(String id) {
		return assessCycleMapper.stopAssessCycle(id);
	}

	public int archiveAssessCycle(String id) {
		return assessCycleMapper.archiveAssessCycle(id);
	}

	public int removeAssessCycle(String id) {
		return assessCycleMapper.removeAssessCycle(id);
	}

	/**
	 * 配置
	 * 
	 * @param cycleId
	 * @param templateId
	 * @param unitIds
	 * @return
	 */
	@Transactional
	public int configTemplate(String cycleId, String templateId, String[] unitIds) {
		int effect = 0;

		assessCycleTemplateService.removeByCycle(cycleId);

		for (String unitId : unitIds) {
			AssessCycleTemplate assessCycleTemplate = new AssessCycleTemplate();
			assessCycleTemplate.setCycleId(cycleId);
			assessCycleTemplate.setTemplateId(templateId);
			assessCycleTemplate.setUnitId(unitId);
			effect += assessCycleTemplateService.save(assessCycleTemplate);
		}
		return effect;
	}

	/**
	 * 获取自己第一个考核周期
	 * 
	 * @return
	 */
	public AssessCycle getSelfFirstAssessCycle() {
	    HfUserSession session = HfUserSession.getCurrentUserSession();
		return getUserFirstAssessCycle(session.getUserId());
	}

	/**
	 * 获取用户第一个考核周期
	 * 
	 * @param userId
	 * @return
	 */
	public AssessCycle getUserFirstAssessCycle(String userId) {
		if(StringUtil.isEmpty(userId)) {
			return null;
		}	
		return getUserFirstAssessCycle(orgUserService.get(userId));
	}

	/**
	 * 获取用户第一个考核周期
	 * 
	 * @param user
	 * @return
	 */
	public AssessCycle getUserFirstAssessCycle(OrgUser user) {
		if(user == null) {
			return null;
		}
		String agencyId = user.getOrgAgencyId();
		return assessCycleMapper.getAgencyFirstAssessCycle(agencyId);
	}

	/**
	 * 获取机构第一个考核周期
	 * 
	 * @param user
	 * @return
	 */
	public AssessCycle getUnitFirstAssessCycle(String unitId) {
		if(StringUtil.isEmpty(unitId)) {
			return null;
		}		
		String agencyId = UnitContainer.getRootUnit(unitId).getId();
		return assessCycleMapper.getAgencyFirstAssessCycle(agencyId);
	}

	/**
	 * 获取拥有的第一个考核周期
	 * 
	 * @param user
	 * @return
	 */
	public AssessCycle getOwnedFirstAssessCycle() {
	    HfUserSession session = HfUserSession.getCurrentUserSession();
		if (session.isAdminRoleLevel()) {
			return assessCycleMapper.getAgencyFirstAssessCycle(null);
		} else {
			String unitId = session.getOwnUnitId();
			return getUnitFirstAssessCycle(unitId);
		}
	}

	/**
	 * 查找自己拥有的考核周期
	 * 
	 * @param offsetPage
	 * @return
	 */
	public PageResult<AssessCycle> findSelfAssessCyclePage(OffsetPage offsetPage) {
	    HfUserSession session = HfUserSession.getCurrentUserSession();
		String agencyId = session.getUserAgencyId();
		return searchPage(new Condition[] {
				new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId),
				new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT)	
		}, offsetPage.getOffset(), offsetPage.getLimit(), true);
	}

	/**
	 * 查找自己拥有的启用的周期
	 * @param offsetPage
	 * @return
	 */
	public PageResult<AssessCycle> findEnabledSelfAssessCyclePage(OffsetPage offsetPage) {
	    HfUserSession session = HfUserSession.getCurrentUserSession();
		String agencyId = session.getUserAgencyId();
		return searchPage(new Condition[] {
				new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId),
				new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.EQUAL, AssessCycle.CYCLE_STATE_START)					
		}, offsetPage.getOffset(), offsetPage.getLimit(), true);
		
	}
	
	
	/**
	 * 查找某用户拥有的考核周期
	 * 
	 * @param offsetPage
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageResult<AssessCycle> findUserAssessCyclePage(OffsetPage offsetPage, String userId) {
		if(StringUtil.isEmpty(userId)) {
			return getEmptyPageResult(offsetPage);
		}
		
		OrgUser user = orgUserService.get(userId);
		if (user == null)
			throw new BusinessException("找不到对应人员档案[ID:" + userId + "]");
		String agencyId = user.getOrgAgencyId();
		return searchPage(new Condition[] {
				new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId),
				new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT)	
		}, offsetPage.getOffset(), offsetPage.getLimit(), true);
	}

	/**
	 * 查找科室拥有的考核周期
	 * 
	 * @param offsetPage
	 * @param unitId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public PageResult<AssessCycle> findUnitAssessCyclePage(OffsetPage offsetPage, String unitId) {		
		if(StringUtil.isEmpty(unitId)) {
			return getEmptyPageResult(offsetPage);
		}	
		String agencyId = UnitContainer.getRootUnit(unitId).getId();
		return searchPage(new Condition[] {
				new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId),
				new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT)	
		}, offsetPage.getOffset(), offsetPage.getLimit(), true);
	}

	/**
	 * 查找自己拥有的
	 * 
	 * @param offsetPage
	 * @return
	 */
	public PageResult<AssessCycle> findOwnedAssessCyclePage(OffsetPage offsetPage, String unitId) {
		
		if(unitId == null || unitId.length() == 0) {			
		    HfUserSession session= HfUserSession.getCurrentUserSession();			
			if(session.isAdminRoleLevel()) {
				return searchPage(new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT), offsetPage.getOffset(), offsetPage.getLimit(), true);
			} else {						
				String agencyId = session.getOwnUnit().getAgency().getId();								
				return searchPage(new Condition[] {
						new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId),
						new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT)	
				}, offsetPage.getOffset(), offsetPage.getLimit(), true);
			}			
		} else {			
			String agencyId = UnitContainer.getRootUnit(unitId).getId();
			return searchPage(new Condition[] {
					new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId),
					new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT)	
			}, offsetPage.getOffset(), offsetPage.getLimit(), true);
		}
	}
	
	/**
	 * 查找可操作的周期
	 * @param query
	 * @param unitId
	 * @return
	 */
	public PageResult<AssessCycle> findAvailableAssessCyclePage(AssessCycleSelectQuery query, String unitId) {
		if(unitId == null || unitId.length() == 0) {			
		    HfUserSession session= HfUserSession.getCurrentUserSession();			
			if(session.isAdminRoleLevel()) {
				return searchPage(new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.EQUAL, AssessCycle.CYCLE_STATE_START), query.getOffset(), query.getLimit(), true);
			} else {						
				String agencyId = session.getOwnUnit().getAgency().getId();								
				return searchPage(new Condition[] {
						new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId),
						new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.EQUAL, AssessCycle.CYCLE_STATE_START)	
				}, query.getOffset(), query.getLimit(), true);
			}			
		} else {			
			String agencyId = UnitContainer.getRootUnit(unitId).getId();
			return searchPage(new Condition[] {
					new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId),
					new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.EQUAL, AssessCycle.CYCLE_STATE_START)	
			}, query.getOffset(), query.getLimit(), true);
		}
	}


}
