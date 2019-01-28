package com.paladin.hf.service.assess.cycle.dto;

import com.paladin.framework.common.OffsetPage;

public class UnassessedQuery extends OffsetPage{
	
	private String unitId;
	
	private String assessCycleId;

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getAssessCycleId() {
		return assessCycleId;
	}

	public void setAssessCycleId(String assessCycleId) {
		this.assessCycleId = assessCycleId;
	}
	
}
