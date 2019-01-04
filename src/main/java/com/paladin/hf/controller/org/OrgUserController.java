package com.paladin.hf.controller.org;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.paladin.framework.common.Condition;
import com.paladin.framework.common.GeneralCriteriaBuilder;
import com.paladin.framework.common.QueryType;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.container.ConstantsContainer;
import com.paladin.framework.excel.read.ExcelReader;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.controller.util.FormType;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.mapper.assess.cycle.PersonCycAssessMapper;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.assess.cycle.dto.PersonCycAssessExt;
import com.paladin.hf.service.assess.cycle.pojo.PersonCycAssessQuery;
import com.paladin.hf.service.org.OrgUserService;
import com.paladin.hf.service.org.dto.ExcelUser;
import com.paladin.hf.service.org.dto.OrgUserQuery;
import com.paladin.hf.service.org.dto.SimpleUser;
import com.paladin.hf.service.syst.SysUserService;


/**
 * 
 * 医德医风档案管理Controller
 *
 */
@Controller
@RequestMapping("/org/user")
public class OrgUserController extends ControllerSupport{

	@Autowired
	private OrgUserService orgUserService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private AssessCycleService assessCycleService;
	
    @Autowired
    private PersonCycAssessMapper perCycAssMapper;
    
	@RequestMapping("/index")
	public String index(@RequestParam(required = false) String cached, HttpServletRequest request, Model model) {
		OrgUserQuery query = null;
		if (cached != null && cached.length() > 0) {
			HttpSession session = request.getSession();
			query = (OrgUserQuery) session.getAttribute(OrgUserQuery.class.getName());
			if (query != null) {
				model.addAttribute("query", query);
			}
		}
		return "console/organization/user_index";
	}

	@RequestMapping("/self/index")
	public String selfIndex(Model model) {

		HfUserSession session = HfUserSession.getCurrentUserSession();

		if (session.isSystemAdmin()) {
			return "no_business";
		} else {
			model.addAttribute("user", orgUserService.get(session.getUserId()));
			AssessCycle assessCycle = assessCycleService.getSelfFirstAssessCycle();
			if (assessCycle != null) {
				model.addAttribute("assessCycleId", assessCycle.getId());
				model.addAttribute("assessCycleName", assessCycle.getCycleName());
			}
		}

		return "console/organization/user_detail_index";
	}

	@RequestMapping("/search/all")
	@ResponseBody
	public Object searchAll(OrgUserQuery query, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(OrgUserQuery.class.getName(), query);
		return CommonResponse.getSuccessResponse(orgUserService.searchUserPage(query));// .searchPage(query)));
	}

	@RequestMapping("/view")
	public String view(@RequestParam(required = true) String id, Model model) {
		OrgUser user = orgUserService.get(id);
		model.addAttribute("user", user);
		AssessCycle assessCycle = assessCycleService.getUserFirstAssessCycle(user);
		if (assessCycle != null) {
			model.addAttribute("assessCycleId", assessCycle.getId());
			model.addAttribute("assessCycleName", assessCycle.getCycleName());
		}

		model.addAttribute("backurl", "/org/user/index?cached=1");
		return "console/organization/user_detail_index";
	}

	@RequestMapping("/view2")
	public String view2(@RequestParam(required = true) String id, Model model) {
		OrgUser user = orgUserService.get(id);
		model.addAttribute("user", user);
		AssessCycle assessCycle = assessCycleService.getUserFirstAssessCycle(user);
		if (assessCycle != null) {
			model.addAttribute("assessCycleId", assessCycle.getId());
			model.addAttribute("assessCycleName", assessCycle.getCycleName());
		}

		model.addAttribute("backurl", "/console/situa/index");
		return "console/organization/user_detail_index";
	}

	@RequestMapping("/viewResult")
	public String viewResult(@RequestParam(required = true) String id, Model model) {
		OrgUser user = orgUserService.get(id);
		model.addAttribute("user", user);
		AssessCycle assessCycle = assessCycleService.getUserFirstAssessCycle(user);
		if (assessCycle != null) {
			model.addAttribute("assessCycleId", assessCycle.getId());
			model.addAttribute("assessCycleName", assessCycle.getCycleName());
		}

		model.addAttribute("backurl", "/console/result/index");
		return "console/organization/user_detail_index";
	}

	@RequestMapping("/add/input")
	public String addInput(Model model) {
		model.addAttribute("user", new OrgUser());
		model.addAttribute("formType", FormType.ADD);
		return "console/organization/user_from";
	}

	@RequestMapping("/edit/input")
	public String editInput(@RequestParam(required = true) String id, Model model) {
		OrgUser orgUser = orgUserService.get(id);
		if (orgUser == null)
			orgUser = new OrgUser();
		model.addAttribute("user", orgUser);
		model.addAttribute("formType", FormType.EDIT);
		return "console/organization/user_from";
	}

	@RequestMapping("/save")
	@ResponseBody
	public Object save(@Valid OrgUser orgUser, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return this.validErrorHandler(bindingResult);
		}

