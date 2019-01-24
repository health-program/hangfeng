package com.paladin.hf.service.ordinary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.UserSession;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.mapper.ordinary.PrizepunishMapper;
import com.paladin.hf.model.ordinary.Prizepunish;
import com.paladin.hf.service.ordinary.dto.PrizepunishDTO;
import com.paladin.hf.service.ordinary.dto.PrizepunishQuery;
import com.paladin.hf.service.ordinary.vo.PrizepunishVO;

/**   
 * @author 黄伟华
 * @version 2018年1月16日 上午10:52:58 
 */
@Service
public class PrizepunishService extends ServiceSupport<Prizepunish>{
    
    @Autowired
    private PrizepunishMapper prizepunishMapper;
    
    /*查询个人的奖惩记录 */
    public PageResult<PrizepunishVO> selectPrizePeople(PrizepunishQuery query){
        Page<PrizepunishVO>  page =  PageHelper.offsetPage(query.getOffset(), query.getLimit());
        query.setOrgUserId(UserSession.getCurrentUserSession().getUserId());
        prizepunishMapper.selectPrizePeople(query);
        return new PageResult<>(page);
    }
    
    /*个人新增，修改奖惩记录 */
    public int prizePeopleSaveOrUpdate(Prizepunish p){
        if (p == null) {
            throw new BusinessException("数据异常");
        }
       
        if(p.getOperationState() == Prizepunish.OPERATION_STATE_SELF_TEMPORARY){
            p.setOperationState(Prizepunish.OPERATION_STATE_SELF_TEMPORARY);
        }
        if(p.getOperationState() == Prizepunish.OPERATION_STATE_SELF_SUBMIT){
            p.setOperationState(Prizepunish.OPERATION_STATE_SELF_SUBMIT);
        }
        
        p.setOrgUserId(UserSession.getCurrentUserSession().getUserId());
        p.setExamineState(Prizepunish.EXAMINE_WAIT);
        
        return saveOrUpdate(p);
    }
    
    /*查看某条记录*/
    public PrizepunishVO prizeView(String id){
        Prizepunish p= get(id);
        if(p != null){
            PrizepunishVO vo = new PrizepunishVO();
            SimpleBeanCopyUtil.simpleCopy(p, vo);
            return vo;
        }
        return null;
    }
    
    
    /*科室查询某个人的奖惩记录 */
    public PageResult<PrizepunishVO> selectPrizeDept(PrizepunishQuery query){
        Page<PrizepunishVO>  page =  PageHelper.offsetPage(query.getOffset(), query.getLimit());
        prizepunishMapper.selectPrizeDept(query);
        return new PageResult<>(page);
    }
    
    /*科室为某个人新增,修改奖惩记录 */
    public int prizeDetpSaveOrUpdate(Prizepunish p){
        if (p == null) {
            throw new BusinessException("数据异常");
        }
        p.setOperationState(Prizepunish.OPERATION_STATE_DEPARTMENT_TEMPORARY);
        p.setExamineState(Prizepunish.EXAMINE_WAIT);
        
         return saveOrUpdate(p);
    }
    
    /*科室为某个人审核奖惩记录*/
    public int examineUpdate(PrizepunishDTO dto){
        if (dto == null) {
            throw new BusinessException("数据异常");
        }
        Prizepunish p = new Prizepunish();
        SimpleBeanCopyUtil.simpleCopy(dto, p);
        
        UserSession userSession = UserSession.getCurrentUserSession();
        String userName = userSession == null ? "" : userSession.getUserName();
        
        if(p.getExamineState() != Prizepunish.EXAMINE_WAIT){
            p.setExaminePeople(userName);
            p.setOperationState(Prizepunish.OPERATION_STATE_DEPARTMENT_SUBMIT);
        }
       return updateSelective(p);
    }
    
    /*机构查询某个人的奖惩记录 */
    public PageResult<PrizepunishVO> selectPrizeUnit(PrizepunishQuery query){
        Page<PrizepunishVO>  page =  PageHelper.offsetPage(query.getOffset(), query.getLimit());
        prizepunishMapper.selectPrizeUnit(query);
        return new PageResult<>(page);
    }
    
    
    /*机构为某个人新增,修改奖惩记录 */
    public int prizeUnitSaveOrUpdate(Prizepunish p){
        if (p == null) {
            throw new BusinessException("数据异常");
        }
        p.setOperationState(Prizepunish.OPERATION_STATE_AGENCY_TEMPORARY);
        p.setExamineState(Prizepunish.EXAMINE_WAIT);
         return saveOrUpdate(p);
    }
    
    /*机构审核*/
    public int examineUnitUpdate(PrizepunishDTO dto){
        if (dto == null) {
            throw new BusinessException("数据异常");
        }
        Prizepunish p = new Prizepunish();
        SimpleBeanCopyUtil.simpleCopy(dto, p);
        
        UserSession userSession = UserSession.getCurrentUserSession();
        String userName = userSession == null ? "" : userSession.getUserName();
        
        if(p.getExamineState() != Prizepunish.EXAMINE_WAIT){
            p.setExaminePeople(userName);
            p.setOperationState(Prizepunish.OPERATION_STATE_AGENCY_SUBMIT);
        }
       return updateSelective(p);
    }
    
}
