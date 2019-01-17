package com.paladin.hf.service.assess.cycle.dto;

import com.paladin.framework.common.OffsetPage;

public class PersonalQueryDTO extends OffsetPage{
	
	private String userId;
	private String assessCycleId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAssessCycleId() {
		return assessCycleId;
	}
	public void setAssessCycleId(String assessCycleId) {
		this.assessCycleId = assessCycleId;
	}
	
}
