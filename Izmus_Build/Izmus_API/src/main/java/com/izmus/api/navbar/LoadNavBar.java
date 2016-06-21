package com.izmus.api.navbar;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.izmus.data.api.navbar.NavbarItem;
import com.izmus.security.permission.IzmusPermissionEvaluator;

@RestController
@RequestMapping("api/Navbar")
public class LoadNavBar {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(LoadNavBar.class);
	@Autowired
	private ApplicationContext context;
	@Autowired
	private IzmusPermissionEvaluator permissionEvaluator;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/GetNavbar", method = RequestMethod.GET)
	public List<NavbarItem> getNavBar() {
		List<NavbarItem> navbar = null;
		Authentication authentication = null;
		try {
			navbar = new ArrayList<>();
			authentication = SecurityContextHolder.getContext().getAuthentication();
			if (permissionEvaluator.hasPermission(authentication, "Assessors Menu", null)){
				navbar.add(getAssessorsMenu(authentication));
			}
			if (permissionEvaluator.hasPermission(authentication, "Finders Menu", null)){
				navbar.add(getFindersMenu(authentication));
			}
			if (permissionEvaluator.hasPermission(authentication, "Admin Menu", null)){
				navbar.add(getAdminMenu(authentication));
			}
		} catch (Exception e) {
			LOGGER.error("Getting Navbar Failed For User: " + authentication);
		}
		return navbar;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getAssessorsMenu(Authentication authentication) {
		NavbarItem assessorsMenu = new NavbarItem();
		assessorsMenu.setHref("");
		assessorsMenu.setLabel(context.getMessage("navBar.menu.assessorsMenu",null, LocaleContextHolder.getLocale()));
		assessorsMenu.setIcon("/views/core/izmus-nav-bar/images/assessor.svg");
		assessorsMenu.setType("toggle");
		assessorsMenu.setSubItems(new ArrayList<NavbarItem>());
		if (permissionEvaluator.hasPermission(authentication, "Startup Assessment", null)){
			assessorsMenu.getSubItems().add(getStartupAssessmnetItem());
		}
		if (permissionEvaluator.hasPermission(authentication, "Assessors Meetings", null)){
			assessorsMenu.getSubItems().add(getAssessorsMeetingsItem());
		}
		return assessorsMenu;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getFindersMenu(Authentication authentication) {
		NavbarItem findersMenu = new NavbarItem();
		findersMenu.setHref("");
		findersMenu.setLabel(context.getMessage("navBar.menu.findersMenu",null, LocaleContextHolder.getLocale()));
		findersMenu.setIcon("/views/core/izmus-nav-bar/images/finders-menu.svg");
		findersMenu.setType("toggle");
		findersMenu.setSubItems(new ArrayList<NavbarItem>());
		if (permissionEvaluator.hasPermission(authentication, "Finders Dashboard", null)){
			findersMenu.getSubItems().add(getFindersDashboardItem());
		}
		return findersMenu;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getFindersDashboardItem() {
		NavbarItem findersDashboardItem = new NavbarItem();
		findersDashboardItem.setHref("/FindersDashboard");
		findersDashboardItem.setLabel(context.getMessage("navBar.menu.findersMenu.findersDashboard",null, LocaleContextHolder.getLocale()));
		findersDashboardItem.setIcon("dashboard");
		findersDashboardItem.setType("link");
		return findersDashboardItem;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getAdminMenu(Authentication authentication) {
		NavbarItem adminMenu = new NavbarItem();
		adminMenu.setHref("");
		adminMenu.setLabel(context.getMessage("navBar.menu.adminMenu",null, LocaleContextHolder.getLocale()));
		adminMenu.setIcon("settings");
		adminMenu.setType("toggle");
		adminMenu.setSubItems(new ArrayList<NavbarItem>());
		if (permissionEvaluator.hasPermission(authentication, "Admin Menu/Users", null)){
			adminMenu.getSubItems().add(getUsersItem());
		}
		if (permissionEvaluator.hasPermission(authentication, "Admin Menu/Roles", null)){
			adminMenu.getSubItems().add(getRolesItem());
		}
		if (permissionEvaluator.hasPermission(authentication, "Admin Menu/Processes", null)){
			adminMenu.getSubItems().add(getProcessesItem());
		}
		return adminMenu;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getProcessesItem() {
		NavbarItem rolesItem = new NavbarItem();
		rolesItem.setHref("/Processes");
		rolesItem.setLabel(context.getMessage("navBar.menu.adminMenu.processes",null, LocaleContextHolder.getLocale()));
		rolesItem.setIcon("/views/core/izmus-nav-bar/images/flow-diagram.svg");
		rolesItem.setType("link");
		return rolesItem;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getRolesItem() {
		NavbarItem rolesItem = new NavbarItem();
		rolesItem.setHref("/Roles");
		rolesItem.setLabel(context.getMessage("navBar.menu.adminMenu.roles",null, LocaleContextHolder.getLocale()));
		rolesItem.setIcon("vpn_key");
		rolesItem.setType("link");
		return rolesItem;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getUsersItem() {
		NavbarItem usersItem = new NavbarItem();
		usersItem.setHref("/Users");
		usersItem.setLabel(context.getMessage("navBar.menu.adminMenu.users",null, LocaleContextHolder.getLocale()));
		usersItem.setIcon("/views/core/izmus-nav-bar/images/users.svg");
		usersItem.setType("link");
		return usersItem;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getStartupAssessmnetItem() {
		NavbarItem proposalItem = new NavbarItem();
		proposalItem.setHref("/StartupAssessment");
		proposalItem.setLabel(context.getMessage("navBar.menu.startupAssessment",null, LocaleContextHolder.getLocale()));
		proposalItem.setIcon("/views/core/izmus-nav-bar/images/startup.svg");
		proposalItem.setType("link");
		return proposalItem;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getAssessorsMeetingsItem() {
		NavbarItem proposalItem = new NavbarItem();
		proposalItem.setHref("/AssessorsMeetings");
		proposalItem.setLabel(context.getMessage("navBar.menu.assessorsMenu.assessorsMeetings",null, LocaleContextHolder.getLocale()));
		proposalItem.setIcon("/views/assessors-meetings/images/meeting.svg");
		proposalItem.setType("link");
		return proposalItem;
	}
}
