package com.paladin.hf.controller.inforelease;


import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.druid.util.StringUtils;
import com.paladin.common.model.syst.SysAttachment;
import com.paladin.common.service.syst.SysAttachmentService;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.model.inforelease.Inforelease;
import com.paladin.hf.service.inforelease.InforeleaseService;
import com.paladin.hf.service.inforelease.dto.InforeleaseDTO;
import com.paladin.hf.service.inforelease.dto.InforeleaseQuery;

/**
 * @author 黄伟华
 * @version 2018年1月9日 下午4:20:50
 */
@Controller
@RequestMapping("/console/infore")
public class InforeleaseController extends ControllerSupport
{
    @Autowired
    private InforeleaseService inforeleaseService;
    
    @Autowired
    private SysAttachmentService attachmentService;
    
    // 通知公告页面跳转
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index(Model model)
    {
        return "/hf/inforelease/notice_index";
    }
    
    // 通知公告新增页面跳转
    @RequestMapping(value = "/add/notice/input", method = {RequestMethod.GET})
    public String addInput(){
        return "/hf/inforelease/notice_add";
    }
    
    @RequestMapping("/notice/get")
    public String getNoticeDetail(@RequestParam(required = true) String id, Model model){
        model.addAttribute("info", inforeleaseService.get(id));
        return "/hf/inforelease/notice_detail";
    }
    
    @RequestMapping(value = "/notice/detail")
    @ResponseBody
    public Object getNoticeDetail(@RequestParam(required = true) String id){
        return CommonResponse.getSuccessResponse(inforeleaseService.detail(id));
    }
    
    // 政策文件页面跳转
    @RequestMapping(value = "/policy/index", method = {RequestMethod.GET})
    public String policyindex(Model model)
    {
        return "/hf/inforelease/policyfile_index";
    }
    
    @RequestMapping("/policyfile/get")
    public String getPolicyDetail(@RequestParam(required = true) String id, Model model){
        model.addAttribute("info", inforeleaseService.get(id));
        return "/hf/inforelease/policyfile_detail";
    }
    
    // 政策文件详情
    @RequestMapping(value = "/policyfile/get", method = {RequestMethod.POST})
    @ResponseBody
    public Object getPolicyFileDetail(@RequestParam(required = true) String id){
        return CommonResponse.getSuccessResponse(inforeleaseService.detail(id));
    }
    
    // 政策文件新增页面跳转
    @RequestMapping(value = "/add/policy/input", method = {RequestMethod.GET})
    public String policyAddInput()
    {
        return "/hf/inforelease/policyfile_add";
    }
    
    // 通知公告,政策文件列表查询
    @ResponseBody
    @RequestMapping(value = "/list")
    public Object list(InforeleaseQuery query)
    {
        return CommonResponse.getSuccessResponse(inforeleaseService.selectInforeleaseAll(query));
    }
    
    // 通知公告,政策文件删除操作
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(@RequestParam(required = true) String id){
        return CommonResponse.getResponse(inforeleaseService.removeByPrimaryKey(id));
    }
    
    // 信息发布页面数据加载
    @RequestMapping(value = "/infoindex", method = {RequestMethod.GET})
    public String infoindex(Model model)
    {
        model.addAttribute("info", inforeleaseService.noticyandpolicyfileAll());
        return "/hf/inforelease/info_index";
    }
    
    // 通知公告,政策文件新增修改操作
    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save(@Valid InforeleaseDTO dto, BindingResult bindingResult,@RequestParam(required = false) MultipartFile[] attachmentFiles){
        if (bindingResult.hasErrors()) {
            return this.validErrorHandler(bindingResult);
        }
        
        List<SysAttachment> attachments = attachmentService.checkOrCreateAttachment(dto.getAttachments(), attachmentFiles);
        if (attachments != null && attachments.size() > 3) {
            return CommonResponse.getErrorResponse("附件数量不能超过3张");
        }
        dto.setAttachments(attachmentService.splicingAttachmentId(attachments));
        String id = dto.getId();
        
        if (StringUtils.isEmpty(id)){
            return CommonResponse.getResponse(inforeleaseService.save(beanCopy(dto, new Inforelease())));
        }else{
            inforeleaseService.update(beanCopy(dto, new Inforelease()));
          return CommonResponse.getSuccessResponse(inforeleaseService.detail(dto.getId()));
        }
    }
   }