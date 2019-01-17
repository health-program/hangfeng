package com.paladin.hf.controller.assess.quantificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.model.assess.quantificate.AssessLevel;
import com.paladin.hf.service.assess.cycle.PersonCycAssessService;
import com.paladin.hf.service.assess.quantificate.AssessLevelService;


@Controller
@RequestMapping("/assess/template/level")
public class AssessLevelController extends ControllerSupport {

	@Autowired
	AssessLevelService assessLevelService;
	
	@Autowired
	PersonCycAssessService personCycAssessService;
	
	@RequestMapping(value = "/index")
	public String index(Model model) {
		return "/hf/assess/quantificate/assess_level_index";
	}
	
	@RequestMapping(value = "/view")
    public String view(Model model) {
        return "/hf/assess/quantificate/assess_level_view";
    }
	
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(@RequestParam(required = true) String id) {
		return CommonResponse.getSuccessResponse(assessLevelService.findTemplateAssessLevel(id));
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/save")
	public Object save(@RequestBody AssessLevel[] assessLevels) {
		return CommonResponse.getResponse(assessLevelService.saveTemplateAssessLevel(assessLevels));
	}
	
	/**
	 * 通过周期id查找模板id
	 * @author jisanjie
	 */
    @RequestMapping(value = "/searchTemplateIdByCycleId")
	public String searchTemplateIdByCycleId(String cycleId,String buserId, Model model){
	      String templateId = assessLevelService.searchTemplateIdByCycleId(cycleId);
//	      UserSession session = UserSession.getCurrentUserSession();
//	      String userId = session.getUserId();
	      model.addAttribute("btemplateId", templateId);
	      model.addAttribute("assessCycleId", cycleId);
	      model.addAttribute("orgUserId", buserId);
	      return "/hf/assess/quantificate/quantitative_result_view";
	}
}
