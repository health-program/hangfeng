package com.paladin.hf.controller.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.common.PageResult;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.statistics.EvaluationDetailedService;
import com.paladin.hf.service.statistics.dto.PersoncycassessQueryDTO;


/**考评结果明细查询
 * @author 黄伟华
 * @version 2018年2月28日 上午8:42:31 
 */
@Controller
@RequestMapping("/console/situa")
public class EvaluationDetailedController
{
    @Autowired
    EvaluationDetailedService evaluationDetailedService;
    
    @Autowired
    AssessCycleService assessCycleService;
    
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model)
    {
        AssessCycle assessCycle = assessCycleService.getOwnedFirstAssessCycle();
        if (assessCycle != null) {
            model.addAttribute("assessCycleId", assessCycle.getId());
            model.addAttribute("assessCycleName", assessCycle.getCycleName());
        }
        return "console/reportanalysis/evaluation_detailed_index";
    }
    
    @RequestMapping("/search/all")
    @ResponseBody
    public Object searchAll(PersoncycassessQueryDTO query) {       
        return CommonResponse.getSuccessResponse(new PageResult<>(evaluationDetailedService.personcycassessDetaileds(query)));
    }
}
