package com.paladin.hf.controller.org;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.service.org.OrgUserService;
import com.paladin.hf.service.org.dto.AppOrgUserSelfDTO;
import com.paladin.hf.service.org.dto.AppOrgUserSelfResumeDTO;
import com.paladin.hf.service.org.dto.OrgUserSelfDTO;

/**
 * 
 * 医德医风档案管理Controller
 *
 */
@Controller
@RequestMapping("/org/user/self")
public class OrgUserSelfController extends ControllerSupport {

	@Autowired
	private OrgUserService orgUserService;

	// ----------------- 档案管理 -----------------

	@RequestMapping("/index")
	public String index(Model model) {
		HfUserSession session = HfUserSession.getCurrentUserSession();
		if (session.isSystemAdmin() || session.isAdminUser()) {
			return "/hf/no_business";
		} else {
			model.addAttribute("id", session.getUserId());
		}
		return "/hf/org/user_self_detail";
	}

	@RequestMapping("/get")
	@ResponseBody
	public Object get() {
		return CommonResponse.getSuccessResponse(orgUserService.getUser(HfUserSession.getCurrentUserSession().getUserId()));
	}

	@RequestMapping("/update")
	@ResponseBody
	public Object udpate(@Valid OrgUserSelfDTO orgUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		if (orgUserService.updateUserSelf(orgUser)) {
			return CommonResponse.getSuccessResponse(orgUserService.getUser(HfUserSession.getCurrentUserSession().getUserId()));
		} else {
			return CommonResponse.getFailResponse();
		}
	}
	
	//app修改个人信息
    @RequestMapping("/app/update")
    @ResponseBody
    public Object appUdpate(@Valid AppOrgUserSelfDTO orgUser, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return this.validErrorHandler(bindingResult);
        }
        if (orgUserService.appUpdateUserSelf(orgUser)) {
            return CommonResponse.getSuccessResponse(orgUserService.getUser(HfUserSession.getCurrentUserSession().getUserId()));
        } else {
            return CommonResponse.getFailResponse();
        }
    }
    
  //app修改个人简历,奖励,奖惩
    @RequestMapping("/app/update/resume")
    @ResponseBody
    public Object appUpdateResume(AppOrgUserSelfResumeDTO dto){
        return CommonResponse.getResponse(orgUserService.appUpdateResume(dto));
    }
}
