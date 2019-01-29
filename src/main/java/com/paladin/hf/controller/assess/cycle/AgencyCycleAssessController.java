package com.paladin.hf.controller.assess.cycle;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.model.assess.cycle.PersonCycAssess;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.assess.cycle.PersonCycAssessService;
import com.paladin.hf.service.assess.cycle.dto.AgencyCycleAssessUpdateDTO;
import com.paladin.hf.service.assess.cycle.dto.AgencyQueryDTO;
import com.paladin.hf.service.assess.cycle.dto.UnassessedQuery;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/assess/cycle/agency")
public class AgencyCycleAssessController extends ControllerSupport {

	@Autowired
	private PersonCycAssessService perCycAssService;

	@Autowired
	private AssessCycleService assessCycleService;

	@RequestMapping("/index")
	@QueryInputMethod(queryClass = AgencyQueryDTO.class)
	public String index(Model model) {
		AgencyQueryDTO query = (AgencyQueryDTO) SecurityUtils.getSubject().getSession().getAttribute(AgencyQueryDTO.class.getName());
		if (query == null) {
			query = new AgencyQueryDTO();
			AssessCycle cycle = assessCycleService.getOwnedFirstAssessCycle();
			if (cycle != null) {
				query.setCycleId(cycle.getId());
				query.setCycleName(cycle.getCycleName());
				model.addAttribute("query", query);
			}
		}
		return "/hf/assess/cycle/agency_index";
	}

	@RequestMapping("/find")
	@ResponseBody
	@QueryOutputMethod(queryClass = AgencyQueryDTO.class, paramIndex = 0)
	public Object findPersonalPage(AgencyQueryDTO query) {
		return CommonResponse.getSuccessResponse(perCycAssService.findAgencyPage(query));
	}

	@RequestMapping("/get")
	@ResponseBody
	public Object getDetail(@RequestParam String userId, @RequestParam String cycleId) {
		return CommonResponse.getSuccessResponse(perCycAssService.getDetailByUserAndCycle(userId, cycleId));
	}

	@RequestMapping("/detail")
	public String detail(@RequestParam String userId, @RequestParam String cycleId, @RequestParam String userName, Model model) {
		model.addAttribute("userId", userId);
		model.addAttribute("cycleId", cycleId);
		model.addAttribute("userName", userName);
		model.addAttribute("name", HfUserSession.getCurrentUserSession().getUserName());
		return "/hf/assess/cycle/agency_detail";
	}

	@RequestMapping("/save/temporary")
	@ResponseBody
	public Object saveTemporary(@Valid AgencyCycleAssessUpdateDTO assess, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		if (perCycAssService.updateAgencyCycleAssess(assess, PersonCycAssess.STATUS_UNIT_GROUP_TEMPORARY)) {
			return CommonResponse.getSuccessResponse(perCycAssService.getDetail(assess.getId()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping("/save/submit")
	@ResponseBody
	public Object saveSubmit(@Valid AgencyCycleAssessUpdateDTO assess, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		if (perCycAssService.updateAgencyCycleAssess(assess, PersonCycAssess.STATUS_UNIT_GROUP_SUBMIT)) {
			return CommonResponse.getSuccessResponse(perCycAssService.getDetail(assess.getId()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping("/reject")
	@ResponseBody
	public Object back(@RequestParam String id, @RequestParam(required = false) String reason) {
		return CommonResponse.getResponse(perCycAssService.rejectAgencyCycleAssess(id, reason));
	}

	@RequestMapping("/submit")
	@ResponseBody
	public Object submit(@RequestParam String id) {
		return CommonResponse.getResponse(perCycAssService.submitAgencyCycleAssess(id));
	}

	/**
	 * 跳转未考评
	 * @author jisanjie
	 */
	@RequestMapping(value = "/to/no/assessment", method = { RequestMethod.GET })
	public String toNoAssessment(String assessCycleId,Model model) {
		model.addAttribute("assessCycleId", assessCycleId);
		return "/hf/assess/cycle/agency_unassessed";
	}

	/**
	 * 未考评
	 * @author jisanjie
	 */
	@RequestMapping(value = "/no/assessment")
	@ResponseBody
	public Object noAssessment(UnassessedQuery query){
		return CommonResponse.getSuccessResponse(perCycAssService.findUnassessedForAgency(query));
	}
}
