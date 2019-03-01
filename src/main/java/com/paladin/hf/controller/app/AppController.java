package com.paladin.hf.controller.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.common.model.syst.SysAttachment;
import com.paladin.common.service.syst.SysAttachmentService;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.utils.qrcode.QRCodeException;
import com.paladin.framework.utils.qrcode.QRCodeUtil;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.model.syst.SysApp;
import com.paladin.hf.service.syst.SysAppService;

/**   
 * @author 黄伟华
 * @version 2019年2月19日 下午1:27:33 
 */
@Controller
@RequestMapping(value = "/app")
public class AppController extends ControllerSupport{
    
    @Autowired
    SysAppService sysAppService;
    
    @Autowired
    SysAttachmentService sysAttachmentService;
    
    @RequestMapping("/ios/url")
    public Object iosIndex(HttpServletRequest request, HttpServletResponse response){
        String url= request.getScheme() + "://"+ request.getServerName()+ ":" + request.getServerPort()
            + request.getContextPath() + "/static/app/index.html";
        try{
            QRCodeUtil.createQRCode(url, response.getOutputStream());// 生成二维码
        }
        catch (QRCodeException | IOException e){
        }
        return null;
    }
    
    @RequestMapping("/android/url")
    public Object androidIndex(HttpServletRequest request, HttpServletResponse response){
        String url= request.getScheme() + "://"+ request.getServerName()+ ":" + request.getServerPort() 
            + request.getContextPath() + "/file/hangfeng.apk";
        try{
            QRCodeUtil.createQRCode(url, response.getOutputStream());// 生成二维码
        }
        catch (QRCodeException | IOException e){
        }
        return null;
    }
    
    /**
     * 获取app下载url
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/obtain/url")
    public Object obtainUrl(HttpServletRequest request, HttpServletResponse response){
        
        SysApp app = sysAppService.sysAppById();
        
        if (app == null){return null;}
        SysAttachment attachment = sysAttachmentService.get(app.getAttachmentId());
        if (attachment == null){return null;}
        String basePath =request.getScheme() + "://"
                + request.getServerName()
                + ":" + request.getServerPort()
                + request.getContextPath() + "/file/"
                + attachment.getPelativePath() + "";
        try{
            QRCodeUtil.createQRCode(basePath, response.getOutputStream());// 生成二维码
        }
        catch (QRCodeException | IOException e){
        }
        return null;
    }
    
    /**
     * app自动更新接口，获取下载url和最新本号version
     * 
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/auto/update")
    @ResponseBody
    public Object updateApp(HttpServletRequest request,HttpServletResponse response) {
        
        SysApp app = sysAppService.sysAppById();
        
        if (app == null) {return null;}
        SysAttachment attachment = sysAttachmentService.get(app.getAttachmentId());
        String downloadURL = request.getScheme() + "://"
            + request.getServerName()
            + ":" + request.getServerPort()
            + request.getContextPath() + "/file/"
            + attachment.getPelativePath() + "";
        
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("version", app.getVersion());
        map.put("downloadURL", downloadURL);
        
        return CommonResponse.getSuccessResponse(map);
    }
    
}
