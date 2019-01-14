package com.paladin.hf.controller.assess.cycle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.model.assess.cycle.PersonCycAssess;
import com.paladin.hf.model.ordinary.Prizepunish;
import com.paladin.hf.model.org.OrgUser;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.assess.cycle.PersonCycAssessService;
import com.paladin.hf.service.assess.cycle.pojo.PersonCycAssessQuery;
import com.paladin.hf.service.assess.quantificate.AssessCycleTemplateService;
import com.paladin.hf.service.org.OrgUserService;

/**
 * 
 * 周期考评-科室Controller
 * 
 * @author jisanjie
 * @version 2018年2月2日 上午10:07:54
 * 
 */

@Controller
@RequestMapping("/depart/cyc")
public class DepartCycleAssessController extends ControllerSupport {
	@Autowired
	private PersonCycAssessService perCycAssService;

	@Autowired
	private AssessCycleService assessCycleService;

	@Autowired
	private AssessCycleTemplateService assessCycleTemplateService;

	@Autowired
	private OrgUserService orgUserService;

	/**
	 * 跳转首页
	 * 
	 * @return String
	 */
	@RequestMapping("/index")
	public String index(@RequestParam(required = false) String cached, HttpServletRequest request, Model model) {
		PersonCycAssessQuery query = null;
		if (cached != null && cached.length() > 0) {
			HttpSession session = request.getSession();
			query = (PersonCycAssessQuery) session.getAttribute(PersonCycAssessQuery.class.getName());
			if (query != null) {
				model.addAttribute("query", query);
			}
		}

		AssessCycle assessCycle = null;

		if (query != null) {
			String assessCycleId = query.getAssessCycleId();
			if (assessCycleId != null && assessCycleId.length() > 0) {
				assessCycle = assessCycleService.get(assessCycleId);
			}
		}

		if (assessCycle == null) {
			assessCycle = assessCycleService.getOwnedFirstAssessCycle();
		}

		if (assessCycle != null) {
			model.addAttribute("assessCycleId", assessCycle.getId());
			model.addAttribute("assessCycleName", assessCycle.getCycleName());
		}
		return "console/departCyc/departcyc_index";
	}

	/**
	 * 
	 * 加载首页数据及条件查询
	 * 
	 * @return Object
	 */
	@RequestMapping("/find/list")
	@ResponseBody
	public Object findAllByOperate(PersonCycAssessQuery personCycAssessQuery, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute(PersonCycAssessQuery.class.getName(), personCycAssessQuery);
		String paramState = personCycAssessQuery.getOperateState();
		if (paramState == null) {// 加载首页默认传入全部考评状态
			personCycAssessQuery
					.setOperateStates(new String[] { PersonCycAssess.ASSESSED_SUBMIT, PersonCycAssess.DEPART_TEMPORARY, PersonCycAssess.DEPART_SUBMIT,
							PersonCycAssess.UNIT_GROUP_TEMPORARY, PersonCycAssess.UNIT_GROUP_SUBMIT, PersonCycAssess.ASSESSSED_CONFIRMED });
		} else if (paramState.equals("1")) {// 待考评
			personCycAssessQuery.setOperateStates(new String[] { "1" });
		} else if (paramState.equals("2")) {// 暂存
			personCycAssessQuery.setOperateStates(new String[] { "2" });
		} else if (paramState.equals("3")) {// 代表所有已考评的状态
			personCycAssessQuery.setOperateStates(new String[] { "3", "4", "5", "6" });
		}
		return CommonResponse.getSuccessResponse(perCycAssService.searchDepartmentCycAssessPage(personCycAssessQuery));
	}

