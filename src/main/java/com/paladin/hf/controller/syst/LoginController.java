package com.paladin.hf.controller.syst;

import java.util.Collection;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.paladin.common.core.permission.MenuPermission;
import com.paladin.framework.utils.WebUtil;
import com.paladin.framework.utils.validate.ValidateUtil;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.IdentificationToken;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.model.syst.SysUser;
import com.paladin.hf.service.syst.SysUserService;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private SysUserService sysUserService;

	@RequestMapping(value = "/index")
	public Object main(HttpServletRequest request) {
		HfUserSession userSession = HfUserSession.getCurrentUserSession();

		int role = 4;
		Unit agency = userSession.getUserAgency();

		if (userSession.isAdminUser() || userSession.isAssessTeamRole() || userSession.isAgencyAssessRole()) {
			role = 2;
		} else if (userSession.isDepartmentAssessRole()) {
			role = 3;
		} else if (userSession.isAssessedRole()) {
			role = 4;
		} else if (userSession.isAdminRoleLevel()) {
			role = 1;
		}

		SysUser sysUser = sysUserService.getUser(userSession.getAccount());

		String page = userSession.isAssessedRole() ? "/hf/index_self" : "/hf/index_manager";

		ModelAndView model = new ModelAndView(page);

		model.addObject("agency", agency == null ? "管理员" : agency.getName());
		model.addObject("name", userSession.getUserName());
		model.addObject("role", role);
		model.addObject("isFirst", sysUser.getIsFirstLogin());

		Collection<MenuPermission> menus = userSession.getMenuResources();
		
		

		// model.addObject("menus", userSession.getMenuResources());

		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Object loginInput(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		boolean isAjax = WebUtil.isAjaxRequest(request);
		if (subject.isAuthenticated()) {
			if (isAjax) {
				WebUtil.sendJson(response, CommonResponse.getSuccessResponse("登录成功"));
				return null;
			} else {
				return main(request);
			}
		}

		return "/hf/login";
	}

	@ResponseBody
	@RequestMapping(value = "/quyi/login")
	public Object quyiLogin(@RequestParam String identification, HttpServletRequest request) {
		if (ValidateUtil.isValidatedAllIdcard(identification)) {
			SysUser user = sysUserService.getSysUserByIdentification(identification);
			if (user == null) {
				return CommonResponse.getFailResponse("找不到对应身份证的用户");
			}

			Subject subject = SecurityUtils.getSubject();
			IdentificationToken token = new IdentificationToken(user.getAccount(), identification);

			try {
				subject.login(token);
			} catch (AuthenticationException e) {
				return CommonResponse.getFailResponse("身份证登录失败");
			}

			return CommonResponse.getSuccessResponse("登录成功", subject.getSession().getId());
		} else {
			return CommonResponse.getFailResponse("请输入有效的身份证号码");
		}
	}

	@RequestMapping(value = "/quyi/app/index")
	public Object quyiAppIndex(@RequestParam String token, Model model) {
		return new ModelAndView("app_index", "token", token);
	}

	@RequestMapping(value = "/update/password")
	@ResponseBody
	public Object updatePassword(@RequestParam String newPassword, @RequestParam String oldPassword) {
		return CommonResponse.getResponse(sysUserService.updateSelfPassword(newPassword, oldPassword));
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Object login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Subject subject = SecurityUtils.getSubject();
		boolean isAjax = WebUtil.isAjaxRequest(request);

		if (subject.isAuthenticated()) {
			if (isAjax) {
				WebUtil.sendJson(response, CommonResponse.getSuccessResponse("登录成功"));
				return null;
			} else {
				return null;
			}
		} else {
			if (isAjax) {
				WebUtil.sendJson(response, CommonResponse.getFailResponse("登录失败,用户名或密码错误！"));
				return null;
			} else {
				model.addAttribute("isError", true);
				return "/hf/login";
			}
		}
	}

}
