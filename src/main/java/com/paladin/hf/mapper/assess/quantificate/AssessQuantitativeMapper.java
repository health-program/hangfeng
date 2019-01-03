package com.paladin.hf.mapper.assess.quantificate;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paladin.hf.model.assess.quantificate.AssessEventScore;
import com.paladin.hf.model.org.OrgUserAssess;
import com.paladin.hf.service.assess.quantificate.pojo.AssessQuantitativeUserQuery;


public interface AssessQuantitativeMapper extends CustomerMapper<AssessQuantitative> {
	
	public List<OrgUserAssess> findUserAssess(@Param("param")AssessQuantitativeUserQuery query, @Param("unitParam")UnitQuery unitQuery);
	
	/**
	 * 查找考核奖惩事件分数
	 * @param cycleId
	 * @param userId
	 * @return
	 */
	public List<AssessEventScore> findAssessEventScore(@Param("cycleId") String cycleId, @Param("userId") String userId, @Param("prizePunishId") String prizePunishId);
	
	
	/**
	 * 查找考核奖惩事件分数
	 * @param cycleId
	 * @param userId
	 * @return
	 */
	public List<AssessEventScore> findUserAssessScore(@Param("cycleId") String cycleId, @Param("userId") String userId);
	
}
