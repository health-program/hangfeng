package com.paladin.hf.service.assess.cycle.dto;

import java.util.Date;

import com.paladin.framework.common.BaseModel;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

/**   
* @author jisanjie
* @version 2019年1月4日 上午10:37:28 
*/
public class PersonnelCycleAssessDTO extends BaseModel {
      
      private String id;
      
      private String orgUserId;
      
      private String assessCycleId;
      
      private String workSummary;
      
      private String selfAssOpinion;
      
      private Integer selfAssGrade;
      
      private Date selfAssTime;
      
      private String departOpinion;
      
      private Integer departGrade;
      
      private Date departAssTime;
      
      private String unitGroupOpinion;
      
      private Integer unitAssGrade;
      
      private Date unitAssTime;
      
      private Integer confirmedResult;
      
      private Date confirmedTime;
      
      private String remarks;
      
      private Integer operateState;
      
      private String assessedSign;
      
      private String depAssessorSign;
      
      private String unitAssessorSign;
      
      private String assessedConfirmSign;
      
      private String unitId;
      
      private String agencyId;
      
      private String assessCycName;
      
      private String userName;
      
      
      
      private String unitName;
      
      private String sex;
      
      private String birthday;
      
      private String partisan;
      
      private String oeducation;
      
      private String jobDuties;
      
      private String comeUnitTime;
      
      private String userProperty;
      
      

      public String getId() {
            return id;
      }

      public void setId(String id) {
            this.id = id;
      }

      public String getOrgUserId() {
            return orgUserId;
      }

      public void setOrgUserId(String orgUserId) {
            this.orgUserId = orgUserId;
      }

      public String getAssessCycleId() {
            return assessCycleId;
      }

      public void setAssessCycleId(String assessCycleId) {
            this.assessCycleId = assessCycleId;
      }

      public String getWorkSummary() {
            return workSummary;
      }

      public void setWorkSummary(String workSummary) {
            this.workSummary = workSummary;
      }

      public String getSelfAssOpinion() {
            return selfAssOpinion;
      }

      public void setSelfAssOpinion(String selfAssOpinion) {
            this.selfAssOpinion = selfAssOpinion;
      }

      public Date getSelfAssTime() {
            return selfAssTime;
      }

      public void setSelfAssTime(Date selfAssTime) {
            this.selfAssTime = selfAssTime;
      }

      public String getDepartOpinion() {
            return departOpinion;
      }

      public void setDepartOpinion(String departOpinion) {
            this.departOpinion = departOpinion;
      }

      public Date getDepartAssTime() {
            return departAssTime;
      }

      public void setDepartAssTime(Date departAssTime) {
            this.departAssTime = departAssTime;
      }

      public String getUnitGroupOpinion() {
            return unitGroupOpinion;
      }

      public void setUnitGroupOpinion(String unitGroupOpinion) {
            this.unitGroupOpinion = unitGroupOpinion;
      }

      public Date getUnitAssTime() {
            return unitAssTime;
      }

      public void setUnitAssTime(Date unitAssTime) {
            this.unitAssTime = unitAssTime;
      }

      public Date getConfirmedTime() {
            return confirmedTime;
      }

      public void setConfirmedTime(Date confirmedTime) {
            this.confirmedTime = confirmedTime;
      }

      public String getRemarks() {
            return remarks;
      }

      public void setRemarks(String remarks) {
            this.remarks = remarks;
      }

      public String getAssessedSign() {
            return assessedSign;
      }

      public void setAssessedSign(String assessedSign) {
            this.assessedSign = assessedSign;
      }

      public String getDepAssessorSign() {
            return depAssessorSign;
      }

      public void setDepAssessorSign(String depAssessorSign) {
            this.depAssessorSign = depAssessorSign;
      }

      public String getUnitAssessorSign() {
            return unitAssessorSign;
      }

      public void setUnitAssessorSign(String unitAssessorSign) {
            this.unitAssessorSign = unitAssessorSign;
      }

      public String getAssessedConfirmSign() {
            return assessedConfirmSign;
      }

      public void setAssessedConfirmSign(String assessedConfirmSign) {
            this.assessedConfirmSign = assessedConfirmSign;
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

      public String getAssessCycName() {
            return assessCycName;
      }

      public void setAssessCycName(String assessCycName) {
            this.assessCycName = assessCycName;
      }

      public String getUserName() {
            return userName;
      }

      public void setUserName(String userName) {
            this.userName = userName;
      }

      public String getUnitName() {
            return unitName;
      }

      public void setUnitName(String unitName) {
            this.unitName = unitName;
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
      
      
      
      /*
       * 扩展显示单位名称
       */
      


      public Integer getSelfAssGrade() {
            return selfAssGrade;
      }

      public void setSelfAssGrade(Integer selfAssGrade) {
            this.selfAssGrade = selfAssGrade;
      }

      public Integer getDepartGrade() {
            return departGrade;
      }

      public void setDepartGrade(Integer departGrade) {
            this.departGrade = departGrade;
      }

      public Integer getUnitAssGrade() {
            return unitAssGrade;
      }

      public void setUnitAssGrade(Integer unitAssGrade) {
            this.unitAssGrade = unitAssGrade;
      }

      public Integer getConfirmedResult() {
            return confirmedResult;
      }

      public void setConfirmedResult(Integer confirmedResult) {
            this.confirmedResult = confirmedResult;
      }

      public Integer getOperateState() {
            return operateState;
      }

      public void setOperateState(Integer operateState) {
            this.operateState = operateState;
      }

      
      /*
       * 扩展显示用字段
       */
      
      public String getDepartmentName() {
          try {
              return UnitContainer.getUnitName(getUnitId());
          }catch(BusinessException e) {
              return "";
          }
      }
      
      public String getAgencyName() {        
          try {
              return UnitContainer.getUnitName(getAgencyId());
          }catch(BusinessException e) {
              return "";
          }
      }
}
