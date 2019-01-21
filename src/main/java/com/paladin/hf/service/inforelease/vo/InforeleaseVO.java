package com.paladin.hf.service.inforelease.vo;

import java.util.List;

import com.paladin.common.model.syst.SysAttachment;
import com.paladin.framework.core.container.AttachmentContainer;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

/**   
 * @author 黄伟华
 * @version 2019年1月7日 上午10:29:02 
 */
public class InforeleaseVO{
    
    private String id;

    private String title;

    private String content;

    private String isRelease;

    private String importance;

    private String releaseTime;

    private String accessory;
    
    private Integer types;
    
    private String orgAgencyId;
    
    private String orgAssessTeamId;
    
    private String orgUnitId;

    private String remarks;
    
    private String attachments;
    
    // 获取附件文件
    public List<SysAttachment> getAttachmentFiles() {
        if (attachments != null && attachments.length() != 0) {
            return AttachmentContainer.getAttachments(attachments.split(","));
        }
        return null;
    }

    public String getOrgAgencyId()
    {
        return orgAgencyId;
    }

    public void setOrgAgencyId(String orgAgencyId)
    {
        this.orgAgencyId = orgAgencyId;
    }

    public String getOrgAssessTeamId()
    {
        return orgAssessTeamId;
    }

    public void setOrgAssessTeamId(String orgAssessTeamId)
    {
        this.orgAssessTeamId = orgAssessTeamId;
    }

    public String getOrgUnitId()
    {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnitId)
    {
        this.orgUnitId = orgUnitId;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getIsRelease()
    {
        return isRelease;
    }

    public void setIsRelease(String isRelease)
    {
        this.isRelease = isRelease;
    }

    public String getImportance()
    {
        return importance;
    }

    public void setImportance(String importance)
    {
        this.importance = importance;
    }

    public String getReleaseTime()
    {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime)
    {
        this.releaseTime = releaseTime;
    }

    public String getAccessory()
    {
        return accessory;
    }

    public void setAccessory(String accessory)
    {
        this.accessory = accessory;
    }

    public Integer getTypes()
    {
        return types;
    }

    public void setTypes(Integer types)
    {
        this.types = types;
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

    public String getUnitName() {
        try {
            return UnitContainer.getUnitName(orgUnitId);
        } catch (BusinessException e) {
            return "";
        }
    }
}
