package com.paladin.hf.service.assess.quantificate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.hf.mapper.assess.quantificate.TemplateMapper;
import com.paladin.hf.model.assess.quantificate.AssessItem;
import com.paladin.hf.model.assess.quantificate.AssessItemExtra;
import com.paladin.hf.model.assess.quantificate.Template;


/**
 * @author 吉三杰
 * @version 2018年1月16日 上午10:42:09
 */

@Service
public class TemplateService extends ServiceSupport<Template> {

	@Autowired
	AssessItemService assessItemService;

	@Autowired
	AssessItemExtraService assessItemExtraService;
	
	@Autowired
	TemplateMapper templateMapper;

	public int updateTemplateState(String id, String state) {
		Template template = new Template();
		template.setId(id);
		template.setEnableState(state);
		return updateSelective(template);
	}

	public List<Template> findStartedTemplateByUnit(String unitId) {
		return searchAll(new Condition[] { new Condition(Template.COLUMN_ORG_UNIT_ID, QueryType.EQUAL, unitId),
				new Condition(Template.COLUMN_ENABLE_STATE, QueryType.EQUAL, Template.STATE_START) });
	}

	/**
	 * 迭代保存树形结构的考评项目
	 * @param templateId
	 * @param item
	 * @param items
	 */
	private void saveAssessItem(String templateId, AssessItem item, List<AssessItem> items) {
		String newId = UUIDUtil.createUUID();
		String oldId = item.getId();

		item.setTemplateId(templateId);
		item.setId(newId);
		assessItemService.save(item);

		for (int i = 0; i < items.size(); i++) {
			AssessItem a = items.get(i);
			if (oldId.equals(a.getParentItemId())) {
				a.setParentItemId(newId);
				saveAssessItem(templateId, a, items);
			}
		}
	}

	@Transactional
	public int copyTemplate(Template template) {

		template.setEnableState(Template.STATE_DRAFT);

		String newTemplateId = UUIDUtil.createUUID();
		String templateId = template.getId();
		Template copy = get(templateId);
		if (copy == null)
			throw new BusinessException("复制的模板不存在");

		template.setId(newTemplateId);
		save(template);

		List<AssessItem> items = assessItemService.findTemplateAssessItem(templateId);

		for (AssessItem item : items) {
			if (item.getParentItemId() == null) {
				saveAssessItem(newTemplateId, item, items);
			}
		}

		List<AssessItemExtra> extras = assessItemExtraService.findTemplateAssessExtraItem(templateId);
		for (AssessItemExtra extra : extras) {
			extra.setTemplateId(newTemplateId);
			extra.setId(UUIDUtil.createUUID());
			assessItemExtraService.save(extra);
		}

		return 1;
	}

	@Transactional
	public int removeTemplate(String id) {
		assessItemService.removeTemplateItem(id);
		assessItemExtraService.removeTemplateItemExtra(id);
		return removeByPrimaryKey(id);
	}

      public int selectAssessCycleByTemplateId(String id) {
            return templateMapper.selectAssessCycleByTemplateId(id) ;
      }
      
      
      
      /**
       * <模板启用时判断是否等级配置>
       * @param id
       * @return
       * @see [类、类#方法、类#成员]
       */
     public int levelCount(String id){
         return this.templateMapper.levelCount(id);
     };
      
      /**
       * <模板启用时判断是否项目配置>
       * @param id
       * @return
       * @see [类、类#方法、类#成员]
       */
      public int itemCount(String id){
          return this.templateMapper.itemCount(id);
      };

}
