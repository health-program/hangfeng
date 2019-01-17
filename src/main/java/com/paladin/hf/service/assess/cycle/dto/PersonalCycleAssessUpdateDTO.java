package com.paladin.hf.service.assess.cycle.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PersonalCycleAssessUpdateDTO {
	@NotEmpty(message="周期考核ID不能为空")
	private String id;
	@NotEmpty(message="工作总结不能为空")
	private String workSummary;
	@NotEmpty(message="自评意见不能为空")
	private String selfAssOpinion;
	@NotNull(message="自评等级不能为空")
	private Integer selfAssGrade;
	@NotEmpty(message="自评签名不能为空")
	private String assessedSign;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAssessedSign() {
		return assessedSign;
	}

	public void setAssessedSign(String assessedSign) {
		this.assessedSign = assessedSign;
	}

	public Integer getSelfAssGrade() {
		return selfAssGrade;
	}

	public void setSelfAssGrade(Integer selfAssGrade) {
		this.selfAssGrade = selfAssGrade;
	}
}
