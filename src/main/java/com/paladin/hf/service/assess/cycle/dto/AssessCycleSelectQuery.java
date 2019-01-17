package com.paladin.hf.service.assess.cycle.dto;

import com.paladin.framework.common.OffsetPage;

public class AssessCycleSelectQuery extends OffsetPage{
	
	private String userId;
	
	private String unitId;
	
	private String orgUnitId;
	
	
	
    public String getOrgUnitId()
    {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnitId)
    {
        this.orgUnitId = orgUnitId;
    }

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	
	
}
