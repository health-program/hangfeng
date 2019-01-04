package com.paladin.hf.service.statistics.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.core.container.ConstantsContainer;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.excel.write.WriteProperty;


/**
 * @author 黄伟华
 * @version 2018年3月7日 下午2:52:54
 */
public class EvaluationResultVO extends OffsetPage {
	private String Id;
	//@WriteProperty(cellIndex = 2, name = "姓名", width = 20)
	private String name;
	private String selfAssGrade;
	private Date selfAssTime;
	private String departAssGrade;
	private Date departAssTime;
	private String unitAssGrade;
	private Date unitAssTime;
	private String confirmedResult;
	private Date confirmedTime;
	private String operateState;
	private String unitId;
	private String agencyId;
	private String assessCycleId;
	private String unitAssGradeCOUNT;
	private String notUnitAssGradeCOUNT;
	private String total;

	// 以下用于导出EXCEL
	@WriteProperty(cellIndex = 0, name = "所属机构", width = 30)	
	private String agencyName;
	@WriteProperty(cellIndex = 1, name = "被考评科室", width = 35)
	private String unitName;	
	@WriteProperty(cellIndex = 2, name = "考评周期", width = 40)
	private String assessCycleName;
	@WriteProperty(cellIndex = 3, name = "已考评人数", width = 15)
	private String unitAssGradeExcelCOUNT;
	@WriteProperty(cellIndex = 4, name = "未考评人数", width = 15)
    private String notUnitAssGradeExcelCOUNT;
	@WriteProperty(cellIndex = 5, name = "总人数", width = 20)
    private String totalExcel;
	/*@WriteProperty(cellIndex = 4, name = "自评等级", width = 10)
	private String selfAssGradeName;
	@WriteProperty(cellIndex = 5, name = "科室考评等级", width = 15)
	private String departAssGradeName;
	@WriteProperty(cellIndex = 6, name = "机构考评等级", width = 15)
	private String unitAssGradeName;
	@WriteProperty(cellIndex = 7, name = "被考评人确认结果", width = 20)
	private String confirmedResultName;
	@WriteProperty(cellIndex = 8, name = "考评状态", width = 20)
	private String operateStateName;*/
	

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
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

	public String getUnitAssGrade() {
		return unitAssGrade;
	}

	public void setUnitAssGrade(String unitAssGrade) {
		this.unitAssGrade = unitAssGrade;
	}

	public String getOperateState() {
		return operateState;
	}

	public void setOperateState(String operateState) {
		this.operateState = operateState;
	}

	public String getUnitName() {
		try {
			return UnitConatiner.getUnitName(unitId);
		}catch(BusinessException e) {
			return "";			
		}
	}

	public String getAgencyName() {
		try {
			return UnitConatiner.getUnitName(agencyId);
		}catch(BusinessException e) {
			return "";			
		}
	}

	public String getSelfAssGrade() {
		return selfAssGrade;
	}

	public void setSelfAssGrade(String selfAssGrade) {
		this.selfAssGrade = selfAssGrade;
	}

	public Date getSelfAssTime() {
		return selfAssTime;
	}

	public void setSelfAssTime(Date selfAssTime) {
		this.selfAssTime = selfAssTime;
	}

	public String getDepartAssGrade() {
		return departAssGrade;
	}

	public void setDepartAssGrade(String departAssGrade) {
		this.departAssGrade = departAssGrade;
	}

	public Date getDepartAssTime() {
		return departAssTime;
	}

	public void setDepartAssTime(Date departAssTime) {
		this.departAssTime = departAssTime;
	}

	public Date getUnitAssTime() {
		return unitAssTime;
	}

	public void setUnitAssTime(Date unitAssTime) {
		this.unitAssTime = unitAssTime;
	}

	public String getConfirmedResult() {
		return confirmedResult;
	}

	public void setConfirmedResult(String confirmedResult) {
		this.confirmedResult = confirmedResult;
	}

	public Date getConfirmedTime() {
		return confirmedTime;
	}

	public void setConfirmedTime(Date confirmedTime) {
		this.confirmedTime = confirmedTime;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAssessCycleId() {
		return assessCycleId;
	}

	public void setAssessCycleId(String assessCycleId) {
		this.assessCycleId = assessCycleId;
	}

	public String getUnitAssGradeCOUNT()
    {
        return unitAssGradeCOUNT;
    }


    public String getNotUnitAssGradeCOUNT()
    {
        return notUnitAssGradeCOUNT;
    }

    public void setNotUnitAssGradeCOUNT(String notUnitAssGradeCOUNT)
    {
        this.notUnitAssGradeCOUNT = notUnitAssGradeCOUNT;
    }

    public String getTotal()
    {
        return total;
    }

    public void setTotal(String total)
    {
        this.total = total;
    }
    
    
    @JsonIgnore
    public String getUnitAssGradeExcelCOUNT()
    {
        return unitAssGradeCOUNT;
    }

    @JsonIgnore
    public String getNotUnitAssGradeExcelCOUNT()
    {
        return notUnitAssGradeCOUNT;
    }

    @JsonIgnore
    public String getTotalExcel()
    {
        return total;
    }


    @JsonIgnore
	public String getSelfAssGradeName() {
		return selfAssGrade == null || selfAssGrade.length() == 0 ? "" : ConstantsContainer.getValue("ass-grades", selfAssGrade);
	}

	@JsonIgnore
	public String getDepartAssGradeName() {
		return departAssGrade == null || departAssGrade.length() == 0 ? "" : ConstantsContainer.getValue("ass-grades", departAssGrade);
	}

	@JsonIgnore
	public String getUnitAssGradeName() {
		return unitAssGrade == null || unitAssGrade.length() == 0 ? "" : ConstantsContainer.getValue("ass-grades", unitAssGrade);

	}

	@JsonIgnore
	public String getConfirmedResultName() {
		return confirmedResult == null || confirmedResult.length() == 0 ? "未确认" : (confirmedResult.equals("1") ? "同意" : "不同意");
	}
	
	@JsonIgnore
	public String getOperateStateName() {
		return operateState == null || operateState.length() == 0 ? "未考评" : ConstantsContainer.getValue("operateState", operateState);
	}
	
	@JsonIgnore
	public String getAssessCycleName() {
		return assessCycleName;
	}

	public void setAssessCycleName(String assessCycleName) {
		this.assessCycleName = assessCycleName;
	}

}
