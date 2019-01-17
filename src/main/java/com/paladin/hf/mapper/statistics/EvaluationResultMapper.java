package com.paladin.hf.mapper.statistics;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.service.statistics.dto.AppraisalSummaryQueryDTO;
import com.paladin.hf.service.statistics.dto.EvaluationResultDeptExcelDTO;
import com.paladin.hf.service.statistics.dto.EvaluationResultPeopleExcelDTO;
import com.paladin.hf.service.statistics.vo.EvaluationResultVO;

/**
 * @author 黄伟华
 * @version 2018年3月7日 下午2:12:09
 */
public interface EvaluationResultMapper extends CustomMapper<EvaluationResultVO>
{
    List<EvaluationResultVO> userAll(@Param("param")AppraisalSummaryQueryDTO query, @Param("unitParam")UnitQuery unitQuery);
    
    List<EvaluationResultVO> evaluationInfo(@Param("param")AppraisalSummaryQueryDTO query, @Param("unitParam")UnitQuery unitQuery);
    
    List<EvaluationResultVO> evaluationPeople(AppraisalSummaryQueryDTO query);
    
    List<EvaluationResultPeopleExcelDTO> excelPeople(AppraisalSummaryQueryDTO query);
    
    List<EvaluationResultDeptExcelDTO> excelDept(AppraisalSummaryQueryDTO query);
}
