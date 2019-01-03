package com.paladin.hf.mapper.assess.quantificate;

import org.apache.ibatis.annotations.Param;

import com.netmatch.model.console.AssessQuantitativeResult;
import com.netmatch.util.CustomerMapper;

public interface AssessQuantitativeResultMapper extends CustomerMapper<AssessQuantitativeResult>{

	public int removeResult(@Param("cycleId") String cycleId, @Param("userId") String userId);

}
