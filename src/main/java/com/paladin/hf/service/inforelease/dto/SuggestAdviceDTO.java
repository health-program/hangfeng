package com.paladin.hf.service.inforelease.dto;

import javax.validation.constraints.NotEmpty;

/**   
 * @author 黄伟华
 * @version 2019年5月22日 下午3:15:13 
 */
public class SuggestAdviceDTO {

    private String id;
    
    @NotEmpty(message = "标题不能为空")
    private String title;
    
    @NotEmpty(message = "内容不能为空")
    private String content;

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
    
}
