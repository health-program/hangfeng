package com.paladin.hf.model.assess.quantificate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import com.paladin.framework.common.BaseModel;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

/**
 * 模板
 * 
 * @author TontoZhou
 * @since 2018年2月6日
 */
public class Template extends BaseModel {

	/**
	 * 启用
	 */
	public final static String STATE_START = "1";
	/**
	 * 暂存，草稿
	 */
	public final static String STATE_DRAFT = "2";
	/**
	 * 停用
	 */
	public final static String STATE_STOP = "3";

	public final static String COLUMN_ENABLE_STATE = "enableState";
	public final static String COLUMN_ORG_UNIT_ID = "orgUnitId";
	public final static String COLUMN_TEMPLAT_NAME = "templateName";

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	@NotEmpty(message = "单位不能为空！")
	private String orgUnitId;

	@NotEmpty(message = "模板名称不能为空！")
	private String templateName;

	private String templateDescribe;

	private String enableState;

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

	public String getEnableState() {
		return enableState;
	}

	public void setEnableState(String enableState) {
		this.enableState = enableState;
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