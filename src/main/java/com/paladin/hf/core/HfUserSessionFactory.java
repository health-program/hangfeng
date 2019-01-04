package com.paladin.hf.core;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.model.syst.AdminUser;
import com.paladin.hf.model.syst.SysUser;
import com.paladin.hf.service.org.OrgUserService;
import com.paladin.hf.service.syst.AdminUserService;

@Component
public class HfUserSessionFactory {

	@Autowired
	private OrgUserService orgUserService;

	@Autowired
	private AdminUserService adminUserService;

	public HfUserSession createOrgUser(OrgUser user, SysUser sysUser) {
		HfUserSession session = new HfUserSession(user.getId(), user.getName(), sysUser.getAccount(), SysUser.TYPE_ORG_USER);

		session.setUnitId(user.getOrgUnitId());
		session.setRoleId(user.getAssessRole());
		session.setOwnUnitId(user.getAssessUnitId());

		return session;
	}

	public HfUserSession createAdminUser(AdminUser user, SysUser sysUser) {
		HfUserSession session = new HfUserSession(user.getId(), user.getName(), sysUser.getAccount(), SysUser.TYPE_ADMIN_USER);
		String unitId = user.getUnitId();

		session.setUnitId(unitId);
		session.setRoleId(user.getRoles().split(","));
		session.setOwnUnitId(unitId);

		return session;
	}

	public HfUserSession createSystemAdmin(SysUser sysUser) {
		HfUserSession session = new HfUserSession(sysUser.getId(), "管理员", sysUser.getAccount(), SysUser.TYPE_ADMIN);
		return session;
	}

	public HfUserSession create(SysUser sysUser) {
		int type = sysUser.getType();

		if (type == SysUser.TYPE_ADMIN) {
			return createSystemAdmin(sysUser);
		} else if (type == SysUser.TYPE_ORG_USER) {

			String orgUserId = sysUser.getUserId();
			if (orgUserId == null || orgUserId.length() == 0)
				throw new AuthenticationException("该账号没有关联的用户");

			OrgUser orgUser = orgUserService.get(orgUserId);
			if (orgUser == null)
				throw new AuthenticationException("该账号没有关联的用户");

			return createOrgUser(orgUser, sysUser);

		} else if (type == SysUser.TYPE_ADMIN_USER) {

			String userId = sysUser.getUserId();
			if (userId == null || userId.length() == 0)
				throw new AuthenticationException("该账号没有关联的用户");

			AdminUser user = adminUserService.get(userId);

			if (user == null)
				throw new AuthenticationException("该账号没有关联的用户");

			return createAdminUser(user, sysUser);
		} else {
			throw new AuthenticationException("该账号异常");
		}

	}
}
