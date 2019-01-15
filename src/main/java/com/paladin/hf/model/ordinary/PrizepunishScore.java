package com.paladin.hf.model.ordinary;

public class PrizepunishScore {
	
	private Prizepunish prizepunish;
		
	private Double addScore;
	
	private Double reduceScore;
	
	private Boolean isVeto;
	
	public PrizepunishScore(Prizepunish prizepunish) {
		this.prizepunish = prizepunish;
	}
    
	public String getId() {
		return prizepunish.getId();
	}

	public String getOrgUserId() {
		return prizepunish.getOrgUserId();
	}

	public String getDictCode() {
		return prizepunish.getDictCode();
	}

	public String getHappenTime() {
		return prizepunish.getHappenTime();
	}

	public String getContent() {
		return prizepunish.getContent();
	}
	
	public String getPlace() {
		return prizepunish.getPlace();
	}

	public String getChecks() {
		return prizepunish.getChecks();
	}

	public String getCheckPeople() {
		return prizepunish.getCheckPeople();
	}

	public Integer getOperationState() {
		return prizepunish.getOperationState();
	}

	public Integer getExamineState() {
		return prizepunish.getExamineState();
	}

	public String getExaminePeople() {
		return prizepunish.getExaminePeople();
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

	public Boolean getIsVeto() {
		return isVeto;
	}

	public void setIsVeto(Boolean isVeto) {
		this.isVeto = isVeto;
	}



}
