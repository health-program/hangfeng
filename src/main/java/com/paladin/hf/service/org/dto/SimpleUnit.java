package com.paladin.hf.service.org.dto;

import com.paladin.hf.core.UnitContainer.Unit;

public class SimpleUnit {
	
	private String name;
	
	private String id;
	
	public SimpleUnit(Unit unit) {
		this.name = unit.getName();
		this.id = unit.getId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
