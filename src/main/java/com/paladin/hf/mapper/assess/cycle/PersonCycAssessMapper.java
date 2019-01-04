package com.paladin.hf.mapper.assess.cycle;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.model.assess.cycle.PersonCycAssess;
import com.paladin.hf.service.assess.cycle.dto.PersonCycAssessExt;
import com.paladin.hf.service.assess.cycle.pojo.PersonCycAssessQuery;



public interface PersonCycAssessMapper extends CustomMapper<PersonCycAssess> {
    
    /*
     * 加载首页列表数据及条件查询
     */
    List<PersonCycAssessExt> associationQueryAll(PersonCycAssessQuery perCycAssQuery);
    
    
    List<PersonCycAssessExt> viewAssessSituation(PersonCycAssessQuery perCycAssQuery);

    
    /**
     * 
     * 根据操作状态筛选科室周期考评首页数据
     * @param states 操作状态数组
     * @param perCycAssQuery 查询参数所在实体类
     * @return PersonCycAssessExt
     * 
     */
    List<PersonCycAssessExt> screenAllByOperate(@Param("states") String[] queryState,@Param("params") PersonCycAssessQuery perCycAssQuery, @Param("unitParam") UnitQuery query);

	PersonCycAssessExt getCycleAssessExt(String id);

    PersonCycAssess searchBaseInfo(@Param("assessCycleId") String assessCycleId, @Param("userId")  String userId);
    

    //未考评按钮
    List<PersonCycAssessExt> noAssessment(Map<String, Object> map);


    PersonCycAssessExt getAssessRegistrationForm(PersonCycAssessQuery perCycAssQuery);


    List<PersonCycAssessExt> findThisYearAssessSituationList(@Param("userId") String orgUserId,  @Param("start")  Date startTime,@Param("end") Date endTime);




    



    
    
}