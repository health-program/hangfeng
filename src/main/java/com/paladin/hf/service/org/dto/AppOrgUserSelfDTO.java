package com.paladin.hf.service.org.dto;

import java.util.Date;

/**   
 * @author 黄伟华
 * @version 2019年1月30日 下午1:49:49 
 */
public class AppOrgUserSelfDTO{
    
    private Date recordCreateTime;
    
    private String name;

    private Integer sex;
    
    private Integer nation;
    
    private Integer partisan;
    
    private Date birthday;
    
    private Integer jobDuties;

    private Integer jobRank;
    
    private Integer oeducation;

    private Date startWorkTime;

    private Date comeUnitTime;
    
    private Integer userProperty;
    
    private Integer jobLevel;
    

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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
