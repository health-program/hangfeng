package com.paladin.hf.model.ordinary;

import javax.persistence.Transient;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paladin.framework.common.BaseModel;
public class Prizepunish  extends BaseModel {
    
	
	public static final int OPERATION_STATE_SELF_TEMPORARY = 0;
	public static final int OPERATION_STATE_DEPARTMENT_TEMPORARY = 1;
	public static final int OPERATION_STATE_AGENCY_TEMPORARY = 2;
	public static final int OPERATION_STATE_SELF_SUBMIT = 3;
	public static final int OPERATION_STATE_DEPARTMENT_SUBMIT = 4;
	public static final int OPERATION_STATE_AGENCY_SUBMIT = 5;

	public static final int EXAMINE_WAIT = 0;//待审核
	public static final int EXAMINE_SUCCESS = 1;//成功
	public static final int EXAMINE_FAILURE = 2;//失败
	
	
	public static final String COLUMN_ORG_USER_ID = "orgUserId";
	public static final String COLUMN_HAPPEN_TIME = "happenTime";
	public static final String COLUMN_DICT_CODE = "dictCode";
	public static final String COLUMN_OPERATION_STATE = "operationState";
	
	
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;

    private String orgUserId;
    
    private String dictCode;

    private String happenTime;

    private String content;

    private String place;

    private String checks;

    private String checkPeople;

    private Integer operationState;

    private Integer examineState;

    private String examinePeople;

    private String remarks;
    
    private String attachments; 
    
    @Transient
    private String endhappenTime;
    
    @Transient
    private String state;
    
    @Transient
    private String stateUnit;
    
    @Transient
    private String submitname;
    
    @Transient
    private String cz;
    
    @Transient
    private String czgr;
    
    @Transient
    private String fileImage[];
    
    
    
    public String[] getFileImage() {
		return fileImage;
	}

	public void setFileImage(String[] fileImage) {
		this.fileImage = fileImage;
	}

	public String getCzgr()
    {
        return czgr;
    }

    public void setCzgr(String czgr)
    {
        this.czgr = czgr;
    }

    public String getCz()
    {
        return cz;
    }

    public void setCz(String cz)
    {
        this.cz = cz;
    }

    public String getSubmitname()
    {
        return submitname;
    }

    public void setSubmitname(String submitname)
    {
        this.submitname = submitname;
    }

    public String getStateUnit()
    {
        return stateUnit;
    }

    public void setStateUnit(String stateUnit)
    {
        this.stateUnit = stateUnit;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getEndhappenTime()
    {
        return endhappenTime;
    }

    public void setEndhappenTime(String endhappenTime)
    {
        this.endhappenTime = endhappenTime;
    }

    public String getOrgUserId()
    {
        return orgUserId;
    }

    public void setOrgUserId(String orgUserId)
    {
        this.orgUserId = orgUserId;
    }

    @Transient
    @JsonIgnore
    private String sort = "";

    @Transient
    @JsonIgnore
    private String order = "";
    
    @Transient
    @JsonIgnore
    private String appSort = "happen_time";
    
    
    
    public String getAppSort()
    {
        return appSort;
    }

    public void setAppSort(String appSort)
    {
        this.appSort = appSort;
    }

    public String getSort()
    {
        return sort;
    }

    public void setSort(String sort)
    {
        this.sort = sort;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(String order)
    {
        this.order = order;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDictCode()
    {
        return dictCode;
    }

    public void setDictCode(String dictCode)
    {
        this.dictCode = dictCode;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getChecks()
    {
        return checks;
    }

    public void setChecks(String checks)
    {
        this.checks = checks;
    }

    public String getCheckPeople() {
        return checkPeople;
    }

    public void setCheckPeople(String checkPeople) {
        this.checkPeople = checkPeople == null ? null : checkPeople.trim();
    }

    public Integer getOperationState() {
        return operationState;
    }

    public void setOperationState(Integer operationState) {
        this.operationState = operationState == null ? null : operationState;
    }

    public Integer getExamineState() {
        return examineState;
    }

    public void setExamineState(Integer examineState) {
        this.examineState = examineState == null ? null : examineState;
    }

    public String getExaminePeople() {
        return examinePeople;
    }

    public void setExaminePeople(String examinePeople) {
        this.examinePeople = examinePeople == null ? null : examinePeople.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getHappenTime()
    {
        return happenTime;
    }

    public void setHappenTime(String happenTime)
    {
        this.happenTime = happenTime;
    }

    public String getAttachments()
    {
        return attachments;
    }

    public void setAttachments(String attachments)
    {
        this.attachments = attachments;
    }
    
}