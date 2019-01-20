package com.paladin.hf.service.assess.cycle.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AssessCycleDTO {

	private String id;

	@NotEmpty(message = "机构不能为空！")
	private String unitId;

	@NotEmpty(message = "周期名称不为空！")
	private String cycleName;

	@NotNull(message = "周期开始时间不为空！")
	private Date cycleStartTime;

	@NotNull(message = "周期截止时间不为空！")
	private Date cycleEndTime;

	@NotNull(message = "考评开始时间不为空！")
	private Date assessStartTime;

	@NotNull(message = "考评截止时间不为空！")
	private Date assessEndTime;

	@NotNull(message = "考评周期类型不为空！")
	@Column(columnDefinition = "enum(1,2)")
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
}
