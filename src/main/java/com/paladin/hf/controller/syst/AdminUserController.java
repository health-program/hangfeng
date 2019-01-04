package com.paladin.hf.controller.syst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.common.model.org.OrgRole;
import com.paladin.common.service.org.OrgRoleService;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.model.syst.AdminUser;
import com.paladin.hf.service.org.dto.SimpleUnit;
import com.paladin.hf.service.syst.AdminUserService;
import com.paladin.hf.service.syst.SysUserService;

@Controller
@RequestMapping("/system/admin/user")
public class AdminUserController extends ControllerSupport{
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private OrgRoleService orgRoleService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping(value ="/index")
	public Object index(Model model) {
		HfUserSession session = HfUserSession.getCurrentUserSession();
		List<OrgRole> roles = orgRoleService.getOwnGrantRoles(session.getRoleLevel(),false);		
		model.addAttribute("roles", roles);
		return "/console/adminUser/index";
	}
	
	@RequestMapping(value ="/list")
	@ResponseBody
	public Object list() {				
		
		List<AdminUser> users = adminUserService.findAll();	
		List<Unit> agencys = DataPermissionUtil.getOwnAgency();
		List<SimpleUnit> result = new ArrayList<>(agencys.size());
		
		for (Unit agency : agencys) {
			result.add(new SimpleUnit(agency));
		}
		
		HashMap<String, Object> response = new HashMap<>();
		response.put("users", users);
		response.put("agencys", result);
		
		return CommonResponse.getSuccessResponse(response);				
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid AdminUser user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		
		return CommonResponse.getResponse(adminUserService.saveOrUpdateAdminUser(user));
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam(required = true) String id) {		
		return CommonResponse.getResponse(adminUserService.removeUser(id));
	}
	
	/**
	 * 重置密码
	 * @param userId
	 * @return
	 */
	@RequestMapping("/reset/password")
	@ResponseBody
	public Object resetPassword(@RequestParam(required = true) String userId) {
		return CommonResponse.getResponse(sysUserService.resetAdminUserPassword(userId));
	}
}
