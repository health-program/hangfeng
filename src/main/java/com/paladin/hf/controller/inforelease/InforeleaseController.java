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
import com.github.pagehelper.util.StringUtil;
import com.paladin.common.model.syst.SysAttachment;
import com.paladin.common.service.syst.SysAttachmentService;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.mapper.assess.cycle.PersonCycAssessMapper;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.model.inforelease.Inforelease;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.assess.cycle.vo.AssessCycleStatisticsVO;
import com.paladin.hf.service.inforelease.InforeleaseService;
import com.paladin.hf.service.inforelease.dto.InforeleaseDTO;
import com.paladin.hf.service.inforelease.dto.InforeleaseQuery;

/**
 * @author 黄伟华
 * @version 2018年1月9日 下午4:20:50
 */
@Controller
@RequestMapping("/inforelease")
public class InforeleaseController extends ControllerSupport {
	@Autowired
	private InforeleaseService inforeleaseService;

	@Autowired
	private SysAttachmentService attachmentService;

	@Autowired
	private PersonCycAssessMapper personCycAssessMapper;
	
	@Autowired
	private AssessCycleService assessCycleService;
	
	// 通知公告页面跳转
	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(Model model) {
		return "/hf/inforelease/notice_index";
	}

	// 政策文件页面跳转
	@RequestMapping(value = "/policy/index", method = { RequestMethod.GET })
	public String policyindex(Model model) {
		return "/hf/inforelease/policyfile_index";
	}

	// 通知公告,政策文件新增页面跳转
	@RequestMapping(value = "/add", method = { RequestMethod.GET })
	public String addInput(@RequestParam(required = true) String type, Model model) {
		model.addAttribute("type", type);
		return "/hf/inforelease/add";
	}

	// 通知公告,政策文件详情页面跳转
	@RequestMapping("/detail")
	public String getNoticeDetail(@RequestParam(required = true) String id, @RequestParam(required = true) String type, Model model) {
		model.addAttribute("info", inforeleaseService.get(id));
		return "/hf/inforelease/detail";
	}

	// 政策文件,通知公告详情
	@RequestMapping(value = "/get")
	@ResponseBody
	public Object getPolicyFileDetail(@RequestParam(required = true) String id) {
		return CommonResponse.getSuccessResponse(inforeleaseService.detail(id));
	}

	// 通知公告,政策文件列表查询
	@ResponseBody
	@RequestMapping(value = "/list")
	public Object list(InforeleaseQuery query) {
		return CommonResponse.getSuccessResponse(inforeleaseService.selectInforeleaseAll(query));
	}

	// 通知公告,政策文件删除操作
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(inforeleaseService.removeByPrimaryKey(id));
	}

	// 通知公告,政策文件新增修改操作
	@RequestMapping(value = "/save")
	@ResponseBody
	public Object save(@Valid InforeleaseDTO dto, BindingResult bindingResult, @RequestParam(required = false) MultipartFile[] attachmentFiles) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}

		List<SysAttachment> attachments = attachmentService.checkOrCreateAttachment(dto.getAttachments(), attachmentFiles);
		if (attachments != null && attachments.size() > 4) {
			return CommonResponse.getErrorResponse("附件数量不能超过4张");
		}
		dto.setAttachments(attachmentService.splicingAttachmentId(attachments));
		String id = dto.getId();

		Inforelease model = beanCopy(dto, (id == null || id.length() == 0) ? new Inforelease() : inforeleaseService.get(id));
		String targetUnit = model.getOrgUnitId();
		HfUserSession session = HfUserSession.getCurrentUserSession();

		if (StringUtil.isEmpty(targetUnit)) {
			if (session.isAdminRoleLevel()) {
				model.setOrgUnitId(null);
			} else {
				List<String> agencyIds = DataPermissionUtil.getOwnAgencyId();
				if (agencyIds != null && agencyIds.size() > 0) {
					targetUnit = agencyIds.get(0);
					model.setOrgUnitId(targetUnit);
				} else {
					return CommonResponse.getErrorResponse("没有权限发布信息");
				}
			}
		}

		if (!session.isAdminRoleLevel()) {
			List<String> agencyIds = DataPermissionUtil.getOwnAgencyId();
			if (agencyIds != null && agencyIds.size() > 0) {
				model.setOwnAgency(agencyIds.get(0));
			} else {
				return CommonResponse.getErrorResponse("没有权限发布信息");
			}
		}

		if (StringUtils.isEmpty(id)) {
			return CommonResponse.getResponse(inforeleaseService.save(model));
		} else {
		    	Inforelease i=  inforeleaseService.get(id);
		    	model.setTypes(i.getTypes());
			inforeleaseService.update(model);
			return CommonResponse.getSuccessResponse(inforeleaseService.detail(dto.getId()));
		}
	}

	// 信息发布页面数据加载
	@RequestMapping(value = "/info/index", method = { RequestMethod.GET })
	public String infoindex(Model model) {
	    model.addAttribute("info", inforeleaseService.noticyandpolicyfileAll());
	    return "/hf/inforelease/inforelease_index";
	}

	@RequestMapping("/app/info/index")
	@ResponseBody
	public Object appInfoindex(Model model) {
		return CommonResponse.getSuccessResponse(inforeleaseService.noticyandpolicyfileAll());
	}

	// 信息发布页面详情页面
	@RequestMapping(value = "/info/detail", method = { RequestMethod.GET })
	public String infoDetail(@RequestParam(required = true) String id, @RequestParam(required = true) boolean isHomePage, String type, Model model) {
		model.addAttribute("id", id);
		model.addAttribute("type", type);
		model.addAttribute("isHomePage", isHomePage);
		return "/hf/inforelease/inforelease_detail";
	}

        @RequestMapping(value = "/info", method = { RequestMethod.GET })
        public String info(Model model) {
    	HfUserSession userSession = HfUserSession.getCurrentUserSession();
    	if (userSession.isOrgUser()) {
    	AssessCycleStatisticsVO vo =  personCycAssessMapper.CycAssessStatistics(userSession.getUserId());
    	if(vo == null){
    	    vo = new AssessCycleStatisticsVO();
    	}
    	    model.addAttribute("cyc", vo);
    	}else{
	    AssessCycle assessCycle = assessCycleService.getOwnedFirstAssessCycle();
	    if (assessCycle != null) {
		model.addAttribute("assessCycleId", assessCycle.getId());
		model.addAttribute("assessCycleName",
			assessCycle.getCycleName());
	    } 
    	}
    	
    	model.addAttribute("list", inforeleaseService.noticyandpolicyfileAll());
    	return userSession.isOrgUser() ? "/hf/right_archivists_org": "/hf/right_archivists_admin";
        }

	// 更多页跳转
	@RequestMapping(value = "/more/index", method = { RequestMethod.GET })
	public String moreIndex(@RequestParam(required = true) String type, boolean isHomePage, Model model) {
		model.addAttribute("type", type);
		model.addAttribute("isHomePage", isHomePage);
		return "/hf/inforelease/inforelease_more_index";
	}

	@RequestMapping("/more")
	@ResponseBody
	public Object inforeleaseMore(InforeleaseQuery query) {
		return CommonResponse.getSuccessResponse(inforeleaseService.inforeleaseMore(query));
	}

}