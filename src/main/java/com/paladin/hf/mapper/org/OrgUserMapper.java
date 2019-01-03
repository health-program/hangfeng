package com.paladin.hf.mapper.org;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.org.OrgUser;

public interface OrgUserMapper extends CustomMapper<OrgUser> {

	int wipeByPrimaryKey(String id);

	int updateUserUnit(@Param("userId") String userId, @Param("agencyId") String agencyId, @Param("teamId") String teamId, @Param("unitId") String unitId);

	int updateUsersUnit(@Param("userIds") String[] userIds, @Param("agencyId") String agencyId, @Param("teamId") String teamId, @Param("unitId") String unitId);

	int updateUnitForTransferAsk(@Param("userId") String userId, @Param("agencyId") String agencyId, @Param("teamId") String teamId,
			@Param("unitId") String unitId);

	int removeTransferAsk(@Param("userIds") String[] userIds);

	int rejectTransferAsk(@Param("userIds") String[] userIds);

	int countElseUserByIdentification(@Param("identification") String identification, @Param("userId") String userId);

	int countUserByIdentification(@Param("identification") String identification);
}
