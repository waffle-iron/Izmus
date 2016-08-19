package com.izmus.api.availablestartups;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.activiti.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.izmus.data.domain.cart.WishList;
import com.izmus.data.domain.startups.StartupAbstract;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.IStartupAbstractRepository;
import com.izmus.data.repository.IWishlistRepository;

@RestController
@RequestMapping("api/AvailableStartups")
public class AvailableStartups {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(AvailableStartups.class);
	private static final String ANALYSIS_REQUEST_PROCESS = "AnalysisRequestProcessId";
	@Autowired
	private IStartupAbstractRepository startupAbstractRepository;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IWishlistRepository wishlistRepository;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('View Available Startups', '')")
	public Page<StartupAbstract> getAllIzmusContacts(
			@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			@RequestParam(value = "searchName", required = false) String searchName,
			@RequestParam(value = "filterSector", required = false) List<String> filterSector,
			@RequestParam(value = "fundingStage", required = false) List<String> fundingStage,
			@RequestParam(value = "productStage", required = false) List<String> productStage,
			@RequestParam(value = "pageSize") Integer pageSize) {
		PageRequest pageable = new PageRequest(pageNumber, pageSize);
		Page<StartupAbstract> returnPage;
		if (searchName == null) {
			if (filterSector == null) {
				if (fundingStage == null) {
					if (productStage == null) {
						returnPage = startupAbstractRepository.findAllByOrderByStartupNameAsc(pageable);
					} else {
						returnPage = startupAbstractRepository
								.findByProductStageIgnoreCaseInOrderByStartupNameAsc(productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = startupAbstractRepository
								.findByFundingStageIgnoreCaseInOrderByStartupNameAsc(fundingStage, pageable);
					} else {
						returnPage = startupAbstractRepository
								.findByFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										fundingStage, productStage, pageable);
					}
				}
			} else {
				if (fundingStage == null) {
					if (productStage == null) {
						returnPage = startupAbstractRepository
								.findBySectorIgnoreCaseInOrderByStartupNameAsc(filterSector, pageable);
					} else {
						returnPage = startupAbstractRepository
								.findBySectorIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										filterSector, productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = startupAbstractRepository
								.findBySectorIgnoreCaseInAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
										filterSector, fundingStage, pageable);
					} else {
						returnPage = startupAbstractRepository
								.findBySectorIgnoreCaseInAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										filterSector, fundingStage, productStage, pageable);
					}
				}
			}
		} else {
			if (filterSector == null) {
				if (fundingStage == null) {
					if (productStage == null) {
						returnPage = startupAbstractRepository
								.findByStartupNameIgnoreCaseContainingOrderByStartupNameAsc(searchName, pageable);
					} else {
						returnPage = startupAbstractRepository
								.findByStartupNameIgnoreCaseContainingAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = startupAbstractRepository
								.findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, fundingStage, pageable);
					} else {
						returnPage = startupAbstractRepository
								.findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, fundingStage, productStage, pageable);
					}
				}
			} else {
				if (fundingStage == null) {
					if (productStage == null) {
						returnPage = startupAbstractRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInOrderByStartupNameAsc(
										searchName, filterSector, pageable);
					} else {
						returnPage = startupAbstractRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, filterSector, productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = startupAbstractRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, filterSector, fundingStage, pageable);
					} else {
						returnPage = startupAbstractRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, filterSector, fundingStage, productStage, pageable);
					}
				}
			}

		}
		return returnPage;
	}

	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/Sectors")
	@PreAuthorize("hasPermission('View Available Startups', '')")
	public Set<String> getAllSectors() {
		TreeSet<String> returnSet = new TreeSet<>();
		List<StartupAbstract> allStartups = startupAbstractRepository.findAll();
		for (StartupAbstract startup : allStartups) {
			if (startup.getSector() != null && !startup.getSector().isEmpty())
				returnSet.add(startup.getSector());
		}
		return returnSet;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/ProductStages")
	@PreAuthorize("hasPermission('View Available Startups', '')")
	public Set<String> getAllProductStages() {
		TreeSet<String> returnSet = new TreeSet<>();
		List<StartupAbstract> allStartups = startupAbstractRepository.findAll();
		for (StartupAbstract startup : allStartups) {
			if (startup.getProductStage() != null && !startup.getProductStage().isEmpty())
				returnSet.add(startup.getProductStage());
		}
		return returnSet;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/FundingStages")
	@PreAuthorize("hasPermission('View Available Startups', '')")
	public Set<String> getAllFundingStages() {
		TreeSet<String> returnSet = new TreeSet<>();
		List<StartupAbstract> allStartups = startupAbstractRepository.findAll();
		for (StartupAbstract startup : allStartups) {
			if (startup.getFundingStage() != null && !startup.getFundingStage().isEmpty())
				returnSet.add(startup.getFundingStage());
		}
		return returnSet;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/AllStartups")
	@PreAuthorize("hasPermission('View Available Startups', '')")
	public List<StartupAbstract> getAllStartups() {
		List<StartupAbstract> allStartups = startupAbstractRepository.findAll();
		return allStartups;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.POST, value = "/AnalysisRequest")
	@PreAuthorize("hasPermission('View Available Startups', '')")
	public String analysisRequest(@RequestParam(value = "startupId", required = true) Integer startupId) {
		LOGGER.info("User Requested A New Startup Analysis");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("startupId", startupId);
		runtimeService.startProcessInstanceByKey(ANALYSIS_REQUEST_PROCESS, variables);
		return "{\"result\": \"success\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.POST, value = "/Wishlist")
	@PreAuthorize("hasPermission('View Available Startups', '')")
	public String wishlist(@RequestParam(value = "startupId", required = true) Integer startupId) {
		LOGGER.info("User Moved Startup To Wishlist");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		WishList listItem = new WishList();
		listItem.setStartupId(startupId);
		listItem.setUserId(user.getUserId());
		wishlistRepository.save(listItem);
		return "{\"result\": \"success\"}";
	}
}
