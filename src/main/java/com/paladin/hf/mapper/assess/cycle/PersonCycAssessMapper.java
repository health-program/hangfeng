package com.paladin.hf.mapper.assess.cycle;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.model.assess.cycle.PersonCycAssess;
import com.paladin.hf.service.assess.cycle.dto.*;
import com.paladin.hf.service.assess.cycle.vo.CycleAssessDetailVO;
import com.paladin.hf.service.assess.cycle.vo.CycleAssessSimpleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PersonCycAssessMapper extends CustomMapper<PersonCycAssess> {

	public List<CycleAssessSimpleVO> findPersonal(PersonalQueryDTO query);
	
	public List<CycleAssessSimpleVO> findDepartment(@Param("unitParam") UnitQuery unitParam, @Param("query") DepartmentQueryDTO query);

	public List<CycleAssessSimpleVO> findAgency(@Param("unitParam") UnitQuery unitParam, @Param("query") AgencyQueryDTO query);
	
	public List<CycleAssessSimpleVO> findByUser(@Param("userId") String userId);
	
	public CycleAssessDetailVO getDetailByUserAndCycle(@Param("userId") String userId, @Param("cycleId") String cycleId);

	public int getCountByUserAndCycle(@Param("userId") String userId, @Param("cycleId") String cycleId);
	
	public int updateConfirmResult(PersonalCycleAssessConfirmDTO personalCycleAssessConfirm);

	public CycleAssessDetailVO getDetailById(@Param("id") String id);

	public int deletePersonalCycleAssess(@Param("id") String id);
	
	public int submitDepartment(@Param("id") String id);
	
	public int submitAgency(@Param("id") String id);
	
	public int rejectDepartment(@Param("id") String id, @Param("rejectReason") String rejectReason);
	
	public int rejectAgency(@Param("id") String id, @Param("rejectReason") String rejectReason);

	//未考评按钮
	List<PersonnelCycleAssessDTO> noAssessment(Map<String, Object> map);
	
}