	/**
	 * 
	 * 跳往科室考评页面
	 * 
	 * @param model
	 * @return
	 *
	 */
	@RequestMapping("/edit")
	public String editInput(@RequestParam(required = true) String id, Model model) {
		PersonCycAssess perCycAss = perCycAssService.get(id);
		if (perCycAss == null)
			perCycAss = new PersonCycAssess();
		// 获取对应的考评周期信息
		AssessCycle assessCycle = assessCycleService.get(perCycAss.getAssessCycleId());
		// 根据人员id查询人员信息
		OrgUser orgUser = orgUserService.get(perCycAss.getOrgUserId());
		if (orgUser == null)
			orgUser = new OrgUser();
		// 获取当前登录人的姓名
		HfUserSession session = HfUserSession.getCurrentUserSession();
		String loginName = session.getUserName();
		model.addAttribute("loginName", loginName);
		model.addAttribute("user", orgUser);
		model.addAttribute("perCycAss", perCycAss);
		model.addAttribute("assessCycle", assessCycle);
		return "console/departCyc/departcyc_confirm";
	}

	/**
	 * 
	 * 科室考评
	 * 
	 * @param perCycAss
	 * @param status
	 * @return Object
	 * 
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Object savesss(PersonCycAssess perCycAss, @RequestParam("status") String status) {
		if (status.equals("1")) {
			// 保存按钮
			perCycAss.setOperateState(PersonCycAssess.DEPART_TEMPORARY);
		} else if (status.equals("2")) {
			// 提交按钮
			perCycAss.setOperateState(PersonCycAssess.DEPART_SUBMIT);
		}
		return CommonResponse.getResponse(perCycAssService.saveOrUpdate(perCycAss));
	}

	/**
	 * 
	 * 查看
	 * 
	 * @param id
	 * @return String
	 * 
	 */
	@RequestMapping("/view")
	public String viewDepartAssessInfo(@RequestParam(required = true) String id, @RequestParam(required = true) String userId, Model model) {
		// 根据选中数据的id查询个人周期考评数据
		PersonCycAssess personCycAssess = perCycAssService.get(id);
		String templateId = assessCycleTemplateService.getTemplateByCycle(personCycAssess.getAssessCycleId(), personCycAssess.getUnitId());

		// 根据人员id查询人员信息
		OrgUser orgUser = orgUserService.get(personCycAssess.getOrgUserId());
		if (orgUser == null)
			orgUser = new OrgUser();
		model.addAttribute("user", orgUser);
		model.addAttribute("templateId", templateId);
		model.addAttribute("personCycAssess", personCycAssess);
		return "console/departCyc/departcyc_base";
	}

	/**
	 * 
	 * 切换到考评情况页面
	 * 
	 * @param id
	 * @param model
	 * @return String
	 * 
	 */
	@RequestMapping("/assess/situation")
	public String situation(@RequestParam(required = true) String id, Model model) {
		PersonCycAssess perCycAss = perCycAssService.get(id);
		if (perCycAss == null)
			perCycAss = new PersonCycAssess();
		AssessCycle assessCycle = assessCycleService.get(perCycAss.getAssessCycleId());
		model.addAttribute("assessCycle", assessCycle);
		model.addAttribute("perCycAss", perCycAss);
		return "console/departCyc/departcyc_from";
	}

	/**
	 * 
	 * 切换到奖惩事件查看页面
	 * 
	 * @param model
	 * @param prizepunish
	 * @param state
	 * @return String
	 * 
	 */
	@RequestMapping(value = "reward/index", method = { RequestMethod.GET })
	public String reward(Model model, Prizepunish prizepunish, String state) {
		model.addAttribute("orgUserId", prizepunish.getOrgUserId());
		return "console/departCyc/reward_punish_index";
	}

	/**
	 * 跳转未考评
	 * @author jisanjie
	 */
	@RequestMapping(value = "/to/no/assessment", method = { RequestMethod.GET })
	public String toNoAssessment(String assessCycleId, Model model) {
		model.addAttribute("assessCycleId", assessCycleId);
		return "console/departCyc/depart_cyc_no_assessment";
	}

	/**
	 * 未考评
	 * @author jisanjie
	 */
	@RequestMapping(value = "/no/assessment")
	@ResponseBody
	public Object noAgencyAssessment(PersonCycAssessQuery personCycAssessQuery) {
		return CommonResponse.getSuccessResponse(perCycAssService.noAssessment(personCycAssessQuery, false));
	}

}
