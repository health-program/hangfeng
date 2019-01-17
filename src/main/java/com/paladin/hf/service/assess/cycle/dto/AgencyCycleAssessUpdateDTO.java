package com.paladin.hf.service.assess.cycle.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AgencyCycleAssessUpdateDTO {
	@NotEmpty(message = "周期考核ID不能为空")
	private String id;
	@NotEmpty(message = "考评小组意见不能为空")
	private String unitGroupOpinion;
	@NotNull(message = "考评小组考评等级不能为空")
	private Integer unitAssGrade;
	@NotEmpty(message = "考评小组签字不能为空")
	private String unitAssessorSign;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getUnitAssessorSign() {
		return unitAssessorSign;
	}
	public void setUnitAssessorSign(String unitAssessorSign) {
		this.unitAssessorSign = unitAssessorSign;
	}

	

}
