package com.paladin.hf.controller.assess.cycle;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.controller.util.FormType;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.core.UnitContainer;
import com.paladin.hf.core.UnitContainer.Unit;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.model.assess.cycle.PersonCycAssess;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.assess.cycle.PersonCycAssessService;
import com.paladin.hf.service.assess.cycle.dto.PersonnelCycleAssessDTO;
import com.paladin.hf.service.assess.cycle.pojo.PersonCycAssessQuery;
import com.paladin.hf.service.assess.quantificate.AssessQuantitativeResultService;
import com.paladin.hf.service.org.OrgUserService;


/**  
 * 个人周期考评 Controller
 * @author jisanjie
 * @version 2018年1月22日 下午3:09:57 
 */
@Controller
@RequestMapping("/person/cyc")
public class PersonCycAssessController extends ControllerSupport{
    @Autowired
    private PersonCycAssessService perCycAssService;  
    
    @Autowired
    private AssessCycleService assessCycleService;
    
    @Autowired
    private OrgUserService orgUserService;
    
    @Autowired
    private AssessQuantitativeResultService assessQuantitativeResultService;

    /**
     * 跳转首页
     * @return String
     */
    @RequestMapping("/index")
    public String index(Model model) {
        HfUserSession session= HfUserSession.getCurrentUserSession();
        if(session.isAdminRoleLevel()) {
        	return "no_business";
        }
        
        Unit unit=session.getUserAgency();
/*        AssessCycle assessCycle = assessCycleService.getOwnedFirstAssessCycle();
        if (assessCycle != null) {
              model.addAttribute("assessCycleId", assessCycle.getId());
              model.addAttribute("assessCycleName", assessCycle.getCycleName());  
      } */
         model.addAttribute("unitName", unit.getName());
         return "/hf/assess/cycle/individual_cycle_index";
    }
    
    
    /**
     * 
     * 加载首页及条件查询
     * @return Object
     */ 
    
    @ResponseBody
    @RequestMapping(value="/search/list",method = {RequestMethod.GET})
    public Object associationQueryPersonalAll(PersonCycAssessQuery perCycAssQuery){
         return CommonResponse.getSuccessResponse(perCycAssService.searchSelfCycAssessPage(perCycAssQuery));
    }
    
    /**
     * 医德医风档案管理-->查看-->考评情况
     * 
     * @return Object
     */ 
    @RequestMapping("/view/assess/situation")
    @ResponseBody
    public Object associationQueryAll(PersonCycAssessQuery perCycAssQuery){
         return CommonResponse.getSuccessResponse(perCycAssService.viewPersonAssessSituation(perCycAssQuery));
    }
    
    /**
     * 
     * 查看
     * @param id
     * @param model
     * @return String
     */
    @RequestMapping("/view")
    public String view(@RequestParam(required = true) String id, Model model) {
        PersonCycAssess perCycAss= perCycAssService.get(id);
        if(perCycAss == null)
            perCycAss = new PersonCycAssess();  
        HfUserSession session = HfUserSession.getCurrentUserSession();
        OrgUser orgUser = orgUserService.get(session.getUserId());// 获取当前用户的信息
        if (orgUser == null)
            orgUser = new OrgUser();
        AssessCycle assessCycle = assessCycleService.get(perCycAss.getAssessCycleId());
        model.addAttribute("assessCycle", assessCycle);
        model.addAttribute("user", orgUser);
        model.addAttribute("cycleRecordId", id);
//        model.addAttribute("perCycAss", perCycAss);
//        model.addAttribute("formType", FormType.VIEW);
        return "/hf/assess/cycle/individual_cycle_detail";    
    }
    
