package com.paladin.hf.mapper.org;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.org.OrgUnit;

public interface OrgUnitMapper extends CustomMapper<OrgUnit>{
	
	public int deleteNoChildUnit(String id);
	
	public int removeNoChildUnit(String id);
	
	public int countUserOfUnit(@Param("unitId") String unitId);
}
