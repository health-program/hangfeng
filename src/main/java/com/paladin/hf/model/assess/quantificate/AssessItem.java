package com.paladin.hf.model.assess.quantificate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.netmatch.model.BaseModel;

public class AssessItem extends BaseModel{
	
	
	public static final String COLUMN_TEMPLATE_ID = "templateId";

	@Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
	
	private String templateId;
	
	private String itemName;
	
	private String itemDescription;
	
	private Integer orderIndex;
	
	private Double basicScore;
	
	private String parentItemId;

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

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getParentItemId() {
		return parentItemId;
	}

	public void setParentItemId(String parentItemId) {
		this.parentItemId = parentItemId;
	}

	public Double getBasicScore() {
		return basicScore;
	}

	public void setBasicScore(Double basicScore) {
		this.basicScore = basicScore;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	
	
}
