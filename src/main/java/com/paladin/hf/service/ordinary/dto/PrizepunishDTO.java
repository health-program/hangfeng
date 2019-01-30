package com.paladin.hf.service.ordinary.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author 黄伟华
 * @version 2019年1月10日 下午5:26:22
 */
public class PrizepunishDTO {

	private String id;

	@NotNull(message = "事件类型不能为空！")
	private Integer dictCode;

	@NotEmpty(message = "发生时间不能为空！")
	private String happenTime;

	private String content;

	private String place;

	private String checks;

	private String checkPeople;

	private String remarks;

	private String attachments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDictCode() {
		return dictCode;
	}

	public void setDictCode(Integer dictCode) {
		this.dictCode = dictCode;
	}

	public String getHappenTime() {
		return happenTime;
	}

	public void setHappenTime(String happenTime) {
		this.happenTime = happenTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getChecks() {
		return checks;
	}

	public void setChecks(String checks) {
		this.checks = checks;
	}

	public String getCheckPeople() {
		return checkPeople;
	}

	public void setCheckPeople(String checkPeople) {
		this.checkPeople = checkPeople;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAttachments() {
		return attachments;
	}

	public void setAttachments(String attachments) {
		this.attachments = attachments;
	}

}
