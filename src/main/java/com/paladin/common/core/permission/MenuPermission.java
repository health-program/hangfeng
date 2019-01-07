package com.paladin.common.core.permission;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.paladin.common.model.org.OrgPermission;
import com.paladin.framework.common.BaseModel;

public class MenuPermission {

	private OrgPermission source;

	// 是否拥有
	private boolean owned;
	// 是否菜单
	private boolean isMenu;

	private Set<MenuPermission> children;

	public MenuPermission(OrgPermission orgPermission, boolean owned) {
		this.source = orgPermission;
		this.isMenu = orgPermission.getIsMenu() == BaseModel.BOOLEAN_YES;
		this.owned = owned;
		this.children = new HashSet<>();
	}

	public void addChild(MenuPermission child) {
		children.add(child);
	}

	public String getId() {
		return source.getId();
	}

	public boolean isOwned() {
		return owned;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}

	public OrgPermission getSource() {
		return source;
	}

	public boolean isMenu() {
		return isMenu;
	}

	public Collection<MenuPermission> getChildren() {
		return children;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof MenuPermission) {
			MenuPermission mp = (MenuPermission) obj;
			return getId().equals(mp.getId());
		}
		return false;
	}

}
