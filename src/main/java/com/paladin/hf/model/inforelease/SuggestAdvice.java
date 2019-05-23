package com.paladin.hf.model.inforelease;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.paladin.framework.common.BaseModel;

/**   
 * @author 黄伟华
 * @version 2019年5月22日 下午1:06:53 
 */
public class SuggestAdvice extends BaseModel{

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
    
    private String title;
    
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
