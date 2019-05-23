package com.paladin.hf.service.inforelease.dto;

import java.util.Date;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.QueryCondition;
import com.paladin.framework.common.QueryType;

/**   
 * @author 黄伟华
 * @version 2019年5月22日 下午1:24:56 
 */
public class SuggestAdviceQuery extends OffsetPage{
    
    private String createUserId;
    
    private String title;
    
    private Date startTime;
    
    private Date endTime;
    
    @QueryCondition(type = QueryType.EQUAL)
    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
    
    @QueryCondition(type = QueryType.LIKE)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @QueryCondition(type = QueryType.GREAT_EQUAL, name = "createTime")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @QueryCondition(type = QueryType.GREAT_EQUAL, name = "createTime")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
}
