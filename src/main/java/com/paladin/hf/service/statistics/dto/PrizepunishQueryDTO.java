package com.paladin.hf.service.statistics.dto;

import java.util.List;

import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;


/**   
 * @author 黄伟华
 * @version 2018年4月3日 下午1:14:33 
 */
public class PrizepunishQueryDTO 
{
    private String unitId;
    private String dictCode;
    private String agencyId;
    private List<String> agencyIds;
    private List<String> unitIds;
    
    
    @QueryCondition(type = QueryType.IN, name = "orgUnitId")
    public List<String> getUnitIds()
    {
        return unitIds;
    }
    public void setUnitIds(List<String> unitIds)
    {
        this.unitIds = unitIds;
    }
    @QueryCondition(type = QueryType.IN, name = "orgAgencyId")
    public List<String> getAgencyIds()
    {
        return agencyIds;
    }
    public void setAgencyIds(List<String> agencyIds)
    {
        this.agencyIds = agencyIds;
    }
    @QueryCondition(type = QueryType.EQUAL)
    public String getAgencyId()
    {
        return agencyId;
    }
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    @QueryCondition(type = QueryType.EQUAL)
    public String getUnitId()
    {
        return unitId;
    }
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }
    @QueryCondition(type = QueryType.EQUAL)
    public String getDictCode()
    {
        return dictCode;
    }
    public void setDictCode(String dictCode)
    {
        this.dictCode = dictCode;
    }
   
    
}
