package com.paladin.hf.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paladin.framework.core.VersionContainer;
import com.paladin.framework.core.VersionContainerManager;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.model.org.OrgUnit;
import com.paladin.hf.service.org.OrgUnitService;

@Component
public class UnitContainer implements VersionContainer {

	@Autowired
	private OrgUnitService unitService;

	private static UnitContainer container;
	private static volatile Map<String, Unit> unitMap;
	private static volatile List<Unit> roots;

	private synchronized void initData() {

		List<OrgUnit> units = unitService.findAll();

		Map<String, Unit> unitMap = new HashMap<>();
		List<Unit> roots = new ArrayList<>();

		for (OrgUnit orgUnit : units) {
			Unit unit = new Unit(orgUnit);
			unitMap.put(orgUnit.getUid(), unit);
		}

		for (Unit unit : unitMap.values()) {
			if (unit.parentId == null) {
				roots.add(unit);
			} else {
				Unit parentUnit = unitMap.get(unit.parentId);
				if (parentUnit == null) {
					roots.add(unit);
				} else {
					unit.parent = parentUnit;
					parentUnit.children.add(unit);
				}
			}
		}

		UnitContainer.unitMap = Collections.unmodifiableMap(unitMap);
		UnitContainer.roots = Collections.unmodifiableList(roots);

	}

	public static class Unit {

		@JsonIgnore
		OrgUnit orgUnit;

		String id;
		String name;
		String parentId;
		String unitType;

		@JsonIgnore
		Unit parent;
		List<Unit> children;

		public Unit(OrgUnit orgUnit) {
			this.id = orgUnit.getUid();
			this.name = orgUnit.getUnitName();
			this.parentId = orgUnit.getParentUnitId();
			this.unitType = orgUnit.getUnitType();
			this.orgUnit = orgUnit;
			children = new ArrayList<>();
		}

		public OrgUnit getOrgUnit() {
			return orgUnit;
		}

		public String getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public Unit getParent() {
			return parent;
		}

		public List<Unit> getChildren() {
			return children;
		}

		@JsonIgnore
		public boolean isAgency() {
			return parent == null;
		}

		@JsonIgnore
		public Unit getAssessTeam() {
			Unit a = this;
			while (a != null) {
				if (a.isAssessTeam()) {
					return a;
				}
				a = a.parent;
			}
			return null;
		}

		@JsonIgnore
		public Unit getAgency() {
			Unit a = this;
			while (a != null) {
				if (a.isAgency()) {
					return a;
				}
				a = a.parent;
			}
			return null;
		}

		@JsonIgnore
		public boolean isAssessTeam() {
			return OrgUnit.UNIT_TYPE_ASSESS_TEAM.equals(unitType);
		}

		public boolean equals(Object obj) {

			if (obj == null)
				return false;

			if (obj == this)
				return true;

			if (obj instanceof Unit) {
				return id.equals(((Unit) obj).id);
			}

			return false;
		}

		public int hashCode() {
			return id.hashCode();
		}

		public String toString() {
			if (parent != null) {
				return parent.toString() + "-" + name;
			} else {
				return name;
			}
		}

		public String getParentId() {
			return parentId;
		}

		public String getUnitType() {
			return unitType;
		}

	}

	// ----------------------------------------------
	// 为了省力，不使用锁方式去持久化单个UNIT，而是每当数据变更则重新读取数据库
	// 如果使用分布式，则这里需要改为REDIS等方式
	// ----------------------------------------------

	public int _saveOrUpdate(OrgUnit unit) {
		int effect = unitService.saveOrUpdate(unit);
		if (effect > 0) {
			VersionContainerManager.versionChanged(getId());
		}
		return effect;
	}

	public int _remove(String id) {
		int effect = unitService.removeUnit(id);
		if (effect > 0) {
			VersionContainerManager.versionChanged(getId());
		}
		return effect;
	}

	public static int saveOrUpdate(OrgUnit unit) {
		return container._saveOrUpdate(unit);
	}

	public static int remove(String id) {
		return container._remove(id);
	}

	// ----------------------------------------------
	// 获取方法
	// ----------------------------------------------