		String uid = orgUser.getId();
		String identifi = orgUser.getIdentification();

		boolean isAdd = uid == null || uid.length() == 0;
		if (identifi == null || identifi.length() == 0) {
			return CommonResponse.getFailResponse("身份证号不能为空！");
		}
		List<OrgUser> sameOrgUsers = orgUserService.searchAll(new Condition("identification", QueryType.EQUAL, identifi));
		if (sameOrgUsers.size() > 0 && sameOrgUsers != null) {
			if (isAdd) {// 新增
				return CommonResponse.getFailResponse("该身份证号已存在！");
			} else {// 修改
				for (OrgUser user : sameOrgUsers) {
					if (!user.getId().equals(uid)) {
						return CommonResponse.getFailResponse("该身份证号已存在！");
					}
				}
			}
		}
		return CommonResponse.getResponse(orgUserService.saveOrUpdateUser(orgUser));
	}

	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(orgUserService.removeByPrimaryKey(id), "删除失败");
	}

	/**
	 * 
	 * 逻辑删除档案人员 物理删除账号
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/real/delete")
	@ResponseBody
	public Object realDelete(@RequestParam(required = true) String id) {
		return CommonResponse.getResponse(orgUserService.wipeByPrimaryKey(id));
	}

	/**
	 * 重置密码
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/reset/password")
	@ResponseBody
	public Object resetPassword(@RequestParam(required = true) String userId) {
		return CommonResponse.getResponse(sysUserService.resetOrgUserPassword(userId));
	}

	/**
	 * 验证账号是否可行
	 * 
	 * @param account
	 * @return
	 */
	@RequestMapping("/validate/account")
	@ResponseBody
	public Object validateAccount(@RequestParam(required = true) String account) {
		return CommonResponse.getSuccessResponse(sysUserService.validateAccount(account));
	}

	/**
	 * 查找单位下所有人员
	 * 
	 * @param unitId
	 * @return
	 */
	@RequestMapping("/find/unit")
	@ResponseBody
	public Object getUnitUser(@RequestParam(value = "unitId", required = true) String unitId) {
		List<OrgUser> users = orgUserService.findUnitUser(unitId);
		return CommonResponse.getSuccessResponse(users);

	}

	/**
	 * 查找单位下所有人员
	 * 
	 * @param unitId
	 * @return
	 */
	@RequestMapping("/find/unit/simple")
	@ResponseBody
	public Object getSimpleUnitUser(@RequestParam(value = "unitId", required = true) String unitId) {
		List<OrgUser> users = orgUserService.findUnitUser(unitId);
		List<SimpleUser> simpleUsers = new ArrayList<>(users.size());
		for (OrgUser user : users) {
			simpleUsers.add(new SimpleUser(user));
		}
		return CommonResponse.getSuccessResponse(simpleUsers);
	}

	/**
	 * 转移人员
	 * 
	 * @return
	 */
	@RequestMapping("/transfer/index")
	public String transferIndex() {
		return "console/organization/user_transfer_index";
	}

	/**
	 * 转移人员
	 * 
	 * @param userId
	 * @param target
	 * @return
	 */
	@RequestMapping("/transfer")
	@ResponseBody
	public Object transfer(@RequestParam(value = "userId[]") String[] userId, @RequestParam String target) {
		return CommonResponse.getResponse(orgUserService.transferUser(userId, target));
	}

	/**
	 * 转移人员申请
	 * 
	 * @return
	 */
	@RequestMapping("/transfer/ask/index")
	public String transferAskIndex() {
		return "console/organization/user_transfer_ask_index";
	}

	/**
	 * 转移进来人员申请
	 * 
	 * @return
	 */
	@RequestMapping("/transfer/ask/in")
	@ResponseBody
	public Object transferAskInUser() {
		return CommonResponse.getSuccessResponse(orgUserService.getTransferAskInUser());
	}

	/**
	 * 转移出去人员申请
	 * 
	 * @return
	 */
	@RequestMapping("/transfer/ask/out")
	@ResponseBody
	public Object transferAskOutUser() {
		return CommonResponse.getSuccessResponse(orgUserService.getTransferAskOutUser());
	}

	/**
	 * 转移人员申请数目
	 * 
	 * @return
	 */
	@RequestMapping("/transfer/ask/count")
	@ResponseBody
	public Object transferAskUserCount() {
		return CommonResponse.getSuccessResponse(orgUserService.getCountOfTransferAskIn());
	}

	/**
	 * 移除转移人员申请
	 * 
	 * @return
	 */
	@RequestMapping("/transfer/ask/remove")
	@ResponseBody
	public Object removeTransferAsk(@RequestParam(value = "userId[]") String[] userId) {
		return CommonResponse.getSuccessResponse(orgUserService.removeTransferAskUser(userId));
	}

	/**
	 * 转移人员
	 * 
	 * @param userId
	 * @param target
	 * @return
	 */
	@RequestMapping("/transfer/ask")
	@ResponseBody
	public Object transferAsk(@RequestParam(value = "userId[]") String[] userId, @RequestParam(value = "target") String target) {
		return CommonResponse.getResponse(orgUserService.transferAsk(userId, target));
	}

	/**
	 * 同意转移人员
	 * 
	 * @param userId
	 * @param target
	 * @return
	 */
	@RequestMapping("/transfer/ask/agree")
	@ResponseBody
	public Object transferAsk(@RequestParam(value = "userId[]") String[] userId) {
		return CommonResponse.getResponse(orgUserService.agreeTransferAsk(userId));
	}

	/**
	 * 拒绝转移人员
	 * 
	 * @param userId
	 * @param target
	 * @return
	 */
	@RequestMapping("/transfer/ask/reject")
	@ResponseBody
	public Object rejectTransferAsk(@RequestParam(value = "userId[]") String[] userId) {
		return CommonResponse.getResponse(orgUserService.rejectTransferAsk(userId));
	}

	/**
	 * 导入人员
	 * 
	 * @return
	 */
	@RequestMapping("/import")
	@ResponseBody
	public Object importUser(@RequestParam("file") MultipartFile file, @RequestParam("unitId") String unitId) {
//		try {
//			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//			ExcelReader<ExcelUser> reader = new DefaultExcelReader<ExcelUser>(ExcelUser.class, new DefaultSheet(workbook.getSheetAt(0)), 2);
//			List<ExcelUser> rows = reader.readRows();
//
//			List<OrgUser> users = new ArrayList<>(rows.size());
//			for (ExcelUser row : rows) {
//
//				OrgUser user = new OrgUser();
//				user.setAccount(row.getAccount());
//				user.setRecordCreateTime(convert(row.getRecordCreateTime()));
//				user.setName(row.getName());
//				user.setIdentification(row.getIdentification());
//
//				user.setSex(ConstantsContainer.getKeyByValue("sex-type", row.getSex()));
//				user.setBirthday(convert(row.getBirthday()));
//				user.setNation(ConstantsContainer.getKeyByValue("nation-type", row.getNation()));
//				user.setPartisan(ConstantsContainer.getKeyByValue("partisan-type", row.getPartisan()));
//				user.setJobRank(ConstantsContainer.getKeyByValue("job-rank-type", row.getJobRank()));
//				user.setJobDuties(ConstantsContainer.getKeyByValue("job-duties-type", row.getJobDuties()));
//				user.setJobLevel(ConstantsContainer.getKeyByValue("job_level", row.getJobLevel()));
//				user.setOeducation(ConstantsContainer.getKeyByValue("oeducation-type", row.getOeducation()));
//				user.setStartWorkTime(convert(row.getStartWorkTime()));
//				user.setComeUnitTime(convert(row.getComeUnitTime()));
//				user.setUserProperty(ConstantsContainer.getKeyByValue("user_property_type", row.getUserProperty()));
//				user.setResume(row.getResume());
//				user.setReward(row.getReward());
//				user.setPunish(row.getPunish());
//
//				users.add(user);
//			}
//
//			return CommonResponse.getSuccessResponse(orgUserService.importUser(users, unitId));
//		} catch (IOException | ExcelReadException e) {
//			throw new BusinessException("导入失败", e);
//		}
		return null;
		//TODO 需要修改
	}

	private java.sql.Date convert(java.util.Date date) {
		if (date == null)
			return null;
		return new java.sql.Date(date.getTime());
	}
	
	
	/**
	 * 跳往本年度周期考评列表页
	 * 
     * @author jisanjie
     * @version 2018/09/26
	 */
	@RequestMapping("/to/this/year/assess/page")
	private String toThisYearAssessPage(String orgUserId,Model model){
	      model.addAttribute("bUserId",orgUserId);
	      OrgUser user = orgUserService.get(orgUserId);
	      HfUserSession session = HfUserSession.getCurrentUserSession();
	       if (session.getAccount().equals(user.getAccount())){
	             model.addAttribute("backurl","/org/user/self/index");  
	       }else {
	             model.addAttribute("backurl","/org/user/view?id=" + orgUserId);
      }
	      return "console/organization/this_year_assess_list";
	}
	
	
	/**
	 * 获取本年度周期考评列表
	 * 
	 * @author jisanjie
	 * @version 2018/09/26
	 */
	@RequestMapping("/find/this/year/list")
	@ResponseBody
	private Object findThisYearAssessSituationList(String orgUserId,Integer myYear){
	      
	    List<PersonCycAssessExt>  list = orgUserService.findThisYearAssessSituationList(orgUserId,myYear);
	    return CommonResponse.getSuccessResponse(list);
	}
	
	
	/**
	 * 跳往人员考评登记表页
	 * 
     * @author jisanjie
     * @version 2018/09/26 
	 */
	@RequestMapping("/assess/registration/form")
	public String assessRegistrationForm(PersonCycAssessQuery perCycAssQuery, Model model){
	        PersonCycAssessExt perCycAss= perCycAssMapper.getAssessRegistrationForm(perCycAssQuery); 
	        model.addAttribute("perCycAss", perCycAss);
            return "console/organization/assess_registration_form";
	}
}
