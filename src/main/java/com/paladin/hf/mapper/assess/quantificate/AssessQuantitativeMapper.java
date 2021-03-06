package com.paladin.hf.mapper.assess.quantificate;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.model.assess.quantificate.AssessEventScore;
import com.paladin.hf.model.assess.quantificate.AssessQuantitative;
import com.paladin.hf.service.assess.quantificate.dto.QuantitativeAgencyQuery;
import com.paladin.hf.service.assess.quantificate.dto.QuantitativeDepartmentQuery;
import com.paladin.hf.service.assess.quantificate.vo.AssessQuantitativeUserVO;

public interface AssessQuantitativeMapper extends CustomMapper<AssessQuantitative> {

	public List<AssessQuantitativeUserVO> findDepartmentUser(@Param("param") QuantitativeDepartmentQuery query, @Param("unitParam") UnitQuery unitQuery);

	public List<AssessQuantitativeUserVO> findAgencyUser(@Param("param") QuantitativeAgencyQuery query, @Param("unitParam") UnitQuery unitQuery);

	
	/**
	 * 查找考核奖惩事件分数
	 * 
	 * @param cycleId
	 * @param userId
	 * @return
	 */
	public List<AssessEventScore> findAssessEventScore(@Param("cycleId") String cycleId, @Param("userId") String userId,
			@Param("prizePunishId") String prizePunishId);

	/**
	 * 查找考核奖惩事件分数
	 * 
	 * @param cycleId
	 * @param userId
	 * @return
	 */
	public List<AssessEventScore> findUserAssessScore(@Param("cycleId") String cycleId, @Param("userId") String userId);

	public int countAssessQuantitative(@Param("prizePunishId") String prizePunishId);
	
}
