package com.paladin.hf.controller.assess.quantificate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
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
@RequestMapping("/assess/template/item")
public class AssessItemController extends ControllerSupport{
	
	@Autowired
	private AssessItemService assessItemService;
	
	@Autowired
	private AssessItemExtraService assessItemExtraService;
	
	@Autowired
	private AssessCycleTemplateService assessCycleTemplateService;
	
	@Autowired
	private OrgUserService orgUserService;
	
	@Autowired
	private AssessQuantitativeResultService assessQuantitativeResultService;
	
	
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object getTemplateItems(@RequestParam(required = true) String id) {		
		return CommonResponse.getSuccessResponse(assessItemService.findTemplateAssessItem(id));
	}
		
	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid AssessItem assessItem, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		if(StringUtils.isEmpty(assessItem.getParentItemId())){
		    assessItem.setParentItemId(null);
		}
		return CommonResponse.getResponse(assessItemService.saveOrUpdate(assessItem));
	}
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(assessItemService.removeByPrimaryKey(id));
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/extra/get")
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
		
		if(assessItemExtra.getId() == null || assessItemExtra.getId().length() == 0) {
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
