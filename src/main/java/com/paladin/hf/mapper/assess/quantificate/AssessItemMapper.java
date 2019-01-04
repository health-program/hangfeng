package com.paladin.hf.mapper.assess.quantificate;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.assess.quantificate.AssessItem;

public interface AssessItemMapper extends CustomMapper<AssessItem>{

	int removeTemplateItem(String templateId);

}
