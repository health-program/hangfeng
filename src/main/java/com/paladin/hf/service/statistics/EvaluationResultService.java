package com.paladin.hf.service.statistics;

import java.io.OutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.UserSession;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.excel.write.ExcelWriter;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.mapper.statistics.EvaluationResultMapper;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.statistics.dto.AppraisalSummaryQueryDTO;
import com.paladin.hf.service.statistics.dto.EvaluationResultExcelDTO;
import com.paladin.hf.service.statistics.vo.EvaluationResultVO;


/**
 * @author 黄伟华
 * @version 2018年3月7日 下午2:12:36
 */
@Service
public class EvaluationResultService {
	@Autowired
	private EvaluationResultMapper evaluationResultMapper;
	@Autowired
	private AssessCycleService assessCycleService;

	public Page<EvaluationResultVO> userAll(AppraisalSummaryQueryDTO query) {
		Page<EvaluationResultVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
		evaluationResultMapper.userAll(query, ServiceSupport.getUnitQueryDouble(query.getUnitId()));
		return page;
	};

	public Page<EvaluationResultVO> evaluationInfo(AppraisalSummaryQueryDTO query) {
        Page<EvaluationResultVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
        evaluationResultMapper.evaluationInfo(query,ServiceSupport.getUnitQueryDouble(query.getUnitId()));
        return page;
    };
    
    public Page<EvaluationResultVO> evaluationPeople(AppraisalSummaryQueryDTO query) {
        Page<EvaluationResultVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
        evaluationResultMapper.evaluationPeople(query);
        return page;
    };
    
    
    
    
    
	
	public void export(OutputStream output, AppraisalSummaryQueryDTO query) {
		String cycleId = query.getAssessCycleId();
		String unitId = query.getUnitId();
		
		if(cycleId == null || cycleId.length() == 0) {
			throw new BusinessException("请选择一个考评周期");
		}
		
		if(unitId == null || unitId.length() == 0) {			
			HfUserSession session = HfUserSession.getCurrentUserSession();
			if(session.isAdminRoleLevel()) {
				throw new BusinessException("导出的数据量可能很大，请选择一个机构或科室");
			}
		}
				
		UnitQuery unitQuery = ServiceSupport.getUnitQueryDouble(unitId);

		List<EvaluationResultVO> list = evaluationResultMapper.evaluationInfo(query, unitQuery);
		if (list == null || list.size() == 0) {
			throw new BusinessException("没有可导的数据");
		}

		AssessCycle cycle = assessCycleService.get(cycleId);
		if (cycle == null) {
			throw new BusinessException("没有可导的数据");
		}
		String cycleName = cycle.getCycleName();
		for (EvaluationResultVO r : list) {
			r.setAssessCycleName(cycleName);
		}

		try {
			Workbook workbook = new XSSFWorkbook();
			ExcelWriter<EvaluationResultVO> writer = new DefaultExcelWriter<EvaluationResultVO>(workbook, EvaluationResultVO.class);
			writer.writeTitle();
			writer.write(list);
			writer.output(output);
		} catch (Exception e) {
			throw new BusinessException("导出EXCEL文件出错", e);
		}
	};
	
   public void exportPeople(OutputStream output, AppraisalSummaryQueryDTO query) {
        String cycleId = query.getAssessCycleId();
        String unitId = query.getUnitId();
        
        if(cycleId == null || cycleId.length() == 0) {
            throw new BusinessException("请选择一个考评周期");
        }
        
        if(unitId == null || unitId.length() == 0) {            
            HfUserSession session = HfUserSession.getCurrentUserSession();
            if(session.isAdminRoleLevel()) {
                throw new BusinessException("导出的数据量可能很大，请选择一个机构或科室");
            }
        }
                
        List<EvaluationResultExcelDTO> list = evaluationResultMapper.excelPeople(query);
        if (list == null || list.size() == 0) {
            throw new BusinessException("没有可导的数据");
        }

        AssessCycle cycle = assessCycleService.get(cycleId);
        if (cycle == null) {
            throw new BusinessException("没有可导的数据");
        }
        String cycleName = cycle.getCycleName();
        for (EvaluationResultExcelDTO r : list) {
            r.setAssessCycleName(cycleName);
        }

        try {
            Workbook workbook = new XSSFWorkbook();
            ExcelWriter<EvaluationResultExcelDTO> writer = new DefaultExcelWriter<EvaluationResultExcelDTO>(workbook, EvaluationResultExcelDTO.class);
            writer.writeTitle();
            writer.write(list);
            writer.output(output);
        } catch (Exception e) {
            throw new BusinessException("导出EXCEL文件出错", e);
        }
    };

}