package com.paladin.hf.service.assess.cycle.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PersonalCycleAssessConfirmDTO {
	@NotEmpty(message = "周期考核ID不能为空")
	private String id;
	@NotNull(message = "确认结果不能为空")
	private Integer confirmedResult;
	@NotEmpty(message = "自评确认签名不能为空")
	private String assessedConfirmSign;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Integer getConfirmedResult() {
		return confirmedResult;
	}
	public void setConfirmedResult(Integer confirmedResult) {
		this.confirmedResult = confirmedResult;
	}
	public String getAssessedConfirmSign() {
		return assessedConfirmSign;
	}
	public void setAssessedConfirmSign(String assessedConfirmSign) {
		this.assessedConfirmSign = assessedConfirmSign;
	}

}
