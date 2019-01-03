package com.paladin.hf.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.SecurityUtils;

import com.paladin.model.system.SysUser;


/**
 * 用户会话信息
 * 
 * @author TontoZhou
 * @since 2018年1月29日
 */
public class HfUserSession implements Serializable {

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
	 * 可操作考评科室下数据角色
	 */
	public final static int ASSESS_ROLE_LEVEL_DEPARTMENT = 2;

	/**
	 * 可操作考评科室以及科室下数据角色
	 */
	public final static int ASSESS_ROLE_LEVEL_DEPARTMENT_ADMIN = 3;

	/**
	 * 可操作考评机构下数据角色
	 */
	public final static int ASSESS_ROLE_LEVEL_AGENCY = 4;

	/**
	 * 可操作考评机构以及机构下的数据角色
	 */
	public final static int ASSESS_ROLE_LEVEL_AGENCY_ADMIN = 5;
	
	/**
	 * 可操作所有机构的数据角色
	 */
	public final static int ASSESS_ROLE_LEVEL_ADMIN = 6;

	// 基础信息
	boolean isSystemAdmin;
	boolean isAdminUser;
	boolean isOrgUser;

	String account;
	String id;
	String name;

	String unitId;
	String agencyId;

	String[] roleIds;
	Integer roleLevel;

	String ownUnitId;
	
