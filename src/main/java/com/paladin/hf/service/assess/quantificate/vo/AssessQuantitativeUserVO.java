package com.paladin.hf.service.assess.quantificate.vo;

import java.util.Date;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

public class AssessQuantitativeUserVO {
	
	private String userId;
	
	private String userName;
	
	private Integer sex;
	
	private Integer isAssessed;
	
	private Integer jobDuties;
	
	private Integer jobRank;
	
	private Integer oeducation;

	private String unitId;

	private String agencyId;
	
	private Date recordCreateTime;
	
	private Integer isAssessor;
	
	private Double baseScore;
	
	private Double addScore;
	
	private Double reduceScore;
	
	private Integer isVeto;
	
	public String getDepartmentName() {
		try {
			return UnitContainer.getUnitName(unitId);
		} catch (BusinessException e) {
			return "";
		}
	}

	public String getAgencyName() {
		try {
			return UnitContainer.getUnitName(agencyId);
		} catch (BusinessException e) {
			return "";
		}
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getIsAssessed() {
		return isAssessed;
	}

	public void setIsAssessed(Integer isAssessed) {
		this.isAssessed = isAssessed;
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

	public Integer getOeducation() {
		return oeducation;
	}

	public void setOeducation(Integer oeducation) {
		this.oeducation = oeducation;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public Date getRecordCreateTime() {
		return recordCreateTime;
	}

	public void setRecordCreateTime(Date recordCreateTime) {
		this.recordCreateTime = recordCreateTime;
	}

	public Integer getIsAssessor() {
		return isAssessor;
	}

	public void setIsAssessor(Integer isAssessor) {
		this.isAssessor = isAssessor;
	}

	public Double getBaseScore() {
		return baseScore;
	}

	public void setBaseScore(Double baseScore) {
		this.baseScore = baseScore;
	}

	public Double getAddScore() {
		return addScore;
	}

	public void setAddScore(Double addScore) {
		this.addScore = addScore;
	}

	public Double getReduceScore() {
		return reduceScore;
	}

	public void setReduceScore(Double reduceScore) {
		this.reduceScore = reduceScore;
	}

	public Integer getIsVeto() {
		return isVeto;
	}

	public void setIsVeto(Integer isVeto) {
		this.isVeto = isVeto;
	}
}
