package com.paladin.hf.service.statistics.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;



/**   
 * @author 黄伟华
 * @version 2018年2月28日 上午9:36:48 
 */
public class PersoncycassessQueryDTO extends OffsetPage
{
    private String unitId;
    private String assessCycleId;
    private String assGrade;
    
    
    public String getAssGrade()
    {
        return assGrade;
    }

    public void setAssGrade(String assGrade)
    {
        this.assGrade = assGrade;
    }

    @QueryCondition(type = QueryType.EQUAL)
    public String getUnitId()
    {
        return unitId;
    }
    
    @QueryCondition(type = QueryType.EQUAL)
    public String getAssessCycleId()
    {
        return assessCycleId;
    }
    public void setAssessCycleId(String assessCycleId)
    {
        this.assessCycleId = assessCycleId;
    }
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }
   
    
    
}
