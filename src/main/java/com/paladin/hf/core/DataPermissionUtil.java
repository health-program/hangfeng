package com.paladin.hf.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer.Unit;

/**
 * 数据权限工具类，数据权限核心算法代码
 * 
 * @author TontoZhou
 * @since 2018年10月15日
 */
public class DataPermissionUtil {

	/**
	 * 用于表中除去单位字段外还有机构冗余字段的查询
	 * 
	 * @param unitId
	 * @return
	 */
	public static UnitQuery getUnitQueryDouble(String unitId) {
		HfUserSession session = HfUserSession.getCurrentUserSession();
		String ownUnitId = session.getOwnUnitId();
		UnitQuery query = new UnitQuery();

		int roleLevel = session.getRoleLevel();

		Unit unit = null;
		if (unitId != null && unitId.length() != 0) {
			unit = UnitContainer.getUnit(unitId);
		}
		if (roleLevel == HfUserSession.ASSESS_ROLE_LEVEL_SELF) {
			// 个人没有权限，个人查询不应该通过该方法过滤
			return null;
		} else if (roleLevel == HfUserSession.ASSESS_ROLE_LEVEL_ADMIN) {
			// 最高权限，管理员级别，可查询所有
			if (unit != null) {
				if (unit.isAgency()) {
					query.agencyId = unitId;
				} else {
					if (unit.isAssessTeam()) {
						query.assessTeamId = unitId;
					} else {
						List<String> unitIds = UnitContainer.getUnitAndChildrenIds(unit);
						if (unitIds.size() == 1) {
							query.unitId = unitIds.get(0);
						} else {
							query.unitIds = unitIds;
						}
					}
				}
			}
		} else if (roleLevel == HfUserSession.ASSESS_ROLE_LEVEL_AGENCY_ADMIN) {
			// 机构最高权限，可操作机构下所有
			if (unit != null) {
				if (unit.isAgency()) {
					if (unitId.equals(ownUnitId)) {
						query.agencyId = unitId;
					} else {
						return null;
					}
				} else {
					if (!unit.getAgency().getId().equals(ownUnitId)) {
						return null;
					} else {
						if (unit.isAssessTeam()) {
							query.assessTeamId = unitId;
						} else {
							List<String> ids = UnitContainer.getUnitAndChildrenIds(unit);
							if (ids.size() == 1) {
								query.unitId = ids.get(0);
							} else {
								query.unitIds = ids;
							}
						}
					}
				}
			} else {
				query.agencyId = session.getOwnUnitId();
			}
		} else if (roleLevel == HfUserSession.ASSESS_ROLE_LEVEL_DEPARTMENT_ADMIN) {
			// 科室部门
			Unit ownUnit = UnitContainer.getUnit(ownUnitId);
			if (ownUnit.isAssessTeam()) {
				// 如果是考评小组考核人
				if (unit == null) {
					query.assessTeamId = ownUnitId;
				} else {
					Unit teamUnit = unit.getAssessTeam();
					if (teamUnit == null || !teamUnit.getId().equals(ownUnitId)) {
						return null;
					}

					List<String> ids = UnitContainer.getUnitAndChildrenIds(unit);
					if (ids.size() == 1) {
						query.unitId = ids.get(0);
					} else {
						query.unitIds = ids;
					}
				}
			} else {
				List<String> ownUnitIds = UnitContainer.getUnitAndChildrenIds(ownUnit);
				if (unit == null) {
					if (ownUnitIds.size() == 1) {
						query.unitId = ownUnitIds.get(0);
					} else {
						query.unitIds = ownUnitIds;
					}
				} else if (ownUnitIds.contains(unitId)) {
					List<String> ids = UnitContainer.getUnitAndChildrenIds(unit);
					if (ids.size() == 1) {
						query.unitId = ids.get(0);
					} else {
						query.unitIds = ids;
					}
				} else {
					return null;
				}
			}
		} else {
			// 不应该存在
			return null;
		}

		return query;
	}

	public static class UnitQuery {

		String unitId;

		String agencyId;

		String assessTeamId;

		List<String> unitIds;

		List<String> agencyIds;

		public String getUnitId() {
			return unitId;
		}

