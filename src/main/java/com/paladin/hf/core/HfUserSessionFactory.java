package com.paladin.hf.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class HfUserSessionFactory {

	@Autowired
	private OrgUserService orgUserService;

	@Autowired
	private OrgRoleService orgRoleService;

	public HfUserSession create(OrgUser user, SysUser sysUser) {
		String account = sysUser.getAccount();
		String roleId = user.getAssessRole();
		Role role = RoleContainer.getRole(roleId);
		HfUserSession session = new HfUserSession(user.getId(), user.getName(), SysUser.TYPE_ORG_USER);
		
		session.account = account;
		
		session.unitId = user.getOrgUnitId();
		session.agencyId = user.getOrgAgencyId();

		session.roleIds = new String[] { roleId };
		session.roleLevel = role.getOrgRole().getRoleLevel();

		session.ownUnitId = user.getAssessUnitId();
				
		return session;
		
	}

	public HfUserSession create(AdminUser user, SysUser sysUser) {
		String account = sysUser.getAccount();

		HfUserSession session = new HfUserSession(user.getId(), user.getName(), SysUser.TYPE_ADMIN_USER);
		session.roleIds = user.getRoles().split(",");
		session.roleLevel = RoleContainer.getRoleLevel(session.roleIds);

		String unitId = user.getUnitId();
		session.account = account;

		session.unitId = unitId;
		session.agencyId = unitId;
		session.ownUnitId = unitId;
				
		return session;
	}

	public HfUserSession createAdmin(SysUser sysUser) {
		HfUserSession session = new HfUserSession(sysUser.getId(), "管理员", SysUser.TYPE_ADMIN);
		session.account = sysUser.getAccount();

		return session;
	}

}
