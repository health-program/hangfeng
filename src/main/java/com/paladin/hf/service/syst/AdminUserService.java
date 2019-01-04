package com.paladin.hf.service.syst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.mapper.syst.AdminUserMapper;
import com.paladin.hf.model.syst.AdminUser;
import com.paladin.hf.service.syst.vo.SysUserVO;

@Service
public class AdminUserService extends ServiceSupport<AdminUser> {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private AdminUserMapper adminUserMapper;

	@Transactional
	public int saveOrUpdateAdminUser(AdminUser user) {

		String roles = user.getRoles();
		String[] roleIds = roles.split(",");

		for (String rid : roleIds) {
			if (PermissionContainer.getInstance().getRole(rid) == null) {
				throw new BusinessException("角色参数错误");
			}
		}

		Unit unit = UnitContainer.getUnit(user.getUnitId());

		if (!unit.isAgency()) {
			throw new BusinessException("单位参数错误");
		}

		if (user.getId() != null) {
			AdminUser updateUser = new AdminUser();
			updateUser.setId(user.getId());
			updateUser.setRoles(user.getRoles());
			updateUser.setName(user.getName());
			updateUser.setDescription(user.getDescription());
			return updateSelective(updateUser);

		} else {
			String id = UUIDUtil.createUUID();
			user.setId(id);

			if (sysUserService.createSysUserAccount(user.getAccount(), user.getId()) > 0) {
				return save(user);
			}
		}
		return 0;
	}

	public int removeUser(String id) {
		// 逻辑删除档案人员
		int effect = this.removeByPrimaryKey(id);
		if (effect > 0) {
			// 物理删除账号
			return adminUserMapper.wipeByPrimaryKey(id);
		}
		return effect;
	}

	public Page<SysUserVO> adminUserLog(OffsetPage offsetPage, String unitId) {
		Page<SysUserVO> page = PageHelper.offsetPage(offsetPage.getOffset(), offsetPage.getLimit());// 分页
		adminUserMapper.adminUserLog(unitId);
		return page;
	}
}
