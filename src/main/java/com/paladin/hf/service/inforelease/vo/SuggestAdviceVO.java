package com.paladin.hf.service.inforelease.vo;

import java.util.Date;

/**   
 * @author 黄伟华
 * @version 2019年5月22日 下午1:34:51 
 */
public class SuggestAdviceVO {

    private String id;
    
    private String title;
    
    private String content;
    
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
}
