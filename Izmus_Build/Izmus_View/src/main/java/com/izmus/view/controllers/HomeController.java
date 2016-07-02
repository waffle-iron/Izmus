package com.izmus.view.controllers;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.izmus.data.configurations.ViewConfig;

@Controller
public class HomeController {
	/*----------------------------------------------------------------------------------------------------*/
	private static final String HOME_VIEW_PROCESS = "HomeViewProcessId";
	@Autowired
	private RuntimeService runtimeService;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getHomePage() {
		ViewConfig viewConfig = new ViewConfig();
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("viewConfig", viewConfig);
		runtimeService.startProcessInstanceByKey(HOME_VIEW_PROCESS, variables);
		return viewConfig.getModelAndView();
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value="/AccessDenied", method=RequestMethod.GET)
	public ModelAndView getAccessDeniedPage() {
		return new ModelAndView("accessDenied");
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Processes", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Processes', '')")
	public ModelAndView getProcesses() {
		ModelAndView returnModel = new ModelAndView("processes");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/ImportExport", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Import Export', '')")
	public ModelAndView getImportInformation() {
		ModelAndView returnModel = new ModelAndView("importExport");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/FindersDashboard", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Finders Menu/Finders Dashboard', '')")
	public ModelAndView getFindersDashboard() {
		ModelAndView returnModel = new ModelAndView("findersDashboard");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Users", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Users', '')")
	public ModelAndView getUsers() {
		ModelAndView returnModel = new ModelAndView("users");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Roles", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Roles', '')")
	public ModelAndView getRoles() {
		ModelAndView returnModel = new ModelAndView("roles");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/StartupAssessment", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Assessors Menu/Startup Assessment', '')")
	public ModelAndView getStartupAssessment() {
		ModelAndView returnModel = new ModelAndView("startupAssessment");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Meetings", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Assessors Menu/Meetings', '')")
	public ModelAndView getMeetings() {
		ModelAndView returnModel = new ModelAndView("meetings");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Contacts", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Assessors Menu/Contacts', '')")
	public ModelAndView getContacts() {
		ModelAndView returnModel = new ModelAndView("contacts");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/AvailableStartups", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Assessors Menu/Available Startups', '')")
	public ModelAndView getAvailableStartups() {
		ModelAndView returnModel = new ModelAndView("availableStartups");
		return returnModel;
	}	
}