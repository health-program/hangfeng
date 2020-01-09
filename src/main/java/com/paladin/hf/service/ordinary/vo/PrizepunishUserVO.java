package com.paladin.hf.service.ordinary.vo;

import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

public class PrizepunishUserVO {

    private String id;
    private String orgAgencyId;
    private String orgUnitId;
    private String isAssessor;
    private String name;
    private String sex;
    private String jobRank;
    private Integer num;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgAgencyId() {
        return orgAgencyId;
    }

    public void setOrgAgencyId(String orgAgencyId) {
        this.orgAgencyId = orgAgencyId;
    }

    public String getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(String orgUnitId) {
        this.orgUnitId = orgUnitId;
    }

    public String getIsAssessor() {
        return isAssessor;
    }

    public void setIsAssessor(String isAssessor) {
        this.isAssessor = isAssessor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getJobRank() {
        return jobRank;
    }

    public void setJobRank(String jobRank) {
        this.jobRank = jobRank;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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
}
