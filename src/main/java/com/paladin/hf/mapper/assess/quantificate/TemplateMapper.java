package com.paladin.hf.mapper.assess.quantificate;

import java.util.List;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.assess.quantificate.Template;
import com.paladin.hf.service.assess.quantificate.dto.TemplateDTO;

public interface TemplateMapper extends CustomMapper<Template> {

	public List<Template> getTemplateList(Template template);

	public int updateTemplateStateById(Template template);

	public Template selectByPriKey(String id);

	public int countAssessCycleByTemplate(String id);

	public int levelCount(String id);

	public int itemCount(String id);

	public TemplateDTO getOneByPrimaryKey(String id);

}