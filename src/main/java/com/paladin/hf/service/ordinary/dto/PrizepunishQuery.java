package com.paladin.hf.service.ordinary.dto;

import com.paladin.framework.common.OffsetPage;

/**   
 * @author 黄伟华
 * @version 2019年1月10日 下午3:22:25 
 */
public class PrizepunishQuery extends OffsetPage{
    
    private String orgUserId;
    
    private String dictCode;
    
    private Integer operationState;
    
    private Integer examineState;
    
    private String startHappenTime;
    
    private String endhappenTime;

    public String getOrgUserId()
    {
        return orgUserId;
    }

    public void setOrgUserId(String orgUserId)
    {
        this.orgUserId = orgUserId;
    }

    public String getDictCode()
    {
        return dictCode;
    }

    public void setDictCode(String dictCode)
    {
        this.dictCode = dictCode;
    }

    public Integer getOperationState()
    {
        return operationState;
    }

    public void setOperationState(Integer operationState)
    {
        this.operationState = operationState;
    }

    public Integer getExamineState()
    {
        return examineState;
    }

    public void setExamineState(Integer examineState)
    {
        this.examineState = examineState;
    }

    public String getStartHappenTime()
    {
        return startHappenTime;
    }

    public void setStartHappenTime(String startHappenTime)
    {
        this.startHappenTime = startHappenTime;
    }

    public String getEndhappenTime()
    {
        return endhappenTime;
    }

    public void setEndhappenTime(String endhappenTime)
    {
        this.endhappenTime = endhappenTime;
    }
}
