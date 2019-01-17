package com.paladin.hf.service.assess.cycle.dto;

import java.util.List;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

/**
 * @author jisanjie
 * @version 2018年1月22日 下午4:29:13
 */
public class PersonCycAssessQuery extends OffsetPage {
	private String assessCycleId;

	private String unitId;

	private List<String> unitIds;

	private String orgUserId;

	private String operateState;

	private String userName;

	private String[] operateStates;

	public String getOperateState() {
		return operateState;
	}

	public void setOperateState(String operateState) {
		this.operateState = operateState;
	}

	public String[] getOperateStates() {
		return operateStates;
	}

	public void setOperateStates(String[] operateStates) {
		this.operateStates = operateStates;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrgUserId() {
		return orgUserId;
	}

	public void setOrgUserId(String orgUserId) {
		this.orgUserId = orgUserId;
	}

	public List<String> getUnitIds() {
		return unitIds;
	}

	public void setUnitIds(List<String> unitIds) {
		this.unitIds = unitIds;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@QueryCondition(type = QueryType.EQUAL)
	public String getAssessCycleId() {
		return assessCycleId;
	}

	public void setAssessCycleId(String assessCycleId) {
		this.assessCycleId = assessCycleId;
	}

}
