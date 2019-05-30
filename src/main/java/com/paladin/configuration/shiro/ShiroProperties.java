package com.paladin.configuration.shiro;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "paladin.shiro")
public class ShiroProperties {

	/**
	 * token field，如果NULL则不用
	 */
	private String tokenField;
	
	/**
	 * 登录方式session域名，区分本地登录和cas
	 */
	private String loginTypeField = "_LOGIN_TYPE_FIELD";

	/**
	 * session 保存在redis中key的前缀
	 */
	private String sessionPrefix = "shiro-session";

	/**
	 * session 在redis中缓存时间
	 */
	private int sessionTime = 30;

	/**
	 * session lastAccessTime 更新间隔
	 */
	private int accessTimeUpdateInterval = 120 * 1000;

	/**
	 * 登录URL
	 */
	private String loginUrl =  "/login";

	/**
	 * 登出URL
	 */
	private String logoutUrl = "/logout";

	/**
	 * 登录成功跳转URL
	 */
	private String successUrl = "/index";

	/**
	 * 未验证跳转页面
	 */
	private String unauthorizedUrl = "/static/html/error_401.html";

	/**
	 * APP请求标识
	 */
	private String appHeaderField = "isApp";	
	
	/**
	 * 是否启用redis
	 */
	private boolean redisEnabled = false;

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getTokenField() {
		return tokenField;
	}

	public void setTokenField(String tokenField) {
		this.tokenField = tokenField;
	}

	public String getSessionPrefix() {
		return sessionPrefix;
	}

	public void setSessionPrefix(String sessionPrefix) {
		this.sessionPrefix = sessionPrefix;
	}

	public int getSessionTime() {
		return sessionTime;
	}

	public void setSessionTime(int sessionTime) {
		this.sessionTime = sessionTime;
	}

	public int getAccessTimeUpdateInterval() {
		return accessTimeUpdateInterval;
	}

	public void setAccessTimeUpdateInterval(int accessTimeUpdateInterval) {
		this.accessTimeUpdateInterval = accessTimeUpdateInterval;
	}

	public String getUnauthorizedUrl() {
		return unauthorizedUrl;
	}

	public void setUnauthorizedUrl(String unauthorizedUrl) {
		this.unauthorizedUrl = unauthorizedUrl;
	}

	public String getAppHeaderField() {
		return appHeaderField;
	}

	public void setAppHeaderField(String appHeaderField) {
		this.appHeaderField = appHeaderField;
	}

	public String getLoginTypeField() {
		return loginTypeField;
	}

	public void setLoginTypeField(String loginTypeField) {
		this.loginTypeField = loginTypeField;
	}

	public boolean isRedisEnabled() {
		return redisEnabled;
	}

	public void setRedisEnabled(boolean redisEnabled) {
		this.redisEnabled = redisEnabled;
	}

}
