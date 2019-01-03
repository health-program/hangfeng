package com.paladin.hf.model.org;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.paladin.framework.common.UnDeleteModel;

public class OrgRole extends UnDeleteModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4924344076726834601L;

	public static final String COLUMN_IS_DEFAULT = "isDefault";
	public static final String COLUMN_ROLE_LEVEL = "roleLevel";

	
	@Id
	@Column(name = "id")
	@GeneratedValue(generator = "UUID")
	private String id;
	
	private String roleName;
	
	private Integer roleLevel;
	
	private String roleDesc;
	
	private Integer isDefault;
	
	private Integer enable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(Integer roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	
}
