package com.paladin.hf.service.syst;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.paladin.framework.core.ServiceSupport;
import com.paladin.hf.model.syst.LoginLog;


@Service
public class LoginLogService extends ServiceSupport<LoginLog>{

	public void addLoginLog(String userName, String userId, int type, String ip, String action) {
		LoginLog log = new LoginLog();
		log.setUserId(userId);
		log.setType(type);
        log.setLogUser(userName);
        log.setLogTime(new Date());
        log.setLogIp(ip);
		save(log);
	}


}
