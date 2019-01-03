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

	@ReadProperty(cellIndex = 3)
	private String identification;
	
	@ReadProperty(cellIndex = 4)
	private String sex;
	
	@ReadProperty(cellIndex = 5)
	private Date birthday;

	@ReadProperty(cellIndex = 6)
	private String nation;

	@ReadProperty(cellIndex = 7)
	private String partisan;
	
	@ReadProperty(cellIndex = 8)
	private String jobRank;
	
	@ReadProperty(cellIndex = 9)
	private String jobDuties;
	
	@ReadProperty(cellIndex = 10)
	private String jobLevel;
	
	@ReadProperty(cellIndex = 11)
	private String oeducation;
	
	@ReadProperty(cellIndex = 12)
	private Date startWorkTime;
	
	@ReadProperty(cellIndex = 13)
	private Date comeUnitTime;
	
	@ReadProperty(cellIndex = 14)
	private String userProperty;
	
	@ReadProperty(cellIndex = 15)
	private String resume;
	
	@ReadProperty(cellIndex = 16)
	private String reward;
	
	@ReadProperty(cellIndex = 17)
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

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPartisan() {
		return partisan;
	}

	public void setPartisan(String partisan) {
		this.partisan = partisan;
	}

	public String getJobRank() {
		return jobRank;
	}

	public void setJobRank(String jobRank) {
		this.jobRank = jobRank;
	}

	public String getJobDuties() {
		return jobDuties;
	}

	public void setJobDuties(String jobDuties) {
		this.jobDuties = jobDuties;
	}

	public String getJobLevel() {
		return jobLevel;
	}

	public void setJobLevel(String jobLevel) {
		this.jobLevel = jobLevel;
	}

	public String getOeducation() {
		return oeducation;
	}

	public void setOeducation(String oeducation) {
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

	public String getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(String userProperty) {
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
