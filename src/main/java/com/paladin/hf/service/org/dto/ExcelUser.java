package com.paladin.hf.service.org.dto;

import java.util.Date;

import com.paladin.framework.excel.read.ReadProperty;

public class ExcelUser {
	
	@ReadProperty(cellIndex = 0)
	private String account;
	
	@ReadProperty(cellIndex = 1)
	private Date recordCreateTime;
	
	@ReadProperty(cellIndex = 2)
	private String name;

	@ReadProperty(cellIndex = 3, enumType="certificate-type")
	private Integer identificationType;
	
	@ReadProperty(cellIndex = 4)
	private String identification;
	
	@ReadProperty(cellIndex = 5 , enumType="sex-type")
	private Integer sex;
	
	@ReadProperty(cellIndex = 6)
	private Date birthday;

	@ReadProperty(cellIndex = 7, enumType="nation-type")
	private Integer nation;

	@ReadProperty(cellIndex = 8, enumType="partisan-type")
	private Integer partisan;
	
	@ReadProperty(cellIndex = 9, enumType="job-rank-type")
	private Integer jobRank;
	
	@ReadProperty(cellIndex = 10, enumType="job-duties-type")
	private Integer jobDuties;
	
	@ReadProperty(cellIndex = 11, enumType="job_level")
	private Integer jobLevel;
	
	@ReadProperty(cellIndex = 12, enumType="oeducation-type")
	private Integer oeducation;
	
	@ReadProperty(cellIndex = 13)
	private Date startWorkTime;
	
	@ReadProperty(cellIndex = 14)
	private Date comeUnitTime;
	
	@ReadProperty(cellIndex = 15, enumType="user_property_type")
	private Integer userProperty;
	
	@ReadProperty(cellIndex = 16)
	private String resume;
	
	@ReadProperty(cellIndex = 17)
	private String reward;
	
	@ReadProperty(cellIndex = 18)
	private String punish;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIdentificationType() {
		return identificationType;
	}

	public void setIdentificationType(Integer identificationType) {
		this.identificationType = identificationType;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
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

	public Integer getJobRank() {
		return jobRank;
	}

	public void setJobRank(Integer jobRank) {
		this.jobRank = jobRank;
	}

	public Integer getJobDuties() {
		return jobDuties;
	}

	public void setJobDuties(Integer jobDuties) {
		this.jobDuties = jobDuties;
	}

	public Integer getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(Integer jobLevel) {
		this.jobLevel = jobLevel;
	}

	public Integer getOeducation() {
		return oeducation;
	}

	public void setOeducation(Integer oeducation) {
		this.oeducation = oeducation;
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

	public Integer getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(Integer userProperty) {
		this.userProperty = userProperty;
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

	
}
