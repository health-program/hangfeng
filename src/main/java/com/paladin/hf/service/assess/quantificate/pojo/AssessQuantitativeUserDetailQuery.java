package com.paladin.hf.service.assess.quantificate.pojo;

import java.util.Date;

public class AssessQuantitativeUserDetailQuery {
	
	private String userId;
	private String cycleId;
	private String eventType;
	private Date startHappenTime;
	private Date endHappenTime;
	public Date getEndHappenTime() {
		return endHappenTime;
	}
	public void setEndHappenTime(Date endHappenTime) {
		this.endHappenTime = endHappenTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCycleId() {
		return cycleId;
	}
	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public Date getStartHappenTime() {
		return startHappenTime;
	}
	public void setStartHappenTime(Date startHappenTime) {
		this.startHappenTime = startHappenTime;
	}
	
}
