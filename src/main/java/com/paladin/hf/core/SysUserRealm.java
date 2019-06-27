package com.paladin.hf.core;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.model.syst.SysUser;
import com.paladin.hf.service.org.OrgUserService;
import com.paladin.hf.service.syst.LoginLogService;
import com.paladin.hf.service.syst.SysUserService;

public class SysUserRealm extends AuthorizingRealm {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private OrgUserService orgUserService;

	@Autowired
	private HfUserSessionFactory userSessionFactory;

	@Autowired
	private LoginLogService loginLogService;

	/**
	 * 认证信息.(身份验证) : Authentication 是用来验证用户身份
	 * 
	 * @param token
	 * @return
	 * @throws AuthenticationException
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		logger.debug("后台登录：SysUserRealm.doGetAuthenticationInfo()");

		SysUser sysUser = null;
		// 获取用户的输入的账号或身份证号.
		String username = (String) token.getPrincipal();
		// 身份证号登录
		List<OrgUser> orgUsers = orgUserService.findUserByIdentification(username);

		if (orgUsers != null && orgUsers.size() > 0) {
			if (orgUsers.size() > 1) {
				throw new UnknownAccountException("存在多个相同身份证号码用户");
			}

			String account = orgUsers.get(0).getAccount();
			sysUser = sysUserService.getUser(account);
		}

		// 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
		if (sysUser == null) {
			sysUser = sysUserService.getUser(username);
		}

		if (sysUser == null) {
			throw new UnknownAccountException();
		}

		if (sysUser.getState() != 1) {
			throw new LockedAccountException(); // 帐号锁定
		}

		/*
		 * 获取权限信息:这里没有进行实现， 请自行根据UserInfo,Role,Permission进行实现； 获取之后可以在前端for循环显示所有链接;
		 */

		HfUserSession userSession = userSessionFactory.create(sysUser);

		SimpleAuthenticationInfo authenticationInfo = null;

		if (token instanceof IdentificationToken) {
			// 加密方式;
			// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
			authenticationInfo = new SimpleAuthenticationInfo(userSession, IdentificationToken.default_password, // 密码
					ByteSource.Util.bytes(IdentificationToken.default_salt), // salt
					sysUser.getAccount() // realm name
			);

		} else {

			// 加密方式;
			// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
			authenticationInfo = new SimpleAuthenticationInfo(userSession, sysUser.getPassword(), // 密码
					ByteSource.Util.bytes(sysUser.getSalt()), // salt
					sysUser.getAccount() // realm name
			);
		}

		logger.info("===>用户[" + sysUser.getAccount() + ":" + userSession.getUserName() + "]登录系统<===");

		// 登录日志与更新最近登录时间
		loginLogService.addLoginLog(userSession.getUserName(), userSession.getUserId(), sysUser.getType(), "", "");
		//sysUserService.UpdatesysUserLastTime(userSession.getAccount());

		return authenticationInfo;
	}

	/**
	 * 此方法调用 hasRole,hasPermission的时候才会进行回调.
	 *
	 * 权限信息.(授权): 1、如果用户正常退出，缓存自动清空； 2、如果用户非正常退出，缓存自动清空；
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。 （需要手动编程进行实现；放在service进行调用）
	 * 在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例， 调用clearCached方法；
	 * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
	 * 
	 * @param principals
	 * @return
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) throws AuthenticationException {
		/*
		 * 当没有使用缓存的时候，不断刷新页面的话，这个代码会不断执行， 当其实没有必要每次都重新设置权限信息，所以我们需要放到缓存中进行管理；
		 * 当放到缓存中时，这样的话，doGetAuthorizationInfo就只会执行一次了， 缓存过期之后会再次执行。
		 */
		// logger.debug("后台权限校验-->AdminShiroRealm.doGetAuthorizationInfo()");

		// SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		// UserSession user = (UserSession) principals.getPrimaryPrincipal();
		//
		// authorizationInfo.addStringPermissions(user.getPermissionCodes());
		// return authorizationInfo;
		return null;

	}

}
