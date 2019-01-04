package com.paladin.hf.service.statistics.vo;

import com.paladin.hf.model.assess.cycle.PersonCycAssess;

/**   
 * @author 黄伟华
 * @version 2018年2月7日 下午3:16:00 
 */
public class AppraisalSummaryVO extends PersonCycAssess{
    
    private String cycleId;
    private String unitId;
    private Integer kpdc;
    private Integer count;
    private String jobRank;
    private String levelCode;
    
    
    public String getLevelCode()
    {
        return levelCode;
    }
    public void setLevelCode(String levelCode)
    {
        this.levelCode = levelCode;
    }
    public String getJobRank()
    {
        return jobRank;
    }
    public void setJobRank(String jobRank)
    {
        this.jobRank = jobRank;
    }
    public String getCycleId()
    {
        return cycleId;
    }
    public void setCycleId(String cycleId)
    {
        this.cycleId = cycleId;
    }
    public String getUnitId()
    {
        return unitId;
    }
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }
    public Integer getKpdc()
    {
        return kpdc;
    }
    public void setKpdc(Integer kpdc)
    {
        this.kpdc = kpdc;
    }
    public Integer getCount()
    {
        return count;
    }
    public void setCount(Integer count)
    {
        this.count = count;
    }
   
}
