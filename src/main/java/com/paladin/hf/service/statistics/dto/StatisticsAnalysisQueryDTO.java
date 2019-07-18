package com.paladin.hf.service.statistics.dto;

import java.util.Date;

/**   
* @author MyKite
* @version 2019年7月17日 上午9:46:17 
*/
public class StatisticsAnalysisQueryDTO {

	private String selfAssTime;
	
	private Date startTime;
	
	private Date endTime;

	public String getSelfAssTime() {
		return selfAssTime;
	}

	public void setSelfAssTime(String selfAssTime) {
		this.selfAssTime = selfAssTime;
	}

	public Date getStartTime() {
	    return startTime;
	}

	public void setStartTime(Date startTime) {
	    this.startTime = startTime;
	}

	public Date getEndTime() {
	    return endTime;
	}

	public void setEndTime(Date endTime) {
	    this.endTime = endTime;
	}
	
	
}
