package com.paladin.hf.service.assess.cycle.vo;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

public class UnassessedUserVO {
	
	private String userId;
	
	private String userName;
	
	private String unitId;

	private String agencyId;

	private Integer operateState;
	

	/*
	 * 扩展显示单位名称
	 */
	
	public String getDepartmentName() {
		try {
			return UnitContainer.getUnitName(unitId);
		} catch (BusinessException e) {
			return "";
		}
	}

	public String getAgencyName() {
		try {
			return UnitContainer.getUnitName(agencyId);
		} catch (BusinessException e) {
			return "";
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getOperateState() {
		return operateState;
	}

	public void setOperateState(Integer operateState) {
		this.operateState = operateState;
	}
}
