package com.paladin.hf.service.assess.cycle;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.mapper.assess.cycle.PersonCycAssessMapper;
import com.paladin.hf.model.assess.cycle.PersonCycAssess;
import com.paladin.hf.service.assess.cycle.dto.*;
import com.paladin.hf.service.assess.cycle.vo.CycleAssessDetailVO;
import com.paladin.hf.service.assess.cycle.vo.CycleAssessSimpleVO;
import com.paladin.hf.service.assess.cycle.vo.UnassessedUserVO;
import com.paladin.hf.service.assess.quantificate.AssessQuantitativeResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PersonCycAssessService extends ServiceSupport<PersonCycAssess> {

	@Autowired
	private PersonCycAssessMapper perCycAssMapper;

	@Autowired
	private AssessQuantitativeResultService assessQuantitativeResultService;

	/**
	 * 获取人员某一周期考核详情
	 * 
	 * @param userId
	 * @param cycleId
	 * @return
	 */
	public CycleAssessDetailVO getDetailByUserAndCycle(String userId, String cycleId) {
		return perCycAssMapper.getDetailByUserAndCycle(userId, cycleId);
	}

	/**
	 * 获取某一周期考核详情
	 * 
	 * @param id
	 * @return
	 */
	public CycleAssessDetailVO getDetail(String id) {
		return perCycAssMapper.getDetailById(id);
	}

	/**
	 * 判断人员某一周期是否考核
	 * 
	 * @param userId
	 * @param cycleId
	 * @return
	 */
	public boolean hasAssessedByUserAndCycle(String userId, String cycleId) {
		return perCycAssMapper.getCountByUserAndCycle(userId, cycleId) > 0;
	}

	/**
	 * 分页查找个人周期考核
	 * 
	 * @param query
	 * @return
	 */
	public PageResult<CycleAssessSimpleVO> findPersonalPage(PersonalQueryDTO query) {
		Page<CycleAssessSimpleVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
		query.setUserId(HfUserSession.getCurrentUserSession().getUserId());
		perCycAssMapper.findPersonal(query);// 条件查询
		return new PageResult<>(page);
	}

	/**
	 * 分页查找部门人员周期考核
	 * 
	 * @param query
	 * @return
	 */
	public PageResult<CycleAssessSimpleVO> findDepartmentPage(DepartmentQueryDTO query) {
		Page<CycleAssessSimpleVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
		UnitQuery unitQuery = DataPermissionUtil.getUnitQueryDouble(query.getUnitId());
		perCycAssMapper.findDepartment(unitQuery, query);// 条件查询
		return new PageResult<>(page);
	}

	/**
	 * 分页查找机构人员周期考核
	 * 
	 * @param query
	 * @return
	 */
	public PageResult<CycleAssessSimpleVO> findAgencyPage(AgencyQueryDTO query) {
		Page<CycleAssessSimpleVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
		UnitQuery unitQuery = DataPermissionUtil.getUnitQueryDouble(query.getUnitId());
		perCycAssMapper.findAgency(unitQuery, query);// 条件查询
		return new PageResult<>(page);
	}

	/**
	 * 分页查找用户的周期考核情况
	 * 
	 * @param query
	 * @return
	 */
	public PageResult<CycleAssessSimpleVO> findUserCycleAssess(PersonalQueryDTO query) {
		Page<CycleAssessSimpleVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
		perCycAssMapper.findByUser(query.getUserId());
		return new PageResult<>(page);
	}

	/**
	 * 保存个人周期考核
	 * 
	 * @param assessDTO
	 * @param statusAssessedTemporary
	 * @return
	 */
	public boolean savePersonalCycleAssess(PersonalCycleAssessSaveDTO assessDTO, int statusAssessedTemporary) {
		PersonCycAssess cycleAssess = new PersonCycAssess();
		SimpleBeanCopyUtil.simpleCopy(assessDTO, cycleAssess);

		HfUserSession session = HfUserSession.getCurrentUserSession();
		if (!session.isOrgUser()) {
			throw new BusinessException("非考评人员不能提交周期考核");
		}

		String cycleId = cycleAssess.getAssessCycleId();
		String userId = session.getUserId();

		if (hasAssessedByUserAndCycle(userId, cycleId)) {
			throw new BusinessException("该周期您已经考核过了，不能重复考核");
		} else {
			Object result = assessQuantitativeResultService.getResult(userId, cycleId);
			if (result == null) {
				throw new BusinessException("请联系考评人先做量化考评！");
			}
		}

		Unit unit = session.getUserUnit();
		Unit team = unit.getAssessTeam();
		Unit agency = unit.getAgency();

		cycleAssess.setUnitId(unit.getId());
		cycleAssess.setAssessTeamId(team == null ? null : team.getId());
		cycleAssess.setAgencyId(agency.getId());

		cycleAssess.setSelfAssTime(new Date());

		if (statusAssessedTemporary != PersonCycAssess.STATUS_ASSESSED_TEMPORARY && statusAssessedTemporary != PersonCycAssess.STATUS_ASSESSED_SUBMIT) {
			throw new BusinessException("考核状态错误");
		}
		cycleAssess.setOperateState(statusAssessedTemporary);
		cycleAssess.setOrgUserId(userId);
		save(cycleAssess);
		return true;
	}

	/**
	 * 更新个人周期考核
	 * 
	 * @param assessDTO
	 * @param statusAssessedTemporary
	 * @return
	 */
	public boolean updatePersonalCycleAssess(PersonalCycleAssessUpdateDTO assessDTO, int statusAssessedTemporary) {
		String id = assessDTO.getId();
		if (id == null || id.length() == 0) {
			throw new BusinessException("找不到对应修改的周期考核");
		}

		PersonCycAssess cycleAssess = get(id);

		if (cycleAssess == null) {
			throw new BusinessException("找不到对应修改的周期考核");
		}

		int status = cycleAssess.getOperateState();
		if (statusAssessedTemporary == PersonCycAssess.STATUS_ASSESSED_TEMPORARY || statusAssessedTemporary == PersonCycAssess.STATUS_ASSESSED_SUBMIT) {
			if (status != PersonCycAssess.STATUS_ASSESSED_TEMPORARY && status != PersonCycAssess.STATUS_BACKWORD) {
				throw new BusinessException("该周期考核不能被个人自评");
			}
		} else {
			throw new BusinessException("操作状态不正确");
		}

		SimpleBeanCopyUtil.simpleCopy(assessDTO, cycleAssess);

		HfUserSession session = HfUserSession.getCurrentUserSession();
		if (!session.isOrgUser()) {
			throw new BusinessException("非考评人员不能提交周期考核");
		}

		cycleAssess.setSelfAssTime(new Date());
		cycleAssess.setOperateState(statusAssessedTemporary);
		update(cycleAssess);
		return true;
	}

	/**
	 * 更新科室考评部分
	 * 
	 * @param assessDTO
	 * @param statusAssessedTemporary
	 * @return
	 */
	public boolean updateDepartmentCycleAssess(DepartmentCycleAssessUpdateDTO assessDTO, int statusAssessedTemporary) {
		String id = assessDTO.getId();
		if (id == null || id.length() == 0) {
			throw new BusinessException("找不到对应修改的周期考核");
		}

		PersonCycAssess cycleAssess = get(id);

		if (cycleAssess == null) {
			throw new BusinessException("找不到对应修改的周期考核");
		}

		int status = cycleAssess.getOperateState();
		if (statusAssessedTemporary == PersonCycAssess.STATUS_DEPART_SUBMIT || statusAssessedTemporary == PersonCycAssess.STATUS_DEPART_TEMPORARY) {
			if (status != PersonCycAssess.STATUS_ASSESSED_SUBMIT && status != PersonCycAssess.STATUS_DEPART_TEMPORARY) {
				throw new BusinessException("该周期考核不能被科室考评");
			}
		} else {
			throw new BusinessException("操作状态不正确");
		}

		SimpleBeanCopyUtil.simpleCopy(assessDTO, cycleAssess);

//      管理用户也可以科室考评
//		HfUserSession session = HfUserSession.getCurrentUserSession();
//		if (!session.isOrgUser()) {
//			throw new BusinessException("非考评人员不能提交周期考核");
//		}

		cycleAssess.setDepartAssTime(new Date());
		cycleAssess.setOperateState(statusAssessedTemporary);
		update(cycleAssess);
		return true;
	}

	/**
	 * 更新考评小组考评部分
	 * 
	 * @param assessDTO
	 * @param statusAssessedTemporary
	 * @return
	 */
	public boolean updateAgencyCycleAssess(AgencyCycleAssessUpdateDTO assessDTO, int statusAssessedTemporary) {
		String id = assessDTO.getId();
		if (id == null || id.length() == 0) {
			throw new BusinessException("找不到对应修改的周期考核");
		}

		PersonCycAssess cycleAssess = get(id);

		if (cycleAssess == null) {
			throw new BusinessException("找不到对应修改的周期考核");
		}

		int status = cycleAssess.getOperateState();
		if (statusAssessedTemporary == PersonCycAssess.STATUS_UNIT_GROUP_SUBMIT || statusAssessedTemporary == PersonCycAssess.STATUS_UNIT_GROUP_TEMPORARY) {
			if (status != PersonCycAssess.STATUS_DEPART_SUBMIT && status != PersonCycAssess.STATUS_UNIT_GROUP_TEMPORARY) {
				throw new BusinessException("该周期考核不能被考评小组考评");
			}
		} else {
			throw new BusinessException("操作状态不正确");
		}

		// 检查量化考评是否完成
		if (statusAssessedTemporary == PersonCycAssess.STATUS_UNIT_GROUP_SUBMIT) {
			if (assessQuantitativeResultService.getResult(cycleAssess.getOrgUserId(), cycleAssess.getAssessCycleId()) == null) {
				throw new BusinessException("请先完成该人员该周期的量化考评");
			}
		}

		SimpleBeanCopyUtil.simpleCopy(assessDTO, cycleAssess);

//      管理用户也可以机构考评
//		HfUserSession session = HfUserSession.getCurrentUserSession();
//		if (!session.isOrgUser()) {
//			throw new BusinessException("非考评人员不能提交周期考核");
//		}

		cycleAssess.setUnitAssTime(new Date());
		cycleAssess.setOperateState(statusAssessedTemporary);
		update(cycleAssess);
		return true;
	}

	/**
	 * 科室驳回周期考评
	 * 
	 * @param id
	 * @param rejectReason
	 * @return
	 */
	public boolean rejectDepartmentCycleAssess(String id, String rejectReason) {
		return perCycAssMapper.rejectDepartment(id, rejectReason) > 0;
	}

	/**
	 * 考评小组提交暂存周期考核
	 * 
	 * @param id
	 * @return
	 */
	public boolean submitAgencyCycleAssess(String id) {
		return perCycAssMapper.submitAgency(id) > 0;
	}

	/**
	 * 考评小组驳回周期考评
	 * 
	 * @param id
	 * @param rejectReason
	 * @return
	 */
	public boolean rejectAgencyCycleAssess(String id, String rejectReason) {
		return perCycAssMapper.rejectAgency(id, rejectReason) > 0;
	}

	/**
	 * 部门提交暂存周期考核
	 * 
	 * @param id
	 * @return
	 */
	public boolean submitDepartmentCycleAssess(String id) {
		return perCycAssMapper.submitDepartment(id) > 0;
	}

	/**
	 * 确认个人周期考核
	 * 
	 * @param confirmDTO
	 * @return
	 */
	public boolean confirmPersonalCycleAssess(PersonalCycleAssessConfirmDTO confirmDTO) {
		return perCycAssMapper.updateConfirmResult(confirmDTO) > 0;
	}

	/**
	 * 删除个人周期考核，只能删除暂存的
	 * 
	 * @param id
	 * @return
	 */
	public boolean removePersonalCycleAssess(String id) {
		return perCycAssMapper.deletePersonalCycleAssess(id) > 0;
	}

	/**
	 * 查看科室未考评人员
	 * @param query
	 * @return
	 */
	public PageResult<UnassessedUserVO> findUnassessedForDepartment(UnassessedQuery query) {
		Page<UnassessedUserVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
		UnitQuery unitQuery = DataPermissionUtil.getUnitQueryDouble(query.getUnitId());
		if (unitQuery == null) {
			return getEmptyPageResult(page);
		}
		perCycAssMapper.findUnassessedForDepartment(unitQuery, query);
		return new PageResult<>(page);
	}

	/**
	 * 查看机构未考评人员
	 * @param query
	 * @return
	 */
	public PageResult<UnassessedUserVO> findUnassessedForAgency(UnassessedQuery query) {
		Page<UnassessedUserVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
		UnitQuery unitQuery = DataPermissionUtil.getUnitQueryDouble(query.getUnitId());
		if (unitQuery == null) {
			return getEmptyPageResult(page);
		}
		perCycAssMapper.findUnassessedForAgency(unitQuery, query);
		return new PageResult<>(page);
	}

	
	public boolean hasRejectedAssess(String userId) {
		return perCycAssMapper.countRejectedAssessByUser(userId) > 0;
	}

	public int agencyBatchSaveResult(List<AgencyCycleAssessBatchDTO> batchSaveDtos) {
		int effect = 0;
		for(AgencyCycleAssessBatchDTO dto : batchSaveDtos) {
			effect += perCycAssMapper.updateAgencyOpinion(dto);
		}	
		return effect;
	}
	
	public int departmentBatchSaveResult(List<DepartmentCycleAssessBatchDTO> batchSaveDtos) {
		int effect = 0;
		for(DepartmentCycleAssessBatchDTO dto : batchSaveDtos) {
			effect += perCycAssMapper.updateDepartmentOpinion(dto);
		}	
		return effect;
	}
}
