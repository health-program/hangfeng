package com.paladin.hf.service.org.dto;

import com.paladin.hf.model.org.OrgUser;

public class SimpleUser {
	
	private String userName;
	
	private String userId;
	
	public SimpleUser(OrgUser user) {
		userName = user.getName();
		userId = user.getId();
	}
	
	public SimpleUser() {
		
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	} 
	
}
