package com.izmus.api.availablestartups;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
	@Autowired
	private IAvailableStartupRepository availableStartupRepository;

	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Assessors Menu/Available Startups', '')")
	public Page<AvailableStartup> getAllIzmusContacts(
			@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			@RequestParam(value = "searchName", required = false) String searchName,
			@RequestParam(value = "filterSector", required = false) String filterSector,
			@RequestParam(value = "fundingStage", required = false) String fundingStage,
			@RequestParam(value = "productStage", required = false) String productStage,
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
								.findByProductStageIgnoreCaseOrderByStartupNameAsc(productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findByFundingStageIgnoreCaseOrderByStartupNameAsc(fundingStage, pageable);
					} else {
						returnPage = availableStartupRepository
								.findByFundingStageIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
										fundingStage, productStage, pageable);
					}
				}
			} else {
				if (fundingStage == null) {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findBySectorIgnoreCaseOrderByStartupNameAsc(filterSector, pageable);
					} else {
						returnPage = availableStartupRepository
								.findBySectorIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
										filterSector, productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findBySectorIgnoreCaseAndFundingStageIgnoreCaseOrderByStartupNameAsc(
										filterSector, fundingStage, pageable);
					} else {
						returnPage = availableStartupRepository
								.findBySectorIgnoreCaseAndFundingStageIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
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
								.findByStartupNameIgnoreCaseContainingAndProductStageIgnoreCaseOrderByStartupNameAsc(
										searchName, productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseOrderByStartupNameAsc(
										searchName, fundingStage, pageable);
					} else {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndFundingStageIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
										searchName, fundingStage, productStage, pageable);
					}
				}
			} else {
				if (fundingStage == null) {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseOrderByStartupNameAsc(
										searchName, filterSector, pageable);
					} else {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
										searchName, filterSector, productStage, pageable);
					}
				} else {
					if (productStage == null) {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseAndFundingStageIgnoreCaseOrderByStartupNameAsc(
										searchName, filterSector, fundingStage, pageable);
					} else {
						returnPage = availableStartupRepository
								.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseAndFundingStageIgnoreCaseAndProductStageIgnoreCaseOrderByStartupNameAsc(
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
}
