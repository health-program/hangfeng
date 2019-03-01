package com.paladin.hf.service.syst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paladin.framework.core.ServiceSupport;
import com.paladin.hf.mapper.syst.SysAppMapper;
import com.paladin.hf.model.syst.SysApp;

/**   
 * @author 黄伟华
 * @version 2018年12月5日 上午9:58:07 
 */
@Service
public class SysAppService extends ServiceSupport<SysApp>{
    
    @Autowired
    SysAppMapper sysAppMapper;
    
    public SysApp sysAppById(){
        return sysAppMapper.sysAppById();
    }
    
}
