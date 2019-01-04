package com.paladin.hf.service.statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.paladin.framework.common.OffsetPage;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.hf.mapper.statistics.EvaluationDetailedMapper;
import com.paladin.hf.service.statistics.dto.PersoncycassessQueryDTO;
import com.paladin.hf.service.statistics.vo.PersoncycassessDetaileVO;


/**   
 * @author 黄伟华
 * @version 2018年2月28日 上午9:07:32 
 */
@Service
public class EvaluationDetailedService extends ServiceSupport<PersoncycassessDetaileVO>
{
    @Autowired
    private EvaluationDetailedMapper evaluationDetailedMapper;
    
    public Page<PersoncycassessDetaileVO> personcycassessDetaileds(PersoncycassessQueryDTO query){
        Page<PersoncycassessDetaileVO> page = PageHelper.offsetPage(query.getOffset(), query.getLimit());//分页
        evaluationDetailedMapper.personcycassessDetaileds(query);
        return page;
    }
}
