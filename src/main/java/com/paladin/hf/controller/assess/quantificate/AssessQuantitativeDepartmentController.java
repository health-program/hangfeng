package com.paladin.hf.controller.assess.quantificate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.utils.StringUtil;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.model.assess.quantificate.AssessEventScore;
import com.paladin.hf.model.assess.quantificate.AssessItem;
import com.paladin.hf.model.assess.quantificate.AssessItemExtra;
import com.paladin.hf.model.assess.quantificate.AssessQuantitative;
import com.paladin.hf.model.assess.quantificate.AssessQuantitativeResult;
import com.paladin.hf.model.ordinary.Prizepunish;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.assess.quantificate.AssessCycleTemplateService;
import com.paladin.hf.service.assess.quantificate.AssessItemExtraService;
import com.paladin.hf.service.assess.quantificate.AssessItemService;
import com.paladin.hf.service.assess.quantificate.AssessQuantitativeResultService;
import com.paladin.hf.service.assess.quantificate.AssessQuantitativeService;
import com.paladin.hf.service.assess.quantificate.dto.AssessQuantitativeUserQuery;
import com.paladin.hf.service.assess.quantificate.dto.QuantitativeBatchSaveDTO;
import com.paladin.hf.service.assess.quantificate.dto.QuantitativeDepartmentQuery;
import com.paladin.hf.service.ordinary.PrizepunishService;
import com.paladin.hf.service.org.OrgUserService;

@Controller
@RequestMapping("/assess/quantitative/department")
public class AssessQuantitativeDepartmentController extends ControllerSupport {

	@Autowired
	private AssessQuantitativeService assessQuantitativeService;

	@Autowired
	private AssessCycleService assessCycleService;

	@Autowired
	private PrizepunishService prizepunishService;

	@Autowired
	private AssessItemExtraService assessItemExtraService;

	@Autowired
	private AssessItemService assessItemService;

	@Autowired
	private AssessCycleTemplateService assessCycleTemplateService;

	@Autowired
	private AssessQuantitativeResultService assessQuantitativeResultService;

	@Autowired
	private OrgUserService userService;

	@RequestMapping(value = "/index")
	@QueryInputMethod(queryClass = QuantitativeDepartmentQuery.class)
	public String departmentIndex(@RequestParam(required = false) String cached, Model model) {
		QuantitativeDepartmentQuery query = null;
		AssessCycle assessCycle = null;
		if (cached != null && cached.length() > 0) {// 查询条件回显
			query = (QuantitativeDepartmentQuery) SecurityUtils.getSubject().getSession().getAttribute(QuantitativeDepartmentQuery.class.getName());
			if (query != null) {
				String assessCycleId = query.getAssessCycleId();
				if (assessCycleId != null && assessCycleId.length() > 0) {
					assessCycle = assessCycleService.get(assessCycleId);
				}
			}
		}

		if (assessCycle != null) {
			model.addAttribute("assessCycleId", assessCycle.getId());
			model.addAttribute("assessCycleName", assessCycle.getCycleName());
		}

		if (query == null) {
			query = new QuantitativeDepartmentQuery();
			assessCycle = assessCycleService.getOwnedFirstAssessCycle();
			if (assessCycle != null) {
				query.setAssessCycleId(assessCycle.getId());
				query.setAssessCycleName(assessCycle.getCycleName());
				model.addAttribute("query", query);
			}
		}
		return "/hf/assess/quantificate/department_quantificate_index";
	}

	@ResponseBody
	@RequestMapping("/user/find")
	@QueryOutputMethod(queryClass = QuantitativeDepartmentQuery.class, paramIndex = 0)
	public Object findDepartmentUser(QuantitativeDepartmentQuery query) {
		return CommonResponse.getSuccessResponse(assessQuantitativeService.findDepartmentUser(query));
	}

	@RequestMapping(value = "/user/index")
	public String quantitativeDepartmentIndex(@RequestParam String userId, @RequestParam String userName, @RequestParam String cycleId, Model model) {
		AssessCycle assessCycle = assessCycleService.get(cycleId);
		model.addAttribute("assessCycleId", assessCycle.getId());
		model.addAttribute("assessCycleName", assessCycle.getCycleName());
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		return "/hf/assess/quantificate/department_quantificate_user_index";
	}

	@ResponseBody
	@RequestMapping(value = "/user/detail")
	public Object quantitativeDetail(AssessQuantitativeUserQuery query) {
		if (StringUtil.isEmpty(query.getCycleId())) {
			return CommonResponse.getSuccessResponse();
		}
		return CommonResponse.getSuccessResponse(assessQuantitativeService.findUserAssessDetail(query));
	}

	@RequestMapping(value = "/user/score/index")
	public String scoreDepartmentIndex(@RequestParam String userId, @RequestParam String cycleId, @RequestParam String userName,
			@RequestParam String prizePunishId, Model model) {

		Prizepunish prizepunish = prizepunishService.get(prizePunishId);

		model.addAttribute("prizepunish", prizepunish);
		model.addAttribute("cycleId", cycleId);
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		return "/hf/assess/quantificate/department_quantificate_score_index";
	}

	@ResponseBody
	@RequestMapping(value = "/user/score/list")
	public Object scoreList(@RequestParam String userId, @RequestParam String cycleId, @RequestParam String prizePunishId) {
		return CommonResponse.getSuccessResponse(assessQuantitativeService.findAssessEventScore(cycleId, userId, prizePunishId));
	}

	@ResponseBody
	@RequestMapping(value = "/user/score/save")
	public Object saveScore(AssessQuantitative assessQuantitative) {

		return CommonResponse.getResponse(assessQuantitativeService.saveOrUpdate(assessQuantitative));
	}

