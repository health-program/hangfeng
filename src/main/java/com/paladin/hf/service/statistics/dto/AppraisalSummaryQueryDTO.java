package com.paladin.hf.service.statistics.dto;

import java.util.Date;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;


/**
 * @author 黄伟华
 * @version 2018年2月7日 下午3:22:21
 */
public class AppraisalSummaryQueryDTO extends OffsetPage{
    private String cycleId;
    private String unitId;
    private String assessCycleId;
    private String selfAssGrade;
	private String departAssGrade;
	private String unitAssGrade;
    private Date startRecordCreateTime;
    private String jobRank;
    private Date endRecordCreateTime;
    private String name;
    
    @QueryCondition(type = QueryType.LIKE)
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @QueryCondition(type = QueryType.EQUAL)
    public String getJobRank()
    {
        return jobRank;
    }

    public void setJobRank(String jobRank)
    {
        this.jobRank = jobRank;
    }

    @QueryCondition(type = QueryType.EQUAL)
    public String getUnitAssGrade()
    {
        return unitAssGrade;
    }

    public void setUnitAssGrade(String unitAssGrade)
    {
        this.unitAssGrade = unitAssGrade;
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

    @QueryCondition(type = QueryType.EQUAL)
    public String getCycleId()
    {
        return cycleId;
    }

    public void setCycleId(String cycleId)
    {
        this.cycleId = cycleId;
    }

    @QueryCondition(type = QueryType.EQUAL)
    public String getUnitId()
    {
        return unitId;
    }

    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }
    
    @QueryCondition(type = QueryType.GREAT_EQUAL, name = "cycleStartTime")
    public Date getStartRecordCreateTime()
    {
        return startRecordCreateTime;
    }
    
    public void setStartRecordCreateTime(Date startRecordCreateTime)
    {
        this.startRecordCreateTime = startRecordCreateTime;
    }
    
    @QueryCondition(type = QueryType.GREAT_EQUAL, name = "cycleEndTime")
    public Date getEndRecordCreateTime()
    {
        return endRecordCreateTime;
    }
    
    public void setEndRecordCreateTime(Date endRecordCreateTime)
    {
        this.endRecordCreateTime = endRecordCreateTime;
    }

	public String getSelfAssGrade() {
		return selfAssGrade;
	}

	public void setSelfAssGrade(String selfAssGrade) {
		this.selfAssGrade = selfAssGrade;
	}

	public String getDepartAssGrade() {
		return departAssGrade;
	}

	public void setDepartAssGrade(String departAssGrade) {
		this.departAssGrade = departAssGrade;
	}
    

}
