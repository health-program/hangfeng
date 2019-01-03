package com.paladin.hf.service.org;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.UserSession;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.mapper.org.OrgUnitMapper;
import com.paladin.hf.model.org.OrgUnit;
import com.paladin.hf.service.org.dto.OrgUnitQuery;


@Service
public class OrgUnitService extends ServiceSupport<OrgUnit> {

	@Autowired
	private OrgUnitMapper orgUnitMapper;

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
	public int removeUnit(String id) {
		if (orgUnitMapper.countUserOfUnit(id) > 0) {
			throw new BusinessException("请先删除该科室或机构下的档案人员");
		}
		int effect = orgUnitMapper.removeNoChildUnit(id);
		return effect;
	}

	public int saveOrUpdateUnit(OrgUnit orgUnit) {

		UserSession session = UserSession.getCurrentUserSession();

		String type = orgUnit.getUnitType();
		String parentId = orgUnit.getParentUnitId();
		Unit parentUnit = null;
		if (parentId != null && parentId.length() != 0) {
			parentUnit = UnitContainer.getUnit(parentId);
		}

		if (OrgUnit.UNIT_TYPE_AGENCY.equals(type)) {
			if (parentUnit != null) {
				throw new BusinessException("医疗机构必须为第一级");
			}
		} else if (OrgUnit.UNIT_TYPE_ASSESS_TEAM.equals(type)) {
			if (parentUnit == null || !parentUnit.isAgency()) {
				throw new BusinessException("考评小组必须直属于医疗机构");
			}
		} else if (OrgUnit.UNIT_TYPE_DEPARTMENT.equals(type)) {
			if (parentUnit == null) {
				throw new BusinessException("科室不能为第一级");
			}
		}

		if (parentUnit == null && !session.isAdmin()) {
			throw new BusinessException("您没有权限新增一个机构");
		}

		if (orgUnit.getContact() == null) {
			orgUnit.setContact("");
		}

		if (orgUnit.getContactPhone() == null) {
			orgUnit.setContactPhone("");
		}

		if (orgUnit.getDistrictCode() == null) {
			orgUnit.setDistrictCode("");
		}

		// String parentId = orgUnit.getParentUnitId();
		// if(parentId == null )
		// {
		// if(!session.isAdmin()) {
		// throw new BusinessException("你没有权限增加该机构");
		// }
		// }
		// else {
		// if(!session.ownUnit(parentId)) {
		// throw new BusinessException("你没有权限增加该机构");
		// }
		// }

		return UnitContainer.saveOrUpdate(orgUnit);
	}

	public List<OrgUnit> findUnit(OrgUnitQuery query) {

		HfUserSession session = HfUserSession.getCurrentUserSession();
		List<Unit> units = session.getOwnAgency();

		if (units == null || units.size() == 0) {
			if (session.isAssessTeamRole()) {
				Unit unit = session.getOwnUnit().getAgency();
				units = Arrays.asList(unit);
			}
		}

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
