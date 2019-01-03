package com.paladin.hf.mapper.assess.quantificate;


public interface AssessLevelMapper extends CustomerMapper<AssessLevel>{
	
	public int removeAssessLevelOfTemplate(String id);

    public String searchTemplateIdByCycleId(String cycleId);
	
}
