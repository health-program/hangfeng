package com.paladin.hf.controller.org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.service.org.OrgUserService;
import com.paladin.hf.service.org.dto.OrgUserClaimQuery;

/**
 * 
 * 医德医风档案管理Controller
 *
 */
@Controller
@RequestMapping("/org/user/claim")
public class OrgUserClaimController extends ControllerSupport {

	@Autowired
	private OrgUserService orgUserService;

	/**
	 * 人员认领页面
	 * @return
	 */
	@RequestMapping("/index")
	public Object claimIndex() {		
		return "/hf/org/user_claim";
	}
	
	/**
	 * 人员认领
	 * @param id
	 * @return
	 */
	@RequestMapping("/find")
	@ResponseBody
	public Object claimFind(OrgUserClaimQuery query) {		
		return CommonResponse.getSuccessResponse(orgUserService.findUserForClaim(query));		
	}
	
	/**
	 * 人员认领
	 * @param id
	 * @return
	 */
	@RequestMapping("/")
	@ResponseBody
	public Object claim(@RequestParam(value = "userId[]") String[] userId) {		
		return CommonResponse.getResponse(orgUserService.claim(userId));		
	}

}
