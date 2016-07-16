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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.izmus.data.domain.startups.AvailableStartup;
import com.izmus.data.repository.IAvailableStartupRepository;

@RestController
@RequestMapping("api/AvailableStartups")
public class AvailableStartups {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(AvailableStartups.class);
	private static final String ANALYSIS_REQUEST_PROCESS = "AnalysisRequestProcessId";
	@Autowired
	private IAvailableStartupRepository availableStartupRepository;
	@Autowired
	private RuntimeService runtimeService;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Assessors Menu/Available Startups', '')")
	public Page<AvailableStartup> getAllIzmusContacts(
			@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			@RequestParam(value = "searchName", required = false) String searchName,
			@RequestParam(value = "filterSector", required = false) List<String> filterSector,
			@RequestParam(value = "fundingStage", required = false) List<String> fundingStage,
			@RequestParam(value = "productStage", required = false) List<String> productStage,
			@RequestParam(value = "pageSize") Integer pageSize) {
		PageRequest pageable = new PageRequest(pageNumber, pageSize);
		Page<AvailableStartup> returnPage;
		if (searchName == null) {
			if (filterSector == null) {
				if (fundingStage == null) {
					if (productStage == null) {
						returnPage = availableStartupRepository.findAllByOrderByStartupNameAsc(pageable);
					} else {
						returnPage = availableStartupRepository
								.findByProductStageIgnoreCaseInOrderByStartupNameAsc(productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findByFundingStageIgnoreCaseInOrderByStartupNameAsc(fundingStage, pageable);
					} else {
						returnPage = availableStartupRepository
								.findByFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										fundingStage, productStage, pageable);
					}
				}
			} else {
				if (fundingStage == null) {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findBySectorIgnoreCaseInOrderByStartupNameAsc(filterSector, pageable);
					} else {
						returnPage = availableStartupRepository
								.findBySectorIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										filterSector, productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findBySectorIgnoreCaseInAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
										filterSector, fundingStage, pageable);
					} else {
						returnPage = availableStartupRepository
								.findBySectorIgnoreCaseInAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										filterSector, fundingStage, productStage, pageable);
					}
				}
			}
		} else {
			if (filterSector == null) {
				if (fundingStage == null) {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingOrderByStartupNameAsc(searchName, pageable);
					} else {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, fundingStage, pageable);
					} else {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, fundingStage, productStage, pageable);
					}
				}
			} else {
				if (fundingStage == null) {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInOrderByStartupNameAsc(
										searchName, filterSector, pageable);
					} else {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndProductStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, filterSector, productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseInAndFundingStageIgnoreCaseInOrderByStartupNameAsc(
										searchName, filterSector, fundingStage, pageable);
					} else {
						returnPage = availableStartupRepository
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
	@PreAuthorize("hasPermission('Assessors Menu/Available Startups', '')")
	public Set<String> getAllSectors() {
		TreeSet<String> returnSet = new TreeSet<>();
		List<AvailableStartup> allStartups = availableStartupRepository.findAll();
		for (AvailableStartup startup : allStartups) {
			if (!startup.getSector().isEmpty())
				returnSet.add(startup.getSector());
		}
		return returnSet;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/ProductStages")
	@PreAuthorize("hasPermission('Assessors Menu/Available Startups', '')")
	public Set<String> getAllProductStages() {
		TreeSet<String> returnSet = new TreeSet<>();
		List<AvailableStartup> allStartups = availableStartupRepository.findAll();
		for (AvailableStartup startup : allStartups) {
			if (!startup.getProductStage().isEmpty())
				returnSet.add(startup.getProductStage());
		}
		return returnSet;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/FundingStages")
	@PreAuthorize("hasPermission('Assessors Menu/Available Startups', '')")
	public Set<String> getAllFundingStages() {
		TreeSet<String> returnSet = new TreeSet<>();
		List<AvailableStartup> allStartups = availableStartupRepository.findAll();
		for (AvailableStartup startup : allStartups) {
			if (!startup.getFundingStage().isEmpty())
				returnSet.add(startup.getFundingStage());
		}
		return returnSet;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.POST, value = "/AnalysisRequest")
	@PreAuthorize("hasPermission('Assessors Menu/Available Startups', '')")
	public String analysisRequest(@RequestParam(value = "startupId", required = true) Integer startupId) {
		LOGGER.info("User Requested A New Startup Analysis");
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("startupId", startupId);
		runtimeService.startProcessInstanceByKey(ANALYSIS_REQUEST_PROCESS, variables);
		return "{\"result\": \"success\"}";
	}
}
