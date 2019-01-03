package com.paladin.hf.service.assess.cycle.dto;

import java.io.Serializable;

import com.netmatch.core.conatiner.UnitConatiner;
import com.netmatch.tonto.framework.exception.BusinessException;
import com.paladin.hf.model.assess.cycle.PersonCycAssess;

import net.coobird.thumbnailator.Thumbnails;

/**   
 * @author jisanjie
 * @version 2018年1月30日 下午1:16:54 
 */
public class PersonCycAssessExt extends PersonCycAssess implements Serializable
{
    /**
       * serialVersionUID
       */
      private static final long serialVersionUID = 1L;

private String userName;//用户名
    
    private String assessCycName;//考评周期名称
    
    /**
     *  新增返回字段
     *  @author jisanjie
     *  @version 2018年9月26日
     *  
     */
    
    private String unitName;
    
    private String sex;
    
    private String birthday;
    
    private String partisan;
    
    private String oeducation;
    
    private String jobDuties;
    
    private String comeUnitTime;
    
    private String userProperty;
    
    

    
    
    public String getAssessCycName()
    {
        return assessCycName;
    }

    public void setAssessCycName(String assessCycName)
    {
        this.assessCycName = assessCycName;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    
    public String getSex() {
          return sex;
    }

    public void setSex(String sex) {
          this.sex = sex;
    }

    public String getBirthday() {
          return birthday;
    }

    public void setBirthday(String birthday) {
          this.birthday = birthday;
    }

    public String getPartisan() {
          return partisan;
    }

    public void setPartisan(String partisan) {
          this.partisan = partisan;
    }

    public String getOeducation() {
          return oeducation;
    }

    public void setOeducation(String oeducation) {
          this.oeducation = oeducation;
    }
    
    public String getJobDuties() {
          return jobDuties;
    }
    
    public void setJobDuties(String jobDuties) {
          this.jobDuties = jobDuties;
    }
    
    public String getComeUnitTime() {
          return comeUnitTime;
    }
    
    public void setComeUnitTime(String comeUnitTime) {
          this.comeUnitTime = comeUnitTime;
    }
    
    public String getUserProperty() {
          return userProperty;
    }
    
    public void setUserProperty(String userProperty) {
          this.userProperty = userProperty;
    }
    
    public String getUnitName() {
              return unitName;
    }

    public void setUnitName(String unitName) {
          this.unitName = unitName;
    }
    
    
    /*
     * 扩展显示单位名称
     */
    


public String getDepartmentName() {
        try {
            return UnitConatiner.getUnitName(getUnitId());
    	}catch(BusinessException e) {
    		return "";
    	}
    }
    
    public String getAgencyName() {        
        try {
            return UnitConatiner.getUnitName(getAgencyId());
    	}catch(BusinessException e) {
    		return "";
    	}
    }
    
    
    
}