    /**
     *  获取当前用户的信息
     * @author jisanjie
     * 
     */
    @RequestMapping("/find/user")
    @ResponseBody
    public Object findUser(@RequestParam(required = true) String id) {
        HfUserSession session = HfUserSession.getCurrentUserSession();
        OrgUser orgUser = orgUserService.get(session.getUserId());// 获取当前用户的信息
        if (orgUser == null)
            orgUser = new OrgUser();
        return CommonResponse.getSuccessResponse(orgUser);
        
    }
    
    
    /**
     * 获取周期考评记录
     * @author jisanjie 
     * 
     */
    @RequestMapping("/find/cycle/record")
    @ResponseBody
    public Object getOneByPrimaryKey(@RequestParam String id) {
          PersonCycAssess perCycAss= perCycAssService.get(id);
          return CommonResponse.getSuccessResponse(perCycAss);
    }
    
    
    @RequestMapping("/detail")
    @ResponseBody
    public Object viewUser(@RequestParam String id, Model model) {
        PersonnelCycleAssessDTO perCycAss= perCycAssService.getCycleAssessExtend(id);
        return CommonResponse.getSuccessResponse(perCycAss);
    }
    
    
    /**
     * 
     * 跳转新增页面
     * @param model
     * @return String
     */
    @RequestMapping("/add")
    public String addInput(Model model) {
        HfUserSession session = HfUserSession.getCurrentUserSession();
        OrgUser orgUser = orgUserService.get(session.getUserId());// 获取当前用户的信息
        if (orgUser == null)
            orgUser = new OrgUser();
        model.addAttribute("user", orgUser);
        model.addAttribute("user",orgUser);
        return "/hf/assess/cycle/individual_cycle_add";    
    }
    
    
    /**
     * 
     * 跳转编辑页面
     * @param id
     * @param model
     * @return String
     */
    @RequestMapping("/edit")
    public String editInput(@RequestParam(required = true) String id, Model model) {
        HfUserSession session = HfUserSession.getCurrentUserSession();
        OrgUser orgUser = orgUserService.get(session.getUserId());// 获取当前用户的信息
        if (orgUser == null)
            orgUser = new OrgUser();
        model.addAttribute("user", orgUser);
        return "/hf/assess/cycle/individual_cycle_detail";    
    }
    
    
    /**
     * 
     * （修改、新增）
     * @param perCycAss
     * @param bindingResult
     * @return Object
     */
    @RequestMapping("/save")
    @ResponseBody 
    public Object save(@Valid PersonCycAssess perCycAss, @RequestParam("status") String status, BindingResult bindingResult) {   
        if(bindingResult.hasErrors()) {
            return this.validErrorHandler(bindingResult);
        }
        if(status.equals("1"))//保存按钮
        {
            perCycAss.setOperateState(PersonCycAssess.ASSESSED_TEMPORARY);
        }else if(status.equals("2"))//提交按钮
        {
            perCycAss.setOperateState(PersonCycAssess.ASSESSED_SUBMIT);
        } 
        HfUserSession session = HfUserSession.getCurrentUserSession();
        String unitId = session.getUserUnitId();
        Unit unit = UnitContainer.getUnit(unitId);
        perCycAss.setUnitId(unitId);//获取科室id
        perCycAss.setAgencyId(unit.getAgency().getId());//机构
        
        Unit assessTeam = unit.getAssessTeam();
        if(assessTeam != null) {
        	perCycAss.setAssessTeamId(assessTeam.getId());//考核小组id
        }
        
        perCycAss.setOrgUserId(session.getUserId());//获取用户id
        if (perCycAss.getId()!=null)
        {              
            //修改
            return CommonResponse.getResponse(perCycAssService.updateSelective(perCycAss));
        }else {
            //根据周期id查询对应考评记录来做判断
            PersonnelCycleAssessDTO personCycAssess = perCycAssService.searchBaseInfo(perCycAss.getAssessCycleId(),session.getUserId()); 
            if (personCycAssess == null){
            	
            	Object result = assessQuantitativeResultService.getResult(session.getUserId(), perCycAss.getAssessCycleId());
            	if(result == null) {
            		return CommonResponse.getFailResponse("该周期的量化考评还未完成，您不能进行周期考评");
            	}
            	
                //新增
                return CommonResponse.getResponse(perCycAssService.save(perCycAss));
            }else {
                return CommonResponse.getErrorResponse("你在该周期内已经自评！");
            }
            
        }
        
    }
    
    /**
     * 
     * 删除
     * @param id
     * @return Object
     */
    @RequestMapping("/delete")
    @ResponseBody
    public Object delete(@RequestParam(required = true) String id) {
        return CommonResponse.getResponse(perCycAssService.removeByPrimaryKey(id));
    }
    
    /**
     * 
     * 跳往考评结果确认页面
     * @param id
     * @param model
     * @return
     * 
     */
    @RequestMapping("/to/confirm")
    public String toConfirm(@RequestParam(required = true) String id, Model model){
        PersonCycAssess perCycAss= perCycAssService.get(id);
        if(perCycAss == null)
            perCycAss = new PersonCycAssess();  
        //获取对应的考评周期信息
        AssessCycle assessCycle=assessCycleService.get(perCycAss.getAssessCycleId());
        //根据人员id查询人员信息
        OrgUser orgUser = orgUserService.get(perCycAss.getOrgUserId());
        if (orgUser == null)
            orgUser = new OrgUser();
        model.addAttribute("user", orgUser);
        model.addAttribute("assessCycle", assessCycle);
        model.addAttribute("perCycAss", perCycAss);
        return "/hf/assess/cycle/individual_cycle_confirm";
        
    }
    
