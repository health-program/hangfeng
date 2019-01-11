package com.paladin.common.core.permission;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.paladin.common.model.org.OrgPermission;
import com.paladin.common.model.org.OrgRole;
import com.paladin.framework.common.BaseModel;

public class Role {

	private String id;

	// 角色名称
	private String roleName;

	// 角色等级
	private int roleLevel;

	// 是否默认角色
	private boolean isDefault;

	// 是否启用
	private boolean enable;

	// 角色说明
	private String roleDesc;

	private HashMap<String, MenuPermission> menuPermissionMap;
	private HashSet<MenuPermission> rootMenuPermissionSet;
	private HashSet<String> permissionCodeSet;

	public Role(OrgRole orgRole) {
		this.id = orgRole.getId();
		this.roleName = orgRole.getRoleName();
		this.roleLevel = orgRole.getRoleLevel();
		this.roleDesc = orgRole.getRoleDesc();
		this.isDefault = orgRole.getIsDefault() == BaseModel.BOOLEAN_YES;
		this.enable = orgRole.getEnable() == BaseModel.BOOLEAN_YES;

		this.menuPermissionMap = new HashMap<>();
		this.rootMenuPermissionSet = new HashSet<>();
		this.permissionCodeSet = new HashSet<>();
	}

	public Role() {
		this.menuPermissionMap = new HashMap<>();
		this.rootMenuPermissionSet = new HashSet<>();
		this.permissionCodeSet = new HashSet<>();
	}

	public void addPermission(OrgPermission orgPermission, Map<String, OrgPermission> allPermissionMap) {
		String code = orgPermission.getExpressionContent();
		if (code != null && code.length() > 0) {
			permissionCodeSet.add(code);
		}

		String permissionId = orgPermission.getId();

		MenuPermission mp = menuPermissionMap.get(permissionId);
		if (mp == null) {
			mp = new MenuPermission(orgPermission, true);
			menuPermissionMap.put(permissionId, mp);

			String parentId = mp.getSource().getParentId();
			while (parentId != null && parentId.length() > 0) {
				if (menuPermissionMap.get(parentId) != null) {
					break;
				}

				OrgPermission op = allPermissionMap.get(parentId);
				if (op != null) {
					mp = new MenuPermission(op, false);
					menuPermissionMap.put(parentId, mp);
					parentId = op.getParentId();
				} else {
					break;
				}
			}
		} else {
			mp.setOwned(true);
		}
	}

	public void initMenuPermission() {
		for (MenuPermission mp : menuPermissionMap.values()) {
			String parentId = mp.getSource().getParentId();
			if (parentId == null || parentId.length() == 0) {
				rootMenuPermissionSet.add(mp);
			} else {
				MenuPermission parentMp = menuPermissionMap.get(parentId);
				if (parentMp == null) {
					rootMenuPermissionSet.add(mp);
				} else {
					parentMp.addChild(mp);
				}
			}
		}
		
		for (MenuPermission mp : rootMenuPermissionSet) {
			mp.init();
		}
	}

	public Collection<MenuPermission> getMenuPermissions() {
		return Collections.unmodifiableCollection(rootMenuPermissionSet);
	}

	public static Collection<MenuPermission> getMultiRoleMenuPermission(Collection<Role> roles) {

		HashSet<MenuPermission> root = new HashSet<>();
		HashMap<String, MenuPermission> mpMap = new HashMap<>();

		for (Role role : roles) {
			for (MenuPermission mp : role.menuPermissionMap.values()) {
				String id = mp.getSource().getId();
				MenuPermission a = mpMap.get(id);
				if (a != null) {
					if (a.isOwned() == false && mp.isOwned()) {
						a.setOwned(true);
					}
				} else {
					mpMap.put(id, new MenuPermission(mp.getSource(), mp.isOwned()));
				}
			}
		}

		for (MenuPermission mp : mpMap.values()) {
			String parentId = mp.getSource().getParentId();
			if (parentId == null || parentId.length() == 0) {
				root.add(mp);
			} else {
				MenuPermission parentMp = mpMap.get(parentId);
				if (parentMp == null) {
					root.add(mp);
				} else {
					parentMp.addChild(mp);
				}
			}
		}

		return root;
	}

	public boolean ownPermission(String code) {
		return permissionCodeSet.contains(code);
	}

	public boolean ownLevel(int roleLevel) {
		return this.roleLevel >= roleLevel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

}
