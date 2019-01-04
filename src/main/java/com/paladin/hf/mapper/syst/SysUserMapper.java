package com.paladin.hf.mapper.syst;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.syst.SysUser;
import com.paladin.hf.service.syst.vo.SysUserVO;


public interface SysUserMapper extends CustomMapper<SysUser>{
    
    List<SysUserVO> sysUserLog(@Param("assessRole")String assessRole);

}
