package com.paladin.hf.service.ordinary.dto;
/**   
 * @author 黄伟华
 * @version 2019年1月10日 下午5:26:22 
 */
public class PrizepunishDTO{
    
    private String id;

    private String orgUserId;
    
    private Integer dictCode;

    private String happenTime;

    private String content;

    private String place;

    private String checks;

    private String checkPeople;

    private Integer operationState;

    private Integer examineState;

    private String examinePeople;

    private String remarks;
    
    private String attachments; 

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getOrgUserId()
    {
        return orgUserId;
    }

    public void setOrgUserId(String orgUserId)
    {
        this.orgUserId = orgUserId;
    }

    public Integer getDictCode()
    {
        return dictCode;
    }

    public void setDictCode(Integer dictCode)
    {
        this.dictCode = dictCode;
    }

    public String getHappenTime()
    {
        return happenTime;
    }

    public void setHappenTime(String happenTime)
    {
        this.happenTime = happenTime;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getPlace()
    {
        return place;
    }

    public void setPlace(String place)
    {
        this.place = place;
    }

    public String getChecks()
    {
        return checks;
    }

    public void setChecks(String checks)
    {
        this.checks = checks;
    }

    public String getCheckPeople()
    {
        return checkPeople;
    }

    public void setCheckPeople(String checkPeople)
    {
        this.checkPeople = checkPeople;
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

    public String getExaminePeople()
    {
        return examinePeople;
    }

    public void setExaminePeople(String examinePeople)
    {
        this.examinePeople = examinePeople;
    }

    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getAttachments()
    {
        return attachments;
    }

    public void setAttachments(String attachments)
    {
        this.attachments = attachments;
    }
    
}
