package com.paladin.hf.controller.assess.quantificate;

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
import com.paladin.hf.model.assess.quantificate.AssessItem;
import com.paladin.hf.model.assess.quantificate.AssessItemExtra;
import com.paladin.hf.model.assess.quantificate.AssessQuantitativeResult;
import com.paladin.hf.service.assess.quantificate.AssessCycleTemplateService;
import com.paladin.hf.service.assess.quantificate.AssessItemExtraService;
import com.paladin.hf.service.assess.quantificate.AssessItemService;
import com.paladin.hf.service.assess.quantificate.AssessQuantitativeResultService;
import com.paladin.hf.service.org.OrgUserService;

@Controller
@RequestMapping("/console/assess/item")
public class AssessItemController extends ControllerSupport{
	
	@Autowired
	AssessItemService assessItemService;
	
	@Autowired
	AssessItemExtraService assessItemExtraService;
	
	@Autowired
	AssessCycleTemplateService assessCycleTemplateService;
	
	@Autowired
	OrgUserService orgUserService;
	
	@Autowired
    AssessQuantitativeResultService assessQuantitativeResultService;
	
	@RequestMapping(value = "/index")
	public String index(Model model) {
		return "hf/assess/quantificate/index";
	}
	
	@ResponseBody
	@RequestMapping(value = "/template")
	public Object getTemplateItems(@RequestParam(required = true) String id) {		
		return CommonResponse.getSuccessResponse(assessItemService.findTemplateAssessItem(id));
	}
		
	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid AssessItem assessItem, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		return CommonResponse.getResponse(assessItemService.saveOrUpdate(assessItem));
	}
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(assessItemService.removeByPrimaryKey(id));
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/extra/template")
	public Object getTemplateExtraItems(@RequestParam(required = true) String id) {		
		return CommonResponse.getSuccessResponse(assessItemExtraService.findTemplateAssessExtraItem(id));
	}
	
	@RequestMapping("/extra/save")
	@ResponseBody
	public Object saveExtra(@Valid AssessItemExtra assessItemExtra, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		
		if(AssessItemExtra.EXTRA_TYPE_VETO.equals(assessItemExtra.getExtraType())) {
			assessItemExtra.setAlterLower(null);
			assessItemExtra.setAlterUpper(null);
			assessItemExtra.setAccumulateUpper(null);
		}
		
		if(assessItemExtra.getId() == null) {
			return CommonResponse.getResponse(assessItemExtraService.save(assessItemExtra));
		} else {
			return CommonResponse.getResponse(assessItemExtraService.updateSelective(assessItemExtra));
		}		
		
	}
	
	@RequestMapping("/extra/delete")
	@ResponseBody
	public Object deleteExtra(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(assessItemExtraService.removeByPrimaryKey(id));
	}
	
	
	@RequestMapping("/extra/cycle")
	@ResponseBody
	public Object getCycleExtraItems(@RequestParam String id, @RequestParam String userId) { 
	      
	      AssessQuantitativeResult aqResult = assessQuantitativeResultService.getResult(userId, id);

	        String unit = null;
	        if(aqResult != null) {
	            unit = aqResult.getUnitId();     
	        }
	        if(unit == null || unit.length() ==0){
	              unit = orgUserService.get(userId).getOrgUnitId();
	        }
	        
	    //OrgUser user = orgUserService.get(userId);
		return CommonResponse.getSuccessResponse(assessItemExtraService.findTemplateAssessExtraItem(assessCycleTemplateService.getTemplateByCycle(id, unit)));
	}
}
