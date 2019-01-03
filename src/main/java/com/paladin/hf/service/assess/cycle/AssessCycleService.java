package com.paladin.hf.service.assess.cycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryType;
import com.paladin.hf.mapper.assess.cycle.AssessCycleMapper;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.model.assess.quantificate.AssessCycleTemplate;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.assess.quantificate.AssessCycleTemplateService;
import com.paladin.hf.service.org.OrgUserService;

import tk.mybatis.mapper.entity.Condition;

/**
 * @author jisanjie
 * @version 2018年1月11日 下午2:38:11
 */

@Service
public class AssessCycleService extends ServiceSupport<AssessCycle> {

	@Autowired
	AssessCycleMapper assessCycleMapper;

	@Autowired
	AssessCycleTemplateService assessCycleTemplateService;

	@Autowired
	OrgUserService orgUserService;

	/**
	 * 保存或更新
	 * 
	 * @param assessCycle
	 * @return
	 */
	public int saveOrUpdateAssessCycle(AssessCycle assessCycle) {
		if (StringUtils.isEmpty(assessCycle.getId())) {
			assessCycle.setCycleState(AssessCycle.CYCLE_STATE_DRAFT);
			return save(assessCycle);
		} else {
			assessCycle.setCycleState(null);
			return updateSelective(assessCycle);
		}
	}

	public int startAssessCycle(String id) {
		return assessCycleMapper.startAssessCycle(id);
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
		UserSession session = UserSession.getCurrentUserSession();
		return getUserFirstAssessCycle(session.getUserId());
	}

	/**
	 * 获取用户第一个考核周期
	 * 
	 * @param userId
	 * @return
	 */
	public AssessCycle getUserFirstAssessCycle(String userId) {
		if(StringUtils.isEmpty(userId)) {
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
		if(StringUtils.isEmpty(unitId)) {
			return null;
		}		
		String agencyId = UnitConatiner.getRootUnit(unitId).getId();
		return assessCycleMapper.getAgencyFirstAssessCycle(agencyId);
	}

	/**
	 * 获取拥有的第一个考核周期
	 * 
	 * @param user
	 * @return
	 */
	public AssessCycle getOwnedFirstAssessCycle() {
		UserSession session = UserSession.getCurrentUserSession();
		if (session.isAdmin()) {
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
	public Page<AssessCycle> findSelfAssessCyclePage(OffsetPage offsetPage) {
		UserSession session = UserSession.getCurrentUserSession();
		String agencyId = session.getUserAgencyId();
		return searchPage(new Condition[] {
				new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId, null),
				new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT, null)	
		}, offsetPage.getOffset(), offsetPage.getLimit(), true);
	}

	/**
	 * 查找自己拥有的启用的周期
	 * @param offsetPage
	 * @return
	 */
	public Page<?> findEnabledSelfAssessCyclePage(OffsetPage offsetPage) {

		UserSession session = UserSession.getCurrentUserSession();
		String agencyId = session.getUserAgencyId();
		return searchPage(new Condition[] {
				new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId, null),
				new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.EQUAL, AssessCycle.CYCLE_STATE_START, null)					
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
	public Page<AssessCycle> findUserAssessCyclePage(OffsetPage offsetPage, String userId) {
		if(StringUtils.isEmpty(userId)) {
			return getEmptyPage(offsetPage);
		}
		
		OrgUser user = orgUserService.get(userId);
		if (user == null)
			throw new BusinessException("找不到对应人员档案[ID:" + userId + "]");
		String agencyId = user.getOrgAgencyId();
		return searchPage(new Condition[] {
				new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId, null),
				new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT, null)	
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
	public Page<AssessCycle> findUnitAssessCyclePage(OffsetPage offsetPage, String unitId) {		
		if(StringUtils.isEmpty(unitId)) {
			return getEmptyPage(offsetPage);
		}	
		String agencyId = UnitConatiner.getRootUnit(unitId).getId();
		return searchPage(new Condition[] {
				new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId, null),
				new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT, null)	
		}, offsetPage.getOffset(), offsetPage.getLimit(), true);
	}

	/**
	 * 查找自己拥有的
	 * 
	 * @param offsetPage
	 * @return
	 */
	public Page<AssessCycle> findOwnedAssessCyclePage(OffsetPage offsetPage, String unitId) {
		
		if(unitId == null || unitId.length() == 0) {			
			UserSession session= UserSession.getCurrentUserSession();			
			if(session.isAdmin()) {
				return searchPage(new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT, null), offsetPage.getOffset(), offsetPage.getLimit(), true);
			} else {						
				String agencyId = session.getOwnUnit().getAgency().getId();								
				return searchPage(new Condition[] {
						new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId, null),
						new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT, null)	
				}, offsetPage.getOffset(), offsetPage.getLimit(), true);
			}			
		} else {			
			String agencyId = UnitConatiner.getRootUnit(unitId).getId();
			return searchPage(new Condition[] {
					new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId, null),
					new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.NOT_EQUAL, AssessCycle.CYCLE_STATE_DRAFT, null)	
			}, offsetPage.getOffset(), offsetPage.getLimit(), true);
		}
	}
	
	/**
	 * 查找可操作的周期
	 * @param query
	 * @param unitId
	 * @return
	 */
	public Page<AssessCycle> findAvailableAssessCyclePage(AssessCycleSelectQuery query, String unitId) {
		if(unitId == null || unitId.length() == 0) {			
			UserSession session= UserSession.getCurrentUserSession();			
			if(session.isAdmin()) {
				return searchPage(new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.EQUAL, AssessCycle.CYCLE_STATE_START, null), query.getOffset(), query.getLimit(), true);
			} else {						
				String agencyId = session.getOwnUnit().getAgency().getId();								
				return searchPage(new Condition[] {
						new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId, null),
						new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.EQUAL, AssessCycle.CYCLE_STATE_START, null)	
				}, query.getOffset(), query.getLimit(), true);
			}			
		} else {			
			String agencyId = UnitConatiner.getRootUnit(unitId).getId();
			return searchPage(new Condition[] {
					new Condition(AssessCycle.COLUMN_UNIT_ID, QueryType.EQUAL, agencyId, null),
					new Condition(AssessCycle.COLUMN_FIELD_CYCLESTATE, QueryType.EQUAL, AssessCycle.CYCLE_STATE_START, null)	
			}, query.getOffset(), query.getLimit(), true);
		}
	}
	
	
	public int cycleCount(String id){
	    return this.assessCycleMapper.cycleCount(id);
	}

      public int selectTemplateIdByAssessCycleId(String id) {
            return assessCycleMapper.selectTemplateIdByAssessCycleId(id);
      }

	

	

}
