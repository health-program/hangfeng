package com.paladin.hf.controller.org;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.query.QueryInputMethod;
import com.paladin.framework.core.query.QueryOutputMethod;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.org.OrgUserService;
import com.paladin.hf.service.org.dto.OrgUserDTO;
import com.paladin.hf.service.org.dto.OrgUserQuery;
import com.paladin.hf.service.org.dto.SimpleUser;

/**
 * 
 * 医德医风档案管理Controller
 *
 */
@Controller
@RequestMapping("/org/user")
public class OrgUserController extends ControllerSupport {

	@Autowired
	private OrgUserService orgUserService;

	// ----------------- 档案管理 -----------------

	@RequestMapping("/index")
	@QueryInputMethod(queryClass = OrgUserQuery.class)
	public String index(HttpServletRequest request, Model model) {
		return "/hf/org/user_index";
	}

	@RequestMapping("/find")
	@ResponseBody
	@QueryOutputMethod(queryClass = OrgUserQuery.class, paramIndex = 0)
	public Object find(OrgUserQuery query) {
		return CommonResponse.getSuccessResponse(orgUserService.findUser(query));
	}

	@RequestMapping("/get")
	@ResponseBody
	public Object get(@RequestParam String id) {
		return CommonResponse.getSuccessResponse(orgUserService.getUser(id));
	}

	@RequestMapping("/add")
	public String add() {
		return "/hf/org/user_add";
	}

	@RequestMapping("/detail")
	public String detail(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "/hf/org/user_detail";
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid OrgUserDTO orgUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		return CommonResponse.getResponse(orgUserService.addUser(orgUser));
	}

	@RequestMapping("/update")
	@ResponseBody
	public Object udpate(@Valid OrgUserDTO orgUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		if (orgUserService.updateUser(orgUser)) {
			return CommonResponse.getSuccessResponse(orgUserService.getUser(orgUser.getId()));
		} else {
			return CommonResponse.getFailResponse();
		}
	}

	/**
	 * 
	 * 逻辑删除档案人员 物理删除账号
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/real/delete")
	@ResponseBody
	public Object realDelete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(orgUserService.removeUser(id));
	}

	/**
	 * 离岗
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/leave")
	@ResponseBody
	public Object leave(@RequestParam String id) {
		return CommonResponse.getResponse(orgUserService.leave(id));
	}

	/**
	 * 查找单位下所有人员
	 * 
	 * @param unitId
	 * @return
	 */
	@RequestMapping("/find/unit")
	@ResponseBody
	public Object getUnitUser(@RequestParam(value = "unitId", required = true) String unitId) {
		List<OrgUser> users = orgUserService.findUnitUser(unitId);
		return CommonResponse.getSuccessResponse(users);

	}

	/**
	 * 查找单位下所有人员
	 * 
	 * @param unitId
	 * @return
	 */
	@RequestMapping("/find/unit/simple")
	@ResponseBody
	public Object getSimpleUnitUser(@RequestParam(value = "unitId", required = true) String unitId) {
		List<OrgUser> users = orgUserService.findUnitUser(unitId);
		List<SimpleUser> simpleUsers = new ArrayList<>(users.size());
		for (OrgUser user : users) {
			simpleUsers.add(new SimpleUser(user));
		}
		return CommonResponse.getSuccessResponse(simpleUsers);
	}

	/**
	 * 导入人员
	 * 
	 * @return
	 */
	@RequestMapping("/import")
	@ResponseBody
	public Object importUser(@RequestParam("file") MultipartFile file, @RequestParam("unitId") String unitId) {
		try {
			return CommonResponse.getSuccessResponse(orgUserService.importUser(file.getInputStream(), unitId));
		} catch (IOException e) {
			return CommonResponse.getFailResponse("导入异常");
		}
	}
}
