package com.paladin.hf.service.assess.cycle.dto;

public class DepartmentCycleAssessBatchDTO {
	
	private String cycleId;
	
	private String userId;
	
	private String opinion;
	
	private Integer grade;
	
	private String depAssessorSign;

	public String getOpinion() {
		return opinion;
	}

	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepAssessorSign() {
	    return depAssessorSign;
	}

	public void setDepAssessorSign(String depAssessorSign) {
	    this.depAssessorSign = depAssessorSign;
	}
	
}
