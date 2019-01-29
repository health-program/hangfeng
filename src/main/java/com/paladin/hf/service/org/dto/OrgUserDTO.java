package com.paladin.hf.service.org.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class OrgUserDTO {
	private String id;

	@NotEmpty(message = "账号不能为空")
	private String account;

	private String orgUnitId;
	
	@NotEmpty(message = "姓名不能为空")
	private String name;

	private Date recordCreateTime;

	@NotNull(message = "性别不能为空")
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

	private Integer isAssessor;

	private String assessUnitId;

	private Integer jobLevel;

	private Integer userProperty;

	@NotEmpty(message = "身份证件号码不能为空")
	private String identification;

	@NotNull(message = "身份证类型不能为空")
	private Integer identificationType;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getOrgUnitId() {
		return orgUnitId;
	}

	public void setOrgUnitId(String orgUnitId) {
		this.orgUnitId = orgUnitId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getRecordCreateTime() {
		return recordCreateTime;
	}

	public void setRecordCreateTime(Date recordCreateTime) {
		this.recordCreateTime = recordCreateTime;
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

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public Integer getIsAssessor() {
		return isAssessor;
	}

	public void setIsAssessor(Integer isAssessor) {
		this.isAssessor = isAssessor;
	}

	public String getAssessUnitId() {
		return assessUnitId;
	}

	public void setAssessUnitId(String assessUnitId) {
		this.assessUnitId = assessUnitId;
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

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public Integer getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(Integer identificationType) {
		this.identificationType = identificationType;
	}

}
