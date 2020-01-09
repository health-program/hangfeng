package com.paladin.hf.service.ordinary;

import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.service.ordinary.vo.PrizepunishUserVO;
import com.paladin.hf.service.org.dto.OrgUserQuery;
import com.paladin.hf.service.org.vo.OrgUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.UserSession;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.mapper.assess.quantificate.AssessQuantitativeMapper;
import com.paladin.hf.mapper.ordinary.PrizepunishMapper;
import com.paladin.hf.model.ordinary.Prizepunish;
import com.paladin.hf.service.ordinary.dto.PrizepunishDTO;
import com.paladin.hf.service.ordinary.dto.PrizepunishQuery;
import com.paladin.hf.service.ordinary.vo.PrizepunishVO;

import java.util.List;

/**
 * @author 黄伟华
 * @version 2018年1月16日 上午10:52:58
 */
@Service
public class PrizepunishService extends ServiceSupport<Prizepunish> {

	@Autowired
	private PrizepunishMapper prizepunishMapper;
	
	@Autowired
	private AssessQuantitativeMapper assessQuantitativeMapper; 

	/* 查询个人的奖惩记录 */
	public PageResult<PrizepunishVO> selectPrizePeople(PrizepunishQuery query) {
		Page<PrizepunishVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
		query.setOrgUserId(UserSession.getCurrentUserSession().getUserId());
		prizepunishMapper.selectPrizePeople(query);
		return new PageResult<>(page);
	}

	/* 查看某条记录 */
	public PrizepunishVO prizeView(String id) {
		Prizepunish p = get(id);
		if (p != null) {
			PrizepunishVO vo = new PrizepunishVO();
			SimpleBeanCopyUtil.simpleCopy(p, vo);
			return vo;
		}
		return null;
	}


	public PageResult<PrizepunishUserVO> orgUserFind(OrgUserQuery query) {
		Page<PrizepunishUserVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
		DataPermissionUtil.UnitQuery unitQuery = DataPermissionUtil.getUnitQueryDouble(query.getOrgUnitId());
		query.setAgencyId(unitQuery.getAgencyId());
		query.setAgencyIds(unitQuery.getAgencyIds());
		query.setUnitId(unitQuery.getUnitId());
		query.setUnitIds(unitQuery.getUnitIds());
		query.setAssessTeamId(unitQuery.getAssessTeamId());
		prizepunishMapper.orgUserFind(query);
		return new PageResult<>(page);
	}

	/* 科室查询某个人的奖惩记录 */
	public PageResult<PrizepunishVO> selectPrizeDept(PrizepunishQuery query) {
		Page<PrizepunishVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
		prizepunishMapper.selectPrizeDept(query);
		return new PageResult<>(page);
	}

	/* 机构查询某个人的奖惩记录 */
	public PageResult<PrizepunishVO> selectPrizeUnit(PrizepunishQuery query) {
		Page<PrizepunishVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());
		prizepunishMapper.selectPrizeUnit(query);
		return new PageResult<>(page);
	}

	/**
	 * 新增个人奖惩信息
	 * 
	 * @param prizepunishDTO
	 * @param operationState
	 * @param examineState
	 * @return
	 */
	public int savePrizepunish(PrizepunishDTO prizepunishDTO, int operationState, int examineState) {
		Prizepunish model = new Prizepunish();
		SimpleBeanCopyUtil.simpleCopy(prizepunishDTO, model);
		model.setOperationState(operationState);
		model.setOrgUserId(UserSession.getCurrentUserSession().getUserId());
		model.setExamineState(examineState);
		return save(model);
	}

	/**
	 * 新增某用户奖惩
	 * 
	 * @param prizepunishDTO
	 * @param userId
	 * @param operationState
	 * @param examineState
	 * @return
	 */
	public int savePrizepunishForUser(PrizepunishDTO prizepunishDTO, String userId, int operationState, int examineState) {
        Prizepunish model = new Prizepunish();
        SimpleBeanCopyUtil.simpleCopy(prizepunishDTO, model);
        UserSession userSession = UserSession.getCurrentUserSession();
        String userName = userSession == null ? "" : userSession.getUserName();
        if (operationState == Prizepunish.OPERATION_STATE_DEPARTMENT_SUBMIT
            || operationState == Prizepunish.OPERATION_STATE_AGENCY_SUBMIT){
            model.setExaminePeople(userName);
        }
        model.setOperationState(operationState);
        model.setOrgUserId(userId);
        model.setExamineState(examineState);
        return save(model);
	}

	/**
	 * 更新奖惩信息
	 * 
	 * @param prizepunishDTO
	 * @return
	 */
	public int updatePrizepunish(PrizepunishDTO prizepunishDTO) {
		Prizepunish model = get(prizepunishDTO.getId());
		SimpleBeanCopyUtil.simpleCopy(prizepunishDTO, model);
		return update(model);
	}

	public int updatePrizepunish(PrizepunishDTO prizepunishDTO, int operationState, int examineState) {
		Prizepunish model = get(prizepunishDTO.getId());
		UserSession userSession = UserSession.getCurrentUserSession();
        String userName = userSession == null ? "" : userSession.getUserName();
        if (operationState == Prizepunish.OPERATION_STATE_DEPARTMENT_SUBMIT
            || operationState == Prizepunish.OPERATION_STATE_AGENCY_SUBMIT){
            model.setExaminePeople(userName);
        }
		SimpleBeanCopyUtil.simpleCopy(prizepunishDTO, model);
		model.setOperationState(operationState);
		model.setExamineState(examineState);
		return update(model);
	}

	public int examineDepartment(String id, boolean pass) {
		UserSession userSession = UserSession.getCurrentUserSession();
		String userName = userSession == null ? "" : userSession.getUserName();
		Prizepunish model = new Prizepunish();
		model.setId(id);
		model.setExaminePeople(userName);
		model.setOperationState(Prizepunish.OPERATION_STATE_DEPARTMENT_SUBMIT);
		model.setExamineState(pass ? Prizepunish.EXAMINE_SUCCESS : Prizepunish.EXAMINE_FAILURE);
		return updateSelective(model);
	}

	public int examineUnit(String id, boolean pass) {
		UserSession userSession = UserSession.getCurrentUserSession();
		String userName = userSession == null ? "" : userSession.getUserName();
		Prizepunish model = new Prizepunish();
		model.setId(id);
		model.setExaminePeople(userName);
		model.setOperationState(Prizepunish.OPERATION_STATE_AGENCY_SUBMIT);
		model.setExamineState(pass ? Prizepunish.EXAMINE_SUCCESS : Prizepunish.EXAMINE_FAILURE);
		return updateSelective(model);
	}
	
	public int prizePunishReject(String id){
	    Prizepunish model = get(id);
	    if(model.getExamineState() != Prizepunish.EXAMINE_FAILURE){
	        int quantitativeCount = assessQuantitativeMapper.countAssessQuantitative(id);
	        if(quantitativeCount > 0){
	            throw new BusinessException("当前奖惩已进行评分不能驳回");
	        }
	    }
        model.setOperationState(Prizepunish.OPERATION_STATE_SELF_TEMPORARY);
        model.setExamineState(Prizepunish.EXAMINE_REJECT);
        model.setExaminePeople(null);
        return update(model);
    }
	
	public boolean hasRejectedPrizePunish(String userId) {
        return prizepunishMapper.countRejectedPrizePunishByUser(userId) > 0;
    }

}
