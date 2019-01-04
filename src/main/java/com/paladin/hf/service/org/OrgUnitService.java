package com.paladin.hf.service.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
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

	/**
	 * 保存或更新单位
	 * 
	 * @param orgUnit
	 * @return
	 */
	@Transactional
	public boolean saveOrUpdateUnit(OrgUnitDTO orgUnitDTO) {

		HfUserSession session = HfUserSession.getCurrentUserSession();

		String id = orgUnitDTO.getUid();
		boolean isUpdate = (id != null && id.length() > 0);
		OrgUnit orgUnit = null;
		if (isUpdate) {
			orgUnit = get(id);
			if (orgUnit == null) {
				throw new BusinessException("找不到更新单位");
			}
		} else {
			orgUnit = new OrgUnit();
		}

		SimpleBeanCopyUtil.simpleCopy(orgUnitDTO, orgUnit);

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

		if (parentUnit == null && !session.isAdminRoleLevel()) {
			throw new BusinessException("您没有权限新增一个机构");
		}

		if(isUpdate) {
			update(orgUnit);
		} else {
			save(orgUnit);
		}
			
		UnitContainer.updateData();
		return true;
	}

}
