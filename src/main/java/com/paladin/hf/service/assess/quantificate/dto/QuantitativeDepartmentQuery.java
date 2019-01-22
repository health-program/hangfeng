package com.paladin.hf.service.assess.quantificate.dto;

import java.util.Date;
import java.util.List;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;


public class QuantitativeDepartmentQuery extends OffsetPage{
	
	private String name;
	private String unitId;
	private String unitName;
	private String jobRank;
	private Integer isAssessor;
	private Date startRecordCreateTime;
	private Date endRecordCreateTime;
	private String assessCycleId;
	private String assessCycleName;
	private Integer isAssessed;
	
	private List<String> unitIds;

	@QueryCondition(type = QueryType.LIKE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@QueryCondition(type = QueryType.EQUAL, name = "orgUnitId")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public String getJobRank() {
		return jobRank;
	}

	public void setJobRank(String jobRank) {
		this.jobRank = jobRank;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public Integer getIsAssessor() {
		return isAssessor;
	}

	public void setIsAssessor(Integer isAssessor) {
		this.isAssessor = isAssessor;
	}

	@QueryCondition(type = QueryType.GREAT_EQUAL, name = "recordCreateTime")
	public Date getStartRecordCreateTime() {
		return startRecordCreateTime;
	}

	public void setStartRecordCreateTime(Date startRecordCreateTime) {
		this.startRecordCreateTime = startRecordCreateTime;
	}

	@QueryCondition(type = QueryType.LESS_EQUAL, name = "recordCreateTime")
	public Date getEndRecordCreateTime() {
		return endRecordCreateTime;
	}

	public void setEndRecordCreateTime(Date endRecordCreateTime) {
		this.endRecordCreateTime = endRecordCreateTime;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public String getAssessCycleId() {
		return assessCycleId;
	}

	public void setAssessCycleId(String assessCycleId) {
		this.assessCycleId = assessCycleId;
	}

	public Integer getIsAssessed() {
		return isAssessed;
	}

	public void setIsAssessed(Integer isAssessed) {
		this.isAssessed = isAssessed;
	}

	public List<String> getUnitIds() {
		return unitIds;
	}

	public void setUnitIds(List<String> unitIds) {
		this.unitIds = unitIds;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getAssessCycleName() {
		return assessCycleName;
	}

	public void setAssessCycleName(String assessCycleName) {
		this.assessCycleName = assessCycleName;
	}

}
