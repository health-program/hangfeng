package com.paladin.hf.service.assess.quantificate;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.mapper.assess.quantificate.AssessQuantitativeResultMapper;
import com.paladin.hf.model.assess.quantificate.AssessQuantitativeResult;

@Service
public class AssessQuantitativeResultService extends ServiceSupport<AssessQuantitativeResult> {

	@Autowired
	private AssessQuantitativeResultMapper assessQuantitativeResultMapper;
	
	public AssessQuantitativeResult getResult(String userId, String cycleId) {

		List<AssessQuantitativeResult> results = searchAll(
				new Condition[] { new Condition(AssessQuantitativeResult.COLUMN_USER_ID, QueryType.EQUAL, userId),
						new Condition(AssessQuantitativeResult.COLUMN_CYCLE_ID, QueryType.EQUAL, cycleId) });

		if (results != null && results.size() > 0) {
			if (results.size() > 1) {
				throw new BusinessException("无法取到用户考评周期唯一的考评结果");
			} else {
				return results.get(0);
			}
		}

		return null;
	}
	
	/**
	 * @author jisanjie
	 */
	@Transactional
	public int saveResult(AssessQuantitativeResult assessQuantitativeResult) {
		String userId = assessQuantitativeResult.getUserId();
		String cycleId = assessQuantitativeResult.getCycleId();
		assessQuantitativeResultMapper.removeResult(cycleId, userId);
		return this.save(assessQuantitativeResult);
	}

}
