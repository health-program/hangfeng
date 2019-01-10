package com.paladin.hf.service.org.dto;

import java.util.Date;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;
import com.paladin.hf.model.org.OrgUser;

public class OrgUserClaimQuery extends OffsetPage {

	private String name;
	private String identification;
	private Integer isAssessor;
	private Date startRecordCreateTime;
	private Date endRecordCreateTime;
	private String orgUnitId;
	private String userProperty;

	private String assessTeamId;
	private String unitId;
	private String agencyId;

	@QueryCondition(type = QueryType.EQUAL)
	public String getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(String userProperty) {
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

	@QueryCondition(type = QueryType.LIKE)
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public Integer getIsDelete() {
		return OrgUser.USER_STATUS_LEAVE;
	}
}
