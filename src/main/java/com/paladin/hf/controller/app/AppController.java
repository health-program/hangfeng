package com.paladin.hf.controller.app;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.utils.qrcode.QRCodeException;
import com.paladin.framework.utils.qrcode.QRCodeUtil;

/**   
 * @author 黄伟华
 * @version 2019年2月19日 下午1:27:33 
 */
@Controller
@RequestMapping(value = "/app")
public class AppController extends ControllerSupport{
    
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
}
