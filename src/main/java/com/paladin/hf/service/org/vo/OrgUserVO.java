package com.paladin.hf.service.org.vo;

import java.util.Date;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

public class OrgUserVO {
	private String id;

	private String orgUnitId;

	private String orgAgencyId;

	private String orgAssessTeamId;

	private String name;

	private String account;

	private Date recordCreateTime;

	private Integer sex;

	private Integer oeducation;

	private Integer nation;

	private Integer partisan;

	private Date birthday;

	private Integer jobDuties;

	private Integer jobRank;

	private Date startWorkTime;

	private Date comeUnitTime;

	private String resume;

	private String reward;

	private String punish;

	private Integer isAdmin;

	private Integer isAssessor;

	private String assessRole;

	private String assessUnitId;

	private Integer jobLevel;

	private Integer userProperty;

	private String identification;
	
	private Integer identificationType;

	private String transferOriginUnitId;

	private String transferOriginTeamId;

	private String transferOriginAgencyId;

	private String transferUnitId;

	private String transferAgencyId;

	private Integer transferStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgUnitId() {
		return orgUnitId;
	}

	public void setOrgUnitId(String orgUnitId) {
		this.orgUnitId = orgUnitId;
	}

	public String getOrgAgencyId() {
		return orgAgencyId;
	}

	public void setOrgAgencyId(String orgAgencyId) {
		this.orgAgencyId = orgAgencyId;
	}

	public String getOrgAssessTeamId() {
		return orgAssessTeamId;
	}

	public void setOrgAssessTeamId(String orgAssessTeamId) {
		this.orgAssessTeamId = orgAssessTeamId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getRecordCreateTime() {
		return recordCreateTime;
	}

	public void setRecordCreateTime(Date recordCreateTime) {
		this.recordCreateTime = recordCreateTime;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Date getStartWorkTime() {
		return startWorkTime;
	}

	public void setStartWorkTime(Date startWorkTime) {
		this.startWorkTime = startWorkTime;
	}

	public Date getComeUnitTime() {
		return comeUnitTime;
	}

	public void setComeUnitTime(Date comeUnitTime) {
		this.comeUnitTime = comeUnitTime;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getPunish() {
		return punish;
	}

	public void setPunish(String punish) {
		this.punish = punish;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Integer getIsAssessor() {
		return isAssessor;
	}

	public void setIsAssessor(Integer isAssessor) {
		this.isAssessor = isAssessor;
	}

	public String getAssessRole() {
		return assessRole;
	}

	public void setAssessRole(String assessRole) {
		this.assessRole = assessRole;
	}

	public String getAssessUnitId() {
		return assessUnitId;
	}

	public void setAssessUnitId(String assessUnitId) {
		this.assessUnitId = assessUnitId;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getTransferOriginUnitId() {
		return transferOriginUnitId;
	}

	public void setTransferOriginUnitId(String transferOriginUnitId) {
		this.transferOriginUnitId = transferOriginUnitId;
	}

	public String getTransferOriginTeamId() {
		return transferOriginTeamId;
	}

	public void setTransferOriginTeamId(String transferOriginTeamId) {
		this.transferOriginTeamId = transferOriginTeamId;
	}

	public String getTransferOriginAgencyId() {
		return transferOriginAgencyId;
	}

	public void setTransferOriginAgencyId(String transferOriginAgencyId) {
		this.transferOriginAgencyId = transferOriginAgencyId;
	}

	public String getTransferUnitId() {
		return transferUnitId;
	}

	public void setTransferUnitId(String transferUnitId) {
		this.transferUnitId = transferUnitId;
	}

	public String getTransferAgencyId() {
		return transferAgencyId;
	}

	public void setTransferAgencyId(String transferAgencyId) {
		this.transferAgencyId = transferAgencyId;
	}

	public Integer getTransferStatus() {
		return transferStatus;
	}

	public void setTransferStatus(Integer transferStatus) {
		this.transferStatus = transferStatus;
	}
	
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getOeducation() {
		return oeducation;
	}

	public void setOeducation(Integer oeducation) {
		this.oeducation = oeducation;
	}

	public Integer getNation() {
		return nation;
	}

	public void setNation(Integer nation) {
		this.nation = nation;
	}

	public Integer getPartisan() {
		return partisan;
	}

	public void setPartisan(Integer partisan) {
		this.partisan = partisan;
	}

	public Integer getJobDuties() {
		return jobDuties;
	}

	public void setJobDuties(Integer jobDuties) {
		this.jobDuties = jobDuties;
	}

	public Integer getJobRank() {
		return jobRank;
	}

	public void setJobRank(Integer jobRank) {
		this.jobRank = jobRank;
	}

	public Integer getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(Integer jobLevel) {
		this.jobLevel = jobLevel;
	}

	public Integer getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(Integer userProperty) {
		this.userProperty = userProperty;
	}

	public Integer getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(Integer identificationType) {
		this.identificationType = identificationType;
	}
	
	/*
	 * 扩展显示用字段
	 */

	public String getUnitName() {
		try {
			return UnitContainer.getUnitName(orgUnitId);
		} catch (BusinessException e) {
			return "";
		}
	}

	public String getUnitRootName() {
		try {
			return UnitContainer.getRootUnitName(orgUnitId);
		} catch (BusinessException e) {
			return "";
		}
	}

	public String getAssessUnitName() {
		if (assessUnitId != null && assessUnitId.length() != 0) {
			try {
				return UnitContainer.getUnitName(assessUnitId);
			} catch (BusinessException e) {
				return "";
			}
		}
		return null;
	}

	public String getTransferOriginUnitName() {
		if (transferStatus != null) {
			try {
				return UnitContainer.getUnit(transferOriginUnitId).toString();
			} catch (BusinessException e) {
				return "";
			}
		}
		return null;
	}

	public String getTransferUnitName() {
		if (transferStatus != null) {
			try {
				return UnitContainer.getUnit(transferUnitId).toString();
			} catch (BusinessException e) {
				return "";
			}
		}
		return null;
	}


}
