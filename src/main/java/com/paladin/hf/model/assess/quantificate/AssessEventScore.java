package com.paladin.hf.model.assess.quantificate;

public class AssessEventScore {
	
	private String assessQuantitativeId;
	
	private String extraId;
    
	private String extraType;

	private String extraDescription;
	
	private Double alterUpper;
	
	private Double alterLower;
	
	private Double accumulateUpper;
						  
	private Double score;

	public String getAssessQuantitativeId() {
		return assessQuantitativeId;
	}

	public void setAssessQuantitativeId(String assessQuantitativeId) {
		this.assessQuantitativeId = assessQuantitativeId;
	}

	public String getExtraId() {
		return extraId;
	}

	public void setExtraId(String extraId) {
		this.extraId = extraId;
	}

	public String getExtraType() {
		return extraType;
	}

	public void setExtraType(String extraType) {
		this.extraType = extraType;
	}

	public Double getAlterUpper() {
		return alterUpper;
	}

	public void setAlterUpper(Double alterUpper) {
		this.alterUpper = alterUpper;
	}

	public Double getAlterLower() {
		return alterLower;
	}

	public void setAlterLower(Double alterLower) {
		this.alterLower = alterLower;
	}

	public Double getAccumulateUpper() {
		return accumulateUpper;
	}

	public void setAccumulateUpper(Double accumulateUpper) {
		this.accumulateUpper = accumulateUpper;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getExtraDescription() {
		return extraDescription;
	}

	public void setExtraDescription(String extraDescription) {
		this.extraDescription = extraDescription;
	}
	
}
