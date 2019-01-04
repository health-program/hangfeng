package com.paladin.hf.service.statistics;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.mapper.statistics.PrizeCountQueryMapper;
import com.paladin.hf.service.statistics.dto.PrizepunishQueryDTO;
import com.paladin.hf.service.statistics.vo.PrizepunishCountVO;

/**   
 * @author 黄伟华
 * @version 2018年4月3日 下午1:17:25 
 */
@Service
public class PrizeCountQueryService 
{
    
    @Autowired
    PrizeCountQueryMapper countQueryMapper;
    
  public  List<PrizepunishCountVO> prizeCountQueryAll(PrizepunishQueryDTO query){
        return countQueryMapper.prizeCountQueryAll(query, DataPermissionUtil.getUnitQueryDouble(query.getUnitId()));
    }
}
