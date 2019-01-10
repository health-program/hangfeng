package com.paladin.hf.controller.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.service.org.OrgUserService;

/**
 * 
 * 医德医风档案管理Controller
 *
 */
@Controller
@RequestMapping("/org/user/transfer")
public class OrgUserTransferController extends ControllerSupport {

	@Autowired
	private OrgUserService orgUserService;

	/**
	 * 转移人员
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String transferIndex() {
		return "/hf/org/user_transfer_index";
	}

	/**
	 * 转移人员
	 * 
	 * @param userId
	 * @param target
	 * @return
	 */
	@RequestMapping("/")
	@ResponseBody
	public Object transfer(@RequestParam(value = "userId[]") String[] userId, @RequestParam String target) {
		return CommonResponse.getResponse(orgUserService.transferUser(userId, target));
	}

	/**
	 * 转移人员申请
	 * 
	 * @return
	 */
	@RequestMapping("/ask/index")
	public String transferAskIndex() {
		return "/hf/org/user_transfer_ask_index";
	}

	/**
	 * 转移进来人员申请
	 * 
	 * @return
	 */
	@RequestMapping("/ask/in")
	@ResponseBody
	public Object transferAskInUser() {
		return CommonResponse.getSuccessResponse(orgUserService.getTransferAskInUser());
	}

	/**
	 * 转移出去人员申请
	 * 
	 * @return
	 */
	@RequestMapping("/ask/out")
	@ResponseBody
	public Object transferAskOutUser() {
		return CommonResponse.getSuccessResponse(orgUserService.getTransferAskOutUser());
	}

	/**
	 * 转移人员申请数目
	 * 
	 * @return
	 */
	@RequestMapping("/ask/count")
	@ResponseBody
	public Object transferAskUserCount() {
		return CommonResponse.getSuccessResponse(orgUserService.getCountOfTransferAskIn());
	}

	/**
	 * 移除转移人员申请
	 * 
	 * @return
	 */
	@RequestMapping("/ask/remove")
	@ResponseBody
	public Object removeTransferAsk(@RequestParam(value = "userId[]") String[] userId) {
		return CommonResponse.getSuccessResponse(orgUserService.removeTransferAskUser(userId));
	}

	/**
	 * 转移人员
	 * 
	 * @param userId
	 * @param target
	 * @return
	 */
	@RequestMapping("/ask")
	@ResponseBody
	public Object transferAsk(@RequestParam(value = "userId[]") String[] userId, @RequestParam(value = "target") String target) {
		return CommonResponse.getResponse(orgUserService.transferAsk(userId, target));
	}

	/**
	 * 同意转移人员
	 * 
	 * @param userId
	 * @param target
	 * @return
	 */
	@RequestMapping("/ask/agree")
	@ResponseBody
	public Object transferAsk(@RequestParam(value = "userId[]") String[] userId) {
		return CommonResponse.getResponse(orgUserService.agreeTransferAsk(userId));
	}

	/**
	 * 拒绝转移人员
	 * 
	 * @param userId
	 * @param target
	 * @return
	 */
	@RequestMapping("/ask/reject")
	@ResponseBody
	public Object rejectTransferAsk(@RequestParam(value = "userId[]") String[] userId) {
		return CommonResponse.getResponse(orgUserService.rejectTransferAsk(userId));
	}
}
