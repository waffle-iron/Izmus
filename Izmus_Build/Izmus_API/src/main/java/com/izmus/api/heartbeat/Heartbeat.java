package com.izmus.api.heartbeat;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/Heartbeat")
public class Heartbeat {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(Heartbeat.class);

	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.POST)
	public String beat(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		LOGGER.info("Heartbeat from: " + ipAddress);
		return "{\"result\": \"success\"}";
	}
}
