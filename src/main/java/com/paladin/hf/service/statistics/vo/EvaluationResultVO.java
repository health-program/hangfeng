package com.paladin.hf.service.statistics.vo;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;


/**
 * @author 黄伟华
 * @version 2018年3月7日 下午2:52:54
 */
public class EvaluationResultVO extends OffsetPage {
	
	private String id;
	private String unitId;
	private String agencyId;
	private String name;
	private String assessCycleId;
	private String unitAssGrade;
	private String operateState;
    private String unitAssGradeCOUNT;
    private String notUnitAssGradeCOUNT;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAssessCycleId()
    {
        return assessCycleId;
    }

    public void setAssessCycleId(String assessCycleId)
    {
        this.assessCycleId = assessCycleId;
    }

    public String getUnitAssGrade()
    {
        return unitAssGrade;
    }

    public void setUnitAssGrade(String unitAssGrade)
    {
        this.unitAssGrade = unitAssGrade;
    }

    public String getOperateState()
    {
        return operateState;
    }

    public void setOperateState(String operateState)
    {
        this.operateState = operateState;
    }
}
