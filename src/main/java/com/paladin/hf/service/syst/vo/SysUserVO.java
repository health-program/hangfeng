package com.paladin.hf.service.syst.vo;

import java.util.Date;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

/**   
 * @author 黄伟华
 * @version 2018年10月9日 上午10:43:19 
 */
public class SysUserVO
{
    private String orgAgencyId;
    private String orgUnitId;
    private String name;
    private Date lastLoginTime;
    
    
    public String getOrgAgencyId()
    {
        return orgAgencyId;
    }
    public void setOrgAgencyId(String orgAgencyId)
    {
        this.orgAgencyId = orgAgencyId;
    }
    
    public String getOrgUnitId()
    {
        return orgUnitId;
    }
    public void setOrgUnitId(String orgUnitId)
    {
        this.orgUnitId = orgUnitId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getUnitRootName() {
        try {
            return UnitContainer.getRootUnitName(orgAgencyId);
        }catch(BusinessException e) {
            return "";          
        }
    }
    
    public String getUnitName() {
        try {
            return UnitContainer.getUnitName(orgUnitId);
        }catch(BusinessException e) {
            return "";
        }
    }
    
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
}
