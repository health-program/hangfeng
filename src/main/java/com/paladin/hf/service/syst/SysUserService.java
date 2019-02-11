package com.paladin.hf.service.syst;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paladin.framework.common.Condition;
import com.paladin.framework.common.GeneralCriteriaBuilder;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.secure.SecureUtil;
import com.paladin.framework.utils.validate.ValidateUtil;
import com.paladin.hf.core.BusinessConfig;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.mapper.syst.SysUserMapper;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.model.syst.AdminUser;
import com.paladin.hf.model.syst.SysUser;
import com.paladin.hf.service.org.OrgUserService;

import tk.mybatis.mapper.entity.Example;

@Service
public class SysUserService extends ServiceSupport<SysUser> {

	@Autowired
	private SysUserMapper sysUserMapper;

	@Autowired
	private OrgUserService orgUserService;

	@Autowired
	private AdminUserService adminUserService;

	/**
	 * 创建一个默认账号
	 * 
	 * @param account
	 * @param orgUserId
	 * @return
	 */
	public int createOrgUserAccount(String account, String orgUserId) {
		return createUserAccount(account, orgUserId, SysUser.TYPE_ORG_USER);
	}

	@Transactional
	public int createOrgUserAndAccount(String account, OrgUser user) {
		if (createUserAccount(account, user.getId(), SysUser.TYPE_ORG_USER) > 0) {
			if (orgUserService.save(user) > 0) {
				return 1;
			}
		}
		throw new BusinessException("创建档案人员失败");
	}

	/**
	 * 创建一个管理人员账号
	 * 
	 * @param account
	 * @param sysUserId
	 * @return
	 */
	public int createSysUserAccount(String account, String sysUserId) {
		return createUserAccount(account, sysUserId, SysUser.TYPE_ADMIN_USER);
	}

	/**
	 * 创建一个账号
	 * 
	 * @param account
	 * @param userId
	 * @param type
	 * @return
	 */
	public int createUserAccount(String account, String userId, Integer type) {

		if (account == null || !validateAccount(account))
			throw new BusinessException("账号不符合规则或者已经存在该账号");

		String salt = SecureUtil.createSalte();
		String password = BusinessConfig.getDefaultPassword();
		password = SecureUtil.createPassword(password, salt);

		SysUser user = new SysUser();
		user.setAccount(account);
		user.setPassword(password);
		user.setSalt(salt);
		user.setUserId(userId);
		user.setState(SysUser.STATE_ENABLED);
		user.setType(type);

		return save(user);

	}

	private final static Pattern accountPattern = Pattern.compile("^\\w{5,30}$");
	private final static Pattern passwordPattern = Pattern.compile("^\\w{6,20}$");

	/**
	 * 验证账号
	 * 
	 * @param account
	 * @return true 可用/false 不可用
	 */
	public boolean validateAccount(String account) {
		if (!accountPattern.matcher(account).matches())
			return false;

		if (ValidateUtil.isValidatedAllIdcard(account)) {
			return false;
		}

		Example example = GeneralCriteriaBuilder.buildAnd(SysUser.class, new Condition(SysUser.COLUMN_FIELD_ACCOUNT, QueryType.EQUAL, account));
		return sysUserMapper.selectCountByExample(example) == 0;
	}

	/**
	 * 根据原账号和用户ID更新账号
	 * 
	 * @param userId
	 * @param originAccount
	 * @param nowAccount
	 * @return
	 */
	public int updateAccount(String userId, String originAccount, String nowAccount) {
		if (!accountPattern.matcher(nowAccount).matches()) {
			throw new BusinessException("账号不符合规则");
		}
		return sysUserMapper.updateAccount(userId, originAccount, nowAccount);
	}

	public SysUser getUser(String account) {
		Example example = GeneralCriteriaBuilder.buildAnd(SysUser.class, new Condition(SysUser.COLUMN_FIELD_ACCOUNT, QueryType.EQUAL, account));
		List<SysUser> users = sysUserMapper.selectByExample(example);
		return (users != null && users.size() > 0) ? users.get(0) : null;
	}

	/**
	 * 更新登录人密码
	 * 
	 * @param password
	 * @return
	 */
	public int updateSelfPassword(String newPassword, String oldPassword) {

		if (newPassword == null || !passwordPattern.matcher(newPassword).matches()) {
			throw new BusinessException("密码不符合规则");
		}

		HfUserSession session = HfUserSession.getCurrentUserSession();
		String account = session.getAccount();
		SysUser user = getUser(account);
		if (user == null) {
			throw new BusinessException("账号异常");
		}

		oldPassword = SecureUtil.createPassword(oldPassword, user.getSalt());
		if (!oldPassword.equals(user.getPassword())) {
			throw new BusinessException("原密码不正确");
		}

		String salt = SecureUtil.createSalte();
		newPassword = SecureUtil.createPassword(newPassword, salt);

		SysUser updateUser = new SysUser();
		updateUser.setId(user.getId());
		updateUser.setSalt(salt);
		updateUser.setPassword(newPassword);
		updateUser.setIsFirstLogin(0);// 密码强制修改后该状态值设为0

		int effect = updateSelective(updateUser);

		if (effect > 0) {
			SecurityUtils.getSubject().logout();
		}

		return effect;
	}

	public int resetOrgUserPassword(String userId) {

		OrgUser user = orgUserService.get(userId);
		SysUser sysUser = getUser(user.getAccount());
		if (sysUser == null) {
			throw new BusinessException("账号异常");
		}

		String salt = SecureUtil.createSalte();
		String password = BusinessConfig.getDefaultPassword();
		password = SecureUtil.createPassword(password, salt);

		SysUser updateUser = new SysUser();
		updateUser.setId(sysUser.getId());
		updateUser.setSalt(salt);
		updateUser.setPassword(password);
		updateUser.setIsFirstLogin(1);// 密码重置后该状态值设为1

		return updateSelective(updateUser);

	}

	public int resetAdminUserPassword(String userId) {

		AdminUser user = adminUserService.get(userId);
		SysUser sysUser = getUser(user.getAccount());
		if (sysUser == null) {
			throw new BusinessException("账号异常");
		}

		String salt = SecureUtil.createSalte();
		String password = BusinessConfig.getDefaultPassword();
		password = SecureUtil.createPassword(password, salt);

		SysUser updateUser = new SysUser();
		updateUser.setId(sysUser.getId());
		updateUser.setSalt(salt);
		updateUser.setPassword(password);
		updateUser.setIsFirstLogin(1);// 密码强制修改后该状态值设为1

		return updateSelective(updateUser);

	}

	public SysUser getSysUserByIdentification(String identification) {
		List<OrgUser> orgUsers = orgUserService.searchAll(new Condition("identification", QueryType.EQUAL, identification));
		if (orgUsers != null && orgUsers.size() > 0) {
			if (orgUsers.size() > 1) {
				throw new UnknownAccountException("存在多个相同身份证号码用户");
			}
			String account = orgUsers.get(0).getAccount();
			return getUser(account);
		}

		return null;
	}

	public void UpdatesysUserLastTime(String account) {
		SysUser sysUser = getUser(account);
		SysUser user = new SysUser();
		user.setId(sysUser.getId());
		user.setIsFirstLogin(0);
		user.setLastLoginTime(new Date());
		sysUserMapper.updateByPrimaryKeySelective(user);
	}

}
