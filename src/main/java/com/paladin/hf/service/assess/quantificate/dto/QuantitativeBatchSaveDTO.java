package com.paladin.hf.service.assess.quantificate.dto;

public class QuantitativeBatchSaveDTO {
	private String userId;
	
	private String cycleId;
	
	private Double baseScore;
	
	private Double addScore;
	
	private Double reduceScore;
	
	private Integer isVeto;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
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
}
