package com.paladin.hf.model.assess.cycle;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.paladin.framework.common.BaseModel;
import com.paladin.framework.core.exception.BusinessException;
import com.paladin.hf.core.UnitContainer;


/**
 * 
 * 考评周期管理实体类
 * @author  jisanjie
 * @version  [版本号, 2018年1月11日]
 */
public class AssessCycle extends BaseModel
{
	/**
	 * 暂存
	 */
	public final static String CYCLE_STATE_DRAFT = "2";
	/**
	 * 启用
	 */
	public final static String CYCLE_STATE_START = "1";
	/**
	 * 归档
	 */
	public final static String CYCLE_STATE_ARCHIVE = "3";	
	/**
	 * 停用
	 */
	public final static String CYCLE_STATE_STOP = "4";
	
	
	
	public static final String COLUMN_UNIT_ID = "unitId";
	public static final String COLUMN_FIELD_CYCLESTATE = "cycleState";

	
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "UUID")
    private String id;
    
    //@UnitRelation(UnitType.AGENCY)
    private String unitId;
    
    @NotEmpty(message="周期名称不为空！")
    private String cycleName;
    
    @NotNull(message="周期开始时间不为空！")
    private Date cycleStartTime;
    
    @NotNull(message="周期截止时间不为空！")
    private Date cycleEndTime;
    
    @NotNull(message="考评开始时间不为空！")
    private Date assessStartTime;
    
    @NotNull(message="考评截止时间不为空！")
    private Date assessEndTime;
    
    @Column(columnDefinition = "enum('1','2','3')")
    private String cycleState;
    
    @Column(columnDefinition = "enum('1','2')")
    private String assessType;
    
    @NotEmpty(message="周期描述不为空！")
    private String cycleDescribe;
      
    
    /*
     * 扩展显示单位名称
     */
    public String getUnitName(){
    	try {
    		return UnitContainer.getUnitName(unitId);
    	}catch(BusinessException e) {
    		return "";
    	}
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUnitId() {
		return unitId;
	}


	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}


	public String getCycleName() {
		return cycleName;
	}


	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}


	public String getCycleState() {
		return cycleState;
	}


	public void setCycleState(String cycleState) {
		this.cycleState = cycleState;
	}


	public String getAssessType() {
		return assessType;
	}


	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}


	public String getCycleDescribe() {
		return cycleDescribe;
	}


	public void setCycleDescribe(String cycleDescribe) {
		this.cycleDescribe = cycleDescribe;
	}


	public Date getCycleStartTime() {
		return cycleStartTime;
	}


	public void setCycleStartTime(Date cycleStartTime) {
		this.cycleStartTime = cycleStartTime;
	}


	public Date getCycleEndTime() {
		return cycleEndTime;
	}


	public void setCycleEndTime(Date cycleEndTime) {
		this.cycleEndTime = cycleEndTime;
	}


	public Date getAssessStartTime() {
		return assessStartTime;
	}


	public void setAssessStartTime(Date assessStartTime) {
		this.assessStartTime = assessStartTime;
	}


	public Date getAssessEndTime() {
		return assessEndTime;
	}


	public void setAssessEndTime(Date assessEndTime) {
		this.assessEndTime = assessEndTime;
	}
    
}