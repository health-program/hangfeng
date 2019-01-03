package com.paladin.hf.service.org.dto;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

public class OrgUnitQuery extends OffsetPage {

	private String unitName;

	private String unitType;

	@QueryCondition(type = QueryType.LIKE)
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

}
