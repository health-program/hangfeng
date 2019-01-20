package com.paladin.hf.service.statistics;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.mapper.statistics.AppraisalSummaryMapper;
import com.paladin.hf.service.statistics.dto.AppraisalSummaryQueryDTO;
import com.paladin.hf.service.statistics.vo.AppraisalSummaryVO;


/**   
 * @author 黄伟华
 * @version 2018年2月7日 下午2:52:08 
 */
@Service
public class AppraisalSummaryService
{
	@Autowired
	 private AppraisalSummaryMapper appraisalSummaryMapper;
	 
	 public List<AppraisalSummaryVO> AppraisalSummaryAll(AppraisalSummaryQueryDTO appraisalQuery){
	    return appraisalSummaryMapper.AppraisalSummaryAll(appraisalQuery, DataPermissionUtil.getUnitQueryDouble(appraisalQuery.getUnitId()));
	 };
 
}
