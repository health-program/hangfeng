package com.paladin.hf.model.assess.quantificate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.paladin.framework.common.BaseModel;

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
	public final static Integer STATE_START = 1;
	/**
	 * 暂存，草稿
	 */
	public final static Integer STATE_DRAFT = 2;
	/**
	 * 停用
	 */
	public final static Integer STATE_STOP = 3;

	public final static String COLUMN_ENABLE_STATE = "enableState";
	public final static String COLUMN_ORG_UNIT_ID = "orgUnitId";
	public final static String COLUMN_TEMPLAT_NAME = "templateName";

	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	private String orgUnitId;

	private String templateName;

	private String templateDescribe;

	private Integer enableState;

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