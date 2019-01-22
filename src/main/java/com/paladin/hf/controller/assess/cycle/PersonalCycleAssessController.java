package com.paladin.hf.controller.assess.cycle;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.model.assess.cycle.PersonCycAssess;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.assess.cycle.PersonCycAssessService;
import com.paladin.hf.service.assess.cycle.dto.PersonalCycleAssessConfirmDTO;
import com.paladin.hf.service.assess.cycle.dto.PersonalCycleAssessSaveDTO;
import com.paladin.hf.service.assess.cycle.dto.PersonalCycleAssessUpdateDTO;
import com.paladin.hf.service.assess.cycle.dto.PersonalQueryDTO;
import com.paladin.hf.service.org.OrgUserService;

/**
 * 个人周期考评 Controller
 * 
 * @author jisanjie
 * @version 2018年1月22日 下午3:09:57
 */
@Controller
@RequestMapping("/assess/cycle/personal")
public class PersonalCycleAssessController extends ControllerSupport {

	@Autowired
	private PersonCycAssessService perCycAssService;

	@Autowired
	private OrgUserService orgUserService;

	@RequestMapping("/index")
	public String index(Model model) {
		HfUserSession session = HfUserSession.getCurrentUserSession();
		if (!session.isOrgUser()) {
			return "/hf/no_business";
		}

		Unit unit = session.getUserAgency();
		model.addAttribute("unitName", unit.getName());
		return "/hf/assess/cycle/personal_index";
	}

	@RequestMapping("/find")
	@ResponseBody
	public Object findPersonalPage(PersonalQueryDTO query) {
		return CommonResponse.getSuccessResponse(perCycAssService.findPersonalPage(query));
	}

	@RequestMapping("/get")
	@ResponseBody
	public Object getDetail(@RequestParam String id) {
		return CommonResponse.getSuccessResponse(perCycAssService.getDetail(id));
	}

	@RequestMapping("/add")
	public String add(Model model) {
		OrgUser user = orgUserService.get(HfUserSession.getCurrentUserSession().getUserId());
		model.addAttribute("user", user);
		return "/hf/assess/cycle/personal_add";
	}

	@RequestMapping("/detail")
	public String detail(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "/hf/assess/cycle/personal_detail";
	}

	@RequestMapping("/has/assessed")
	@ResponseBody
	public Object judgeAssessed(@RequestParam String cycleId) {
		return CommonResponse.getSuccessResponse(perCycAssService.hasAssessedByUserAndCycle(HfUserSession.getCurrentUserSession().getUserId(), cycleId));
	}

	@RequestMapping("/save/temporary")
	@ResponseBody
	public Object saveTemporary(@Valid PersonalCycleAssessSaveDTO assess, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		return CommonResponse.getResponse(perCycAssService.savePersonalCycleAssess(assess, PersonCycAssess.STATUS_ASSESSED_TEMPORARY));
	}

	@RequestMapping("/save/submit")
	@ResponseBody
	public Object saveSubmit(@Valid PersonalCycleAssessSaveDTO assess, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		return CommonResponse.getResponse(perCycAssService.savePersonalCycleAssess(assess, PersonCycAssess.STATUS_ASSESSED_SUBMIT));
	}

	@RequestMapping("/update/temporary")
	@ResponseBody
	public Object updateTemporary(@Valid PersonalCycleAssessUpdateDTO assess, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}

		if (perCycAssService.updatePersonalCycleAssess(assess, PersonCycAssess.STATUS_ASSESSED_TEMPORARY)) {
			return CommonResponse.getSuccessResponse(perCycAssService.getDetail(assess.getId()));
		}

		return CommonResponse.getFailResponse();
	}

	@RequestMapping("/update/submit")
	@ResponseBody
	public Object updateSubmit(@Valid PersonalCycleAssessUpdateDTO assess, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}

		if (perCycAssService.updatePersonalCycleAssess(assess, PersonCycAssess.STATUS_ASSESSED_SUBMIT)) {
			return CommonResponse.getSuccessResponse(perCycAssService.getDetail(assess.getId()));
		}

		return CommonResponse.getFailResponse();
	}

	@RequestMapping("/confirm/input")
	public String toConfirm(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("name", HfUserSession.getCurrentUserSession().getUserName());
		return "/hf/assess/cycle/personal_confirm";
	}

	@RequestMapping("/confirm")
	@ResponseBody
	public Object confirm(@Valid PersonalCycleAssessConfirmDTO confirm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		return CommonResponse.getResponse(perCycAssService.confirmPersonalCycleAssess(confirm));
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam String id) {
		return CommonResponse.getResponse(perCycAssService.removePersonalCycleAssess(id));
	}

	@RequestMapping("/user/find")
	@ResponseBody
	public Object findUser(PersonalQueryDTO query) {
		return CommonResponse.getSuccessResponse(perCycAssService.findUserCycleAssess(query));
	}

	@RequestMapping("/user/detail")
	public String detailUser(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "/hf/assess/cycle/personal_detail_layer";
	}
	
}
