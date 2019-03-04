package com.paladin.hf.mapper.assess.quantificate;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.assess.quantificate.AssessQuantitativeResult;

public interface AssessQuantitativeResultMapper extends CustomMapper<AssessQuantitativeResult>{

	public int removeResult(@Param("cycleId") String cycleId, @Param("userId") String userId);

	public int countResultByAssessCycle(@Param("assessCycleId") String assessCycleId);
	
	

}
