package com.izmus.api.landingpage;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ContactUs")
public class ContactUs {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactUs.class);
	private static final String CONTACT_US_PROCESS = "ContactUsProcessId";
	@Autowired
	private RuntimeService runtimeService;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.POST)
	public String contactUs(@RequestParam(value = "name") String name,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "subject", required = false) String subject,
			@RequestParam(value = "message", required = false) String message){
		LOGGER.info("User Posted Contact Us Message With Name: " + name + " E-Mail: " + email + " Subject " + subject + " Message: " + message);
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("name", name);
		variables.put("email", email);
		variables.put("subject", subject);
		variables.put("message", message);
		runtimeService.startProcessInstanceByKey(CONTACT_US_PROCESS, variables);
		return "{\"result\": \"success\"}";
	}
}
