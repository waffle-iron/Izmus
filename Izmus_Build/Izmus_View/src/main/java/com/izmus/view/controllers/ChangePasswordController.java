package com.izmus.view.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChangePasswordController {
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value="/ChangePassword", method=RequestMethod.GET)
	@PreAuthorize("hasRole('USER')")
	public ModelAndView getLandingPage() {
		return new ModelAndView("changePassword");
	}
}
