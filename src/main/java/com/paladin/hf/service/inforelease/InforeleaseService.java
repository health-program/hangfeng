package com.paladin.hf.service.inforelease;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.mapper.inforelease.InforeleaseMapper;
import com.paladin.hf.model.inforelease.Inforelease;
import com.paladin.hf.service.inforelease.dto.InforeleaseQuery;
import com.paladin.hf.service.inforelease.vo.InforeleaseVO;


/**   
 * @author 黄伟华
 * @version 2018年1月9日 下午5:05:49 
 */
@Service
public class InforeleaseService extends ServiceSupport<Inforelease>
{
    @Autowired
    private InforeleaseMapper inforeleaseMapper;
    
    public PageResult<InforeleaseVO> selectInforeleaseAll(InforeleaseQuery query) {
          Page<InforeleaseVO>  page =  PageHelper.offsetPage(query.getOffset(), query.getLimit());
          HfUserSession userSession = HfUserSession.getCurrentUserSession();
          if (userSession.isAdminRoleLevel()) {//登录人为系统管理员，展示所有信息
                inforeleaseMapper.selectInforeleaseAll(query);
            }else{
              String unitId = userSession.getUserUnitId();
              Unit unit = UnitContainer.getUnit(unitId);
              String agencyId = unit.getAgency().getId();
              Unit team = unit.getAssessTeam();         
              String teamId = team == null?null:team.getId();
              if (userSession.isOrgUser()){ 
                    inforeleaseMapper.selectInforelease(query,unitId,teamId,agencyId); 
              }else{
                    inforeleaseMapper.selectInforelease(query,null,null,agencyId); 
              }
            }
          return new PageResult<>(page);
      }
    
    public List<InforeleaseVO> noticyandpolicyfileAll(){
        
        HfUserSession userSession = HfUserSession.getCurrentUserSession();
        if (userSession.isAdminRoleLevel()) {//登录人为系统管理员，展示所有信息
          return  inforeleaseMapper.noticyandpolicyfileAll();
        }else{
              String unitId = userSession.getUserUnitId();
              Unit unit = UnitContainer.getUnit(unitId);
              String agencyId = unit.getAgency().getId();
              Unit team = unit.getAssessTeam();         
              String teamId = team == null?null:team.getId();
              if (userSession.isOrgUser()) {//登录人为档案人员            
                    return inforeleaseMapper.noticyandpolicyfile(unitId,teamId,agencyId);
              }else {//登录人为单位管理员
                    return inforeleaseMapper.noticyandpolicyfile(null,null,agencyId);
              } 
        }
    }
    
    public InforeleaseVO detail(String id){
        Inforelease info =get(id);
        if(info != null){
            InforeleaseVO vo = new InforeleaseVO();
            SimpleBeanCopyUtil.simpleCopy(info, vo);
            return vo;
        }
        return null;
    }
    
    //查看更多
    public PageResult<InforeleaseVO> inforeleaseMore(InforeleaseQuery query) {
        Page<InforeleaseVO>  page =  PageHelper.offsetPage(query.getOffset(), query.getLimit());
        HfUserSession userSession = HfUserSession.getCurrentUserSession();
        if (userSession.isAdminRoleLevel()) {//登录人为系统管理员，展示所有信息
            inforeleaseMapper.inforeleaseMoreAll(query);
        }else{
              String unitId = userSession.getUserUnitId();
              Unit unit = UnitContainer.getUnit(unitId);
              String agencyId = unit.getAgency().getId();
              Unit team = unit.getAssessTeam();         
              String teamId = team == null?null:team.getId();
              if (userSession.isOrgUser()) {//登录人为档案人员            
                     inforeleaseMapper.inforeleaseMore(query,unitId,teamId,agencyId);
              }else {//登录人为单位管理员
                     inforeleaseMapper.inforeleaseMore(query,null,null,agencyId);
              } 
        }
        return new PageResult<>(page);
    }
    
    
}
