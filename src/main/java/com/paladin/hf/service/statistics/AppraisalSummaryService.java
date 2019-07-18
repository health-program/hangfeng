package com.paladin.hf.service.statistics;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.util.StringUtil;
import com.paladin.hf.core.DataPermissionUtil;
import com.paladin.hf.mapper.statistics.AppraisalSummaryMapper;
import com.paladin.hf.service.statistics.dto.AppraisalSummaryQueryDTO;
import com.paladin.hf.service.statistics.dto.StatisticsAnalysisQueryDTO;
import com.paladin.hf.service.statistics.vo.AppraisalSummaryVO;
import com.paladin.hf.service.statistics.vo.OrgUnitPeopleTotalVO;
import com.paladin.hf.service.statistics.vo.StatisticsAnalysisVO;


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
	 
	 public List<OrgUnitPeopleTotalVO> peopleTotal(){
		 return appraisalSummaryMapper.peopleTotal();
	 }
	 
	 public List<StatisticsAnalysisVO> findEcahtes(StatisticsAnalysisQueryDTO query){
	     if(StringUtil.isNotEmpty(query.getSelfAssTime())){
		 DateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
		 
		 String startTime =query.getSelfAssTime()+"-02-01";
		 int yaer = Integer.parseInt(query.getSelfAssTime())+1;
		 String endTime = String.valueOf(yaer)+"-02-28";
		 
		 try {
		    query.setStartTime(format.parse(startTime));
		    query.setEndTime(format.parse(endTime));
		} catch (ParseException e) {
		    e.printStackTrace();
		}
	     }
	     return appraisalSummaryMapper.findEcahtes(query);
	 }
 
}
