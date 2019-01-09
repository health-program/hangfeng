package com.paladin.hf.controller.syst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.service.syst.SysUserService;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {
	
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 验证账号是否可行
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping("/validate/account")
	@ResponseBody
	public Object validateAccount(@RequestParam(required = true) String account) {
		return CommonResponse.getSuccessResponse(sysUserService.validateAccount(account));
	}

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/reset/password")
	@ResponseBody
	public Object resetPassword(@RequestParam(required = true) String userId) {
		return CommonResponse.getResponse(sysUserService.resetOrgUserPassword(userId));
	}
	
}