	/**
	 * 获取根单位数组
	 * 
	 * @return
	 */
	public static List<Unit> getRoots() {
		return roots;
	}

	/**
	 * 根据ID获取单位，未找到会抛出异常
	 * 
	 * @param id
	 * @return
	 */
	public static Unit getUnit(String id) {
		Unit unit = unitMap.get(id);
		if (unit != null)
			return unit;
		throw new BusinessException("无法找到单位[ID：" + id + "]");
	}

	/**
	 * 根据ID获取单位名称，未找到会抛出异常
	 * 
	 * @param id
	 * @return
	 */
	public static String getUnitName(String id) {
		return getUnit(id).getName();
	}

	/**
	 * 根据ID获取根单位，未找到会抛出异常
	 * 
	 * @param id
	 * @return
	 */
	public static Unit getRootUnit(String id) {
		if (id != null) {
			Unit unit = unitMap.get(id);
			if (unit != null) {
				while (unit.parent != null)
					unit = unit.parent;
				return unit;
			}
		}

		throw new BusinessException("无法找到单位[ID：" + id + "]");
	}

	/**
	 * 获取根单位
	 * 
	 * @param unit
	 * @return
	 */
	public static Unit getRootUnit(Unit unit) {
		if (unit != null) {
			while (unit.parent != null)
				unit = unit.parent;
			return unit;
		}
		throw new IllegalArgumentException("单位参数不能为空");
	}

	/**
	 * 根据ID获取根单位名称，未找到会抛出异常
	 * 
	 * @param id
	 * @return
	 */
	public static String getRootUnitName(String id) {
		return getRootUnit(id).getName();
	}

	/**
	 * 根据ID获取单位，并且返回该单位下所有子单位集合，未找到会抛出异常
	 * 
	 * @param id
	 * @return
	 */
	public static List<Unit> getUnitChildren(String id) {
		List<Unit> units = new ArrayList<>();
		for (Unit child : getUnit(id).children)
			getUnitAndChildren(child, units);
		return units;
	}

	/**
	 * 返回该单位下所有子单位集合，
	 * 
	 * @param unit
	 * @return
	 */
	public static List<Unit> getUnitChildren(Unit unit) {
		List<Unit> units = new ArrayList<>();
		if (unit != null) {
			for (Unit child : unit.children)
				getUnitAndChildren(child, units);
		}
		throw new IllegalArgumentException("单位参数不能为空");
	}

	/**
	 * 获取该单位以及子单位的ID集合
	 * 
	 * @param id
	 * @return
	 */
	public static List<String> getUnitAndChildrenIds(String id) {
		List<String> ids = new ArrayList<>();
		getUnitAndChildrenIds(getUnit(id), ids);
		return ids;
	}

	/**
	 * 获取该单位以及子单位的ID集合
	 * 
	 * @param unit
	 * @return
	 */
	public static List<String> getUnitAndChildrenIds(Unit unit) {
		List<String> ids = new ArrayList<>();
		if (unit != null) {
			getUnitAndChildrenIds(unit, ids);
		}
		return ids;
	}

	private static void getUnitAndChildrenIds(Unit unit, List<String> ids) {
		ids.add(unit.id);
		for (Unit child : unit.children) {
			getUnitAndChildrenIds(child, ids);
		}
	}

	/**
	 * 获取该单位以及子单位集合
	 * 
	 * @param id
	 * @return
	 */
	public static List<Unit> getUnitAndChildren(String id) {
		List<Unit> units = new ArrayList<>();
		getUnitAndChildren(getUnit(id), units);
		return units;
	}

	/**
	 * 获取该单位以及子单位集合
	 * 
	 * @param unit
	 * @return
	 */
	public static List<Unit> getUnitAndChildren(Unit unit) {
		List<Unit> units = new ArrayList<>();
		if (unit != null) {
			getUnitAndChildren(unit, units);
		}
		return units;
	}

	private static void getUnitAndChildren(Unit unit, List<Unit> units) {
		units.add(unit);
		for (Unit child : unit.children) {
			getUnitAndChildren(child, units);
		}
	}

	@Override
	public boolean versionChangedHandle(long version) {
		initData();
		container = this;
		return true;
	}

	@Override
	public String getId() {
		return "unit_container";
	}

}
