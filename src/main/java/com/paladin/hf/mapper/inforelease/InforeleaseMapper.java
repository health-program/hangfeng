package com.paladin.hf.mapper.inforelease;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.model.inforelease.Inforelease;
import com.paladin.hf.service.inforelease.dto.InforeleaseQuery;
import com.paladin.hf.service.inforelease.vo.InforeleaseVO;


public interface InforeleaseMapper extends CustomMapper<Inforelease>{
    
    List<InforeleaseVO> selectInforeleaseAll(InforeleaseQuery query);
    
    List<InforeleaseVO> noticyandpolicyfileAll();
    
    
    
    /**
     * @author jisanjie
     * 
     * 
     */
    List<InforeleaseVO> showAssigned(@Param("info") Inforelease inforelease, @Param("uid") String unitId,@Param("tid") String teamId,@Param("aid") String agencyId);
    
    List<InforeleaseVO> getList(@Param("info") Inforelease inforelease,@Param("query") UnitQuery query);

    List<InforeleaseVO> findListMore(@Param("info") Inforelease inforelease, @Param("uid") String unitId,@Param("tid") String teamId,@Param("aid") String agencyId);
    /**
     * 
     */
 
}