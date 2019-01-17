package com.paladin.hf.service.assess.cycle.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DepartmentCycleAssessUpdateDTO {
	@NotEmpty(message="周期考核ID不能为空")
	private String id;
	@NotEmpty(message="科室意见不能为空")
	private String departOpinion;
	@NotNull(message="科室考评等级不能为空")
	private Integer departGrade;
	@NotEmpty(message="科室签字不能为空")
	private String depAssessorSign;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getDepAssessorSign() {
		return depAssessorSign;
	}

	public void setDepAssessorSign(String depAssessorSign) {
		this.depAssessorSign = depAssessorSign;
	}

}
