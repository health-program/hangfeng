package com.paladin.hf.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.apache.shiro.SecurityUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paladin.common.core.permission.MenuPermission;
import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.common.core.permission.Role;
import com.paladin.framework.core.UserSession;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.model.syst.SysUser;

/**
 * 用户会话信息
 * 
 * @author TontoZhou
 * @since 2018年1月29日
 */
public class HfUserSession extends UserSession implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7077877290125259117L;

	/**
	 * 缺省的几个固有角色ID
	 * 
	 * 被考评角色 科室考评角色 机构考评角色
	 * 
	 */
	public final static String DEFAULT_ROLE_SELF_ID = "1";
	public final static String DEFAULT_ROLE_DEPARTMENT_ADMIN_ID = "2";
	public final static String DEFAULT_ROLE_AGENCY_ADMIN_ID = "3";
	public final static String DEFAULT_ROLE_ASSESS_TEAM_ADMIN_ID = "4";

	/**
	 * 可操作个人数据角色
	 */
	public final static int ASSESS_ROLE_LEVEL_SELF = 1;

	/**
	 * 可操作考评科室以及科室下数据角色
	 */
	public final static int ASSESS_ROLE_LEVEL_DEPARTMENT_ADMIN = 3;

	/**
	 * 可操作考评机构以及机构下的数据角色
	 */
	public final static int ASSESS_ROLE_LEVEL_AGENCY_ADMIN = 5;

	/**
	 * 可操作所有机构的数据角色
	 */
	public final static int ASSESS_ROLE_LEVEL_ADMIN = 6;

	// 基础信息
	private boolean isSystemAdmin;
	private boolean isAdminUser;
	private boolean isOrgUser;

	private String unitId;
	private String agencyId;

	private String[] roleIds;
	private int roleLevel;

	private String ownUnitId;

	public HfUserSession(String userId, String userName, String account, int type) {
		super(userId, userName, account);

		if (type == SysUser.TYPE_ADMIN) {
			isSystemAdmin = true;
			roleLevel = ASSESS_ROLE_LEVEL_ADMIN;
		} else if (type == SysUser.TYPE_ORG_USER) {
			isOrgUser = true;
		} else if (type == SysUser.TYPE_ADMIN_USER) {
			isAdminUser = true;
		}
	}

	/**
	 * 获取当前用户会话
	 * 
	 * @return
	 */
	public static HfUserSession getCurrentUserSession() {
		return (HfUserSession) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 设置所属单位
	 * 
	 * @param unitId
	 */
	public void setUnitId(String unitId) {
		Unit unit = UnitContainer.getUnit(unitId);
		if (unit == null) {
			throw new RuntimeException("不存在单位[id:" + unitId + "]");
		}

		this.unitId = unitId;
		this.agencyId = unit.getAgency().getId();
	}

	/**
	 * 设置角色ID
	 * 
	 * @param roleIds
	 */
	public void setRoleId(String... roleIds) {
		int roleLevel = 0;
		for (int i = 0; i < roleIds.length; i++) {
			Role role = PermissionContainer.getInstance().getRole(roleIds[i]);
			roleIds[i] = role.getId();
			roleLevel = Math.max(roleLevel, role.getRoleLevel());
		}
		this.roleLevel = roleLevel;
		this.roleIds = roleIds;
	}

	/**
	 * 设置考评单位
	 * 
	 * @param ownUnitId
	 */
	public void setOwnUnitId(String ownUnitId) {
		this.ownUnitId = ownUnitId;
	}

	/**
	 * 是否系统管理员
	 * 
	 * @return
	 */
	public boolean isSystemAdmin() {
		return isSystemAdmin;
	}

	/**
	 * 是否管理人员
	 * 
	 * @return
	 */
	public boolean isAdminUser() {
		return isAdminUser;
	}

	/**
	 * 是否档案人员
	 * 
	 * @return
	 */
	public boolean isOrgUser() {
		return isOrgUser;
	}

	/**
	 * 是否考评小组角色
	 * 
	 * @return
	 */
	public boolean isAssessTeamRole() {
		return isOrgUser && DEFAULT_ROLE_ASSESS_TEAM_ADMIN_ID.equals(roleIds[0]);
	}

	/**
	 * 是否机构考评角色
	 * 
	 * @return
	 */
	public boolean isAgencyAssessRole() {
		return isOrgUser && DEFAULT_ROLE_AGENCY_ADMIN_ID.equals(roleIds[0]);
	}

	/**
	 * 是否科室考评角色
	 * 
	 * @return
	 */
	public boolean isDepartmentAssessRole() {
		return isOrgUser && DEFAULT_ROLE_DEPARTMENT_ADMIN_ID.equals(roleIds[0]);
	}

	/**
	 * 是否被考评角色
	 * 
	 * @return
	 */
	public boolean isAssessedRole() {
		return isOrgUser && DEFAULT_ROLE_SELF_ID.equals(roleIds[0]);
	}

	/**
	 * 是否管理员等级
	 * @return
	 */
	public boolean isAdminRoleLevel() {
		return ASSESS_ROLE_LEVEL_ADMIN == roleLevel;
	}

	/**
	 * 获取角色拥有的数据等级
	 * 
	 * @return
	 */
	public int getRoleLevel() {
		return roleLevel;
	}

	/**
	 * 获取用户所在机构
	 * 
	 * @return
	 */
	@JsonIgnore
	public Unit getUserAgency() {
		if (agencyId == null)
			return null;
		return UnitContainer.getUnit(agencyId);
	}

	/**
	 * 获取用户所在机构ID
	 * 
	 * @return
	 */
	public String getUserAgencyId() {
		return agencyId;
	}

	/**
	 * 获取用户所在科室
	 * 
	 * @return
	 */
	@JsonIgnore
	public Unit getUserUnit() {
		if (unitId == null)
			return null;
		return UnitContainer.getUnit(unitId);
	}

	/**
	 * 获取用户所在科室ID
	 * 
	 * @return
	 */
	public String getUserUnitId() {
		return unitId;
	}

	/**
	 * 获取考评单位
	 * 
	 * @return
	 */
	@JsonIgnore
	public Unit getOwnUnit() {
		if (ownUnitId == null) {
			return null;
		}
		return UnitContainer.getUnit(ownUnitId);
	}

	/**
	 * 获取考评单位
	 * 
	 * @return
	 */
	public String getOwnUnitId() {
		return ownUnitId;
	}

	/**
	 * 菜单资源
	 * 
	 * @return
	 */
	public Collection<MenuPermission> getMenuResources() {
		PermissionContainer container = PermissionContainer.getInstance();
		if (isSystemAdmin) {
			container.getMenuPermissionCollection();
		}

		if (isOrgUser) {
			return container.getRole(roleIds[0]).getMenuPermissions();
		}

		if (isAdminUser) {
			HashSet<MenuPermission> coll = new HashSet<>();
			for (String rid : roleIds) {
				Role role = container.getRole(rid);
				if (role != null) {
					coll.addAll(role.getMenuPermissions());
				}
			}
			return coll;
		}

		return new ArrayList<MenuPermission>();
	}

}
