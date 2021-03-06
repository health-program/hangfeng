package com.paladin.hf.service.assess.quantificate.dto;

import javax.validation.constraints.NotEmpty;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

public class TemplateDTO {

	private String id;

	@NotEmpty(message = "单位不能为空！")
	private String orgUnitId;

	@NotEmpty(message = "模板名称不能为空！")
	private String templateName;

	private String templateDescribe;

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

	/*
	 * 扩展显示用字段
	 */

	public String getUnitName() {
		try {
			return UnitContainer.getUnitName(orgUnitId);
		} catch (BusinessException e) {
			return "";
		}
	}

}