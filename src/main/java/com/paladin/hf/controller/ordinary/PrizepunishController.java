package com.paladin.hf.controller.ordinary;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.github.pagehelper.util.StringUtil;
import com.paladin.common.model.syst.SysAttachment;
import com.paladin.common.service.syst.SysAttachmentService;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.model.ordinary.Prizepunish;
import com.paladin.hf.service.ordinary.PrizepunishService;
import com.paladin.hf.service.ordinary.dto.PrizepunishDTO;
import com.paladin.hf.service.ordinary.dto.PrizepunishQuery;


/**
 * 平时考评个人-奖惩事件
 * 
 * @author 黄伟华
 * @version 2018年1月15日 下午4:31:17
 */
@Controller
@RequestMapping("/prizepunish")
public class PrizepunishController extends ControllerSupport {
	
	@Autowired
	private PrizepunishService prizepunishService;
	
	@Autowired
    private SysAttachmentService attachmentService;
	
	@RequestMapping( "/index")
	public String index(){
	    if (HfUserSession.getCurrentUserSession().isAdminRoleLevel()) {
            return "no_business";
        }
	    return "/hf/prizepunish/personal/prizepunish_index";
	}
	
	@RequestMapping( "/find")
	@ResponseBody
	@QueryOutputMethod(queryClass = PrizepunishQuery.class, paramIndex = 0)
	public Object find(PrizepunishQuery query){
	    return CommonResponse.getSuccessResponse(prizepunishService.selectPrizePeople(query));
	}
	
	@RequestMapping( "/add")
    public String add(Model model){
        return "/hf/prizepunish/personal/prizepunish_add";
    }
	
	@RequestMapping( "/save")
	@ResponseBody
	public Object saveOrUpdate(@Valid PrizepunishDTO dto,BindingResult bindingResult,@RequestParam(required = false) MultipartFile[] attachmentFiles){
	    
	    if (bindingResult.hasErrors()) {
            return this.validErrorHandler(bindingResult);
        }
	    
	    List<SysAttachment> attachments = attachmentService.checkOrCreateAttachment(dto.getAttachments(), attachmentFiles);
        if (attachments != null && attachments.size() > 3) {
            return CommonResponse.getErrorResponse("附件数量不能超过3张");
        }
        dto.setAttachments(attachmentService.splicingAttachmentId(attachments));
        String id = dto.getId();

        Prizepunish model = beanCopy(dto, (id == null || id.length() == 0) ? new Prizepunish() : prizepunishService.get(id));
        
	    if(prizepunishService.prizePeopleSaveOrUpdate(model) >0 ){
	        if(StringUtil.isNotEmpty(id)){
	            return CommonResponse.getSuccessResponse(prizepunishService.prizeView(dto.getId()));
	        }else{
	            return CommonResponse.getResponse(true);
	        }
	    }
	    return CommonResponse.getErrorResponse("操作失败");
	}
	
	@RequestMapping( "/view")
	public Object view(@RequestParam String id,Model model){
	    model.addAttribute("id",id);
	    return "/hf/prizepunish/personal/prizepunish_detail";
	}
	
	@RequestMapping( "/detail")
    @ResponseBody
	public Object detail(@RequestParam String id){
	    return CommonResponse.getSuccessResponse(prizepunishService.prizeView(id));
	}
	
	@RequestMapping("/delete")
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object delete(@RequestParam(required = true) String id) {
        return CommonResponse.getResponse(prizepunishService.removeByPrimaryKey(id));
    }
	
	@RequestMapping( "/view/layer")
	public Object viewLayer(@RequestParam String id,Model model){
	    model.addAttribute("id",id);
	    return "/hf/prizepunish/personal/prizepunish_detail_layer";
	}
	
}
