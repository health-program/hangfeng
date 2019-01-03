package com.paladin.hf.model.syst;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * author netmatch
 * date 2017/1/6 0006 上午 11:21
 */
public class LoginLog  {
	
    @Id
    @Column(name = "log_id")
    @GeneratedValue(generator = "UUID")
    private String logId;

    private String logUser;
    
    private String userId;
    
    private Integer type;

    private Date logTime;

    private String logIp;

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getLogUser() {
        return logUser;
    }
    
    public void setLogUser(String logUser) {
        this.logUser = logUser;
    }


    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getLogTime() {
		return logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

}
