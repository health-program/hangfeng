package com.paladin.hf.service.assess.cycle.vo;

import java.util.Date;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

public class AssessCycleVO {
	private String id;

	private String unitId;

	private String cycleName;

	private Date cycleStartTime;

	private Date cycleEndTime;

	private Date assessStartTime;

	private Date assessEndTime;

	private Integer cycleState;

	private Integer assessType;

	private String cycleDescribe;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getCycleName() {
		return cycleName;
	}

	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}

	public Date getCycleStartTime() {
		return cycleStartTime;
	}

	public void setCycleStartTime(Date cycleStartTime) {
		this.cycleStartTime = cycleStartTime;
	}

	public Date getCycleEndTime() {
		return cycleEndTime;
	}

	public void setCycleEndTime(Date cycleEndTime) {
		this.cycleEndTime = cycleEndTime;
	}

	public Date getAssessStartTime() {
		return assessStartTime;
	}

	public void setAssessStartTime(Date assessStartTime) {
		this.assessStartTime = assessStartTime;
	}

	public Date getAssessEndTime() {
		return assessEndTime;
	}

	public void setAssessEndTime(Date assessEndTime) {
		this.assessEndTime = assessEndTime;
	}

	public Integer getCycleState() {
		return cycleState;
	}

	public void setCycleState(Integer cycleState) {
		this.cycleState = cycleState;
	}

	public Integer getAssessType() {
		return assessType;
	}

	public void setAssessType(Integer assessType) {
		this.assessType = assessType;
	}

	public String getCycleDescribe() {
		return cycleDescribe;
	}

	public void setCycleDescribe(String cycleDescribe) {
		this.cycleDescribe = cycleDescribe;
	}

	/*
	 * 扩展显示单位名称
	 */
	public String getUnitName() {
		try {
			return UnitContainer.getUnitName(unitId);
		} catch (BusinessException e) {
			return "";
		}
	}

}
