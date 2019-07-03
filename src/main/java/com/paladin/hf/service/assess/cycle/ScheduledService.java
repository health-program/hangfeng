package com.paladin.hf.service.assess.cycle;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.paladin.hf.mapper.assess.cycle.PersonCycAssessMapper;

@Service
public class ScheduledService {

	@Autowired
	private PersonCycAssessMapper perCycAssMapper;

	private int day = 7;

	// 0 0 2 * * ? 每天凌晨2点执行
	@Scheduled(cron = "0 0 2 * * ?")
	public void scheduledSelfConfirm() {
		long time = System.currentTimeMillis() - ((day + 1) * 24 * 60 * 60 * 1000);
		Date lastTime = new Date(time);
		perCycAssMapper.updateAutoConfirm(lastTime);
	}
}
