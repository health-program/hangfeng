package com.paladin.hf.model.assess.quantificate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.paladin.framework.common.BaseModel;

public class AssessQuantitative extends BaseModel{
	
	public static final String COLUMN_FIELD_PRIZEPUNISH_ID = "prizePunishId";
	public static final String COLUMN_FIELD_CYCLE_ID = "cycleId";
	public static final String COLUMN_FIELD_USER_ID = "userId";
	public static final String COLUMN_FIELD_ASSESS_ITEM_EXTRA_ID = "assessItemExtraId";

	
	@Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
	
	private String userId;
	
	private String cycleId;
	
	private String prizePunishId;
	
	private String assessItemExtraId;
	
	private Double score;

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

	public String getPrizePunishId() {
		return prizePunishId;
	}

	public void setPrizePunishId(String prizePunishId) {
		this.prizePunishId = prizePunishId;
	}

	public String getAssessItemExtraId() {
		return assessItemExtraId;
	}

	public void setAssessItemExtraId(String assessItemExtraId) {
		this.assessItemExtraId = assessItemExtraId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getCycleId() {
		return cycleId;
	}

	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}
	
}
