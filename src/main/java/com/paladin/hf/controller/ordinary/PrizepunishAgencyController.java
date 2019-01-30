package com.paladin.hf.controller.ordinary;

import com.github.pagehelper.util.StringUtil;
import com.paladin.common.model.syst.SysAttachment;
import com.paladin.common.service.syst.SysAttachmentService;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.model.ordinary.Prizepunish;
import com.paladin.hf.service.ordinary.PrizepunishService;
import com.paladin.hf.service.ordinary.dto.PrizepunishDTO;
import com.paladin.hf.service.ordinary.dto.PrizepunishQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

/**   
 * @author 黄伟华
 * @version 2018年1月29日 下午4:54:14 
 */
@Controller
@RequestMapping("/prizepunish/agency")
public class PrizepunishAgencyController extends ControllerSupport{
    
    @Autowired
    private PrizepunishService prizepunishService;
    
    @Autowired
    private SysAttachmentService attachmentService;
    
    
    @RequestMapping( "/index")
    public String index(){
        return "/hf/prizepunish/agency/prizepunish_agency_index";
    }
    
    @RequestMapping("/detail/index")
    @QueryOutputMethod(queryClass = PrizepunishQuery.class, paramIndex = 0)
    public String detailIndex(@RequestParam String orgUserId,Model model){
        model.addAttribute("orgUserId",orgUserId);
        return "/hf/prizepunish/agency/prizepunish_agency_detail_index";
    }
    
    /*机构查询某个人的奖惩记录 */
    @RequestMapping("/find")
    @ResponseBody
    @QueryInputMethod(queryClass = PrizepunishQuery.class)
    public Object prizeDeptFind(PrizepunishQuery query){
        return CommonResponse.getSuccessResponse(prizepunishService.selectPrizeUnit(query));
    }
    
    
    @RequestMapping("/add")
    public String add(@RequestParam String orgUserId,Model model){
        model.addAttribute("orgUserId",orgUserId);
        return "/hf/prizepunish/agency/prizepunish_agency_add";
    }
    
    /*机构为某个人新增奖惩记录 */
    @RequestMapping("/save")
    @ResponseBody
    public Object save(@Valid PrizepunishDTO dto,BindingResult bindingResult,@RequestParam(required = false) MultipartFile[] attachmentFiles){
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
        
        if(prizepunishService.prizeUnitSaveOrUpdate(model) >0 ){
            if(StringUtil.isNotEmpty(id)){
                return CommonResponse.getSuccessResponse(prizepunishService.prizeView(dto.getId()));
            }else{
                return CommonResponse.getResponse(true);
            }
        }
        return CommonResponse.getErrorResponse("操作失败");
    }
    
    @RequestMapping("/view")
    public String getView(@RequestParam String id,@RequestParam String orgUserId,Model model){
        model.addAttribute("orgUserId",orgUserId);
        model.addAttribute("id",id);
        return "/hf/prizepunish/agency/prizepunish_agency_detail";
    }
    
    @RequestMapping("/get")
    @ResponseBody
    public Object view(@RequestParam String id){
        return CommonResponse.getSuccessResponse(prizepunishService.prizeView(id));
    }
    
    @RequestMapping("/examine")
    public String examine(@RequestParam String id,@RequestParam String orgUserId,Model model){
        model.addAttribute("orgUserId",orgUserId);
        model.addAttribute("id",id);
        return "/hf/prizepunish/agency/prizepunish_agency_examine";
    }
    
    /*机构审核奖惩 */
    @RequestMapping("/examine/update")
    @ResponseBody
    public Object examineUpdate(PrizepunishDTO dto){
        return CommonResponse.getResponse(prizepunishService.examineUnitUpdate(dto));
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(@RequestParam String id){
        return prizepunishService.removeByPrimaryKey(id);
    }
    
}


