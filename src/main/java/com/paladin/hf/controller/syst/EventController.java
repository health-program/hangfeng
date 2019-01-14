package com.paladin.hf.controller.syst;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paladin.framework.core.ControllerSupport;
import com.paladin.framework.web.response.CommonResponse;
import com.paladin.hf.service.syst.EventService;

@Controller
@RequestMapping("/sys/event")
public class EventController extends ControllerSupport {

	@Autowired
	private EventService eventService;

	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String index() {
		return "/hf/syst/event_index";
	}

	@ResponseBody
	@RequestMapping(value = "/find")
	public Object findAll() {
		return CommonResponse.getSuccessResponse(eventService.findEvent());
	}

	@ResponseBody
	@RequestMapping(value = "/save")
	public Object save(@RequestParam String name) {
		return CommonResponse.getSuccessResponse(eventService.addEvent(name));
	}

	@ResponseBody
	@RequestMapping(value = "/update")
	public Object update(@RequestParam Integer code, @RequestParam String name) {
		return CommonResponse.getSuccessResponse(eventService.updateEvent(code, name));
	}
}
