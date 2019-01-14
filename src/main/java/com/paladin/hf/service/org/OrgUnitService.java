package com.paladin.hf.service.org;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.mapper.org.OrgUnitMapper;
import com.paladin.hf.model.org.OrgUnit;
import com.paladin.hf.service.org.dto.OrgUnitDTO;

@Service
public class OrgUnitService extends ServiceSupport<OrgUnit> {

	@Autowired
	private OrgUnitMapper orgUnitMapper;

	/**
	 * 保存或更新单位
	 * 
	 * @param orgUnit
	 * @return
	 */
	@Transactional
	public boolean saveUnit(OrgUnitDTO orgUnitDTO) {

		HfUserSession session = HfUserSession.getCurrentUserSession();

		OrgUnit orgUnit = new OrgUnit();
		SimpleBeanCopyUtil.simpleCopy(orgUnitDTO, orgUnit);

		Integer type = orgUnit.getUnitType();
		if (type == null) {
			throw new BusinessException("请选择单位类型");
		}

		String parentId = orgUnit.getParentUnitId();
		Unit parentUnit = null;
		if (parentId != null && parentId.length() != 0) {
			parentUnit = UnitContainer.getUnit(parentId);
		}

		if (OrgUnit.UNIT_TYPE_AGENCY == type) {
			if (parentUnit != null) {
				throw new BusinessException("医疗机构必须为第一级");
			}
		} else if (OrgUnit.UNIT_TYPE_ASSESS_TEAM == type) {
			if (parentUnit == null || !parentUnit.isAgency()) {
				throw new BusinessException("考评小组必须直属于医疗机构");
			}
		} else if (OrgUnit.UNIT_TYPE_DEPARTMENT == type) {
			if (parentUnit == null) {
				throw new BusinessException("科室不能为第一级");
			}
		}

		if (parentUnit == null && !session.isAdminRoleLevel()) {
			throw new BusinessException("您没有权限新增一个机构");
		}

		save(orgUnit);
		UnitContainer.updateData();
		return true;
	}

	/**
	 * 保存或更新单位
	 * 
	 * @param orgUnit
	 * @return
	 */
	@Transactional
	public boolean updateUnit(OrgUnitDTO orgUnitDTO) {

		HfUserSession session = HfUserSession.getCurrentUserSession();

		String id = orgUnitDTO.getUid();
		OrgUnit orgUnit = get(id);
		if (orgUnit == null) {
			throw new BusinessException("找不到更新单位");
		}

		String originParentId = orgUnit.getParentUnitId();
		Integer type = orgUnit.getUnitType();

		if (orgUnitDTO.getUnitType() != null && !type.equals(orgUnitDTO.getUnitType())) {
			throw new BusinessException("不能变更单位科室类型");
		}
		orgUnitDTO.setUnitType(type);

		String nowParentId = orgUnitDTO.getParentUnitId();
		if(id.equals(nowParentId)) {
			throw new BusinessException("不能把自己转移到自己下面");
		}
		
		if (OrgUnit.UNIT_TYPE_AGENCY == type) {
			if (nowParentId != null && nowParentId.length() > 0) {
				throw new BusinessException("只能存在同机构下科室之间的移动");
			}
		} else if (OrgUnit.UNIT_TYPE_ASSESS_TEAM == type) {
			if (!originParentId.equals(nowParentId)) {
				throw new BusinessException("只能存在同机构下科室之间的移动");
			}
		} else if (OrgUnit.UNIT_TYPE_DEPARTMENT == type) {
			Unit originParentUnit = UnitContainer.getUnit(originParentId);
			Unit nowParentUnit = UnitContainer.getUnit(nowParentId) ;
			if (!originParentUnit.getAgency().equals(nowParentUnit.getAgency())) {
				throw new BusinessException("只能存在同机构下科室之间的移动");
			}
		}

		SimpleBeanCopyUtil.simpleCopy(orgUnitDTO, orgUnit);

		if (!session.isAdminRoleLevel()) {
			throw new BusinessException("您没有权限修改机构");
		}

		update(orgUnit);
		UnitContainer.updateData();
		return true;
	}

	/**
	 * 删除单位
	 * 
	 * @param id
	 * @return
	 */
	public int deleteUnit(String id) {
		return orgUnitMapper.deleteNoChildUnit(id);
	}

	/**
	 * 逻辑删除单位
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public int removeUnit(String id) {
		if (orgUnitMapper.countUserOfUnit(id) > 0) {
			throw new BusinessException("请先删除该科室或机构下的档案人员");
		}
		int effect = orgUnitMapper.removeNoChildUnit(id);
		UnitContainer.updateData();
		return effect;
	}

	public List<OrgUnit> findOwnUnit() {

		List<Unit> units = DataPermissionUtil.getOwnAgency();
		List<OrgUnit> orgUnits = new ArrayList<>();

		if (units != null && units.size() > 0) {
			for (Unit unit : units) {
				addUnit2List(unit, orgUnits);
			}
		}

		return orgUnits;
	}

	private void addUnit2List(Unit unit, List<OrgUnit> orgUnits) {
		orgUnits.add(unit.getOrgUnit());
		for (Unit u : unit.getChildren()) {
			addUnit2List(u, orgUnits);
		}
	}
}