    /**
     * 
     * 确认考评结果
     * @param id
     * @return
     * 
     */
    @RequestMapping("/confirm")
    @ResponseBody
    public Object assessConfirm(PersonCycAssess personCycAssess){
        return  CommonResponse.getResponse(perCycAssService.updateOperateState(personCycAssess,PersonCycAssess.ASSESSSED_CONFIRMED));
    }
    
    
    /**
     * 
     * 被驳回
     * @param id
     * @return Object
     */
    @RequestMapping("/backward")
    @ResponseBody
    public Object backward(String id){
      return CommonResponse.getResponse(perCycAssService.resetOperateState(id, PersonCycAssess.BACKWORD));
    }
    
    
/*=====================================APP端===================================*/
    
    /**
     * 
     * 加载首页及条件查询
     * @return Object
     */ 
    
    @ResponseBody
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping("/app/search/list")
    public Object appAssociationQueryPersonalAll(PersonCycAssessQuery perCycAssQuery){
         return CommonResponse.getSuccessResponse(perCycAssService.searchSelfCycAssessPage(perCycAssQuery));
    }
    
    
    /**
     * 
     * 查看
     * @param id
     * @param model
     * @return String
     */
    @ResponseBody
    @RequestMapping("/app/view")
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object appView(@RequestParam(required = true) String id, Model model) {
        ModelMap map = new ModelMap();
        PersonCycAssess perCycAss= perCycAssService.get(id);
        if(perCycAss == null)
            perCycAss = new PersonCycAssess();  
        HfUserSession session = HfUserSession.getCurrentUserSession();
        OrgUser orgUser = orgUserService.get(session.getUserId());// 获取当前用户的信息
        if (orgUser == null)
            orgUser = new OrgUser();
        AssessCycle assessCycle = assessCycleService.get(perCycAss.getAssessCycleId());
        map.put("assessCycle", assessCycle);
        map.put("user", orgUser);
        map.put("perCycAss", perCycAss);
       return CommonResponse.getSuccessResponse(map);
    }
    
    
    @CrossOrigin(origins = "*", maxAge = 3600)
    @RequestMapping("/app/add")
    @ResponseBody
    public Object appAddInput(Model model) {
        ModelMap map = new ModelMap();
        PersonCycAssess perCycAss=new PersonCycAssess();
        HfUserSession session = HfUserSession.getCurrentUserSession();
        OrgUser orgUser = orgUserService.get(session.getUserId());// 获取当前用户的信息
        if (orgUser == null)
            orgUser = new OrgUser();
        map.put("user", orgUser);
        map.put("perCycAss", perCycAss);
        return CommonResponse.getSuccessResponse(map);    
    }
    
    
    /**
     * 
     * （修改、新增）
     * @param perCycAss
     * @param bindingResult
     * @return Object
     */
    @RequestMapping("app/save")
    @ResponseBody 
    @CrossOrigin(origins = "*", maxAge = 3600)
    public Object appSave(@RequestBody PersonCycAssess perCycAss, BindingResult bindingResult) {   
         if(bindingResult.hasErrors()) {
            return this.validErrorHandler(bindingResult);
        }
        if(perCycAss.getSubmitname().equals("1"))//保存按钮
        {
            perCycAss.setOperateState(PersonCycAssess.ASSESSED_TEMPORARY);
        }else if(perCycAss.getSubmitname().equals("2"))//提交按钮
        {
            perCycAss.setOperateState(PersonCycAssess.ASSESSED_SUBMIT);
        } 
        HfUserSession session = HfUserSession.getCurrentUserSession();
        String unitId = session.getUserUnitId();
        Unit unit = UnitContainer.getUnit(unitId);
        perCycAss.setUnitId(unitId);//获取科室id
        perCycAss.setAgencyId(unit.getAgency().getId());//机构
        
        Unit assessTeam = unit.getAssessTeam();
        if(assessTeam != null) {
        	perCycAss.setAssessTeamId(assessTeam.getId());
        }
        
        perCycAss.setOrgUserId(session.getUserId());//获取用户id
        if (perCycAss.getId()!=null)
        {              
            //修改
            return CommonResponse.getResponse(perCycAssService.updateSelective(perCycAss));
        }else {
            //根据考评周期id查询对应考评信息来做判断
            PersonnelCycleAssessDTO personCycAssess = perCycAssService.searchBaseInfo(perCycAss.getAssessCycleId(),session.getUserId()); 
            if (personCycAssess == null){
                //新增
                return CommonResponse.getResponse(perCycAssService.save(perCycAss));
            }else {
                return CommonResponse.getErrorResponse("你在该周期内已经自评！");
            }
            
        }
        
    }
}
