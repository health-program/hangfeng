package com.paladin.hf.controller.statistics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.service.statistics.PrizeCountQueryService;
import com.paladin.hf.service.statistics.dto.PrizepunishQueryDTO;


/**   
 * @author 黄伟华
 * 奖惩事件统计结果查询
 * @version 2018年4月3日 上午10:41:39 
 */
@Controller
@RequestMapping("/prize/count")
public class PrizepunishCountController extends ControllerSupport
{
    @Autowired
    private PrizeCountQueryService prizeCountQueryService;
    
    @RequestMapping(value = "/index", method = { RequestMethod.GET })
    public String index(Model model) {
        return "/hf/reportanalysis/prize_count_index";
    }
    
    @RequestMapping("/search/all")
    @ResponseBody
    public Object searchAll(PrizepunishQueryDTO query) {
    	return CommonResponse.getSuccessResponse(prizeCountQueryService.prizeCountQueryAll(query));
    }
}
