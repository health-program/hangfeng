package com.paladin.hf.model.assess.quantificate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.netmatch.model.BaseModel;

public class AssessQuantitativeResult extends BaseModel{
	
	public static final String COLUMN_USER_ID = "userId";

	public static final String COLUMN_CYCLE_ID = "cycleId";

	@Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
	
	private String userId;
	
	private String cycleId;
	
	private Double baseScore;
	
	private Double addScore;
	
	private Double reduceScore;
	
	private Integer isVeto;
	
	private String unitId;
	
	private String agencyId;
	
	private String assessTeamId;
	
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

      public String getAssessTeamId() {
            return assessTeamId;
      }

      public void setAssessTeamId(String assessTeamId) {
            this.assessTeamId = assessTeamId;
      }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
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
