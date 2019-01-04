package com.paladin.hf.service.assess.quantificate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.hf.mapper.assess.quantificate.AssessItemExtraMapper;
import com.paladin.hf.model.assess.quantificate.AssessItemExtra;


@Service
public class AssessItemExtraService extends ServiceSupport<AssessItemExtra> {

	@Autowired
	AssessItemExtraMapper assessItemExtraMapper;

	public List<AssessItemExtra> findTemplateAssessExtraItem(String templateId) {
		return searchAll(new Condition(AssessItemExtra.COLUMN_TEMPLATE_ID, QueryType.EQUAL, templateId));
	}

	public int removeTemplateItemExtra(String templateId) {
		return assessItemExtraMapper.removeTemplateItemExtra(templateId);
	}

	public List<AssessItemExtra> findTemplateAssessExtraItem(String templateId, String extraType) {
		return searchAll(
				new Condition[] { 
						new Condition(AssessItemExtra.COLUMN_TEMPLATE_ID, QueryType.EQUAL, templateId),
						new Condition(AssessItemExtra.COLUMN_EXTRA_TYPE, QueryType.EQUAL, extraType) 
				}
		);
	}

}
