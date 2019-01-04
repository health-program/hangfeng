package com.paladin.hf.mapper.statistics;

import java.util.List;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.service.statistics.dto.PersoncycassessQueryDTO;
import com.paladin.hf.service.statistics.vo.PersoncycassessDetaileVO;

/**   
 * @author 黄伟华
 * @version 2018年2月28日 上午9:07:57 
 */
public interface EvaluationDetailedMapper extends CustomMapper<PersoncycassessDetaileVO>
{
    List<PersoncycassessDetaileVO> personcycassessDetaileds(PersoncycassessQueryDTO query);
}
