package com.paladin.hf.controller.assess.cycle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/console/asscyc")
public class AssessCycleController extends ControllerSupport {
      @Autowired
      private AssessCycleService assessCycleService;
      
      @Autowired
      private TemplateService templateService;
      
      @Autowired
      private AssessCycleTemplateService assessCycleTemplateService;
      
      @RequestMapping(value = "/index")
      public String index(Model model) {
            
            HfUserSession session = HfUserSession.getCurrentUserSession();
            
            Unit agency = null;
            
            if (!session.isAdminRoleLevel()) {
                  if (session.isAssessTeamRole()) {
                        agency = session.getOwnUnit().getAgency();
                  }
                  else {
                        List<Unit> agencys = DataPermissionUtil.getOwnAgency();
                        int size = agencys.size();
                        if (size == 1) {
                              agency = agencys.get(0);
                        }
                        else if (size == 0) {
                              agency = session.getUserAgency();
                        }
                  }
                  
                  if (agency != null) {
                        model.addAttribute("agencyId", agency.getId());
                        model.addAttribute("agencyName", agency.getName());
                  }
            }
            
            return "/hf/assess/cycle/cycle_index";
      }
      
      @ResponseBody
      @RequestMapping(value = "/list")
      public Object listPage(AssessCycleQuery assessCycleQuery) {
            return CommonResponse.getSuccessResponse(assessCycleService.searchPage(assessCycleQuery));
      }
      
      @ResponseBody
      @RequestMapping(value = "/select/self")
      public Object selfListPage(OffsetPage offsetPage) {
            return CommonResponse.getSuccessResponse(assessCycleService.findSelfAssessCyclePage(offsetPage));
      }
      
      @ResponseBody
      @CrossOrigin(origins = "*", maxAge = 3600)
      @RequestMapping(value = "/app/select/self")
      public Object appselfListPage(OffsetPage offsetPage) {
            return CommonResponse
                  .getSuccessResponse(assessCycleService.findSelfAssessCyclePage(offsetPage));
      }
      
      @ResponseBody
      @RequestMapping(value = "/select/user")
      public Object userListPage(AssessCycleSelectQuery query) {
            return CommonResponse.getSuccessResponse(assessCycleService.findUserAssessCyclePage(query, query.getUserId()));
      }
      
      @ResponseBody
      @RequestMapping(value = "/select/unit")
      public Object unitListPage(AssessCycleSelectQuery query) {
            return CommonResponse.getSuccessResponse(
                  assessCycleService.findUnitAssessCyclePage(query, query.getUnitId()));
      }
      
      @ResponseBody
      @RequestMapping(value = "/select/assess")
      public Object assessListPage(AssessCycleSelectQuery query) {
            return CommonResponse.getSuccessResponse(
                  assessCycleService.findAvailableAssessCyclePage(query, query.getUnitId()));
      }
      
      @ResponseBody
      @RequestMapping(value = "/select/own")
      public Object ownListPage(AssessCycleSelectQuery query) {
            return CommonResponse.getSuccessResponse(
                  assessCycleService.findOwnedAssessCyclePage(query, query.getUnitId()));
      }
      
      @ResponseBody
      @RequestMapping(value = "/select/selfenabled")
      public Object selfEnabledListPage(OffsetPage offsetPage) {
            return CommonResponse
                  .getSuccessResponse(assessCycleService.findEnabledSelfAssessCyclePage(offsetPage));
      }
      
      @ResponseBody
      @RequestMapping(value = "/get")
      public Object get(@RequestParam(required = true) String id) {
            return CommonResponse.getSuccessResponse(assessCycleService.get(id));
      }
      
      @RequestMapping("/view")
      public String view(@RequestParam(required = true) String id, Model model) {
            HfUserSession session = HfUserSession.getCurrentUserSession();
            AssessCycleDTO assessCycleDTO = assessCycleService.getOneById(id);
            if (assessCycleDTO == null)
                  assessCycleDTO = new AssessCycleDTO();
            
            Unit agency = null;
            if (!session.isAdminRoleLevel()) {
                  if (session.isAssessTeamRole()) {
                        agency = session.getOwnUnit().getAgency();
                  }
                  else {
                        List<Unit> agencys = DataPermissionUtil.getOwnAgency();
                        int size = agencys.size();
                        if (size == 1) {
                              agency = agencys.get(0);
                        }
                        else if (size == 0) {
                              agency = session.getUserAgency();
                        }
                  }
                  
            }else{
                  String unitId = assessCycleDTO.getUnitId();
                  if (unitId != null && unitId.length() > 0) {
                        Unit unit = UnitContainer.getUnit(unitId);
                        agency = unit.getAgency();
                  }
            }
            
            if (agency != null) {
                  model.addAttribute("agencyId", agency.getId());
                  model.addAttribute("agencyName", agency.getName());
            }
            model.addAttribute("id", id);
            model.addAttribute("assessCycleDTO", assessCycleDTO);
            return "/hf/assess/cycle/cycle_view";
      }
      
