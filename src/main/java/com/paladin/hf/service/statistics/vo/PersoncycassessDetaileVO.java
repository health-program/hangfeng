package com.paladin.hf.service.statistics.vo;

import com.paladin.framework.core.exception.BusinessException;



/**   
 * @author 黄伟华
 * @version 2018年2月28日 上午9:21:27 
 */
public class PersoncycassessDetaileVO 
{
    private String id;
    private String unitId;
    private String agencyId;
    private String name;
    private String orgUserId;
    private String assesscycleId;
    private String unitassGrade;
    private String operateState;
    
    public String getOrgUserId()
    {
        return orgUserId;
    }
    public void setOrgUserId(String orgUserId)
    {
        this.orgUserId = orgUserId;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getAssesscycleId()
    {
        return assesscycleId;
    }
    public void setAssesscycleId(String assesscycleId)
    {
        this.assesscycleId = assesscycleId;
    }
    public String getUnitId()
    {
        return unitId;
    }
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }
    public String getAgencyId()
    {
        return agencyId;
    }
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getUnitassGrade()
    {
        return unitassGrade;
    }
    public void setUnitassGrade(String unitassGrade)
    {
        this.unitassGrade = unitassGrade;
    }
    public String getOperateState()
    {
        return operateState;
    }
    public void setOperateState(String operateState)
    {
        this.operateState = operateState;
    }
    
    public String getUnitName() {
        try {
            return UnitConatiner.getUnitName(unitId);
		}catch(BusinessException e) {
			return "";			
		}
    }
    
    public String getUnitRootName() {
        try {
            return UnitConatiner.getRootUnitName(unitId);
		}catch(BusinessException e) {
			return "";			
		}
    }
}
