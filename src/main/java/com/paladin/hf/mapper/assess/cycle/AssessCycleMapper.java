package com.paladin.hf.mapper.assess.cycle;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.assess.cycle.AssessCycle;

public interface AssessCycleMapper extends CustomMapper<AssessCycle> {

	public int startAssessCycle(String id);
	
	public int archiveAssessCycle(String id);

	public int removeAssessCycle(String id);

	public int stopAssessCycle(String id);

	public AssessCycle getAgencyFirstAssessCycle(String agencyId);
	
    public int countByAssessCycleId(String id);

}