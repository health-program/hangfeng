package com.paladin.hf.service.assess.quantificate;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.mapper.assess.quantificate.AssessQuantitativeResultMapper;
import com.paladin.hf.model.assess.quantificate.AssessQuantitativeResult;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.assess.quantificate.dto.QuantitativeBatchSaveDTO;
import com.paladin.hf.service.org.OrgUserService;

@Service
public class AssessQuantitativeResultService extends ServiceSupport<AssessQuantitativeResult> {

	@Autowired
	private AssessQuantitativeResultMapper assessQuantitativeResultMapper;

	@Autowired
	private OrgUserService userService;

	public AssessQuantitativeResult getResult(String userId, String cycleId) {

		List<AssessQuantitativeResult> results = searchAll(new Condition[] { new Condition(AssessQuantitativeResult.COLUMN_USER_ID, QueryType.EQUAL, userId),
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
	 * 是否量化
	 * @param assessCycleId
	 * @return
	 */
	public boolean hasResult(String assessCycleId) {
		return assessQuantitativeResultMapper.countResultByAssessCycle(assessCycleId) > 0;
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

	/**
	 * 批量存储量化结果
	 * 
	 * @param batchSaveDtos
	 * @return
	 */
	@Transactional
	public int batchSaveResult(List<QuantitativeBatchSaveDTO> batchSaveDtos) {
		int effect = 0;
		for (QuantitativeBatchSaveDTO dto : batchSaveDtos) {
			AssessQuantitativeResult result = new AssessQuantitativeResult();
			SimpleBeanCopyUtil.simpleCopy(dto, result);

			// 将被评人当时所属的组织信息录入量化考评结果表
			String userId = result.getUserId();
			String cycleId = result.getCycleId();

			if (userId == null || userId.length() == 0) {
				throw new BusinessException("找不到对应考评人员");
			}
			if (cycleId == null || cycleId.length() == 0) {
				throw new BusinessException("考评周期不能为空");
			}

			OrgUser orgUser = userService.get(userId);
			if (orgUser == null) {
				throw new BusinessException("找不到对应考评人员");
			}

			result.setAgencyId(orgUser.getOrgAgencyId());
			result.setUnitId(orgUser.getOrgUnitId());
			result.setAssessTeamId(orgUser.getOrgAssessTeamId());
			assessQuantitativeResultMapper.removeResult(cycleId, userId);
			effect += save(result);
		}
		return effect;
	}
}
