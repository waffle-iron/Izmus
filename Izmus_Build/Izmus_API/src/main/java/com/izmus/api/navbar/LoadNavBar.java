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
			if (permissionEvaluator.hasPermission(authentication, "Investors Menu", null)){
				navbar.add(getInvestorsMenu(authentication));
			}
			if (permissionEvaluator.hasPermission(authentication, "Cart Menu", null)){
				navbar.add(getCartMenu(authentication));
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
		if (permissionEvaluator.hasPermission(authentication, "Assessors Menu/Startup Assessment", null)){
			assessorsMenu.getSubItems().add(getStartupAssessmnetItem());
		}
		if (permissionEvaluator.hasPermission(authentication, "Assessors Menu/Available Startups", null)){
			assessorsMenu.getSubItems().add(getAvailableStartupsItem());
		}
		if (permissionEvaluator.hasPermission(authentication, "Assessors Menu/Meetings", null)){
			assessorsMenu.getSubItems().add(getMeetingsItem());
		}
		if (permissionEvaluator.hasPermission(authentication, "Assessors Menu/Contacts", null)){
			assessorsMenu.getSubItems().add(getContactsItem());
		}
		return assessorsMenu;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getCartMenu(Authentication authentication) {
		NavbarItem findersMenu = new NavbarItem();
		findersMenu.setHref("");
		findersMenu.setLabel(context.getMessage("navBar.menu.cartMenu",null, LocaleContextHolder.getLocale()));
		findersMenu.setIcon("shopping_cart");
		findersMenu.setType("toggle");
		findersMenu.setSubItems(new ArrayList<NavbarItem>());
		if (permissionEvaluator.hasPermission(authentication, "Cart Menu/Wish List", null)){
			findersMenu.getSubItems().add(getWishListItem());
		}
		if (permissionEvaluator.hasPermission(authentication, "Cart Menu/My Requests", null)){
			findersMenu.getSubItems().add(getCartItem());
		}
		return findersMenu;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getCartItem() {
		NavbarItem findersDashboardItem = new NavbarItem();
		findersDashboardItem.setHref("/MyRequests");
		findersDashboardItem.setLabel(context.getMessage("navBar.menu.cartMenu.myRequests",null, LocaleContextHolder.getLocale()));
		findersDashboardItem.setIcon("/views/core/izmus-nav-bar/images/my-requests.svg");
		findersDashboardItem.setType("link");
		return findersDashboardItem;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getWishListItem() {
		NavbarItem findersDashboardItem = new NavbarItem();
		findersDashboardItem.setHref("/WishList");
		findersDashboardItem.setLabel(context.getMessage("navBar.menu.cartMenu.wishList",null, LocaleContextHolder.getLocale()));
		findersDashboardItem.setIcon("/views/core/izmus-nav-bar/images/wish-list.svg");
		findersDashboardItem.setType("link");
		return findersDashboardItem;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getFindersMenu(Authentication authentication) {
		NavbarItem findersMenu = new NavbarItem();
		findersMenu.setHref("");
		findersMenu.setLabel(context.getMessage("navBar.menu.findersMenu",null, LocaleContextHolder.getLocale()));
		findersMenu.setIcon("/views/core/izmus-nav-bar/images/finders-menu.svg");
		findersMenu.setType("toggle");
		findersMenu.setSubItems(new ArrayList<NavbarItem>());
		if (permissionEvaluator.hasPermission(authentication, "Finders Menu/Finders Dashboard", null)){
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
		if (permissionEvaluator.hasPermission(authentication, "Admin Menu/Import Export", null)){
			adminMenu.getSubItems().add(getImportExportItem());
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
	private NavbarItem getImportExportItem() {
		NavbarItem rolesItem = new NavbarItem();
		rolesItem.setHref("/ImportExport");
		rolesItem.setLabel(context.getMessage("navBar.menu.adminMenu.importExport",null, LocaleContextHolder.getLocale()));
		rolesItem.setIcon("import_export");
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
	private NavbarItem getMeetingsItem() {
		NavbarItem proposalItem = new NavbarItem();
		proposalItem.setHref("/Meetings");
		proposalItem.setLabel(context.getMessage("navBar.menu.assessorsMenu.meetings",null, LocaleContextHolder.getLocale()));
		proposalItem.setIcon("/views/meetings/images/meeting.svg");
		proposalItem.setType("link");
		return proposalItem;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getContactsItem() {
		NavbarItem proposalItem = new NavbarItem();
		proposalItem.setHref("/Contacts");
		proposalItem.setLabel(context.getMessage("navBar.menu.assessorsMenu.contacts",null, LocaleContextHolder.getLocale()));
		proposalItem.setIcon("/views/contacts/images/contact.svg");
		proposalItem.setType("link");
		return proposalItem;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getAvailableStartupsItem() {
		NavbarItem proposalItem = new NavbarItem();
		proposalItem.setHref("/AvailableStartups");
		proposalItem.setLabel(context.getMessage("navBar.menu.assessorsMenu.availableStartups",null, LocaleContextHolder.getLocale()));
		proposalItem.setIcon("/views/core/izmus-nav-bar/images/available-startups.svg");
		proposalItem.setType("link");
		return proposalItem;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getInvestorsMenu(Authentication authentication) {
		NavbarItem findersMenu = new NavbarItem();
		findersMenu.setHref("");
		findersMenu.setLabel(context.getMessage("navBar.menu.investorsMenu",null, LocaleContextHolder.getLocale()));
		findersMenu.setIcon("/views/core/izmus-nav-bar/images/investors-menu.svg");
		findersMenu.setType("toggle");
		findersMenu.setSubItems(new ArrayList<NavbarItem>());
		if (permissionEvaluator.hasPermission(authentication, "Investors Menu/Investors Dashboard", null)){
			findersMenu.getSubItems().add(getInvestorsDashboardItem());
		}
		return findersMenu;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private NavbarItem getInvestorsDashboardItem() {
		NavbarItem findersDashboardItem = new NavbarItem();
		findersDashboardItem.setHref("/InvestorsDashboard");
		findersDashboardItem.setLabel(context.getMessage("navBar.menu.investorsMenu.investorsDashboard",null, LocaleContextHolder.getLocale()));
		findersDashboardItem.setIcon("dashboard");
		findersDashboardItem.setType("link");
		return findersDashboardItem;
	}
}
