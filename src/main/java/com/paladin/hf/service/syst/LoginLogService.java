package com.paladin.hf.service.syst;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.hf.mapper.syst.LoginLogMapper;
import com.paladin.hf.model.syst.LoginLog;
import com.paladin.hf.service.syst.vo.SysUserVO;

@Service
public class LoginLogService extends ServiceSupport<LoginLog> {

	@Autowired
	private LoginLogMapper loginLogMapper;

	public void addLoginLog(String userName, String userId, int type, String ip, String action) {
		LoginLog log = new LoginLog();
		log.setUserId(userId);
		log.setType(type);
		log.setLogUser(userName);
		log.setLogTime(new Date());
		log.setLogIp(ip);
		save(log);
	}


	public PageResult<SysUserVO> findOrgUserLoginLog(OffsetPage offsetPage, String assessRole) {
		Page<SysUserVO> page = PageHelper.offsetPage(offsetPage.getOffset(), offsetPage.getLimit());// 分页
		loginLogMapper.findOrgUserLoginLog(assessRole);
		return new PageResult<>(page);
	}

	public PageResult<SysUserVO> findAdminUserLoginLog(OffsetPage offsetPage, String unitId) {
		Page<SysUserVO> page = PageHelper.offsetPage(offsetPage.getOffset(), offsetPage.getLimit());// 分页
		loginLogMapper.findAdminUserLoginLog(unitId);
		return new PageResult<>(page);
	}

}
