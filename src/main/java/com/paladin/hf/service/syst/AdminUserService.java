package com.paladin.hf.service.syst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.common.core.permission.PermissionContainer;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.mapper.syst.AdminUserMapper;
import com.paladin.hf.model.syst.AdminUser;
import com.paladin.hf.service.syst.dto.AdminUserDTO;

@Service
public class AdminUserService extends ServiceSupport<AdminUser> {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private AdminUserMapper adminUserMapper;

	@Transactional
	public boolean saveAdminUser(AdminUserDTO userDTO) {
		AdminUser user = new AdminUser();
		SimpleBeanCopyUtil.simpleCopy(userDTO, user);

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

		String id = UUIDUtil.createUUID();
		user.setId(id);

		if (sysUserService.createSysUserAccount(user.getAccount(), user.getId()) > 0) {
			return save(user) > 0;
		}
		return false;
	}

	@Transactional
	public boolean updateAdminUser(AdminUserDTO userDTO) {
		AdminUser user = get(userDTO.getId());
		if (user == null) {
			throw new BusinessException("找不到对应更新的管理人员");
		}

		String originAccount = user.getAccount();

		SimpleBeanCopyUtil.simpleCopy(userDTO, user);

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

		String nowAccount = user.getAccount();
		if (nowAccount == null || nowAccount.length() == 0) {
			throw new BusinessException("账号不能为空");
		}

		if (!nowAccount.equals(originAccount)) {
			if (sysUserService.updateAccount(user.getId(), originAccount, nowAccount) <= 0) {
				throw new BusinessException("更新账号失败");
			}
		}

		return update(user) > 0;
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
}
