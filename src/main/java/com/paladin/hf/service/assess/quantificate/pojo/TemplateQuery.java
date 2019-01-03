package com.paladin.hf.service.assess.quantificate.pojo;

import com.netmatch.tonto.framework.service.QueryCondition;
import com.netmatch.tonto.framework.service.QueryType;
import com.netmatch.tonto.framework.web.controller.OffsetPage;

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
