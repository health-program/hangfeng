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
import com.paladin.framework.utils.uuid.UUIDUtil;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.service.org.OrgUnitService;
import com.paladin.hf.service.org.dto.OrgUnitDTO;
import com.paladin.hf.service.org.dto.SimpleUnit;
import com.paladin.hf.service.org.vo.OrgUnitVO;

@Controller
@RequestMapping("/org/unit")
public class OrgUnitController extends ControllerSupport {

	@Autowired
	private OrgUnitService orgUnitService;

	@RequestMapping("/index")
	public String index() {
		return "/hf/org/unit_index";
	}

	@RequestMapping("/find")
	@ResponseBody
	public Object find() {
		return CommonResponse.getSuccessResponse(orgUnitService.findOwnUnit());
	}

	@RequestMapping("/get")
	@ResponseBody
	public Object getDetail(@RequestParam String id, Model model) {
		return CommonResponse.getSuccessResponse(beanCopy(orgUnitService.get(id), new OrgUnitVO()));
	}

	@RequestMapping("/add")
	public String addInput(@RequestParam(required = false) String parentId, Model model) {
		if (parentId != null && parentId.length() > 0) {
			model.addAttribute("parentUnit", UnitContainer.getUnit(parentId));
		}
		return "/hf/org/unit_add";
	}

	@RequestMapping("/detail")
	public String detailInput(@RequestParam String id, Model model) {
		model.addAttribute("id", id);
		return "/hf/org/unit_detail";
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid OrgUnitDTO orgUnitDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		String id = UUIDUtil.createUUID();
		orgUnitDTO.setUid(id);
		if (orgUnitService.saveUnit(orgUnitDTO)) {
			return CommonResponse.getSuccessResponse(orgUnitService.get(id));
		}
		return CommonResponse.getFailResponse();
	}

	@RequestMapping("/update")
	@ResponseBody
	public Object update(@Valid OrgUnitDTO orgUnitDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return validErrorHandler(bindingResult);
		}
		if (orgUnitService.updateUnit(orgUnitDTO)) {
			return CommonResponse.getSuccessResponse(beanCopy(orgUnitService.get(orgUnitDTO.getUid()), new OrgUnitVO()));
		}
		return CommonResponse.getFailResponse();
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
	
	@RequestMapping("/transfer")
    @ResponseBody
	public Object transfer(@RequestParam String newUid,@RequestParam String oldUid){
	    return CommonResponse.getResponse(orgUnitService.transfer(newUid, oldUid));
	}
}
