package com.paladin.hf.model.assess.cycle;



import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.paladin.framework.common.BaseModel;



/**
 * 
 * 个人周期考评实体类
 * 
 * @author  jisanjie
 * @version  [版本号, 2018年1月22日]
 */

public class PersonCycAssess extends BaseModel  {
    
    /**
     * 被评人暂存
     */
    public static final String ASSESSED_TEMPORARY = "0";
    
    /**
     * 被评人提交
     */
    public static final String ASSESSED_SUBMIT = "1";
    
    /**
     * 科室暂存
     */
    public static final String DEPART_TEMPORARY="2";
    
    /**
     * 科室提交
     */
    public static final String DEPART_SUBMIT="3";
    
    /**
     * 考评小组暂存
     */
    public static final String UNIT_GROUP_TEMPORARY="4";
    
    /**
     * 考评小组提交
     */
    public static final String UNIT_GROUP_SUBMIT="5";
    
    /**
     * 被考评人已确认
     */
    public static final String ASSESSSED_CONFIRMED="6";
    
    /**
     * 被退回
     */
    public static final String BACKWORD="7";
    
    /**
     * 未自评
     */
    public static final String NOT_EVALUATION="8";
    
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;

    private String orgUserId;

    private String assessCycleId;

    private String workSummary;

    private String selfAssOpinion;

    private String selfAssGrade;

    private String selfAssTime;

    private String departOpinion;

    private String departGrade;

    private String departAssTime;

    private String unitGroupOpinion;

    private String unitAssGrade;

    private String unitAssTime;

    private String confirmedResult;

    private String confirmedTime;

    private String remarks;

    private String operateState;

    private String assessedSign;

    private String depAssessorSign;

    private String unitAssessorSign;

    private String assessedConfirmSign;
    
    private String unitId;
    
    private String agencyId;
    
    private String assessTeamId;
    
    @Transient
    private List<String> unitIds;
    
    @Transient
    private String  submitname;
    

  

    public String getSubmitname() {
		return submitname;
	}

	public void setSubmitname(String submitname) {
		this.submitname = submitname;
	}

	public String getOrgUserId()
    {
        return orgUserId;
    }

    public void setOrgUserId(String orgUserId)
    {
        this.orgUserId = orgUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public String getAssessCycleId() {
        return assessCycleId;
    }

    public void setAssessCycleId(String assessCycleId) {
        this.assessCycleId = assessCycleId == null ? null : assessCycleId.trim();
    }

    public String getWorkSummary() {
        return workSummary;
    }

    public void setWorkSummary(String workSummary) {
        this.workSummary = workSummary == null ? null : workSummary.trim();
    }

    public String getSelfAssOpinion() {
        return selfAssOpinion;
    }

    public void setSelfAssOpinion(String selfAssOpinion) {
        this.selfAssOpinion = selfAssOpinion == null ? null : selfAssOpinion.trim();
    }

    public String getSelfAssGrade() {
        return selfAssGrade;
    }

    public void setSelfAssGrade(String selfAssGrade) {
        this.selfAssGrade = selfAssGrade == null ? null : selfAssGrade.trim();
    }


    public String getDepartOpinion() {
        return departOpinion;
    }

    public void setDepartOpinion(String departOpinion) {
        this.departOpinion = departOpinion == null ? null : departOpinion.trim();
    }

    public String getDepartGrade() {
        return departGrade;
    }

    public void setDepartGrade(String departGrade) {
        this.departGrade = departGrade == null ? null : departGrade.trim();
    }


    public String getUnitGroupOpinion() {
        return unitGroupOpinion;
    }

    public void setUnitGroupOpinion(String unitGroupOpinion) {
        this.unitGroupOpinion = unitGroupOpinion == null ? null : unitGroupOpinion.trim();
    }

    public String getUnitAssGrade() {
        return unitAssGrade;
    }

    public void setUnitAssGrade(String unitAssGrade) {
        this.unitAssGrade = unitAssGrade == null ? null : unitAssGrade.trim();
    }


    public String getConfirmedResult() {
        return confirmedResult;
    }

    public void setConfirmedResult(String confirmedResult) {
        this.confirmedResult = confirmedResult == null ? null : confirmedResult.trim();
    }


    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getOperateState() {
        return operateState;
    }

    public void setOperateState(String operateState) {
        this.operateState = operateState == null ? null : operateState.trim();
    }

    public String getAssessedSign() {
        return assessedSign;
    }

    public void setAssessedSign(String assessedSign) {
        this.assessedSign = assessedSign == null ? null : assessedSign.trim();
    }

    public String getDepAssessorSign() {
        return depAssessorSign;
    }

    public void setDepAssessorSign(String depAssessorSign) {
        this.depAssessorSign = depAssessorSign == null ? null : depAssessorSign.trim();
    }

    public String getUnitAssessorSign() {
        return unitAssessorSign;
    }

    public void setUnitAssessorSign(String unitAssessorSign) {
        this.unitAssessorSign = unitAssessorSign == null ? null : unitAssessorSign.trim();
    }

    public String getAssessedConfirmSign() {
        return assessedConfirmSign;
    }

    public void setAssessedConfirmSign(String assessedConfirmSign) {
        this.assessedConfirmSign = assessedConfirmSign == null ? null : assessedConfirmSign.trim();
    }

   

    public String getSelfAssTime()
    {
        return selfAssTime;
    }

    public void setSelfAssTime(String selfAssTime)
    {
        this.selfAssTime = selfAssTime;
    }

    public String getDepartAssTime()
    {
        return departAssTime;
    }

    public void setDepartAssTime(String departAssTime)
    {
        this.departAssTime = departAssTime;
    }

    public String getUnitAssTime()
    {
        return unitAssTime;
    }

    public void setUnitAssTime(String unitAssTime)
    {
        this.unitAssTime = unitAssTime;
    }

    public String getConfirmedTime()
    {
        return confirmedTime;
    }

    public void setConfirmedTime(String confirmedTime)
    {
        this.confirmedTime = confirmedTime;
    }

    public String getUnitId() {
        return unitId;
    }
    
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }
    

    public String getAgencyId()
    {
        return agencyId;
    }

    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
   
    public List<String> getUnitIds()
    {
        return unitIds;
    }

    public void setUnitIds(List<String> unitIds)
    {
        this.unitIds = unitIds;
    }

	public String getAssessTeamId() {
		return assessTeamId;
	}

	public void setAssessTeamId(String assessTeamId) {
		this.assessTeamId = assessTeamId;
	}
    
}