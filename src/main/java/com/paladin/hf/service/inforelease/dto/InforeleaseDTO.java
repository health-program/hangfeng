package com.paladin.hf.service.inforelease.dto;

import javax.validation.constraints.NotEmpty;

/**
 * @author 黄伟华
 * @version 2019年1月8日 下午3:08:47
 */
public class InforeleaseDTO {

	private String id;

	@NotEmpty(message = "标题不能为空")
	private String title;

	private String content;

	@NotEmpty(message = "是否发布不能为空")
	private String isRelease;

	@NotEmpty(message = "重要程度不能为空")
	private String importance;

	@NotEmpty(message = "发布时间不能为空")
	private String releaseTime;

	private String accessory;

	private Integer types;
	
	private Integer typeClassify;

	private String remarks;

	private String orgUnitId;

	private String attachments;

	public String getOrgUnitId() {
		return orgUnitId;
	}

	public void setOrgUnitId(String orgUnitId) {
		this.orgUnitId = orgUnitId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(String isRelease) {
		this.isRelease = isRelease;
	}

	public String getImportance() {
		return importance;
	}

	public void setImportance(String importance) {
		this.importance = importance;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public Integer getTypes() {
		return types;
	}

	public void setTypes(Integer types) {
		this.types = types;
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

	public Integer getTypeClassify() {
	    return typeClassify;
	}

	public void setTypeClassify(Integer typeClassify) {
	    this.typeClassify = typeClassify;
	}

}
