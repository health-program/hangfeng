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
import com.paladin.hf.service.assess.cycle.dto.DepartmentCycleAssessBatchDTO;
import com.paladin.hf.service.assess.cycle.dto.DepartmentCycleAssessUpdateDTO;
import com.paladin.hf.service.assess.cycle.dto.DepartmentQueryDTO;
import com.paladin.hf.service.assess.cycle.dto.UnassessedQuery;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.validation.Valid;

@Controller
@RequestMapping("/assess/cycle/department")
public class DepartmentCycleAssessController extends ControllerSupport {

	@Autowired
	private PersonCycAssessService perCycAssService;

	@Autowired
	private AssessCycleService assessCycleService;

	@RequestMapping("/index")
	@QueryInputMethod(queryClass = DepartmentQueryDTO.class)
	public String index(Model model) {
		DepartmentQueryDTO query = (DepartmentQueryDTO) SecurityUtils.getSubject().getSession().getAttribute(DepartmentQueryDTO.class.getName());
		if (query == null) {
			query = new DepartmentQueryDTO();
			AssessCycle cycle = assessCycleService.getOwnedFirstAssessCycle();
			if (cycle != null) {
				query.setCycleId(cycle.getId());
				query.setCycleName(cycle.getCycleName());
				model.addAttribute("query", query);
			}
		}
		return "/hf/assess/cycle/department_index";
	}

	@RequestMapping("/find")
	@ResponseBody
	@QueryOutputMethod(queryClass = DepartmentQueryDTO.class, paramIndex = 0)
	public Object findPersonalPage(DepartmentQueryDTO query) {
		return CommonResponse.getSuccessResponse(perCycAssService.findDepartmentPage(query));
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
		return "/hf/assess/cycle/department_detail";
	}

	@RequestMapping("/save/temporary")
	@ResponseBody
	public Object saveTemporary(@Valid DepartmentCycleAssessUpdateDTO assess, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		if (perCycAssService.updateDepartmentCycleAssess(assess, PersonCycAssess.STATUS_DEPART_TEMPORARY)) {
			return CommonResponse.getSuccessResponse(perCycAssService.getDetail(assess.getId()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping("/save/submit")
	@ResponseBody
	public Object saveSubmit(@Valid DepartmentCycleAssessUpdateDTO assess, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		if (perCycAssService.updateDepartmentCycleAssess(assess, PersonCycAssess.STATUS_DEPART_SUBMIT)) {
			return CommonResponse.getSuccessResponse(perCycAssService.getDetail(assess.getId()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping("/reject")
	@ResponseBody
	public Object back(@RequestParam String id, @RequestParam(required = false) String reason) {
		return CommonResponse.getResponse(perCycAssService.rejectDepartmentCycleAssess(id, reason));
	}

	@RequestMapping("/submit")
	@ResponseBody
	public Object submit(@RequestParam String id) {
		return CommonResponse.getResponse(perCycAssService.submitDepartmentCycleAssess(id));
	}

	@RequestMapping(value = "/to/no/assessment", method = { RequestMethod.GET })
	public String toNoAssessment(String assessCycleId, Model model) {
		model.addAttribute("assessCycleId", assessCycleId);
		return "/hf/assess/cycle/department_unassessed";
	}

	@RequestMapping(value = "/no/assessment")
	@ResponseBody
	public Object noAssessment(UnassessedQuery query){
		return CommonResponse.getSuccessResponse(perCycAssService.findUnassessedForDepartment(query));
	}
	
	@RequestMapping("/batch/index")
	public String batchIndex(Model model) {
		AssessCycle assessCycle = assessCycleService.getOwnedFirstAssessCycle();
		if (assessCycle != null) {
			model.addAttribute("cycleId", assessCycle.getId());
			model.addAttribute("cycleName", assessCycle.getCycleName());
		}
		return "/hf/assess/cycle/department_batch";
	}

	@RequestMapping("/batch/find")
	@ResponseBody
	public Object batchFind(DepartmentQueryDTO query) {
		return CommonResponse.getSuccessResponse(perCycAssService.findDepartmentPage(query));
	}
	
	@RequestMapping(value = "/batch/save")
	@ResponseBody
	public Object batchSave(@RequestBody List<DepartmentCycleAssessBatchDTO> batchSaveDtos) {
		return CommonResponse.getResponse(perCycAssService.departmentBatchSaveResult(batchSaveDtos));
	}
}
