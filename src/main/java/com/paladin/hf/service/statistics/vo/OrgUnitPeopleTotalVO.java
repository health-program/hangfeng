package com.paladin.hf.service.statistics.vo;

/**
 * @author MyKite
 * @version 2019年7月18日 上午9:36:17
 */
public class OrgUnitPeopleTotalVO {

	private String unitName;

	private String orgAgencyId;

	private int total;

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getOrgAgencyId() {
		return orgAgencyId;
	}

	public void setOrgAgencyId(String orgAgencyId) {
		this.orgAgencyId = orgAgencyId;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

}
