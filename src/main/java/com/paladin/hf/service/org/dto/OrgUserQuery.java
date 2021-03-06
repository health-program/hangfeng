package com.paladin.hf.service.org.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

import java.util.Date;
import java.util.List;

public class OrgUserQuery extends OffsetPage {

	private String name;
	private String identification;
	private Integer isAssessor;
	private Date startRecordCreateTime;
	private Date endRecordCreateTime;
	private String orgUnitId;

	private String assessTeamId;
	private String unitId;
	private String agencyId;
	private List<String> unitIds;
	private List<String> agencyIds;

	private Integer userProperty;
	private Integer jobRank;

	private List<Integer> transferStatus;

	@QueryCondition(type = QueryType.EQUAL)
	public Integer getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(Integer userProperty) {
		this.userProperty = userProperty;
	}

	@QueryCondition(type = QueryType.LIKE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@QueryCondition(type = QueryType.EQUAL, name = "orgUnitId")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@QueryCondition(type = QueryType.EQUAL, name = "orgAgencyId")
	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	@QueryCondition(type = QueryType.IN, name = "orgUnitId")
	public List<String> getUnitIds() {
		return unitIds;
	}

	public void setUnitIds(List<String> unitIds) {
		this.unitIds = unitIds;
	}

	@QueryCondition(type = QueryType.IN, name = "orgAgencyId")
	public List<String> getAgencyIds() {
		return agencyIds;
	}

	public void setAgencyIds(List<String> agencyIds) {
		this.agencyIds = agencyIds;
	}

	@QueryCondition(type = QueryType.EQUAL, name = "orgAssessTeamId")
	public String getAssessTeamId() {
		return assessTeamId;
	}

	public void setAssessTeamId(String assessTeamId) {
		this.assessTeamId = assessTeamId;
	}

	public String getOrgUnitId() {
		return orgUnitId;
	}

	public void setOrgUnitId(String orgUnitId) {
		this.orgUnitId = orgUnitId;
	}

	@QueryCondition(type = QueryType.IN, name = "transferStatus")
	public List<Integer> getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(List<Integer> transferStatus) {
		this.transferStatus = transferStatus;
	}

	@QueryCondition(type = QueryType.LIKE)
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public Integer getJobRank() {
		return jobRank;
	}

	public void setJobRank(Integer jobRank) {
		this.jobRank = jobRank;
	}
}
