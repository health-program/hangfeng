package com.paladin.hf.mapper.assess.quantificate;

import java.util.List;

import com.paladin.hf.model.assess.quantificate.Template;


public interface TemplateMapper extends CustomerMapper<Template> {

    List<Template> getTemplateList(Template template);

    void updateTemplateStateById(Template template);

    Template selectByPriKey(String id);
    
    int selectAssessCycleByTemplateId(String id);
    
    
    /**
     * <模板启用时判断是否等级配置>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    int levelCount(String id);
    
    /**
     * <模板启用时判断是否项目配置>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    int itemCount(String id);


}