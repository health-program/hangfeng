package com.paladin.hf.service.assess.cycle;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.mapper.assess.cycle.PersonCycAssessMapper;
import com.paladin.hf.model.assess.cycle.PersonCycAssess;
import com.paladin.hf.service.assess.cycle.dto.PersonCycAssessExt;
import com.paladin.hf.service.assess.cycle.pojo.PersonCycAssessQuery;

/**
 * @author jisanjie
 * @version 2018年1月22日 下午4:41:13
 */
@Service
public class PersonCycAssessService extends ServiceSupport<PersonCycAssess> {

	@Autowired
	private PersonCycAssessMapper perCycAssMapper;
	
	/*
	 * 加载周期考评自评
	 * 首页数据及条件查询
	 */
	public Page<PersonCycAssessExt> searchSelfCycAssessPage(PersonCycAssessQuery perCycAssQuery) {
		Page<PersonCycAssessExt> page = PageHelper.offsetPage(perCycAssQuery.getOffset(), perCycAssQuery.getLimit());// 分页
		HfUserSession session = HfUserSession.getCurrentUserSession();
		String orgUserId = session.getUserId();// 获取当前用户的id
		perCycAssQuery.setOrgUserId(orgUserId);
		perCycAssMapper.associationQueryAll(perCycAssQuery);// 条件查询
		return page;
	}
	
	/*
	 * 医德医风档案管理-->查看-->考评情况
	 */
	public Page<PersonCycAssessExt> viewPersonAssessSituation(PersonCycAssessQuery perCycAssQuery) {
	        Page<PersonCycAssessExt> page = PageHelper.offsetPage(perCycAssQuery.getOffset(), perCycAssQuery.getLimit());// 分页
	        perCycAssMapper.viewAssessSituation(perCycAssQuery);// 条件查询
	        return page;
	    }
	
	/*
	 * 根据id删除个人周期考评数据
	 */
	public int deletePerCycAss(String id) {
		return perCycAssMapper.deleteByPrimaryKey(id);
	}
	
	/*
	 * 被评人确认考评结果
	 */
	public int updateOperateState(PersonCycAssess personCycAssess, String assesssedConfirmed) {
		personCycAssess.setOperateState(assesssedConfirmed);
		return updateSelective(personCycAssess);
	}

	/*
	 * 根据操作状态筛选周期考评-科室首页数据及
	 * 条件查询
	 */
	public Page<PersonCycAssessExt> searchDepartmentCycAssessPage(PersonCycAssessQuery perCycAssQuery) {
		Page<PersonCycAssessExt> page = PageHelper.offsetPage(perCycAssQuery.getOffset(), perCycAssQuery.getLimit());// 分页
		String unitId = perCycAssQuery.getUnitId();        
        UnitQuery query =  getUnitQueryDouble(unitId);
        if(query == null) {
        	page.setTotal(0L);
        	return page;
        }
		String[] queryState = perCycAssQuery.getOperateStates();// queryState 考评状态传入值
		perCycAssMapper.screenAllByOperate(queryState, perCycAssQuery, query);
		return page;
	}
	
	/*
     * 根据操作状态筛选周期考评-机构首页数据及
     * 条件查询
     */
    public Page<PersonCycAssessExt> organizationScreenPersonAssess(PersonCycAssessQuery perCycAssQuery)
    {   	
        Page<PersonCycAssessExt> page = PageHelper.offsetPage(perCycAssQuery.getOffset(), perCycAssQuery.getLimit());//分页   	
        String unitId = perCycAssQuery.getUnitId();        
        UnitQuery query =  getUnitQueryDouble(unitId);
        if(query == null) {
        	page.setTotal(0L);
        	return page;
        }
        String[] queryState= perCycAssQuery.getOperateStates();//queryState 考评状态传入值
        perCycAssMapper.screenAllByOperate(queryState, perCycAssQuery, query);
        return page;
    }
    
    
    /*
     * 根据考评周期id查询对应考评信息
     * 来判断该周期内用户是否已有操作记录
     */
    public PersonCycAssess searchBaseInfo(String assessCycleId, String userId)
    {
        return perCycAssMapper.searchBaseInfo(assessCycleId,userId);
    }
    
    /**
	 * 获取扩展的考评情况
	 * @param userId
	 * @param cycleId
	 * @return
	 */
	public PersonCycAssessExt getCycleAssessExtend(String id) {		
		return perCycAssMapper.getCycleAssessExt(id);
	}
	
	
	/*
	 * 被驳回
	 */
    public int resetOperateState(String id, String backword) {
          PersonCycAssess personCycAssess = new PersonCycAssess();
          personCycAssess.setId(id);
          personCycAssess.setOperateState(backword);
          return updateSelective(personCycAssess);
    }

    public Page<PersonCycAssessExt> noAssessment(PersonCycAssessQuery personCycAssessQuery, boolean isAgency) {     
          Page<PersonCycAssessExt> page = PageHelper.offsetPage(personCycAssessQuery.getOffset(), personCycAssessQuery.getLimit());// 分页
          UserSession session = UserSession.getCurrentUserSession();
          if(session.isAdmin()){//当前用户为系统管理员
                Map<String, Object> map = new HashMap<>();
                map.put("assessCycleId", personCycAssessQuery.getAssessCycleId());
                map.put("isAgency", isAgency?1:0);
                perCycAssMapper.noAssessment(map);
          } else {
                String unitId = personCycAssessQuery.getUnitId();        
                UnitQuery query =  getUnitQueryDouble(unitId);
                if(query == null) {
                    page.setTotal(0L);
                    return page;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("assessCycleId", personCycAssessQuery.getAssessCycleId());
                map.put("unitQuery",query);
                map.put("isAgency", isAgency?1:0);
                perCycAssMapper.noAssessment(map);
          }
          return page;
    }
    
}
