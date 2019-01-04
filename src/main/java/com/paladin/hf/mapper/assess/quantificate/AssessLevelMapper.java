package com.paladin.hf.mapper.assess.quantificate;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.assess.quantificate.AssessLevel;

public interface AssessLevelMapper extends CustomMapper<AssessLevel>{
	
	public int removeAssessLevelOfTemplate(String id);

    public String searchTemplateIdByCycleId(String cycleId);
	
}