		public String getAgencyId() {
			return agencyId;
		}

		public List<String> getUnitIds() {
			return unitIds;
		}

		public List<String> getAgencyIds() {
			return agencyIds;
		}

		public String getAssessTeamId() {
			return assessTeamId;
		}

		public void setAssessTeamId(String assessTeamId) {
			this.assessTeamId = assessTeamId;
		}

	}

	/**
	 * 获取用户可操作的机构
	 * 
	 * @return
	 */
	public static List<Unit> getOwnAgency() {
		HfUserSession session = HfUserSession.getCurrentUserSession();

		if (session.isAdminRoleLevel()) {
			return UnitContainer.getRoots();
		}

		if (session.getRoleLevel() == HfUserSession.ASSESS_ROLE_LEVEL_AGENCY_ADMIN) {
			return Arrays.asList(session.getOwnUnit());
		}

		return new ArrayList<>();
	}

	/**
	 * 获取用户可操作的机构ID数组
	 * 
	 * @return
	 */
	public static List<String> getOwnAgencyId() {
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
	public static List<Unit> getOwnDepartment() {
		HfUserSession session = HfUserSession.getCurrentUserSession();
		List<Unit> units = new ArrayList<>();
		int roleLevel = session.getRoleLevel();

		if (HfUserSession.ASSESS_ROLE_LEVEL_AGENCY_ADMIN < roleLevel) {
			// 所有拥有机构及其子部门的权限
			for (Unit agency : getOwnAgency()) {
				getOwnDepartment(agency, units);
			}
		} else if (HfUserSession.ASSESS_ROLE_LEVEL_DEPARTMENT_ADMIN == roleLevel) {
			// 拥有该科室以及科室下的权限
			getOwnDepartment(session.getOwnUnit(), units);
		}
		return units;
	}

	private static void getOwnDepartment(Unit unit, Collection<Unit> units) {
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
	public static List<String> getOwnDepartmentId() {
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
	public static boolean ownAgency(String unitId) {
		HfUserSession session = HfUserSession.getCurrentUserSession();

		if (unitId == null || unitId.length() == 0) {
			throw new BusinessException("不存在该部门");
		}

		Unit unit = UnitContainer.getUnit(unitId);
		if (unit == null) {
			throw new BusinessException("不存在该部门");
		}

		if (unit.isAgency()) {
			return session.isAdminRoleLevel() || getOwnAgency().contains(unit);
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
	public static boolean ownDepartment(String unitId) {
		HfUserSession session = HfUserSession.getCurrentUserSession();

		if (unitId == null || unitId.length() == 0) {
			throw new BusinessException("不存在该部门");
		}

		Unit unit = UnitContainer.getUnit(unitId);
		if (unit == null) {
			throw new BusinessException("不存在该部门");
		}

		if (unit.isAgency()) {
			throw new BusinessException("该单位为机构");
		} else {

			if (session.isAdminRoleLevel()) {
				return true;
			}

			int roleLevel = session.getRoleLevel();
			String ownUnitId = session.getOwnUnitId();

			if (HfUserSession.ASSESS_ROLE_LEVEL_AGENCY_ADMIN == roleLevel) {
				return ownUnitId.equals(unit.getAgency().getId());
			} else {
				if (session.isAssessTeamRole()) {
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
	public static boolean ownUnit(String unitId) {
		HfUserSession session = HfUserSession.getCurrentUserSession();

		if (unitId == null) {
			throw new BusinessException("不存在该部门");
		}

		Unit unit = UnitContainer.getUnit(unitId);
		if (unit == null) {
			throw new BusinessException("不存在该部门");
		}

		if (session.isAdminRoleLevel()) {
			return true;
		}

		String ownUnitId = session.getOwnUnitId();

		if (unit.isAgency()) {
			return unitId.equals(ownUnitId);
		} else {

			if (session.isAdminRoleLevel()) {
				return true;
			}

			int roleLevel = session.getRoleLevel();

			if (HfUserSession.ASSESS_ROLE_LEVEL_AGENCY_ADMIN == roleLevel) {
				return ownUnitId.equals(unit.getAgency().getId());
			} else {
				if (session.isAssessTeamRole()) {
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

}
