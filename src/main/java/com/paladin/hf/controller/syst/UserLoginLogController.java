package com.paladin.hf.controller.syst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.service.syst.AdminUserService;
import com.paladin.hf.service.syst.SysUserService;

/**   
 * @author 黄伟华
 * @version 2018年10月9日 上午9:36:42 
 */

@Controller
@RequestMapping("/login/log")
public class UserLoginLogController
{
    @Autowired
    private SysUserService sysUserService;
    
    @Autowired
    private AdminUserService adminUserService;
    
    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    public String index()
    {
        return "/console/loginLog/index";
    }
    
    @ResponseBody
    @RequestMapping(value = "/sysUserLog", method = {RequestMethod.GET})
    public Object sysUserLog(OffsetPage offsetPage,String assessRole) {
        return CommonResponse.getSuccessResponse(sysUserService.sysUserLog(offsetPage, assessRole));
    } 
    
    @ResponseBody
    @RequestMapping(value = "/adminUserLog", method = {RequestMethod.GET})
    public Object adminUserLog(OffsetPage offsetPage,String unitId) {
        return CommonResponse.getSuccessResponse(adminUserService.adminUserLog(offsetPage,unitId));
    } 
}