	@ResponseBody
	@RequestMapping(value = "/user/score/delete")
	public Object removeScore(@RequestParam String id) {
		return CommonResponse.getResponse(assessQuantitativeService.removeByPrimaryKey(id));
	}

	@RequestMapping(value = "/user/result")
	public String quantitativeDepartmentResult(@RequestParam String userId, @RequestParam String cycleId, @RequestParam String userName, Model model) {
		model.addAttribute("cycleId", cycleId);
		model.addAttribute("userId", userId);
		model.addAttribute("userName", userName);
		return "/hf/assess/quantificate/department_quantificate_user_result";
	}

	@ResponseBody
	@RequestMapping("/result/detail")
	public Object detailAssessResult(@RequestParam String cycleId, @RequestParam String userId) {
		Map<String, Object> result = new HashMap<>();
		AssessQuantitativeResult aqResult = assessQuantitativeResultService.getResult(userId, cycleId);

		String unit = null;
		if (aqResult != null) {
			unit = aqResult.getUnitId();
			result.put("hasAssessed", 1);
		} else {
			result.put("hasAssessed", 0);
		}
		
		if (unit == null || unit.length() == 0) {
			unit = userService.get(userId).getOrgUnitId();
		}

		String templateId = assessCycleTemplateService.getTemplateByCycle(cycleId, unit);

		List<AssessItem> items = assessItemService.findTemplateAssessItem(templateId);
		List<AssessItemExtra> extras = assessItemExtraService.findTemplateAssessExtraItem(templateId);

		result.put("items", items);
		result.put("extras", extras);

		if (aqResult != null) {
			result.put("result", aqResult);
		} else {

			double baseScore = 0d;
			double addScore = 0d;
			double reduceScore = 0d;
			boolean isVeto = false;

			for (AssessItem item : items) {
				if (item.getParentItemId() == null) {
					baseScore += item.getBasicScore();
				}
			}

			List<AssessEventScore> scores = assessQuantitativeService.findUserAssessScore(userId, cycleId);

			Map<String, List<AssessEventScore>> extra2scoreMap = new HashMap<>();

			for (AssessEventScore score : scores) {

				String extraType = score.getExtraType();

				if (extraType.equals(AssessItemExtra.EXTRA_TYPE_VETO)) {
					isVeto = true;
					continue;
				}

				String extraId = score.getExtraId();

				List<AssessEventScore> subScores = extra2scoreMap.get(extraId);
				if (subScores == null) {
					subScores = new ArrayList<>();
					extra2scoreMap.put(extraId, subScores);
				}
				subScores.add(score);
			}

			for (List<AssessEventScore> subScores : extra2scoreMap.values()) {
				// 累计分数，并且判断是否超过上限
				double score = 0d;
				AssessEventScore first = subScores.get(0);
				Double accumulateUpper = first.getAccumulateUpper();
				String type = first.getExtraType();

				for (AssessEventScore subScore : subScores) {
					score += subScore.getScore();
				}

				if (score > 0) {
					if (accumulateUpper != null) {
						score = Math.min(accumulateUpper, score);
					}

					if (type.equals(AssessItemExtra.EXTRA_TYPE_ADD_SCORE)) {
						addScore += score;
					} else if (type.equals(AssessItemExtra.EXTRA_TYPE_LESS_SCORE)) {
						reduceScore += score;
					}
				}
			}

			result.put("baseScore", baseScore);
			result.put("addScore", addScore);
			result.put("reduceScore", reduceScore);
			result.put("isVeto", isVeto ? 1 : 0);
		}

		return CommonResponse.getSuccessResponse(result);
	}

	@ResponseBody
	@RequestMapping(value = "/result/save")
	public Object saveScore(AssessQuantitativeResult assessQuantitativeResult) {
		// 将被评人当时所属的组织信息录入量化考评结果表
		OrgUser orgUser = userService.get(assessQuantitativeResult.getUserId());
		assessQuantitativeResult.setAgencyId(orgUser.getOrgAgencyId());
		assessQuantitativeResult.setUnitId(orgUser.getOrgUnitId());
		assessQuantitativeResult.setAssessTeamId(orgUser.getOrgAssessTeamId());
		return CommonResponse.getResponse(assessQuantitativeResultService.saveResult(assessQuantitativeResult));
	}

	@RequestMapping(value = "/batch/index")
	public String batchIndex(Model model) {
		AssessCycle assessCycle = assessCycleService.getOwnedFirstAssessCycle();
		if (assessCycle != null) {
			model.addAttribute("assessCycleId", assessCycle.getId());
			model.addAttribute("assessCycleName", assessCycle.getCycleName());
		}
		return "/hf/assess/quantificate/department_quantificate_batch";
	}
	
	@RequestMapping(value = "/batch/find")
	@ResponseBody
	public Object batchFind(QuantitativeDepartmentQuery query) {
		return CommonResponse.getSuccessResponse(assessQuantitativeService.findDepartmentUser(query));
	}
	
	@RequestMapping(value = "/batch/save")
	@ResponseBody
	public Object batchSave(@RequestBody List<QuantitativeBatchSaveDTO> batchSaveDtos) {
		return CommonResponse.getSuccessResponse(assessQuantitativeResultService.batchSaveResult(batchSaveDtos));
	}
	
	@ResponseBody
	@RequestMapping("/result")
	public Object getQuantitativeAssessResult(@RequestParam String cycleId, @RequestParam String userId) {		
		return CommonResponse.getSuccessResponse(assessQuantitativeResultService.getResult(userId, cycleId));		
	}
	
}
