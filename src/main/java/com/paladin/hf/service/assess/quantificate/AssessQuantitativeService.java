package com.paladin.hf.service.assess.quantificate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.mapper.assess.quantificate.AssessQuantitativeMapper;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.model.assess.quantificate.AssessEventScore;
import com.paladin.hf.model.assess.quantificate.AssessItemExtra;
import com.paladin.hf.model.assess.quantificate.AssessQuantitative;
import com.paladin.hf.model.ordinary.Prizepunish;
import com.paladin.hf.model.ordinary.PrizepunishScore;
import com.paladin.hf.model.org.OrgUserAssess;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.assess.quantificate.dto.QuantificatedOrgUserDTO;
import com.paladin.hf.service.assess.quantificate.pojo.AssessQuantitativeUserDetailQuery;
import com.paladin.hf.service.assess.quantificate.pojo.AssessQuantitativeUserQuery;
import com.paladin.hf.service.ordinary.PrizepunishService;

@Service
public class AssessQuantitativeService extends ServiceSupport<AssessQuantitative> {

	@Autowired
	AssessQuantitativeMapper assessQuantitativeMapper;

	@Autowired
	AssessCycleService assessCycleService;

	@Autowired
	PrizepunishService prizepunishService;

	/**
	 * 查找考核用户信息
	 * 
	 * @param query
	 * @return
	 */
	public PageResult<OrgUserAssess> findUserAssess(AssessQuantitativeUserQuery query) {
		Page<OrgUserAssess> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
		UnitQuery unitQuery = DataPermissionUtil.getUnitQueryDouble(query.getUnitId());
		assessQuantitativeMapper.findUserAssess(query, unitQuery);
		return new PageResult<>(page);
	}

	/**
	 * 查找周期内该用户的奖惩详情
	 * 
	 * @param userId
	 * @param cycleId
	 * @return
	 */
	public List<PrizepunishScore> findUserAssessDetail(AssessQuantitativeUserDetailQuery query) {

		String cycleId = query.getCycleId();
		String userId = query.getUserId();
		Date startTime = query.getStartHappenTime();
		Date endTime = query.getEndHappenTime();
		Integer eventType = query.getEventType();

		AssessCycle assessCycle = assessCycleService.get(cycleId);

		Date cycleStartTime = assessCycle.getCycleStartTime();
		Date cycleEndTime = assessCycle.getCycleEndTime();

		if (startTime == null || startTime.getTime() < cycleStartTime.getTime()) {
			startTime = cycleStartTime;
		}

		if (endTime == null || endTime.getTime() < cycleEndTime.getTime()) {
			endTime = cycleEndTime;
		}

		List<Condition> conditions = new ArrayList<>();
		conditions.add(new Condition(Prizepunish.COLUMN_OPERATION_STATE, QueryType.IN,
				new Integer[] { Prizepunish.OPERATION_STATE_DEPARTMENT_SUBMIT, Prizepunish.OPERATION_STATE_AGENCY_SUBMIT }));
		conditions.add(new Condition(Prizepunish.COLUMN_ORG_USER_ID, QueryType.EQUAL, userId));
		conditions.add(new Condition(Prizepunish.COLUMN_HAPPEN_TIME, QueryType.GREAT_EQUAL, startTime));
		conditions.add(new Condition(Prizepunish.COLUMN_HAPPEN_TIME, QueryType.LESS_EQUAL, endTime));

		if (eventType != null) {
			conditions.add(new Condition(Prizepunish.COLUMN_DICT_CODE, QueryType.EQUAL, eventType));
		}

		List<Prizepunish> prizepunishs = prizepunishService.searchAll(conditions);
		List<PrizepunishScore> prizepunishScores = new ArrayList<>(prizepunishs.size());
		for (Prizepunish prizepunish : prizepunishs) {

			PrizepunishScore prizepunishScore = new PrizepunishScore(prizepunish);

			List<AssessEventScore> eventScores = assessQuantitativeMapper.findAssessEventScore(cycleId, userId, prizepunish.getId());
			if (eventScores != null && eventScores.size() > 0) {
				double addScore = 0d;
				double reduceScore = 0d;
				boolean isVeto = false;

				for (AssessEventScore eventScore : eventScores) {
					String type = eventScore.getExtraType();
					if (AssessItemExtra.EXTRA_TYPE_ADD_SCORE.equals(type)) {
						addScore += eventScore.getScore();
					} else if (AssessItemExtra.EXTRA_TYPE_LESS_SCORE.equals(type)) {
						reduceScore += eventScore.getScore();
					} else if (AssessItemExtra.EXTRA_TYPE_VETO.equals(type)) {
						isVeto = true;
					}
				}

				if (addScore > 0) {
					prizepunishScore.setAddScore(addScore);
				}

				if (reduceScore > 0) {
					prizepunishScore.setReduceScore(reduceScore);
				}

				if (isVeto) {
					prizepunishScore.setIsVeto(true);
				}

			}

			prizepunishScores.add(prizepunishScore);
		}

		return prizepunishScores;
	}

	/**
	 * 查询用户评分结果
	 * 
	 * @param userId
	 * @param cycleId
	 * @return
	 */
	public List<AssessEventScore> findUserAssessScore(String userId, String cycleId) {
		return assessQuantitativeMapper.findUserAssessScore(cycleId, userId);
	}

	/**
	 * 查找考核奖惩事件分数
	 * 
	 * @param cycleId
	 * @param userId
	 * @param prizePunishId
	 * @return
	 */
	public List<AssessEventScore> findAssessEventScore(String cycleId, String userId, String prizePunishId) {
		return assessQuantitativeMapper.findAssessEventScore(cycleId, userId, prizePunishId);
	}

	public int saveOrUpdate(AssessQuantitative model) {

		if (model.getId() == null || model.getId().length() == 0) {
			List<AssessQuantitative> result = searchAll(
					new Condition[] { new Condition(AssessQuantitative.COLUMN_FIELD_USER_ID, QueryType.EQUAL, model.getUserId()),
							new Condition(AssessQuantitative.COLUMN_FIELD_CYCLE_ID, QueryType.EQUAL, model.getCycleId()),
							new Condition(AssessQuantitative.COLUMN_FIELD_PRIZEPUNISH_ID, QueryType.EQUAL, model.getPrizePunishId()),
							new Condition(AssessQuantitative.COLUMN_FIELD_ASSESS_ITEM_EXTRA_ID, QueryType.EQUAL, model.getAssessItemExtraId()) });

			if (result != null && result.size() > 0) {
				throw new BusinessException("无法对同一奖惩内容做多次评分");
			}
		}

		return super.saveOrUpdate(model);
	}

}
