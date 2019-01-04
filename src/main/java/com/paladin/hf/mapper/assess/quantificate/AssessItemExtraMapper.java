package com.paladin.hf.mapper.assess.quantificate;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.assess.quantificate.AssessItemExtra;

public interface AssessItemExtraMapper extends CustomMapper<AssessItemExtra>{

	int removeTemplateItemExtra(String templateId);

}
