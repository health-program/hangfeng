package com.paladin.hf.service.assess.quantificate.vo;

import com.paladin.hf.core.UnitContainer;

public class TemplateVO {
	
	private String id;

	private String orgUnitId;

	private String templateName;

	private String templateDescribe;

	private Integer enableState;
	
	public String getUnitName() {
		try {
			return UnitContainer.getUnitName(orgUnitId);
		} catch(Exception e) {
			return "";
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgUnitId() {
		return orgUnitId;
	}

	public void setOrgUnitId(String orgUnitId) {
		this.orgUnitId = orgUnitId;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateDescribe() {
		return templateDescribe;
	}

	public void setTemplateDescribe(String templateDescribe) {
		this.templateDescribe = templateDescribe;
	}

	public Integer getEnableState() {
		return enableState;
	}

	public void setEnableState(Integer enableState) {
		this.enableState = enableState;
	}
}
