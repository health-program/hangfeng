package com.paladin.hf.mapper.assess.quantificate;

import java.util.List;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.assess.quantificate.Template;
import com.paladin.hf.service.assess.quantificate.dto.TemplateDTO;


public interface TemplateMapper extends CustomMapper<Template> {

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

    TemplateDTO getOneByPrimaryKey(String id);


}