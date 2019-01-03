package com.paladin.hf.mapper.assess.quantificate;

import java.util.List;

import com.paladin.hf.model.assess.quantificate.AssessCycleTemplate;



public interface AssessCycleTemplateMapper extends CustomerMapper<AssessCycleTemplate> {

	public int removeByCycle(String cycleId);

	
	/**
	 * 根据周期id查询周期模板对应表，前提是org_unit表的is_delete = 0
	 * 
	 */
    public List<AssessCycleTemplate> findRelationByCycle(String cycleId);



}