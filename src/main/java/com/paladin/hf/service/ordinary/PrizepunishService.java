package com.paladin.hf.service.ordinary;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.hf.mapper.ordinary.PrizepunishMapper;
import com.paladin.hf.model.ordinary.Prizepunish;

/**   
 * @author 黄伟华
 * @version 2018年1月16日 上午10:52:58 
 */
@Service
public class PrizepunishService extends ServiceSupport<Prizepunish>
{
    @Autowired
    private PrizepunishMapper prizepunishMapper;
    
    public List<Map<String, Object>> getPageList(Prizepunish  prizepunish,OffsetPage offsetPage) {
        PageHelper.offsetPage(offsetPage.getOffset(), offsetPage.getLimit());      
        return prizepunishMapper.selectPrizepunishAll(prizepunish);
    }
    
    public List<Map<String, Object>> getPageList2(Prizepunish  prizepunish,OffsetPage offsetPage) {
        PageHelper.offsetPage(offsetPage.getOffset(), offsetPage.getLimit());               
        return prizepunishMapper.selectPrizepunishAll2(prizepunish);
    }
       

    public List<Map<String, Object>> appGetPageList(Prizepunish  prizepunish,OffsetPage offsetPage) {
        PageHelper.offsetPage(offsetPage.getOffset(), offsetPage.getLimit());      
        return prizepunishMapper.appSelectPrizepunishAll(prizepunish);//,CamelCaseUtil.toUnderlineName(prizepunish.getAppSort())+" "+prizepunish.getOrder()
    }
    
    
    public Prizepunish prizepId(String id){
        return prizepunishMapper.prizepId(id);
    }
}
