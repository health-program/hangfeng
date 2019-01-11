package com.paladin.hf.mapper.syst;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.syst.LoginLog;
import com.paladin.hf.service.syst.vo.SysUserVO;

/**
 * author netmatch date 2017/1/6 0006 上午 11:25
 */
@Service
public interface LoginLogMapper extends CustomMapper<LoginLog> {
	
	public List<SysUserVO> findOrgUserLoginLog(@Param("assessRole") String assessRole);
	
	public List<SysUserVO> findAdminUserLoginLog(@Param("unitId") String unitId);
}