	HfUserSession(String id, String name, int type) {

		this.id = id;
		this.name = name;

		if (type == SysUser.TYPE_ADMIN) {
			isSystemAdmin = true;
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
	 * 是否系统管理员
	 * 
	 * @return
	 */
	public boolean isAdmin() {
		return isSystemAdmin || roleLevel == ASSESS_ROLE_LEVEL_ADMIN;
	}
	
	/**
	 * 是否管理人员
	 * @return
	 */
	public boolean isAdminUser() {
		return isAdminUser;
	}
	
	/**
	 * 是否档案人员
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
	 * 获取角色拥有的数据等级
	 * @return
	 */
	public Integer getRoleLevel() {
		return roleLevel;
	}

	/**
	 * 获取人员ID
	 * 
	 * @return
	 */
	public String getUserId() {
		return id;
	}

	/**
	 * 获取用户姓名
	 * 
	 * @return
	 */
	public String getUserName() {
		return name;
	}

	/**
	 * 获取用户所在机构
	 * 
	 * @return
	 */
	public Unit getUserAgency() {
		if (agencyId == null)
			return null;
		return UnitConatiner.getUnit(agencyId);
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
	public Unit getUserUnit() {
		if (unitId == null)
			return null;
		return UnitConatiner.getUnit(unitId);
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
	public Unit getOwnUnit() {
		if (ownUnitId == null) {
			return null;
		}
		return UnitConatiner.getUnit(ownUnitId);
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
	 * 获取用户可操作的机构
	 * 
	 * @return
	 */
	public List<Unit> getOwnAgency() {

		if (isAdmin()) {
			return UnitConatiner.getRoots();
		}

		if (isAdminUser || roleLevel == ASSESS_ROLE_LEVEL_AGENCY_ADMIN) {
			return Arrays.asList(getOwnUnit());
		}

		return new ArrayList<>();
	}

	/**
	 * 获取用户可操作的机构ID数组
	 * 
	 * @return
	 */
	public List<String> getOwnAgencyId() {
		List<Unit> units = getOwnAgency();
		List<String> result = new ArrayList<>(units.size());
		for (Unit unit : units)
			result.add(unit.getId());
		return result;
	}

	/**
	 * 获取用户可操作的科室部门
	 * 
	 * @return
	 */
	public List<Unit> getOwnDepartment() {

		List<Unit> units = new ArrayList<>();

		if (isAdmin() || ASSESS_ROLE_LEVEL_AGENCY_ADMIN == roleLevel) {
			// 所有拥有机构及其子部门的权限
			for (Unit agency : getOwnAgency()) {
				getOwnDepartment(agency, units);
			}

		} else if (ASSESS_ROLE_LEVEL_AGENCY == roleLevel) {
			// 该角色不会拥有机构操作权限，但是以下科室的权限都有
			for (Unit agency : getOwnAgency()) {
				for (Unit child : agency.getChildren()) {
					getOwnDepartment(child, units);
				}
			}

		} else if (ASSESS_ROLE_LEVEL_DEPARTMENT_ADMIN == roleLevel) {
			// 拥有该科室以及科室下的权限
			getOwnDepartment(getOwnUnit(), units);

		} else if (ASSESS_ROLE_LEVEL_DEPARTMENT == roleLevel) {
			// 拥有该科室下的权限
			for (Unit child : getOwnUnit().getChildren()) {
				getOwnDepartment(child, units);
			}
		}

		return units;
	}

	private void getOwnDepartment(Unit unit, Collection<Unit> units) {
		units.add(unit);
		for (Unit child : unit.getChildren()) {
			getOwnDepartment(child, units);
		}
	}

	/**
	 * 获取用户可操作的科室部门ID
	 * 
	 * @return
	 */
	public List<String> getOwnDepartmentId() {
		List<Unit> units = getOwnDepartment();
		List<String> ids = new ArrayList<>(units.size());
		for (Unit unit : units)
			ids.add(unit.getId());
		return ids;
	}

	/**
	 * 是否拥有操作该机构权限
	 * 
	 * @param unitId
	 * @return
	 */
	public boolean ownAgency(String unitId) {

		if (unitId == null) {
			throw new BusinessException("不存在该部门");
		}

		Unit unit = UnitConatiner.getUnit(unitId);
		if (unit == null) {
			throw new BusinessException("不存在该部门");
		}

		if (unit.isAgency()) {
			if (isAdmin())
				return true;
			return getOwnAgency().contains(unit);
		} else {
			throw new BusinessException("该单位为部门科室");
		}
	}

	/**
	 * 是否拥有操作该科室权限
	 * 
	 * @param unitId
	 * @return
	 */
	public boolean ownDepartment(String unitId) {

		if (unitId == null) {
			throw new BusinessException("不存在该部门");
		}

		Unit unit = UnitConatiner.getUnit(unitId);
		if (unit == null) {
			throw new BusinessException("不存在该部门");
		}

		if (unit.isAgency()) {
			throw new BusinessException("该单位为机构");
		} else {

			if (isAdmin())
				return true;

			if (ASSESS_ROLE_LEVEL_AGENCY_ADMIN == roleLevel || ASSESS_ROLE_LEVEL_AGENCY == roleLevel) {
				return ownUnitId.equals(UnitConatiner.getRootUnit(unit).getId());
			} else {

				if (isAssessTeamRole()) {
					Unit team = unit.getAssessTeam();
					if (team != null) {
						return team.getId().equals(ownUnitId);
					} else {
						return false;
					}
				} else {
					return getOwnDepartment().contains(unit);
				}
			}
		}
	}

	/**
	 * 是否拥有该单位
	 * 
	 * @param unitId
	 * @return
	 */
	public boolean ownUnit(String unitId) {

		if (unitId == null) {
			throw new BusinessException("不存在该部门");
		}

		Unit unit = UnitConatiner.getUnit(unitId);
		if (unit == null) {
			throw new BusinessException("不存在该部门");
		}

		if (isAdmin())
			return true;

		if (unit.isAgency()) {
			return unitId.equals(ownUnitId);
		} else {
			if (ASSESS_ROLE_LEVEL_AGENCY_ADMIN == roleLevel || ASSESS_ROLE_LEVEL_AGENCY == roleLevel) {
				return ownUnitId.equals(UnitConatiner.getRootUnit(unit).getId());
			} else {

				if (isAssessTeamRole()) {
					Unit team = unit.getAssessTeam();
					if (team != null) {
						return team.getId().equals(ownUnitId);
					} else {
						return false;
					}
				} else {
					return getOwnDepartment().contains(unit);
				}
			}
		}
	}

	/**
	 * 权限code
	 * 
	 * @return
	 */
	public Collection<String> getPermissionCodes() {
		if (isSystemAdmin)
			return RoleContainer.getAdminPermissionCodes();

		if (isOrgUser)
			return RoleContainer.getRolePermissionCodes(roleIds[0]);

		if (isAdminUser)
			return RoleContainer.getRolePermissionCodes(roleIds);

		return new ArrayList<String>();

	}

	/**
	 * 菜单资源
	 * 
	 * @return
	 */
	public List<MenuResource> getMenuResources() {
		if (isSystemAdmin)
			return RoleContainer.getAdminMenuResources();

		if (isOrgUser)
			return RoleContainer.getRoleMenuResource(roleIds[0]);

		if (isAdminUser)
			return RoleContainer.getRoleMenuResource(roleIds);

		return new ArrayList<MenuResource>();
	}

	public String getAccount() {
		return account;
	}




}
