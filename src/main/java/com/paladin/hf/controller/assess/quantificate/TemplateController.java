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

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.model.assess.quantificate.AssessItem;
import com.paladin.hf.model.assess.quantificate.AssessItemExtra;
import com.paladin.hf.model.assess.quantificate.Template;
import com.paladin.hf.service.assess.quantificate.AssessItemExtraService;
import com.paladin.hf.service.assess.quantificate.AssessItemService;
import com.paladin.hf.service.assess.quantificate.TemplateService;
import com.paladin.hf.service.assess.quantificate.dto.TemplateDTO;
import com.paladin.hf.service.assess.quantificate.dto.TemplateQuery;
import com.paladin.hf.service.assess.quantificate.vo.TemplateVO;

@Controller
@RequestMapping("/assess/template")
public class TemplateController extends ControllerSupport {

	@Autowired
	private TemplateService templateService;

	@Autowired
	private AssessItemService assessItemService;

	@Autowired
	private AssessItemExtraService assessItemExtraService;

	@RequestMapping(value = "/index")
	public String index(Model model) {
		return "/hf/assess/quantificate/template_index";
	}

	/**
	 * 加载模板列表信息
	 * 
	 * @param template
	 * @return ModelMap
	 */
	@ResponseBody
	@RequestMapping(value = "/find")
	public Object list(TemplateQuery query) {	
		List<String> agencyIds = DataPermissionUtil.getOwnAgencyId();
		if(agencyIds == null || agencyIds.size() == 0) {
			return CommonResponse.getSuccessResponse(templateService.getEmptyPageResult(query)); 
		} else {
			if(agencyIds.size() == 1) {
				query.setUnitId(agencyIds.get(0));
			} else {
				
			}
		}
		return CommonResponse.getSuccessResponse(templateService.searchPage(query).convert(TemplateVO.class));
	}

	@RequestMapping("/get")
	@ResponseBody
	public Object get(@RequestParam String id, Model model) {
		return CommonResponse.getSuccessResponse(beanCopy(templateService.get(id), new TemplateVO()));
	}

	@RequestMapping("/add")
	public String addInput() {
		return "/hf/assess/quantificate/template_add";
	}

	@RequestMapping("/detail")
	public String detailInput(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "/hf/assess/quantificate/template_detail";
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid TemplateDTO templateDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		Template model = beanCopy(templateDTO, new Template());
		String id = UUIDUtil.createUUID();
		model.setId(id);
		model.setEnableState(Template.STATE_DRAFT);
		if (templateService.save(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(templateService.get(id), new TemplateVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping("/update")
	@ResponseBody
	public Object update(@Valid TemplateDTO templateDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = templateDTO.getId();
		Template model = beanCopy(templateDTO, templateService.get(id));
		if (templateService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(templateService.get(id), new TemplateVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(templateService.removeTemplate(id));
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
		return CommonResponse.getResponse(templateService.startTemplate(id));
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
		return CommonResponse.getResponse(templateService.stopTemplate(id));
	}

	/**
	 * 复制
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/copy/input")
	public String copyInput(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "/hf/assess/quantificate/template_copy";
	}

	/**
	 * 复制
	 * 
	 * @param templateDTO
	 * @param bindingResult
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/copy")
	public Object copy(@Valid TemplateDTO templateDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		return CommonResponse.getResponse(templateService.copyTemplate(templateDTO));
	}

	/**
	 * 配置
	 * 
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/config/input")
	public String configInput(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "/hf/assess/quantificate/template_config";
	}

	/**
	 * 模板详细信息
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/config/detail")
	public Object detail(@RequestParam(required = true) String id) {
		List<AssessItem> items = assessItemService.findTemplateAssessItem(id);
		List<AssessItemExtra> extras = assessItemExtraService.findTemplateAssessExtraItem(id);

		Map<String, Object> result = new HashMap<>();
		result.put("items", items);
		result.put("extras", extras);
		return CommonResponse.getSuccessResponse(result);
	}

}
