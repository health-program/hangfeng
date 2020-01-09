package com.paladin.hf.mapper.ordinary;

import java.util.List;

import com.paladin.hf.service.ordinary.vo.PrizepunishUserVO;
import com.paladin.hf.service.org.dto.OrgUserQuery;
import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.ordinary.Prizepunish;
import com.paladin.hf.service.ordinary.dto.PrizepunishQuery;
import com.paladin.hf.service.ordinary.vo.PrizepunishVO;


public interface PrizepunishMapper extends CustomMapper<Prizepunish>{
    
    /*科室查询某个人的奖惩记录 */
    List<PrizepunishVO> selectPrizeDept(PrizepunishQuery query);
    
    /*查询个人的奖惩记录 */
    List<PrizepunishVO> selectPrizePeople(PrizepunishQuery query);
    
    /*机构查询某个人的奖惩记录 */
    List<PrizepunishVO> selectPrizeUnit(PrizepunishQuery query);
    
    /*查看是否有被驳回的奖惩事件 */
    public int countRejectedPrizePunishByUser(@Param("userId") String userId);

    List<PrizepunishUserVO> orgUserFind(OrgUserQuery query);
}