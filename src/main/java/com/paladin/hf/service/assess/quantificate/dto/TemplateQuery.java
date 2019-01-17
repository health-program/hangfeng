package com.paladin.hf.service.assess.quantificate.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

public class TemplateQuery extends OffsetPage {

	private String enableState;

	private String unitId;

	private String templateName;

	@QueryCondition(type = QueryType.EQUAL)
	public String getEnableState() {
		return enableState;
	}

	public void setEnableState(String enableState) {
		this.enableState = enableState;
	}

	@QueryCondition(type = QueryType.EQUAL, name = "orgUnitId")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@QueryCondition(type = QueryType.LIKE)
	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

}
