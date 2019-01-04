package com.paladin.hf.controller.statistics;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.container.ConstantsContainer;
import com.paladin.framework.core.container.ConstantsContainer.KeyValue;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.statistics.AppraisalSummaryService;
import com.paladin.hf.service.statistics.dto.AppraisalSummaryQueryDTO;
import com.paladin.hf.service.statistics.vo.AppraisalSummaryVO;


/**考评结果汇总   
 * @author 黄伟华
 * @version 2018年2月7日 下午1:56:13 
 */
@Controller
@RequestMapping("/console/appsum")
public class AppraisalSummaryController extends ControllerSupport
{
    @Autowired
    AppraisalSummaryService appraisalSummaryService;
    
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
        return "console/reportanalysis/appraisal_summary_index";
    }
        
    @RequestMapping("/search/eharts")
    @ResponseBody
    public Object searchAllEharts(AppraisalSummaryQueryDTO query) {
        ModelMap map = new ModelMap();
        Map<String, List<KeyValue>>  keyValues=ConstantsContainer.getTypeChildren("AppAssGrades");
        List<AppraisalSummaryVO> list=appraisalSummaryService.AppraisalSummaryAll(query);
        map.put("key", keyValues);
        map.put("list", list);
        return  CommonResponse.getSuccessResponse(map);
    }
}