      @RequestMapping("/add/input")
      public String addInput(String parentId, Model model) {
            HfUserSession session = HfUserSession.getCurrentUserSession();
            
            Unit agency = null;
            
            if (!session.isAdminRoleLevel()) {
                  if (session.isAssessTeamRole()) {
                        agency = session.getOwnUnit().getAgency();
                  }
                  else {
                        List<Unit> agencys = DataPermissionUtil.getOwnAgency();
                        int size = agencys.size();
                        if (size == 1) {
                              agency = agencys.get(0);
                        }
                        else if (size == 0) {
                              agency = session.getUserAgency();
                        }
                  }
                  
                  if (agency != null) {
                        model.addAttribute("agencyId", agency.getId());
                        model.addAttribute("agencyName", agency.getName());
                  }
            }
            
            model.addAttribute("assessCycleDTO", new AssessCycleDTO());
            return "/hf/assess/cycle/cycle_add";
      }
      
      @RequestMapping("/get/detail")
      @ResponseBody
      public Object getDetail(@RequestParam(required = true) String id, Model model) {
            AssessCycleDTO assessCycleDTO = assessCycleService.getOneById(id);
            return CommonResponse.getSuccessResponse(assessCycleDTO);
      }
      
//      @RequestMapping("/edit/input")
//      public String editInput(@RequestParam(required = true) String id, Model model) {
//            HfUserSession session = HfUserSession.getCurrentUserSession();
//            
//            Unit agency = null;
//            AssessCycle assessCycle = assessCycleService.get(id);
//            if (assessCycle == null)
//                  assessCycle = new AssessCycle();
//            
//            if (!session.isAdminRoleLevel()) {
//                  if (session.isAssessTeamRole()) {
//                        agency = session.getOwnUnit().getAgency();
//                  }
//                  else {
//                        List<Unit> agencys = DataPermissionUtil.getOwnAgency();
//                        int size = agencys.size();
//                        if (size == 1) {
//                              agency = agencys.get(0);
//                        }
//                        else if (size == 0) {
//                              agency = session.getUserAgency();
//                        }
//                  }
//                  
//                  if (agency != null) {
//                        model.addAttribute("agencyId", agency.getId());
//                        model.addAttribute("agencyName", agency.getName());
//                  }
//            }else{
//                  String unitId = assessCycle.getUnitId();
//                  if (unitId != null && unitId.length() > 0) {
//                        Unit unit = UnitContainer.getUnit(unitId);
//                        agency = unit.getAgency();
//                  }
//            }
//           
//            model.addAttribute("assessCycle", assessCycle);
//            model.addAttribute("formType", FormType.EDIT);
//            return "/hf/assess/cycle/cycle_view";
//            
//
//      }
      
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
              return CommonResponse.getSuccessResponse(assessCycleService.get(id));
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
              model.setCycleState(AssessCycle.CYCLE_STATE_DRAFT);;
              if (assessCycleService.update(model) > 0) {
                  return CommonResponse.getSuccessResponse(assessCycleService.get(id));
              }
              return CommonResponse.getFailResponse();
      }
      
      @RequestMapping("/delete")
      @ResponseBody
      public Object delete(@RequestParam(required = true) String id) {
            return CommonResponse.getResponse(assessCycleService.removeAssessCycle(id), "只能删除暂存的考评周期");
      }
      
      @RequestMapping("/start")
      @ResponseBody
      public Object start(@RequestParam(required = true) String id) {
            return CommonResponse.getResponse(assessCycleService.startAssessCycle(id), "只能启用暂存和停用考评周期");
      }
      
      /**
       * 
       * 根据考评周期id查询对应的模板id是否存在
       * 
       * @param id
       * @return
       */
      @RequestMapping("/is/start")
      @ResponseBody
      public Object isStart(@RequestParam(required = true) String id) {
            return CommonResponse.getSuccessResponse(assessCycleService.selectTemplateIdByAssessCycleId(id));
      }
      
      @RequestMapping("/stop")
      @ResponseBody
      public Object stop(@RequestParam(required = true) String id) {
            return CommonResponse.getResponse(assessCycleService.stopAssessCycle(id), "只能停用已经启用的考评周期");
      }
      
      @RequestMapping("/archive")
      @ResponseBody
      public Object archive(@RequestParam(required = true) String id) {
            return CommonResponse.getResponse(assessCycleService.archiveAssessCycle(id), "不能归档暂存的考评周期");
      }
      
      @RequestMapping("/template/config/index")
      public Object templateConfigIndex() {
            return "/hf/assess/quantificate/config";
      }
      
      /**
       * 跳转模板配置查看页面
       * 
       * @return
       */
      @RequestMapping("/template/config/view")
      public Object templateConfigView() {
            return "/hf/assess/quantificate/template_config_view";
      }
      
      @RequestMapping("/template/config")
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
            }
            else {
                  agency = UnitContainer.getUnit(unitId);
            }
            
            Map<String, Object> result = new HashMap<>();
            result.put("templates", templates);
            result.put("relations", relations);
            result.put("agency", agency);
            
            return CommonResponse.getSuccessResponse(result);
      }
      
      @RequestMapping("/template/config/set")
      @ResponseBody
      public Object setTemplateConfig(AssessCycleTemplateRequest request) {
            return CommonResponse.getResponse(assessCycleService.configTemplate(request.getCycleId(),
                  request.getTemplateId(),
                  request.getUnitIds().split(",")));
      }
      
      /**
       * <周期启用时判断是否模板配置>
       * 
       * @param id
       * @return
       * @see [类、类#方法、类#成员]
       */
      @ResponseBody
      @RequestMapping(value = "/cycle", method = {RequestMethod.GET})
      public Object cycleCount(@RequestParam(required = true) String id) {
            return CommonResponse.getSuccessResponse(assessCycleService.cycleCount(id));
      }
      
}
