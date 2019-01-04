package com.paladin.hf.mapper.statistics;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paladin.hf.core.DataPermissionUtil.UnitQuery;
import com.paladin.hf.service.statistics.dto.AppraisalSummaryQueryDTO;
import com.paladin.hf.service.statistics.vo.AppraisalSummaryVO;


/**   
 * @author 黄伟华
 * @version 2018年2月7日 下午2:46:30 
 */
public interface AppraisalSummaryMapper
{
    List<AppraisalSummaryVO> AppraisalSummaryAll(@Param("param") AppraisalSummaryQueryDTO appraisalQuery, @Param("unitParam")UnitQuery unitQuery);
}
