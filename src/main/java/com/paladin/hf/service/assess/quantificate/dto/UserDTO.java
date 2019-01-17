package com.paladin.hf.service.assess.quantificate.dto;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.paladin.framework.common.UnDeleteBaseModel;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;

import tk.mybatis.mapper.annotation.IgnoreInMultipleResult;

/**   
* @author jisanjie
* @version 2019年1月17日 下午1:07:26 
*/
public class UserDTO  extends UnDeleteBaseModel implements Serializable{
      

      /**
       * serialVersionUID
       */
      private static final long serialVersionUID = -3412275683687577374L;
      
      
      
      public static final String COLUMN_ORG_UNIT_ID = "orgUnitId";
      public static final String COLUMN_TRANSFER_AGENCY_ID = "transferAgencyId";
      public static final String COLUMN_TRANSFER_STATUS = "transferStatus";

      public static final String COLUMN_TRANSFER_ORIGIN_AGENCY_ID = "transferOriginAgencyId";
      public static final String COLUMN_TRANSFER_ORIGIN_TEAM_ID = "transferOriginTeamId";
      public static final String COLUMN_TRANSFER_ORIGIN_UNIT_ID = "transferOriginUnitId";

      public static final String COLUMN_IDENTIFICATION = "identification";

      public static final int TRANSFER_STATUS_ASK = 1;
      public static final int TRANSFER_STATUS_SUCCESS = 2;
      public static final int TRANSFER_STATUS_FAIL = 3;
      
      public static final int USER_STATUS_LEAVE = 2;
      public static final int USER_STATUS_DELETE = 1;
      public static final int USER_STATUS_NORMAL = 0;


      @Id
      @Column(name = "id")
      @GeneratedValue(generator = "UUID")
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

      @IgnoreInMultipleResult
      private String resume;
      @IgnoreInMultipleResult
      private String reward;
      @IgnoreInMultipleResult
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

      public static String getColumnIdentification() {
          return COLUMN_IDENTIFICATION;
      }
      
      /*
       * 扩展显示用字段
       */

      public String getUnitName() {
          try {
              return UnitContainer.getUnitName(orgUnitId);
          }catch(BusinessException e) {
              return "";
          }
      }

      public String getUnitRootName() {
          try {
              return UnitContainer.getRootUnitName(orgUnitId);
          }catch(BusinessException e) {
              return "";          
          }
      }

}
