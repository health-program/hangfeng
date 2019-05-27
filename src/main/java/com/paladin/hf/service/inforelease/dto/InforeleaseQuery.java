package com.paladin.hf.service.inforelease.dto;

import com.paladin.framework.common.OffsetPage;

/**   
 * @author 黄伟华
 * @version 2019年1月7日 上午10:10:17 
 */
public class InforeleaseQuery extends OffsetPage{
    
    private String isRelease;
    
    private String importance;
    
    private String title;
    
    private String releaseTime;
    
    private String endreleaseTime;
    
    private String type;//0:通知公告 1:政策文件 2:宣传教育 3:廉政提醒

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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getReleaseTime()
    {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime)
    {
        this.releaseTime = releaseTime;
    }

    public String getEndreleaseTime()
    {
        return endreleaseTime;
    }

    public void setEndreleaseTime(String endreleaseTime)
    {
        this.endreleaseTime = endreleaseTime;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

}
