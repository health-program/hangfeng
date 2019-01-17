package com.paladin.hf.service.statistics.dto;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.excel.write.WriteProperty;
import com.paladin.hf.core.UnitContainer;

/**   
 * @author 黄伟华
 * @version 2018年10月8日 下午2:56:54 
 */
public class EvaluationResultPeopleExcelDTO
{
    private String Id;
    private String unitId;
	private String agencyId;
    
    // 以下用于导出EXCEL
    @WriteProperty(cellIndex = 0, name = "所属机构", width = 30)    
    private String agencyName;
    @WriteProperty(cellIndex = 2, name = "姓名", width = 15)
    private String name;
    @WriteProperty(cellIndex = 1, name = "被考评科室", width = 30)
    private String unitName;    
    @WriteProperty(cellIndex = 3, name = "考评周期", width = 30)
    private String assessCycleName;
    @WriteProperty(cellIndex = 4, name = "自评等级", width = 10, enumType = "ass-grades")
    private  Integer selfAssGrade;
    @WriteProperty(cellIndex = 5, name = "科室考评等级", width = 15, enumType = "ass-grades")
    private Integer departAssGrade;
    @WriteProperty(cellIndex = 6, name = "机构考评等级", width = 15, enumType = "ass-grades")
    private Integer unitAssGrade;
    // TODO TEST
    @WriteProperty(cellIndex = 7, name = "被考评人确认结果", width = 20, enumType = "boolean")
    private Integer confirmedResult;
    @WriteProperty(cellIndex = 8, name = "考评状态", width = 20, enumType = "operateState")
    private String operateStateName;
    
    public String getUnitName() {
		try {
			return UnitContainer.getUnitName(unitId);
		}catch(BusinessException e) {
			return "";			
		}
	}

	public String getAgencyName() {
		try {
			return UnitContainer.getUnitName(agencyId);
		}catch(BusinessException e) {
			return "";			
		}
	}
    
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssessCycleName() {
		return assessCycleName;
	}
	public void setAssessCycleName(String assessCycleName) {
		this.assessCycleName = assessCycleName;
	}
	public Integer getSelfAssGrade() {
		return selfAssGrade;
	}
	public void setSelfAssGrade(Integer selfAssGrade) {
		this.selfAssGrade = selfAssGrade;
	}
	public Integer getDepartAssGrade() {
		return departAssGrade;
	}
	public void setDepartAssGrade(Integer departAssGrade) {
		this.departAssGrade = departAssGrade;
	}
	public Integer getUnitAssGrade() {
		return unitAssGrade;
	}
	public void setUnitAssGrade(Integer unitAssGrade) {
		this.unitAssGrade = unitAssGrade;
	}
	public Integer getConfirmedResult() {
		return confirmedResult;
	}
	public void setConfirmedResult(Integer confirmedResult) {
		this.confirmedResult = confirmedResult;
	}
	public String getOperateStateName() {
		return operateStateName;
	}
	public void setOperateStateName(String operateStateName) {
		this.operateStateName = operateStateName;
	}

}
