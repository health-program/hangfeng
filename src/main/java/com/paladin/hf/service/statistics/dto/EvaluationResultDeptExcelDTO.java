package com.paladin.hf.service.statistics.dto;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.excel.write.WriteProperty;
import com.paladin.hf.core.UnitContainer;

/**   
 * @author 黄伟华
 * @version 2019年1月9日 下午1:58:25 
 */
public class EvaluationResultDeptExcelDTO{
    
    private String id;
    private String unitId;
    private String agencyId;

    // 以下用于导出EXCEL
    @WriteProperty(cellIndex = 0, name = "所属机构", width = 30)    
    private String agencyName;
    @WriteProperty(cellIndex = 1, name = "被考评科室", width = 35)
    private String unitName;    
    @WriteProperty(cellIndex = 2, name = "考评周期", width = 40)
    private String assessCycleName;
    @WriteProperty(cellIndex = 3, name = "已考评人数", width = 15)
    private String unitAssGradeCOUNT;
    @WriteProperty(cellIndex = 4, name = "未考评人数", width = 15)
    private String notUnitAssGradeCOUNT;
    @WriteProperty(cellIndex = 5, name = "总人数", width = 20)
    private String total;
    
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
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getAssessCycleName() {
        return assessCycleName;
    }

    public void setAssessCycleName(String assessCycleName) {
        this.assessCycleName = assessCycleName;
    }

    public String getUnitAssGradeCOUNT()
    {
        return unitAssGradeCOUNT;
    }

    public void setUnitAssGradeCOUNT(String unitAssGradeCOUNT)
    {
        this.unitAssGradeCOUNT = unitAssGradeCOUNT;
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

    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

}
