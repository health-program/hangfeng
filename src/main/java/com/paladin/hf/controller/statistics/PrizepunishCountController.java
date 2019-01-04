package com.paladin.hf.controller.statistics;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.service.statistics.PrizeCountQueryService;
import com.paladin.hf.service.statistics.dto.PrizepunishQueryDTO;
import com.paladin.hf.service.statistics.vo.PrizepunishCountVO;


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
    @Autowired
    private DictService dictService;
    
    @RequestMapping(value = "/index", method = { RequestMethod.GET })
    public String index(Model model) {
       
        return "console/peacedepartment/prize_count_index";
    }
    
    @RequestMapping("/search/all")
    @ResponseBody
    public Object searchAll(PrizepunishQueryDTO query) {
        ModelMap map=new ModelMap();
         List<Dict> dicts=dictService.selectDictPrize(query.getDictCode());
         List<PrizepunishCountVO> list=prizeCountQueryService.prizeCountQueryAll(query);
         map.put("dicts", dicts);
         map.put("list", list);
         return CommonResponse.getSuccessResponse(map); 
    }
}
