package com.paladin.hf.mapper.inforelease;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.inforelease.Inforelease;
import com.paladin.hf.service.inforelease.dto.InforeleaseQuery;
import com.paladin.hf.service.inforelease.vo.InforeleaseVO;


public interface InforeleaseMapper extends CustomMapper<Inforelease>{
    
    List<InforeleaseVO> selectInforeleaseAll(@Param("query")InforeleaseQuery query);
    List<InforeleaseVO> selectInforelease(@Param("query")InforeleaseQuery query,@Param("agencyIds") List<String> agencyIds);
    
    List<InforeleaseVO> noticyandpolicyfileAll();
    List<InforeleaseVO> noticyandpolicyfile(@Param("unitIds") List<String> unitIds);
    
    List<InforeleaseVO> inforeleaseMoreAll(@Param("query")InforeleaseQuery query);
    List<InforeleaseVO> inforeleaseMore(@Param("query")InforeleaseQuery query,@Param("unitIds") List<String> unitIds);
    
}