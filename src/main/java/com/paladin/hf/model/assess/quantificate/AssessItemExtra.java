package com.paladin.hf.model.assess.quantificate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class AssessItemExtra extends BaseModel{
	
	/**
	 * 加分
	 */
	public static final String EXTRA_TYPE_ADD_SCORE = "1";
	/**
	 * 扣分
	 */
	public static final String EXTRA_TYPE_LESS_SCORE = "2";
	/**
	 * 一票否决
	 */
	public static final String EXTRA_TYPE_VETO = "3";

	
	public static final String COLUMN_TEMPLATE_ID = "templateId";
	public static final String COLUMN_EXTRA_TYPE = "extraType";

	@Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
	
	private String templateId;
	
	private String extraType;
	
	private String extraDescription;
	
	private Integer orderIndex;
	
	private Double alterUpper;
	
	private Double alterLower;
	
	private Double accumulateUpper;

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

	public String getExtraType() {
		return extraType;
	}

	public void setExtraType(String extraType) {
		this.extraType = extraType;
	}

	public String getExtraDescription() {
		return extraDescription;
	}

	public void setExtraDescription(String extraDescription) {
		this.extraDescription = extraDescription;
	}

	public Double getAlterUpper() {
		return alterUpper;
	}

	public void setAlterUpper(Double alterUpper) {
		this.alterUpper = alterUpper;
	}

	public Double getAlterLower() {
		return alterLower;
	}

	public void setAlterLower(Double alterLower) {
		this.alterLower = alterLower;
	}

	public Double getAccumulateUpper() {
		return accumulateUpper;
	}

	public void setAccumulateUpper(Double accumulateUpper) {
		this.accumulateUpper = accumulateUpper;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}
	
}
