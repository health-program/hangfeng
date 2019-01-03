package com.paladin.hf.model.assess.quantificate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.netmatch.model.BaseModel;

public class AssessLevel extends BaseModel{
	
	public final static String COLUMN_TEMPLATE_ID = "templateId";
	
	@Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
	
	private String levelCode;
	
	private String templateId;
	
	private Integer totalUpper;
	
	private Integer totalLower;
	
	private Integer addUpper;
	
	private Integer addLower;
	
	private Integer lessUpper;
	
	private Integer lessLower;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Integer getTotalUpper() {
		return totalUpper;
	}

	public void setTotalUpper(Integer totalUpper) {
		this.totalUpper = totalUpper;
	}

	public Integer getTotalLower() {
		return totalLower;
	}

	public void setTotalLower(Integer totalLower) {
		this.totalLower = totalLower;
	}

	public Integer getAddUpper() {
		return addUpper;
	}

	public void setAddUpper(Integer addUpper) {
		this.addUpper = addUpper;
	}

	public Integer getAddLower() {
		return addLower;
	}

	public void setAddLower(Integer addLower) {
		this.addLower = addLower;
	}

	public Integer getLessUpper() {
		return lessUpper;
	}

	public void setLessUpper(Integer lessUpper) {
		this.lessUpper = lessUpper;
	}

	public Integer getLessLower() {
		return lessLower;
	}

	public void setLessLower(Integer lessLower) {
		this.lessLower = lessLower;
	}

}
