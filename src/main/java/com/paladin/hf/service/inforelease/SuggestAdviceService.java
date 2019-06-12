package com.paladin.hf.service.inforelease;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paladin.framework.common.PageResult;
import com.paladin.framework.core.ServiceSupport;
import com.paladin.framework.core.copy.SimpleBeanCopier.SimpleBeanCopyUtil;
import com.paladin.hf.core.HfUserSession;
import com.paladin.hf.mapper.inforelease.SuggestAdviceMapper;
import com.paladin.hf.model.inforelease.SuggestAdvice;
import com.paladin.hf.service.inforelease.dto.SuggestAdviceQuery;
import com.paladin.hf.service.inforelease.vo.SuggestAdviceVO;

/**   
 * @author 黄伟华
 * @version 2019年5月22日 下午1:10:25 
 */
@Service
public class SuggestAdviceService extends ServiceSupport<SuggestAdvice> {

    @SuppressWarnings("unused")
	@Autowired
    private SuggestAdviceMapper suggestAdviceMapper;

    public PageResult<SuggestAdviceVO> findSuggestAdvice(SuggestAdviceQuery query) {
	HfUserSession userSession = HfUserSession.getCurrentUserSession();
	if (!userSession.isAdminRoleLevel()) {
	    query.setCreateUserId(userSession.getUserId());
	}
	return searchPage(query).convert(SuggestAdviceVO.class);
    }

    public SuggestAdviceVO detail(String id) {
	SuggestAdvice info = get(id);
	if (info != null) {
	    SuggestAdviceVO vo = new SuggestAdviceVO();
	    SimpleBeanCopyUtil.simpleCopy(info, vo);
	    return vo;
	}
	return null;
    }
}
