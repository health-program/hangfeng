package com.paladin.hf.controller.syst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.service.syst.LoginLogService;

/**   
 * @author 黄伟华
 * @version 2018年10月9日 上午9:36:42 
 */

@Controller
@RequestMapping("/sys/loginlog")
public class UserLoginLogController extends ControllerSupport
{
    @Autowired
    private LoginLogService loginLogService;
    
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index()
    {
        return "/hf/syst/loginlog_index";
    }
    
    @ResponseBody
    @RequestMapping(value = "/find/orguser", method = {RequestMethod.GET})
    public Object sysUserLog(OffsetPage offsetPage,String assessRole) {
        return CommonResponse.getSuccessResponse(loginLogService.findOrgUserLoginLog(offsetPage, assessRole));
    } 
    
    @ResponseBody
    @RequestMapping(value = "/find/adminuser", method = {RequestMethod.GET})
    public Object adminUserLog(OffsetPage offsetPage,String unitId) {
        return CommonResponse.getSuccessResponse(loginLogService.findAdminUserLoginLog(offsetPage,unitId));
    } 
}
