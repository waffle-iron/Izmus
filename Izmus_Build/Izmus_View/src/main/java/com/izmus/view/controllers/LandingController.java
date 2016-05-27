package com.izmus.view.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LandingController {
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value="/LandingPage", method=RequestMethod.GET)
	public ModelAndView getLandingPage() {
		return new ModelAndView("landingPage");
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value="/Login", method=RequestMethod.GET)
	public ModelAndView getLoginPage() {
		return new ModelAndView("landingPage");
	}
}
