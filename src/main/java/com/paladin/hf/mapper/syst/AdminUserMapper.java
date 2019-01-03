package com.paladin.hf.mapper.syst;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.syst.AdminUser;
import com.paladin.hf.model.syst.SysUserVo;

public interface AdminUserMapper extends CustomMapper<AdminUser>{

	int wipeByPrimaryKey(String id);
	
	 List<SysUserVo> adminUserLog(@Param("unitId")String unitId);

}
