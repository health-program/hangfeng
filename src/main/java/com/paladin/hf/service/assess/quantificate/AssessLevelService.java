package com.paladin.hf.service.assess.quantificate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.mapper.assess.quantificate.AssessLevelMapper;
import com.paladin.hf.model.assess.quantificate.AssessLevel;


@Service
public class AssessLevelService extends ServiceSupport<AssessLevel> {

	@Autowired
	AssessLevelMapper assessLevelMapper;

	public List<AssessLevel> findTemplateAssessLevel(String templateId) {
		return searchAll(new Condition(AssessLevel.COLUMN_TEMPLATE_ID, QueryType.EQUAL, templateId, null));
	}

	@Transactional
	public int saveTemplateAssessLevel(AssessLevel[] assessLevels) {

		if (assessLevels == null || assessLevels.length == 0)
			return 0;

		int effect = 0;
		String templateId = null;
		for (AssessLevel assessLevel : assessLevels) {
			String tid = assessLevel.getTemplateId();
			if (StringUtils.isEmpty(tid)) {
				throw new BusinessException("等级配置必须针对一个模板");
			} else {
				if (templateId == null) {
					templateId = tid;
				} else if (!templateId.equals(tid)) {
					throw new BusinessException("等级配置必须针对一个模板");
				}
			}
		}

		assessLevelMapper.removeAssessLevelOfTemplate(templateId);
		for (AssessLevel assessLevel : assessLevels) {
			assessLevel.setId(null);
			effect += save(assessLevel);
		}

		return effect;
	}

      public String searchTemplateIdByCycleId(String cycleId) {
            return assessLevelMapper.searchTemplateIdByCycleId(cycleId);
      }

}
