package com.paladin.hf.controller.ordinary;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.core.UserSession;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.model.ordinary.Prizepunish;
import com.paladin.hf.service.ordinary.PrizepunishService;
import com.paladin.hf.service.org.OrgUserService;
import com.paladin.hf.service.org.dto.OrgUserQuery;

/**   
 * @author 黄伟华
 * @version 2018年1月19日 上午10:17:09 
 */
@Controller
@RequestMapping("/console/prize")
public class PrizePunishDeptController extends ControllerSupport
{
	// TODO
//    @Autowired
//    private PrizepunishService prizepunishService;
//    
//    @Autowired
//    private OrgUserService orgUserService;
//    
//    @Autowired
//    private ImageService imageService;
//    
//    @RequestMapping(value = "/index", method = { RequestMethod.GET })
//    public String index(@RequestParam(required = false) String cached, HttpServletRequest request, Model model) {
//		OrgUserQuery query = null;
//		if (cached != null && cached.length() > 0) {
//			HttpSession session = request.getSession();
//			query = (OrgUserQuery) session.getAttribute(OrgUserQuery.class.getName());
//			if (query != null) {
//				model.addAttribute("query", query);
//			}
//		}		
//        return "console/peacedepartment/prize_index";
//    }
//    
//    
//    @RequestMapping("/search/all")
//    @ResponseBody
//    public Object searchAll(OrgUserQuery query) {
//        
//       // query.setUnitId(UserSession.getCurrentUserSession().getUserDepartment().getId());
//        return CommonResponse.getSuccessResponse(new PageResult(orgUserService.searchUserPage(query)));
//    }
//    
//    /**
//     * 审核页面跳转
//     * <一句话功能简述>
//     * <功能详细描述>
//     * @param id
//     * @param model
//     * @return
//     * @see [类、类#方法、类#成员]
//     */
//    @RequestMapping("/view")
//    public String view(@RequestParam(required = true) String id, Model model,HttpServletRequest request) {
//        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/app/";    
//        Prizepunish prizepunish= prizepunishService.get(id);
//        List<Images> images=imageService.imagesId(id);
//        String typeName = null;
//        if(prizepunish == null)
//            prizepunish = new Prizepunish(); 
//        if(images.size()>0){
//             typeName=images.get(0).getTypeName();
//        }
//        model.addAttribute("basePath", basePath);
//        model.addAttribute("prize", prizepunish);
//        model.addAttribute("images", images);
//        model.addAttribute("imageType",typeName);
//        return "console/peacedepartment/prize_from"; 
//    }
    
//    /**
//     * 科室审核操作
//     * <一句话功能简述>
//     * <功能详细描述>
//     * @param prizepunish
//     * @param bindingResult
//     * @return
//     * @see [类、类#方法、类#成员]
//     */
//    @RequestMapping("/update")
//    @ResponseBody
//    public Object update(@Valid Prizepunish prizepunish, BindingResult bindingResult) {   
//        if(bindingResult.hasErrors()) {
//            return this.validErrorHandler(bindingResult);
//        }
//        String name=UserSession.getCurrentUserSession().getUserName();
//        if(prizepunish.getExamineState().equals("0")){
//            prizepunish.setOperationState("1");
//        }
//        if(prizepunish.getExamineState().equals("1") || prizepunish.getExamineState().equals("2")){
//            prizepunish.setExaminePeople(name);
//            prizepunish.setOperationState("4");
//        }
//        return CommonResponse.getResponse(prizepunishService.updateSelective(prizepunish));
//    }
    
}
