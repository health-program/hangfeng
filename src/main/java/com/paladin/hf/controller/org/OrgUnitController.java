package com.paladin.hf.controller.org;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.controller.util.FormType;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.model.org.OrgUnit;
import com.paladin.hf.service.org.OrgUnitService;
import com.paladin.hf.service.org.dto.OrgUnitDTO;
import com.paladin.hf.service.org.dto.SimpleUnit;


@Controller
@RequestMapping("/org/unit")
public class OrgUnitController extends ControllerSupport {

	@Autowired
	private OrgUnitService orgUnitService;

	@RequestMapping("/index")
	public String index() {
		return "console/organization/unit_index";
	}

	@RequestMapping("/search/all")
	@ResponseBody
	public Object searchAll() {
		return CommonResponse.getSuccessResponse(orgUnitService.findOwnUnit());
	}

	@RequestMapping("/view")
	public String view(@RequestParam(required = true) String id, Model model) {
		OrgUnit orgUnit = orgUnitService.get(id);
		if (orgUnit == null)
			orgUnit = new OrgUnit();
		model.addAttribute("unit", orgUnit);
		model.addAttribute("parentUnitId", orgUnit.getParentUnitId());
		model.addAttribute("formType", FormType.VIEW);
		return "console/organization/unit_from";
	}

	@RequestMapping("/add/input")
	public String addInput(String parentId, Model model) {
		model.addAttribute("parentUnitId", parentId);
		model.addAttribute("unit", new OrgUnit());
		model.addAttribute("formType", FormType.ADD);
		return "console/organization/unit_from";
	}

	@RequestMapping("/edit/input")
	public String editInput(@RequestParam(required = true) String id, Model model) {
		OrgUnit orgUnit = orgUnitService.get(id);
		if (orgUnit == null)
			orgUnit = new OrgUnit();
		model.addAttribute("unit", orgUnit);
		model.addAttribute("parentUnitId", orgUnit.getParentUnitId());
		model.addAttribute("formType", FormType.EDIT);
		return "console/organization/unit_from";
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid OrgUnitDTO orgUnit, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}
		// TODO 判断orgUnitDTO哪些字段可以改
		return CommonResponse.getResponse(orgUnitService.saveOrUpdateUnit(orgUnit));
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(orgUnitService.removeUnit(id), "请先删除所有子级单位");
	}

	
	/**
	 * 获取拥有的科室
	 * 
	 * @return
	 */
	@RequestMapping("/own/department")
	@ResponseBody
	public Object getOwnDepartment() {
		Map<String, Object> result = new HashMap<>();

		List<Unit> agencys = DataPermissionUtil.getOwnAgency();
		if (agencys.size() > 0) {
			result.put("agency", agencys);
		} else {

			List<Unit> units = DataPermissionUtil.getOwnDepartment();
			String[] ids = new String[units.size()];
			Set<Unit> uniqueAgencys = new HashSet<>();
			int i = 0;
			for (Unit unit : units) {
				uniqueAgencys.add(UnitContainer.getRootUnit(unit));
				ids[i++] = unit.getId();
			}

			// 为了完整显示上级
			result.put("ownIds", ids);
			result.put("departments", uniqueAgencys);
		}

		return CommonResponse.getSuccessResponse(result);
	}

	@RequestMapping("/own/agency")
	@ResponseBody
	public Object getOwnAgency() {
		List<Unit> agencys = DataPermissionUtil.getOwnAgency();
		List<SimpleUnit> result = new ArrayList<>(agencys.size());
		
		for (Unit agency : agencys) {
			result.add(new SimpleUnit(agency));
		}

		return CommonResponse.getSuccessResponse(result);
	}
	
	@RequestMapping("/self/agency")
	@ResponseBody
	public Object getSelfAgency() {
		HfUserSession session = HfUserSession.getCurrentUserSession();
		return CommonResponse.getSuccessResponse(new SimpleUnit(session.getUserAgency()));
	}
	
	@RequestMapping("/all")
	@ResponseBody
	public Object allUnit() {
		return CommonResponse.getSuccessResponse(UnitContainer.getRoots());
	}
}
