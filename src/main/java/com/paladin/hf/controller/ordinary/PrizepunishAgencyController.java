package com.paladin.hf.controller.ordinary;

import com.paladin.common.model.syst.SysAttachment;
import com.paladin.common.service.syst.SysAttachmentService;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.utils.uuid.UUIDUtil;
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
public class PrizepunishAgencyController extends ControllerSupport {

	@Autowired
	private PrizepunishService prizepunishService;

	@Autowired
	private SysAttachmentService attachmentService;

	@RequestMapping("/index")
	public String index() {
		return "/hf/prizepunish/agency/prizepunish_agency_index";
	}

	@RequestMapping("/detail/index")
	@QueryInputMethod(queryClass = PrizepunishQuery.class)
	public String detailIndex(@RequestParam String orgUserId, @RequestParam String userName, Model model) {
		model.addAttribute("orgUserId", orgUserId);
		model.addAttribute("userName", userName);
		return "/hf/prizepunish/agency/prizepunish_agency_detail_index";
	}

	/* 机构查询某个人的奖惩记录 */
	@RequestMapping("/find")
	@ResponseBody
	@QueryOutputMethod(queryClass = PrizepunishQuery.class, paramIndex = 0)
	public Object prizeDeptFind(PrizepunishQuery query) {
		return CommonResponse.getSuccessResponse(prizepunishService.selectPrizeUnit(query));
	}

	@RequestMapping("/add")
	public String add(@RequestParam String orgUserId, @RequestParam String userName, Model model) {
		model.addAttribute("orgUserId", orgUserId);
		model.addAttribute("userName", userName);
		return "/hf/prizepunish/agency/prizepunish_agency_add";
	}

	@RequestMapping("/save/temporary")
	@ResponseBody
	public Object saveTemporary(@Valid PrizepunishDTO dto, @RequestParam String userId, BindingResult bindingResult,
			@RequestParam(required = false) MultipartFile[] attachmentFiles) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}

		List<SysAttachment> attachments = attachmentService.checkOrCreateAttachment(dto.getAttachments(), attachmentFiles);
		if (attachments != null && attachments.size() > 4) {
			return CommonResponse.getErrorResponse("附件数量不能超过3张");
		}
		dto.setAttachments(attachmentService.splicingAttachmentId(attachments));
		dto.setId(UUIDUtil.createUUID());
		if (prizepunishService.savePrizepunishForUser(dto, userId, Prizepunish.OPERATION_STATE_AGENCY_TEMPORARY, Prizepunish.EXAMINE_WAIT) > 0) {
			return CommonResponse.getSuccessResponse(prizepunishService.prizeView(dto.getId()));
		}
		return CommonResponse.getErrorResponse("操作失败");
	}

	@RequestMapping("/save/submit")
	@ResponseBody
	public Object saveSubmit(@Valid PrizepunishDTO dto, @RequestParam String userId, BindingResult bindingResult,
			@RequestParam(required = false) MultipartFile[] attachmentFiles) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}

		List<SysAttachment> attachments = attachmentService.checkOrCreateAttachment(dto.getAttachments(), attachmentFiles);
		if (attachments != null && attachments.size() > 4) {
			return CommonResponse.getErrorResponse("附件数量不能超过3张");
		}
		dto.setAttachments(attachmentService.splicingAttachmentId(attachments));
		dto.setId(UUIDUtil.createUUID());
		if (prizepunishService.savePrizepunishForUser(dto, userId, Prizepunish.OPERATION_STATE_AGENCY_SUBMIT, Prizepunish.EXAMINE_SUCCESS) > 0) {
			return CommonResponse.getSuccessResponse(prizepunishService.prizeView(dto.getId()));
		}
		return CommonResponse.getErrorResponse("操作失败");
	}

	@RequestMapping("/update/temporary")
	@ResponseBody
	public Object updateTemporary(@Valid PrizepunishDTO dto, BindingResult bindingResult, @RequestParam(required = false) MultipartFile[] attachmentFiles) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}

		List<SysAttachment> attachments = attachmentService.checkOrCreateAttachment(dto.getAttachments(), attachmentFiles);
		if (attachments != null && attachments.size() > 4) {
			return CommonResponse.getErrorResponse("附件数量不能超过3张");
		}

		dto.setAttachments(attachmentService.splicingAttachmentId(attachments));

		if (prizepunishService.updatePrizepunish(dto) > 0) {
			return CommonResponse.getSuccessResponse(prizepunishService.prizeView(dto.getId()));
		}
		return CommonResponse.getErrorResponse("操作失败");
	}

	@RequestMapping("/update/submit")
	@ResponseBody
	public Object updateSubmit(@Valid PrizepunishDTO dto, BindingResult bindingResult, @RequestParam(required = false) MultipartFile[] attachmentFiles) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}

		List<SysAttachment> attachments = attachmentService.checkOrCreateAttachment(dto.getAttachments(), attachmentFiles);
		if (attachments != null && attachments.size() > 4) {
			return CommonResponse.getErrorResponse("附件数量不能超过3张");
		}

		dto.setAttachments(attachmentService.splicingAttachmentId(attachments));

		if (prizepunishService.updatePrizepunish(dto, Prizepunish.OPERATION_STATE_AGENCY_SUBMIT, Prizepunish.EXAMINE_SUCCESS) > 0) {
			return CommonResponse.getSuccessResponse(prizepunishService.prizeView(dto.getId()));
		}
		return CommonResponse.getErrorResponse("操作失败");
	}

	@RequestMapping("/view")
	public String getView(@RequestParam String id, @RequestParam String orgUserId, @RequestParam String userName, Model model) {
		model.addAttribute("orgUserId", orgUserId);
		model.addAttribute("id", id);
		model.addAttribute("userName", userName);
		return "/hf/prizepunish/agency/prizepunish_agency_detail";
	}

	@RequestMapping("/get")
	@ResponseBody
	public Object view(@RequestParam String id) {
		return CommonResponse.getSuccessResponse(prizepunishService.prizeView(id));
	}

	@RequestMapping("/examine")
	public String examine(@RequestParam String id, @RequestParam String orgUserId, @RequestParam String userName, Model model) {
		model.addAttribute("orgUserId", orgUserId);
		model.addAttribute("id", id);
		model.addAttribute("userName", userName);
		return "/hf/prizepunish/agency/prizepunish_agency_examine";
	}

	/* 科室审核奖惩 */
	@RequestMapping("/examine/pass")
	@ResponseBody
	public Object examinePass(@RequestParam String id) {
		return CommonResponse.getResponse(prizepunishService.examineUnit(id, true));
	}

	@RequestMapping("/examine/reject")
	@ResponseBody
	public Object examineReject(@RequestParam String id) {
		return CommonResponse.getResponse(prizepunishService.examineUnit(id, false));
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam String id) {
		return prizepunishService.removeByPrimaryKey(id);
	}
	
	

}
