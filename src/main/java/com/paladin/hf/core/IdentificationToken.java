package com.paladin.hf.core;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.netmatch.tonto.utils.secure.SecureUtil;

public class IdentificationToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1712764331783306964L;

	private final static String default_origin_password = "qiyipassword";
	public final static String default_salt = SecureUtil.createSalte();
	public final static String default_password = SecureUtil.createPassword(default_origin_password, default_salt);
	
	public IdentificationToken(final String account, final String identification) {
		super(account, default_origin_password);
	}
	
}
