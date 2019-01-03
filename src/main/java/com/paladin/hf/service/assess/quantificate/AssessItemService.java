package com.paladin.hf.service.assess.quantificate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.hf.mapper.assess.quantificate.AssessItemMapper;
import com.paladin.hf.model.assess.quantificate.AssessItem;



@Service
public class AssessItemService extends ServiceSupport<AssessItem>{

	@Autowired
	AssessItemMapper assessItemMapper;
	
	public List<AssessItem> findTemplateAssessItem(String templateId) {
		return searchAll(new Condition(AssessItem.COLUMN_TEMPLATE_ID, QueryType.EQUAL, templateId, null));
	}

	public int removeTemplateItem(String templateId) {
		return assessItemMapper.removeTemplateItem(templateId);
	}

}
