package com.izmus.api.landingpage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ContactUs")
public class ContactUs {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactUs.class);
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.POST)
	public String contactUs(@RequestParam(value = "name") String name,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "subject", required = false) String subject,
			@RequestParam(value = "message", required = false) String message){
		LOGGER.info("User Posted Contact Us Message With Name: " + name + " E-Mail: " + email + " Subject " + subject + " Message: " + message);
		return "{\"result\": \"success\"}";
	}
}
