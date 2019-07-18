package com.paladin.hf.controller.statistics;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;

/**   
 * @author MyKite
 * @version 2019年7月16日 上午9:32:07 
 */
@Controller
@RequestMapping("/statistics/analysis")
public class StatisticsAnalysisController extends ControllerSupport{
    
    

    @RequestMapping("index")
    public String index(){
	 return "/hf/reportanalysis/statistics_analysis_index";
    }
    
    @RequestMapping("/find")
    public Object find(){
	return CommonResponse.getSuccessResponse();
    }
}
