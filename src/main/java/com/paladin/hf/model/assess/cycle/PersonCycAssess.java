package com.paladin.hf.model.assess.cycle;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.paladin.framework.common.BaseModel;

/**
 * 
 * 个人周期考评实体类
 * 
 * @author jisanjie
 * @version [版本号, 2018年1月22日]
 */

public class PersonCycAssess extends BaseModel {

	/** 被退回 */
	public static final int STATUS_BACKWORD = -1;
	/** 被评人暂存 */
	public static final int STATUS_ASSESSED_TEMPORARY = 0;
	/** 被评人提交 */
	public static final int STATUS_ASSESSED_SUBMIT = 1;
	/** 科室暂存 */
	public static final int STATUS_DEPART_TEMPORARY = 2;
	/** 科室提交 */
	public static final int STATUS_DEPART_SUBMIT = 3;
	/** 考评小组暂存 */
	public static final int STATUS_UNIT_GROUP_TEMPORARY = 4;
	/** 考评小组提交 */
	public static final int STATUS_UNIT_GROUP_SUBMIT = 5;
	/** 被考评人已确认 */
	public static final int STATUS_ASSESSSED_CONFIRMED = 6;

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
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

	private Integer confirmedResult;

	private Date confirmedTime;

	private String remarks;

	private Integer operateState;

	private String assessedSign;

	private String depAssessorSign;

	private String unitAssessorSign;

	private String assessedConfirmSign;
	
	private String rejectReason;

	private String unitId;

	private String agencyId;

	private String assessTeamId;

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

	public Integer getSelfAssGrade() {
		return selfAssGrade;
	}

	public void setSelfAssGrade(Integer selfAssGrade) {
		this.selfAssGrade = selfAssGrade;
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

	public Integer getDepartGrade() {
		return departGrade;
	}

	public void setDepartGrade(Integer departGrade) {
		this.departGrade = departGrade;
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

	public Integer getUnitAssGrade() {
		return unitAssGrade;
	}

	public void setUnitAssGrade(Integer unitAssGrade) {
		this.unitAssGrade = unitAssGrade;
	}

	public Date getUnitAssTime() {
		return unitAssTime;
	}

	public void setUnitAssTime(Date unitAssTime) {
		this.unitAssTime = unitAssTime;
	}

	public Integer getConfirmedResult() {
		return confirmedResult;
	}

	public void setConfirmedResult(Integer confirmedResult) {
		this.confirmedResult = confirmedResult;
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

	public Integer getOperateState() {
		return operateState;
	}

	public void setOperateState(Integer operateState) {
		this.operateState = operateState;
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

	public String getAssessTeamId() {
		return assessTeamId;
	}

	public void setAssessTeamId(String assessTeamId) {
		this.assessTeamId = assessTeamId;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

}