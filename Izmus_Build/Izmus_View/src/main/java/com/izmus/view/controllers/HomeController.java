package com.izmus.view.controllers;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.izmus.data.configurations.ViewConfig;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.IUserRepository;

@Controller
public class HomeController {
	/*----------------------------------------------------------------------------------------------------*/
	private static final String HOME_VIEW_PROCESS = "HomeViewProcessId";
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IUserRepository userRepository;
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
		logLastViewForUser("processes");
		ModelAndView returnModel = new ModelAndView("processes");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void logLastViewForUser(String view) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user = userRepository.findDistinctUserByUserId(user.getUserId());
		user.setLastView(view);
		userRepository.save(user);
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/ImportExport", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Import Export', '')")
	public ModelAndView getImportInformation() {
		logLastViewForUser("importExport");
		ModelAndView returnModel = new ModelAndView("importExport");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/FindersDashboard", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Finders Menu/Finders Dashboard', '')")
	public ModelAndView getFindersDashboard() {
		logLastViewForUser("findersDashboard");
		ModelAndView returnModel = new ModelAndView("findersDashboard");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Users", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Users', '')")
	public ModelAndView getUsers() {
		logLastViewForUser("users");
		ModelAndView returnModel = new ModelAndView("users");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Roles", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Admin Menu/Roles', '')")
	public ModelAndView getRoles() {
		logLastViewForUser("roles");
		ModelAndView returnModel = new ModelAndView("roles");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/StartupAssessment", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Assessors Menu/Startup Assessment', '')")
	public ModelAndView getStartupAssessment() {
		logLastViewForUser("startupAssessment");
		ModelAndView returnModel = new ModelAndView("startupAssessment");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Meetings", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Assessors Menu/Meetings', '')")
	public ModelAndView getMeetings() {
		logLastViewForUser("meetings");
		ModelAndView returnModel = new ModelAndView("meetings");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/Contacts", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Assessors Menu/Contacts', '')")
	public ModelAndView getContacts() {
		logLastViewForUser("contacts");
		ModelAndView returnModel = new ModelAndView("contacts");
		return returnModel;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/AvailableStartups", method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Assessors Menu/Available Startups', '')")
	public ModelAndView getAvailableStartups() {
		logLastViewForUser("availableStartups");
		ModelAndView returnModel = new ModelAndView("availableStartups");
		return returnModel;
	}	
}