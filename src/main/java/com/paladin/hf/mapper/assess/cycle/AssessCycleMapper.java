package com.paladin.hf.mapper.assess.cycle;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.assess.cycle.AssessCycle;

public interface AssessCycleMapper extends CustomMapper<AssessCycle> {

	public int startAssessCycle(String id);
	
	public int archiveAssessCycle(String id);

	public int removeAssessCycle(String id);

	public int stopAssessCycle(String id);

	public AssessCycle getAgencyFirstAssessCycle(String agencyId);
	
	/**
     * <周期启用时判断是否模板配置>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
	int cycleCount(String id);

    public int selectTemplateIdByAssessCycleId(String id);

}