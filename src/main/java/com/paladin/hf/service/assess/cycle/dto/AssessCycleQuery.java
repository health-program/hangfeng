package com.paladin.hf.service.assess.cycle.dto;

import java.util.Date;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

public class AssessCycleQuery extends OffsetPage{

	private String unitId;

	private String cycleName;

	private Date cycleStartTime;

	private Date cycleEndTime;

	private String cycleState;

	private String assessType;

	@QueryCondition(type = QueryType.EQUAL)
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@QueryCondition(type = QueryType.LIKE)
	public String getCycleName() {
		return cycleName;
	}

	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public String getCycleState() {
		return cycleState;
	}

	public void setCycleState(String cycleState) {
		this.cycleState = cycleState;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public String getAssessType() {
		return assessType;
	}

	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}

	@QueryCondition(type = QueryType.GREAT_EQUAL)
	public Date getCycleStartTime() {
		return cycleStartTime;
	}

	public void setCycleStartTime(Date cycleStartTime) {
		this.cycleStartTime = cycleStartTime;
	}

	@QueryCondition(type = QueryType.LESS_EQUAL)
	public Date getCycleEndTime() {
		return cycleEndTime;
	}

	public void setCycleEndTime(Date cycleEndTime) {
		this.cycleEndTime = cycleEndTime;
	}

}
