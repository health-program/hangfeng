package com.paladin.hf.mapper.org;

import org.apache.ibatis.annotations.Param;

import com.paladin.framework.mybatis.CustomMapper;
import com.paladin.hf.model.org.OrgUser;

public interface OrgUserMapper extends CustomMapper<OrgUser> {

	public int wipeByPrimaryKey(String id);

	public int updateUserUnit(@Param("userId") String userId, @Param("agencyId") String agencyId, @Param("teamId") String teamId,
			@Param("unitId") String unitId);

	public int updateUsersUnit(@Param("userIds") String[] userIds, @Param("agencyId") String agencyId, @Param("teamId") String teamId,
			@Param("unitId") String unitId);

	public int updateUnitForTransferAsk(@Param("userId") String userId, @Param("agencyId") String agencyId, @Param("teamId") String teamId,
			@Param("unitId") String unitId);

	public int removeTransferAsk(@Param("userIds") String[] userIds);

	public int rejectTransferAsk(@Param("userIds") String[] userIds);

	public int countElseUserByIdentification(@Param("identification") String identification, @Param("userId") String userId);

	public int countUserByIdentification(@Param("identification") String identification);

	public int updateUserStatus(@Param("userId") String userId, @Param("status") int status);

	public int updateUserForClaim(@Param("userId") String userId, @Param("agencyId") String agencyId, @Param("teamId") String teamId,
			@Param("unitId") String unitId);
}
