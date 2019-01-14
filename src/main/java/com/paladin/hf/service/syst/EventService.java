package com.paladin.hf.service.syst;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paladin.common.model.syst.SysConstant;
import com.paladin.common.service.syst.SysConstantService;
import com.paladin.framework.core.container.ConstantsContainer;
import com.paladin.framework.core.container.ConstantsContainer.KeyValue;
import com.paladin.framework.core.exception.BusinessException;

@Service
public class EventService {

	@Autowired
	private SysConstantService constantService;

	public static final String TYPE_EVENT = "event_type";

	public List<KeyValue> findEvent() {
		return ConstantsContainer.getTypeChildren(TYPE_EVENT).get(TYPE_EVENT);
	}
	
	@Transactional
	public boolean addEvent(String name) {

		Integer key = ConstantsContainer.getTypeKey(TYPE_EVENT, name);
		if (key != null) {
			throw new BusinessException("已经存在相同名称事件");
		}

		List<KeyValue> constants = ConstantsContainer.getTypeChildren(TYPE_EVENT).get(TYPE_EVENT);
		int code = 0;

		if (constants != null) {
			for (KeyValue kv : constants) {
				code = Math.max(code, kv.getKey());
			}
		}

		code += 1;
		
		SysConstant model = new SysConstant();
		model.setCode(code);
		model.setType(TYPE_EVENT);
		model.setOrderNo(code);
		model.setName(name);

		constantService.save(model);
		ConstantsContainer.updateData();

		return true;
	}

	@Transactional
	public boolean updateEvent(Integer code, String name) {
		SysConstant model = new SysConstant();
		model.setCode(code);
		model.setType(TYPE_EVENT);
		model.setOrderNo(code);
		model.setName(name);
		if (constantService.update(model) > 0) {
			ConstantsContainer.updateData();
			return true;
		} else {
			return false;
		}
	}



}
