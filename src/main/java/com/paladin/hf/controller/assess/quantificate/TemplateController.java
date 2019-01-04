package com.paladin.hf.controller.assess.quantificate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.common.Condition;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.controller.util.FormType;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.model.assess.quantificate.AssessItem;
import com.paladin.hf.model.assess.quantificate.AssessItemExtra;
import com.paladin.hf.model.assess.quantificate.Template;
import com.paladin.hf.service.assess.quantificate.AssessItemExtraService;
import com.paladin.hf.service.assess.quantificate.AssessItemService;
import com.paladin.hf.service.assess.quantificate.TemplateService;
import com.paladin.hf.service.assess.quantificate.pojo.TemplateQuery;

/**
 * 考评模板管理Controller
 * 
 * @author jisanjie
 * @version 2018年1月16日 上午10:21:43
 */

@Controller
@RequestMapping("/console/temp")
public class TemplateController extends ControllerSupport {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private AssessItemService assessItemService;

	@Autowired
	private AssessItemExtraService assessItemExtraService;

	/**
	 * 
	 * 跳转首页
	 * 
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/index")
	public String index(Model model) {
		wrapAgency(model);
		return "console/templ/index";
	}

	private void wrapAgency(Model model) {
		HfUserSession session = HfUserSession.getCurrentUserSession();

		Unit agency = null;

		if (!session.isAdminRoleLevel()) {
			if (session.isAssessTeamRole()) {
				agency = session.getOwnUnit().getAgency();
			} else {
				List<Unit> agencys = DataPermissionUtil.getOwnAgency();
				int size = agencys.size();
				if (size == 1) {
					agency = agencys.get(0);
				} else if (size == 0) {
					agency = session.getUserAgency();
				}
			}

			if (agency != null) {
				model.addAttribute("agencyId", agency.getId());
				model.addAttribute("agencyName", agency.getName());
			}
		}

	}

	/**
	 * 
	 * 加载模板列表信息
	 * 
	 * @param template
	 * @return ModelMap
	 */
	@ResponseBody
	@RequestMapping(value = "/search/all")
	public Object list(TemplateQuery query) {
		return CommonResponse.getSuccessResponse(templateService.searchPage(query));
	}

	@RequestMapping("/view")
	public String view(@RequestParam(required = true) String id, Model model) {

		Template template = templateService.get(id);
		if (template == null)
			throw new BusinessException("无法找到对应的模板");
		model.addAttribute("temp", template);

		return "console/templ/detail";
	}

	/**
	 * 模板详细信息
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/detail")
	public Object detail(@RequestParam(required = true) String id) {

		List<AssessItem> items = assessItemService.findTemplateAssessItem(id);
		List<AssessItemExtra> extras = assessItemExtraService.findTemplateAssessExtraItem(id);

		Map<String, Object> result = new HashMap<>();
		result.put("items", items);
		result.put("extras", extras);
		return CommonResponse.getSuccessResponse(result);
	}

	@RequestMapping("/add/input")
	public String addInput(Model model) {
		wrapAgency(model);
		model.addAttribute("temp", new Template());
		model.addAttribute("formType", FormType.ADD);
		return "console/templ/from";
	}

	@RequestMapping("/edit/input")
	public String editInput(@RequestParam(required = true) String id, Model model) {
		Template template = templateService.get(id);
		if (template == null)
			template = new Template();
		wrapAgency(model);
		model.addAttribute("temp", template);
		model.addAttribute("formType", FormType.EDIT);
		return "console/templ/from";
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid Template template, BindingResult bindingResult, @RequestParam("isCopy") String isCopy) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}

		String tid = template.getId();
		String tname = template.getTemplateName();
		String orgUnitId = template.getOrgUnitId();

		boolean copy = "1".equals(isCopy);
		boolean isAdd = tid == null || tid.length() == 0 || copy;

		List<Template> sameNameTemplate = templateService.searchAll(new Condition[] { new Condition(Template.COLUMN_TEMPLAT_NAME, QueryType.EQUAL, tname),
				new Condition(Template.COLUMN_ORG_UNIT_ID, QueryType.EQUAL, orgUnitId) });
		if (sameNameTemplate != null && sameNameTemplate.size() > 0) {
			if (isAdd) {// 新增、复制
				return CommonResponse.getFailResponse("存在相同名称的模板");
			} else {
				for (Template t : sameNameTemplate) {
					if (!t.getId().equals(tid)) {// 修改
						return CommonResponse.getFailResponse("存在相同名称的模板");
					}
				}
			}
		}

		if (copy) {
			return CommonResponse.getResponse(templateService.copyTemplate(template));
		} else {
			template.setEnableState(Template.STATE_DRAFT);
			return CommonResponse.getResponse(templateService.saveOrUpdate(template));
		}
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(templateService.removeTemplate(id), "删除失败");
	}

	/**
	 * 
	 * 启用模板
	 * 
	 * @param id
	 * @return ModelMap
	 */
	@ResponseBody
	@RequestMapping(value = "/start", method = { RequestMethod.GET })
	public Object startTemplate(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(templateService.updateTemplateState(id, Template.STATE_START));
	}

	/**
	 * 
	 * 停用模板
	 * 
	 * @param template
	 * @return ModelMap
	 */
	@ResponseBody
	@RequestMapping(value = "/stop", method = { RequestMethod.GET })
	public Object stopTemplate(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(templateService.updateTemplateState(id, Template.STATE_STOP));
	}

	/**
	 * 
	 * 停用前根据模板id进行查询 该模板在其他考评周期中是否被引用
	 * 
	 * @param id
	 * @return Object
	 */
	@ResponseBody
	@RequestMapping(value = "/is/stop", method = { RequestMethod.GET })
	public Object isStopTemplate(@RequestParam(required = true) String id) {
		return CommonResponse.getSuccessResponse(templateService.selectAssessCycleByTemplateId(id));
	}

	/**
	 * 
	 * 跳转复制页面
	 * 
	 * @param template
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "/copy", method = { RequestMethod.GET })
	public String skipCopy(Model model, @RequestParam(required = true) String id) {
		wrapAgency(model);
		Template template = templateService.get(id);
		model.addAttribute("temp", template);
		model.addAttribute("isCopy", "1");
		model.addAttribute("formType", FormType.ADD);
		return "console/templ/from";
	}

	/**
	 * <模板启用时判断是否等级配置>
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@ResponseBody
	@RequestMapping(value = "/level", method = { RequestMethod.GET })
	public Object levelCount(@RequestParam(required = true) String id) {
		return CommonResponse.getSuccessResponse(templateService.levelCount(id));
	}

	/**
	 * <模板启用时判断是否项目配置>
	 * 
	 * @param id
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@ResponseBody
	@RequestMapping(value = "/item", method = { RequestMethod.GET })
	public Object itemCount(@RequestParam(required = true) String id) {
		return CommonResponse.getSuccessResponse(templateService.itemCount(id));
	}

}
