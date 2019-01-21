package com.paladin.hf.model.assess.quantificate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.paladin.framework.common.BaseModel;

public class AssessCycleTemplate extends BaseModel {

	public final static String COLUMN_CYCLE_ID = "cycleId";
    public static final String COLUMN_UNIT_ID = "unitId";
	
	
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;

	private String templateId;

	private String cycleId;

	private String unitId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
}
