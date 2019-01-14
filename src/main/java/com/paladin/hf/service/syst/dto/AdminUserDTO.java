package com.paladin.hf.service.syst.dto;

import javax.validation.constraints.NotEmpty;

public class AdminUserDTO {

	private String id;

	@NotEmpty(message = "机构不能为空！")
	private String unitId;

	@NotEmpty(message = "名称不能为空！")
	private String name;

	@NotEmpty(message = "账号不能为空！")
	private String account;

	private String description;

	@NotEmpty(message = "角色不能为空！")
	private String roles;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
