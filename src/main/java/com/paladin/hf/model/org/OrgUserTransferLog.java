package com.paladin.hf.model.org;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class OrgUserTransferLog {
	
	public final static Integer TRANSFER_TYPE_ASK = 1;
	public final static Integer TRANSFER_TYPE_ADMIN = 2;

	
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
	
	private String userId;
	
	private String unitFrom;
	
	private String unitTo;
	
	private Date transferTime;
	
	private Integer transferType;
	
	private String transferBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUnitFrom() {
		return unitFrom;
	}

	public void setUnitFrom(String unitFrom) {
		this.unitFrom = unitFrom;
	}

	public String getUnitTo() {
		return unitTo;
	}

	public void setUnitTo(String unitTo) {
		this.unitTo = unitTo;
	}

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	public Integer getTransferType() {
		return transferType;
	}

	public void setTransferType(Integer transferType) {
		this.transferType = transferType;
	}

	public String getTransferBy() {
		return transferBy;
	}

	public void setTransferBy(String transferBy) {
		this.transferBy = transferBy;
	}
	
}
