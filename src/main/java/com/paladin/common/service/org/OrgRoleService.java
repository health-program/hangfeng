package com.paladin.common.service.org;

import java.util.List;

import org.springframework.stereotype.Service;

import com.paladin.common.model.org.OrgRole;
import com.paladin.framework.common.BaseModel;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;

@Service
public class OrgRoleService extends ServiceSupport<OrgRole> {

	@Override
	public int removeByPrimaryKey(Object id) {
		OrgRole role = get(id);
		if (role.getIsDefault() == BaseModel.BOOLEAN_YES) {
			throw new BusinessException("不能删除默认角色");
		}

		return super.removeByPrimaryKey(id);
	}

	public List<OrgRole> getOwnGrantRoles(int roleLevel, boolean defaultabled) {
		/**
		 * 只能获取数据等级小于等于自己的角色
		 */
		return searchAll(new Condition[] { 
				new Condition(OrgRole.COLUMN_FIELD_IS_DEFAULT, QueryType.EQUAL, defaultabled ? 1 : 0),
				new Condition(OrgRole.COLUMN_FIELD_ROLE_LEVEL, QueryType.LESS_EQUAL, roleLevel) }
		);
	}

}