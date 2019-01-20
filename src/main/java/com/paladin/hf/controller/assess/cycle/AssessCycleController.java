package com.paladin.hf.controller.assess.cycle;

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

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.model.assess.quantificate.AssessCycleTemplate;
import com.paladin.hf.model.assess.quantificate.Template;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.assess.cycle.dto.AssessCycleDTO;
import com.paladin.hf.service.assess.cycle.dto.AssessCycleQuery;
import com.paladin.hf.service.assess.cycle.dto.AssessCycleSelectQuery;
import com.paladin.hf.service.assess.cycle.vo.AssessCycleVO;
import com.paladin.hf.service.assess.quantificate.AssessCycleTemplateService;
import com.paladin.hf.service.assess.quantificate.TemplateService;
import com.paladin.hf.service.assess.quantificate.pojo.AssessCycleTemplateRequest;

/**
 * 
 * 考评周期管理Controller
 * 
 * @author jisanjie
 * @version 2018年1月11日 下午2:02:44
 */
@Controller
@RequestMapping("/assess/cycle")
public class AssessCycleController extends ControllerSupport {
	@Autowired
	private AssessCycleService assessCycleService;

	@Autowired
	private TemplateService templateService;

	@Autowired
	private AssessCycleTemplateService assessCycleTemplateService;

	@RequestMapping(value = "/index")
	public String index(Model model) {
		return "/hf/assess/cycle/cycle_index";
	}

	@ResponseBody
	@RequestMapping(value = "/list")
	public Object listPage(AssessCycleQuery assessCycleQuery) {
		List<String> agencyIds = DataPermissionUtil.getOwnAgencyId();
		if (agencyIds == null || agencyIds.size() == 0) {
			return CommonResponse.getSuccessResponse(templateService.getEmptyPageResult(assessCycleQuery));
		} else {
			if (agencyIds.size() == 1) {
				assessCycleQuery.setUnitId(agencyIds.get(0));
			} else {

			}
		}
		return CommonResponse.getSuccessResponse(assessCycleService.searchPage(assessCycleQuery).convert(AssessCycleVO.class));
	}
	
	@ResponseBody
	@RequestMapping(value = "/select/self")
	public Object selfListPage(OffsetPage offsetPage) {
		return CommonResponse.getSuccessResponse(assessCycleService.findSelfAssessCyclePage(offsetPage));
	}

	@ResponseBody
	@RequestMapping(value = "/app/select/self")
	public Object appselfListPage(OffsetPage offsetPage) {
		return CommonResponse.getSuccessResponse(assessCycleService.findSelfAssessCyclePage(offsetPage));
	}

	@ResponseBody
	@RequestMapping(value = "/select/user")
	public Object userListPage(AssessCycleSelectQuery query) {
		return CommonResponse.getSuccessResponse(assessCycleService.findUserAssessCyclePage(query, query.getUserId()));
	}

	@ResponseBody
	@RequestMapping(value = "/select/unit")
	public Object unitListPage(AssessCycleSelectQuery query) {
		return CommonResponse.getSuccessResponse(assessCycleService.findUnitAssessCyclePage(query, query.getUnitId()));
	}

	@ResponseBody
	@RequestMapping(value = "/select/assess")
	public Object assessListPage(AssessCycleSelectQuery query) {
		return CommonResponse.getSuccessResponse(assessCycleService.findAvailableAssessCyclePage(query, query.getUnitId()));
	}

	@ResponseBody
	@RequestMapping(value = "/select/own")
	public Object ownListPage(AssessCycleSelectQuery query) {
		return CommonResponse.getSuccessResponse(assessCycleService.findOwnedAssessCyclePage(query, query.getUnitId()));
	}

	@ResponseBody
	@RequestMapping(value = "/select/selfenabled")
	public Object selfEnabledListPage(OffsetPage offsetPage) {
		return CommonResponse.getSuccessResponse(assessCycleService.findEnabledSelfAssessCyclePage(offsetPage));
	}

	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(@RequestParam(required = true) String id) {
		return CommonResponse.getSuccessResponse(beanCopy(assessCycleService.get(id),new AssessCycleVO()));
	}

	@RequestMapping("/add")
	public String addInput() {
		return "/hf/assess/cycle/cycle_add";
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid AssessCycleDTO assessCycleDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		AssessCycle model = beanCopy(assessCycleDTO, new AssessCycle());
		String id = UUIDUtil.createUUID();
		model.setId(id);
		model.setCycleState(AssessCycle.CYCLE_STATE_DRAFT);
		if (assessCycleService.save(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(assessCycleService.get(id),new AssessCycleVO()));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping("/update")
	@ResponseBody
	public Object update(@Valid AssessCycleDTO assessCycleDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = assessCycleDTO.getId();
		AssessCycle model = beanCopy(assessCycleDTO, assessCycleService.get(id));
		
		if (assessCycleService.update(model) > 0) {
			return CommonResponse.getSuccessResponse(beanCopy(assessCycleService.get(id),new AssessCycleVO()));
		}
		return CommonResponse.getFailResponse();
	}
	
	@RequestMapping("/start")
	@ResponseBody
	public Object start(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(assessCycleService.startAssessCycle(id));
	}

	@RequestMapping("/stop")
	@ResponseBody
	public Object stop(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(assessCycleService.stopAssessCycle(id));
	}

	@RequestMapping("/archive")
	@ResponseBody
	public Object archive(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(assessCycleService.archiveAssessCycle(id));
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(assessCycleService.removeAssessCycle(id));
	}

	@RequestMapping("/config/template/index")
	public Object templateConfigIndex() {
		return "/hf/assess/quantificate/config";
	}

	@RequestMapping("/config/template/get")
	@ResponseBody
	public Object getTemplateConfig(@RequestParam(required = true) String id) {

		AssessCycle assessCycle = assessCycleService.get(id);

		String unitId = assessCycle.getUnitId();

		List<Template> templates = templateService.findStartedTemplateByUnit(unitId);
		List<AssessCycleTemplate> relations = assessCycleTemplateService.findRelationByCycle(id);

		Unit agency = null;
		HfUserSession session = HfUserSession.getCurrentUserSession();

		if (session.isAssessTeamRole()) {
			agency = session.getOwnUnit();
		} else {
			agency = UnitContainer.getUnit(unitId);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("templates", templates);
		result.put("relations", relations);
		result.put("agency", agency);

		return CommonResponse.getSuccessResponse(result);
	}

	@RequestMapping("/config/template/save")
	@ResponseBody
	public Object setTemplateConfig(AssessCycleTemplateRequest request) {
		return CommonResponse.getResponse(assessCycleService.configTemplate(request.getCycleId(), request.getTemplateId(), request.getUnitIds().split(",")));
	}

}
