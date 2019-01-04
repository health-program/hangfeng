package com.paladin.hf.service.statistics.vo;
/**   
 * @author 黄伟华
 * @version 2018年2月26日 下午4:22:44 
 */
public class PersoncycassessVO
{
    private String id;
    private String agencyId;
    private String unitassgrade;
    private String gradeCount;
    
    public String getGradeCount()
    {
        return gradeCount;
    }
    public void setGradeCount(String gradeCount)
    {
        this.gradeCount = gradeCount;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getAgencyId()
    {
        return agencyId;
    }
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    public String getUnitassgrade()
    {
        return unitassgrade;
    }
    public void setUnitassgrade(String unitassgrade)
    {
        this.unitassgrade = unitassgrade;
    }
    
    
}
