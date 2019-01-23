package com.paladin.hf.service.statistics;

import java.io.OutputStream;
import java.util.List;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.framework.excel.write.DefaultWriteRow;
import com.paladin.framework.excel.write.ExcelWriter;
import com.paladin.framework.excel.write.WriteRow;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.mapper.statistics.EvaluationResultMapper;
import com.paladin.hf.model.assess.cycle.AssessCycle;
import com.paladin.hf.service.assess.cycle.AssessCycleService;
import com.paladin.hf.service.statistics.dto.AppraisalSummaryQueryDTO;
import com.paladin.hf.service.statistics.dto.EvaluationResultDeptExcelDTO;
import com.paladin.hf.service.statistics.dto.EvaluationResultPeopleExcelDTO;
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
	
	public PageResult<EvaluationResultVO> userAll(AppraisalSummaryQueryDTO query) {
		Page<EvaluationResultVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
		evaluationResultMapper.userAll(query, DataPermissionUtil.getUnitQueryDouble(query.getUnitId()));
		return new PageResult<>(page);
	}

	public PageResult<EvaluationResultVO> evaluationInfo(AppraisalSummaryQueryDTO query) {
        Page<EvaluationResultVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
        evaluationResultMapper.evaluationInfo(query,DataPermissionUtil.getUnitQueryDouble(query.getUnitId()));
        return new PageResult<>(page);
    }
    
    public PageResult<EvaluationResultVO> evaluationPeople(AppraisalSummaryQueryDTO query) {
        Page<EvaluationResultVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());// 分页
        evaluationResultMapper.evaluationPeople(query);
        return new PageResult<>(page);
    }
	
    
    private  static  final WriteRow evaluationResultDeptExce = DefaultWriteRow.createWriteRow(EvaluationResultDeptExcelDTO.class, null);
    
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
				
		UnitQuery unitQuery = DataPermissionUtil.getUnitQueryDouble(unitId);

		List<EvaluationResultVO> list = evaluationResultMapper.evaluationInfo(query, unitQuery);
		
		if (list == null || list.size() == 0) {
            throw new BusinessException("没有可导的数据");
        }
		
		List<EvaluationResultDeptExcelDTO> resultDept =SimpleBeanCopyUtil.simpleCopyList(list, EvaluationResultDeptExcelDTO.class);

		AssessCycle cycle = assessCycleService.get(cycleId);
		if (cycle == null) {
			throw new BusinessException("没有可导的数据");
		}
		
		String cycleName = cycle.getCycleName();
		for (EvaluationResultDeptExcelDTO r : resultDept) {
			r.setAssessCycleName(cycleName);
		}

		SXSSFWorkbook workbook = new SXSSFWorkbook();
		
		  try {
	            ExcelWriter<EvaluationResultDeptExcelDTO> writer = new ExcelWriter<>(workbook, evaluationResultDeptExce);
	            writer.openNewSheet("单位下属科室考评表");
	            writer.write(resultDept);
	            writer.output(output);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new BusinessException("导出Excel失败", e);
	        }
	}
	
private  static  final WriteRow evaluationResultPeople = DefaultWriteRow.createWriteRow(EvaluationResultPeopleExcelDTO.class, null);
	
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
                
        List<EvaluationResultPeopleExcelDTO> list = evaluationResultMapper.excelPeople(query);
        if (list == null || list.size() == 0) {
            throw new BusinessException("没有可导的数据");
        }

        AssessCycle cycle = assessCycleService.get(cycleId);
        if (cycle == null) {
            throw new BusinessException("没有可导的数据");
        }
        String cycleName = cycle.getCycleName();
        for (EvaluationResultPeopleExcelDTO r : list) {
            r.setAssessCycleName(cycleName);
        }
        
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        
          try {
                ExcelWriter<EvaluationResultPeopleExcelDTO> writer = new ExcelWriter<>(workbook, evaluationResultPeople);
                writer.openNewSheet("科室下属人员考评表");
                writer.write(list);
                writer.output(output);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException("导出Excel失败", e);
            }
    }

}
