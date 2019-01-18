package com.paladin.hf.service.assess.quantificate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.framework.common.Condition;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.hf.mapper.assess.quantificate.AssessItemExtraMapper;
import com.paladin.hf.mapper.assess.quantificate.AssessItemMapper;
import com.paladin.hf.mapper.assess.quantificate.TemplateMapper;
import com.paladin.hf.model.assess.quantificate.AssessItem;
import com.paladin.hf.model.assess.quantificate.AssessItemExtra;
import com.paladin.hf.model.assess.quantificate.Template;
import com.paladin.hf.service.assess.quantificate.dto.TemplateDTO;

/**
 * @author 吉三杰
 * @version 2018年1月16日 上午10:42:09
 */

@Service
public class TemplateService extends ServiceSupport<Template> {

	@Autowired
	private AssessItemService assessItemService;

	@Autowired
	private AssessItemExtraService assessItemExtraService;

	@Autowired
	private AssessItemExtraMapper assessItemExtraMapper;
	
	@Autowired
	private AssessItemMapper assessItemMapper;
	
	@Autowired
	private TemplateMapper templateMapper;

	public List<Template> findStartedTemplateByUnit(String unitId) {
		return searchAll(new Condition[] { new Condition(Template.COLUMN_ORG_UNIT_ID, QueryType.EQUAL, unitId),
				new Condition(Template.COLUMN_ENABLE_STATE, QueryType.EQUAL, Template.STATE_START) });
	}

	/**
	 * 迭代保存树形结构的考评项目
	 * 
	 * @param templateId
	 * @param item
	 * @param items
	 */
	private void saveAssessItem(String templateId, AssessItem item, List<AssessItem> items) {
		String newId = UUIDUtil.createUUID();
		String oldId = item.getId();

		item.setTemplateId(templateId);
		item.setId(newId);
		assessItemMapper.insert(item);

		for (int i = 0; i < items.size(); i++) {
			AssessItem a = items.get(i);
			if (oldId.equals(a.getParentItemId())) {
				a.setParentItemId(newId);
				saveAssessItem(templateId, a, items);
			}
		}
	}

	/**
	 * 复制模板
	 * 
	 * @param templateDTO
	 * @return
	 */
	@Transactional
	public boolean copyTemplate(TemplateDTO templateDTO) {
		Template template = new Template();
		SimpleBeanCopyUtil.simpleCopy(templateDTO, template);
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
			assessItemExtraMapper.insert(extra);
		}

		return true;
	}

	/**
	 * 停用模板
	 * 
	 * @param id
	 * @return
	 */
	public boolean stopTemplate(String id) {
		if (templateMapper.countAssessCycleByTemplate(id) > 0) {
			throw new BusinessException("该模板已经被其他考评周期引用，无法停用");
		}
		Template template = new Template();
		template.setId(id);
		template.setEnableState(Template.STATE_STOP);
		return updateSelective(template) > 0;
	}

	/**
	 * 启用模板
	 * 
	 * @param id
	 * @return
	 */
	public boolean startTemplate(String id) {
		if (hasConfiguredlevel(id) && hasConfiguredItem(id)) {
			Template template = new Template();
			template.setId(id);
			template.setEnableState(Template.STATE_START);
			return updateSelective(template) > 0;
		} else {
			throw new BusinessException("模板必须配置完成项目和等级后才能启用");
		}
	}

	/**
	 * 删除模板
	 * 
	 * @param id
	 * @return
	 */
	@Transactional
	public boolean removeTemplate(String id) {
		Template template = get(id);
		if (template.getEnableState() == Template.STATE_DRAFT) {
			assessItemService.removeTemplateItem(id);
			assessItemExtraService.removeTemplateItemExtra(id);
			removeByPrimaryKey(id);
		} else {
			throw new BusinessException("不能删除已经启用过的模板");
		}

		return true;
	}

	/**
	 * 是否配置了等级
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasConfiguredlevel(String id) {
		return templateMapper.levelCount(id) > 0;
	}

	/**
	 * 是否配置了项目
	 * 
	 * @param id
	 * @return
	 */
	public boolean hasConfiguredItem(String id) {
		return templateMapper.itemCount(id) > 0;
	}

}
