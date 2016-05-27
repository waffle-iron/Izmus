package com.izmus.view.controllers;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.izmus.data.configurations.ViewConfig;

@Controller
@RequestMapping(value = "/RegisterClient")
public class ClientRegistrationController {
	@Autowired
	private RuntimeService runtimeService;
	private static final String CLIENT_REGISTRATION_PROCESS_ID = "ClientRegistrationProcessId";
	private static final String HOME_REDIRECT = "redirect:/";
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getRegister() {
		return new ModelAndView(HOME_REDIRECT);
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView postRegister(@RequestParam(value = "regUserName", required = true) String regUserName,
			@RequestParam(value = "eMail", required = true) String eMail) {
		ViewConfig viewConfig = new ViewConfig();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("userName", regUserName);
		variables.put("eMail", eMail);
		variables.put("viewConfig", viewConfig);
		runtimeService.startProcessInstanceByKey(CLIENT_REGISTRATION_PROCESS_ID, variables);
		return new ModelAndView(HOME_REDIRECT);
	}
}
