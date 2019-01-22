package com.paladin.hf.service.assess.cycle.dto;

public class AssessCycleTemplateRequest {
	
	private String cycleId;
	
	private String templateId;
	
	private String unitIds;

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUnitIds() {
		return unitIds;
	}

	public void setUnitIds(String unitIds) {
		this.unitIds = unitIds;
	}


}
