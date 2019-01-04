package com.paladin.hf.controller.statistics;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.statistics.EvaluationResultService;
import com.paladin.hf.service.statistics.dto.AppraisalSummaryQueryDTO;


/**
 * 考评结果查看
 * 
 * @author 黄伟华
 * @version 2018年3月5日 下午4:12:30
 */
@Controller
@RequestMapping("/console/result")
public class EvaluationResultController {
	@Autowired
	private EvaluationResultService evaluationResultService;

	@Autowired
	private AssessCycleService assessCycleService;

	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index(Model model) {
		AssessCycle assessCycle = assessCycleService.getOwnedFirstAssessCycle();
		if (assessCycle != null) {
			model.addAttribute("assessCycleId", assessCycle.getId());
			model.addAttribute("assessCycleName", assessCycle.getCycleName());
		}
		return "console/reportanalysis/evaluation_result_index";
	}

	@RequestMapping("/search/all")
	@ResponseBody
	public Object searchAll(AppraisalSummaryQueryDTO query) {
		return CommonResponse.getSuccessResponse(new PageResult(evaluationResultService.userAll(query)));
	}
	
	@RequestMapping("/evaluation/count")
    public String evaluationCount(Model model,AppraisalSummaryQueryDTO query) {
	    model.addAttribute("unitId", query.getUnitId());
        model.addAttribute("assessCycleId", query.getAssessCycleId());
        return "console/reportanalysis/evaluation_result_info";
    }
	
	@RequestMapping("/evaluation/info")
    @ResponseBody
    public Object evaluationInfo(AppraisalSummaryQueryDTO query) {
        return CommonResponse.getSuccessResponse(new PageResult(evaluationResultService.evaluationInfo(query)));
    }
	
	@RequestMapping("/evaluation/people")
    @ResponseBody
    public Object evaluationPeople(AppraisalSummaryQueryDTO query) {
        return CommonResponse.getSuccessResponse(new PageResult(evaluationResultService.evaluationPeople(query)));
    }

	@RequestMapping("/export")
	public void export(HttpServletResponse response, AppraisalSummaryQueryDTO query) {
		try {
			// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
			response.setContentType("multipart/form-data");
			// 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
			response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("单位下属科室考评情况.xlsx", "UTF-8"));
			evaluationResultService.export(response.getOutputStream(), query);
		} catch (Exception e) {
			response.reset();
			if (e instanceof BusinessException) {
				throw (BusinessException) e;
			} else {
				throw new BusinessException("导出EXCEL失败", e);
			}
		}
	}

	   @RequestMapping("/exportPeople")
	    public void exportPeople(HttpServletResponse response, AppraisalSummaryQueryDTO query) {
	        try {
	            // 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
	            response.setContentType("multipart/form-data");
	            // 2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
	            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("科室下属人员考评情况.xlsx", "UTF-8"));
	            evaluationResultService.exportPeople(response.getOutputStream(), query);
	        } catch (Exception e) {
	            response.reset();
	            if (e instanceof BusinessException) {
	                throw (BusinessException) e;
	            } else {
	                throw new BusinessException("导出EXCEL失败", e);
	            }
	        }
	    }
	
	
}
