package com.paladin.hf.controller.statistics;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.service.statistics.AppraisalSummaryService;
import com.paladin.hf.service.statistics.dto.StatisticsAnalysisQueryDTO;
import com.paladin.hf.service.statistics.vo.OrgUnitPeopleTotalVO;
import com.paladin.hf.service.statistics.vo.StatisticsAnalysisVO;

/**   
 * @author MyKite
 * @version 2019年7月16日 上午9:32:07 
 */
@Controller
@RequestMapping("/statistics/analysis")
public class StatisticsAnalysisController extends ControllerSupport{
    
    @Autowired
    AppraisalSummaryService appraisalSummaryService;

    @RequestMapping("index")
    public String index(){
	 return "/hf/reportanalysis/statistics_analysis_index";
    }
    
    @RequestMapping("/find/echarts")
    @ResponseBody
    public Object find(StatisticsAnalysisQueryDTO query){
    	List<OrgUnitPeopleTotalVO> total = appraisalSummaryService.peopleTotal();
    	List<StatisticsAnalysisVO> vo= appraisalSummaryService.findEcahtes(query);
    	HashMap<String, Object> response = new HashMap<>();
		response.put("total", total);
		response.put("vo", vo);
	return CommonResponse.getSuccessResponse(response);
    }
}
