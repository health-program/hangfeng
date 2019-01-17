package com.paladin.hf.mapper.assess.cycle;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.model.assess.cycle.PersonCycAssess;
import com.paladin.hf.service.assess.cycle.dto.AgencyQueryDTO;
import com.paladin.hf.service.assess.cycle.dto.DepartmentQueryDTO;
import com.paladin.hf.service.assess.cycle.dto.PersonalCycleAssessConfirmDTO;
import com.paladin.hf.service.assess.cycle.dto.PersonalQueryDTO;
import com.paladin.hf.service.assess.cycle.vo.AssessCycleDetailVO;
import com.paladin.hf.service.assess.cycle.vo.AssessCycleSimpleVO;

public interface PersonCycAssessMapper extends CustomMapper<PersonCycAssess> {

	public List<AssessCycleSimpleVO> findPersonal(PersonalQueryDTO query);
	
	public List<AssessCycleSimpleVO> findDepartment(@Param("unitParam") UnitQuery unitParam, @Param("query") DepartmentQueryDTO query);

	public List<AssessCycleSimpleVO> findAgency(@Param("unitParam") UnitQuery unitParam, @Param("query") AgencyQueryDTO query);
	
	public AssessCycleDetailVO getDetailByUserAndCycle(@Param("userId") String userId, @Param("cycleId") String cycleId);

	public int getCountByUserAndCycle(@Param("userId") String userId, @Param("cycleId") String cycleId);
	
	public int updateConfirmResult(PersonalCycleAssessConfirmDTO personalCycleAssessConfirm);

	public AssessCycleDetailVO getDetailById(@Param("id") String id);

	public int deletePersonalCycleAssess(@Param("id") String id);
	
	public int submitDepartment(@Param("id") String id);
	
	public int submitAgency(@Param("id") String id);
	
	public int rejectDepartment(@Param("id") String id, @Param("rejectReason") String rejectReason);
	
	public int rejectAgency(@Param("id") String id, @Param("rejectReason") String rejectReason);
}