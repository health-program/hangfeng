package com.paladin.hf.service.assess.cycle.vo;

import java.util.Date;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;


public class CycleAssessDetailVO {

	private String id;

	private String orgUserId;

	private String assessCycleId;

	private String workSummary;

	private String selfAssOpinion;

	private Integer selfAssGrade;

	private Date selfAssTime;

	private String departOpinion;

	private Integer departGrade;

	private Date departAssTime;

	private String unitGroupOpinion;

	private Integer unitAssGrade;

	private Date unitAssTime;

	private String confirmedResult;

	private Date confirmedTime;

	private String remarks;

	private Integer operateState;

	private String assessedSign;

	private String depAssessorSign;

	private String unitAssessorSign;

	private String assessedConfirmSign;

	private String unitId;

	private String agencyId;

	private String assessCycName;

	private String userName;

	private Integer sex;

	private Date birthday;

	private Integer jobDuties;

	private Integer userProperty;
	
	private Integer partisan;
	
	private Integer oeducation;
	
	private Date comeUnitTime;
	
	private Double baseScore;
	
	private Double addScore;
	
	private Double reduceScore;
	
	private Integer isVeto;
	
	private String rejectReason;

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

	public String getConfirmedResult() {
		return confirmedResult;
	}

	public void setConfirmedResult(String confirmedResult) {
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

	public String getOrgUserId() {
		return orgUserId;
	}

	public void setOrgUserId(String orgUserId) {
		this.orgUserId = orgUserId;
	}

	public String getAssessCycleId() {
		return assessCycleId;
	}

	public void setAssessCycleId(String assessCycleId) {
		this.assessCycleId = assessCycleId;
	}

	public String getWorkSummary() {
		return workSummary;
	}

	public void setWorkSummary(String workSummary) {
		this.workSummary = workSummary;
	}

	public String getSelfAssOpinion() {
		return selfAssOpinion;
	}

	public void setSelfAssOpinion(String selfAssOpinion) {
		this.selfAssOpinion = selfAssOpinion;
	}

	public Date getSelfAssTime() {
		return selfAssTime;
	}

	public void setSelfAssTime(Date selfAssTime) {
		this.selfAssTime = selfAssTime;
	}

	public String getDepartOpinion() {
		return departOpinion;
	}

	public void setDepartOpinion(String departOpinion) {
		this.departOpinion = departOpinion;
	}

	public Date getDepartAssTime() {
		return departAssTime;
	}

	public void setDepartAssTime(Date departAssTime) {
		this.departAssTime = departAssTime;
	}

	public String getUnitGroupOpinion() {
		return unitGroupOpinion;
	}

	public void setUnitGroupOpinion(String unitGroupOpinion) {
		this.unitGroupOpinion = unitGroupOpinion;
	}

	public Date getUnitAssTime() {
		return unitAssTime;
	}

	public void setUnitAssTime(Date unitAssTime) {
		this.unitAssTime = unitAssTime;
	}

	public Date getConfirmedTime() {
		return confirmedTime;
	}

	public void setConfirmedTime(Date confirmedTime) {
		this.confirmedTime = confirmedTime;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAssessedSign() {
		return assessedSign;
	}

	public void setAssessedSign(String assessedSign) {
		this.assessedSign = assessedSign;
	}

	public String getDepAssessorSign() {
		return depAssessorSign;
	}

	public void setDepAssessorSign(String depAssessorSign) {
		this.depAssessorSign = depAssessorSign;
	}

	public String getUnitAssessorSign() {
		return unitAssessorSign;
	}

	public void setUnitAssessorSign(String unitAssessorSign) {
		this.unitAssessorSign = unitAssessorSign;
	}

	public String getAssessedConfirmSign() {
		return assessedConfirmSign;
	}

	public void setAssessedConfirmSign(String assessedConfirmSign) {
		this.assessedConfirmSign = assessedConfirmSign;
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

	public String getAssessCycName() {
		return assessCycName;
	}

	public void setAssessCycName(String assessCycName) {
		this.assessCycName = assessCycName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getJobDuties() {
		return jobDuties;
	}

	public void setJobDuties(Integer jobDuties) {
		this.jobDuties = jobDuties;
	}

	public Integer getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(Integer userProperty) {
		this.userProperty = userProperty;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Double getBaseScore() {
		return baseScore;
	}

	public void setBaseScore(Double baseScore) {
		this.baseScore = baseScore;
	}

	public Double getAddScore() {
		return addScore;
	}

	public void setAddScore(Double addScore) {
		this.addScore = addScore;
	}

	public Double getReduceScore() {
		return reduceScore;
	}

	public void setReduceScore(Double reduceScore) {
		this.reduceScore = reduceScore;
	}

	public Integer getIsVeto() {
		return isVeto;
	}

	public void setIsVeto(Integer isVeto) {
		this.isVeto = isVeto;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Integer getPartisan() {
		return partisan;
	}

	public void setPartisan(Integer partisan) {
		this.partisan = partisan;
	}

	public Integer getOeducation() {
		return oeducation;
	}

	public void setOeducation(Integer oeducation) {
		this.oeducation = oeducation;
	}

	public Date getComeUnitTime() {
		return comeUnitTime;
	}

	public void setComeUnitTime(Date comeUnitTime) {
		this.comeUnitTime = comeUnitTime;
	}
}
