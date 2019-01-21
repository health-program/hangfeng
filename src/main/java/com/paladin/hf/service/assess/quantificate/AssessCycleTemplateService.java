package com.paladin.hf.service.assess.quantificate;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.hf.mapper.assess.quantificate.AssessCycleTemplateMapper;
import com.paladin.hf.model.assess.quantificate.AssessCycleTemplate;

/**
 * 考评周期模板关联业务
 * 
 * @author TontoZhou
 * @since 2018年2月8日
 */
@Service
public class AssessCycleTemplateService extends ServiceSupport<AssessCycleTemplate> {

	@Autowired
	private AssessCycleTemplateMapper assessCycleTemplateMapper;

	public List<AssessCycleTemplate> findRelationByCycle(String cycleId) {
		return assessCycleTemplateMapper.findRelationByCycle(cycleId);
	}

	public int removeByCycle(String cycleId) {
		return assessCycleTemplateMapper.removeByCycle(cycleId);
	}

	public String getTemplateByCycle(String id, String unitId) {
		List<AssessCycleTemplate> result = searchAll(new Condition[] { new Condition(AssessCycleTemplate.COLUMN_UNIT_ID, QueryType.EQUAL, unitId),
				new Condition(AssessCycleTemplate.COLUMN_CYCLE_ID, QueryType.EQUAL, id) });

		if (result.size() > 0) {
			return result.get(0).getTemplateId();
		}

		return null;
	}

}
