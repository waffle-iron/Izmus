package com.izmus.view.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ServerErrorHandlerController {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerErrorHandlerController.class);
	protected static final String HOME_VIEW = "home";
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/ServerErrorHandler", method = RequestMethod.GET)
	public ModelAndView getServerErrorPage(HttpServletRequest req, HttpServletResponse response,
			@RequestParam(value = "errorCode", required = false) String errorCode) {
		LOGGER.error("There was an internal server error with error code: " + errorCode);
		return new ModelAndView(HOME_VIEW);
	}
}