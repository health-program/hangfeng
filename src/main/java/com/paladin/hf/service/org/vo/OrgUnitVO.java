package com.paladin.hf.service.org.vo;

import com.paladin.hf.core.UnitContainer;

public class OrgUnitVO {

	private String uid;

	private String unitName;

	private String unitDescription;

	private String parentUnitId;

	private Integer unitType;
	
	private Integer agencyType;
	
	private Integer sort;

	private String districtCode;

	private String contact;

	private String contactPhone;

	public String getParentUnitName() {
		if (parentUnitId != null && parentUnitId.length() > 0) {
			try {
				return UnitContainer.getUnit(parentUnitId).getName();
			} catch (Exception e) {
			}
		}
		return null;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public String getUnitDescription() {
		return unitDescription;
	}

	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}

	public String getParentUnitId() {
		return parentUnitId;
	}

	public void setParentUnitId(String parentUnitId) {
		this.parentUnitId = parentUnitId;
	}

	public Integer getUnitType() {
		return unitType;
	}

	public void setUnitType(Integer unitType) {
		this.unitType = unitType;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public Integer getAgencyType() {
	    return agencyType;
	}

	public void setAgencyType(Integer agencyType) {
	    this.agencyType = agencyType;
	}

	public Integer getSort() {
	    return sort;
	}

	public void setSort(Integer sort) {
	    this.sort = sort;
	}
	
}
