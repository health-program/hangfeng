package com.paladin.hf.controller.inforelease;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.model.inforelease.SuggestAdvice;
import com.paladin.hf.service.inforelease.SuggestAdviceService;
import com.paladin.hf.service.inforelease.dto.SuggestAdviceDTO;
import com.paladin.hf.service.inforelease.dto.SuggestAdviceQuery;

/**谏言献策   
 * @author 黄伟华
 * @version 2019年5月22日 下午1:05:13 
 */
@Controller
@RequestMapping("/advice")
public class SuggestAdviceController extends ControllerSupport{
    
    @Autowired
    private SuggestAdviceService suggestAdviceService;
    
    @RequestMapping("/index")
    @QueryInputMethod(queryClass = SuggestAdviceQuery.class)
    public String index(){
	return "/hf/inforelease/suggest_advice_index";
    }
    
    @RequestMapping("/find")
    @ResponseBody
    @QueryOutputMethod(queryClass = SuggestAdviceQuery.class, paramIndex = 0)
    public Object find(SuggestAdviceQuery query){
	return CommonResponse.getSuccessResponse(suggestAdviceService.findSuggestAdvice(query));
    }
    
    @RequestMapping("/detail")
    public String detail(@RequestParam(required = true) String id, Model model) {
	boolean isAdmin = HfUserSession.getCurrentUserSession().isAdminRoleLevel();
	model.addAttribute("id", id);
	model.addAttribute("isAdmin", isAdmin);
	return "/hf/inforelease/suggest_advice_detail";
    }

    @RequestMapping("/get")
    @ResponseBody
    public Object detail(@RequestParam(required = true) String id){
	return CommonResponse.getSuccessResponse(suggestAdviceService.detail(id));
    }
    
    @RequestMapping(value = "/add", method = { RequestMethod.GET })
    public String addInput() {
	return "/hf/inforelease/suggest_advice_add";
    }
    
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@Valid SuggestAdviceDTO dto,BindingResult bindingResult){
	if (bindingResult.hasErrors()) {
	    return this.validErrorHandler(bindingResult);
	}
	String id = dto.getId();
	SuggestAdvice model = beanCopy(dto,(id == null || id.length() == 0) ? new SuggestAdvice() : suggestAdviceService.get(id));
	if (StringUtils.isEmpty(id)) {
	    return CommonResponse.getResponse(suggestAdviceService.save(model));
	} else {
	    suggestAdviceService.update(model);
	    return CommonResponse.getSuccessResponse(suggestAdviceService.detail(dto.getId()));
	}
    }
    
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam(required = true) String id){
	return CommonResponse.getResponse(suggestAdviceService.removeByPrimaryKey(id));
    }
    
}
