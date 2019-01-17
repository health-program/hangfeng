package com.paladin.hf.service.assess.cycle.vo;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;


public class AssessCycleSimpleVO {

	private String id;

	private String userId;
	
	private String userName;

	private String assessCycleId;
	
	private String assessCycName;

	private Integer selfAssGrade;

	private Integer departGrade;

	private Integer unitAssGrade;

	private Integer confirmedResult;

	private Integer operateState;

	private String unitId;

	private String agencyId;


	/*
	 * 扩展显示单位名称
	 */

	public Integer getSelfAssGrade() {
		return selfAssGrade;
	}

	public void setSelfAssGrade(Integer selfAssGrade) {
		this.selfAssGrade = selfAssGrade;
	}

	public Integer getDepartGrade() {
		return departGrade;
	}

	public void setDepartGrade(Integer departGrade) {
		this.departGrade = departGrade;
	}

	public Integer getUnitAssGrade() {
		return unitAssGrade;
	}

	public void setUnitAssGrade(Integer unitAssGrade) {
		this.unitAssGrade = unitAssGrade;
	}

	public Integer getConfirmedResult() {
		return confirmedResult;
	}

	public void setConfirmedResult(Integer confirmedResult) {
		this.confirmedResult = confirmedResult;
	}

	public Integer getOperateState() {
		return operateState;
	}

	public void setOperateState(Integer operateState) {
		this.operateState = operateState;
	}

	public String getDepartmentName() {
		try {
			return UnitContainer.getUnitName(unitId);
		} catch (BusinessException e) {
			return "";
		}
	}

	public String getAgencyName() {
		try {
			return UnitContainer.getUnitName(agencyId);
		} catch (BusinessException e) {
			return "";
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAssessCycleId() {
		return assessCycleId;
	}

	public void setAssessCycleId(String assessCycleId) {
		this.assessCycleId = assessCycleId;
	}

	public String getAssessCycName() {
		return assessCycName;
	}

	public void setAssessCycName(String assessCycName) {
		this.assessCycName = assessCycName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
