package com.paladin.hf.mapper.statistics;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.service.statistics.dto.PrizepunishQueryDTO;
import com.paladin.hf.service.statistics.vo.PrizepunishCountVO;

/**   
 * @author 黄伟华
 * @version 2018年4月3日 下午1:12:42 
 */
public interface PrizeCountQueryMapper
{
    List<PrizepunishCountVO> prizeCountQueryAll(@Param("param") PrizepunishQueryDTO query, @Param("unitParam")UnitQuery unitQuery);
}
