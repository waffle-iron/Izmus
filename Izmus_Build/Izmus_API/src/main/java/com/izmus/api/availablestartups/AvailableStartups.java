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
	public Page<AvailableStartup> getAllIzmusContacts(@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
			@RequestParam(value = "searchName", required = false) String searchName,
			@RequestParam(value = "filterSector", required = false) String filterSector){
		Page<AvailableStartup> returnPage;
		if (searchName == null){
			if (filterSector == null){
				returnPage = availableStartupRepository.findAllByOrderByStartupNameAsc(new PageRequest(pageNumber, 50));
			}
			else {
				returnPage = availableStartupRepository.findBySectorIgnoreCaseContainingOrderByStartupNameAsc(filterSector, new PageRequest(pageNumber, 50));
			}
		}
		else {
			if (filterSector == null){
				returnPage = availableStartupRepository.findByStartupNameIgnoreCaseContainingOrderByStartupNameAsc(searchName, new PageRequest(pageNumber, 50));
			}
			else {
				returnPage = availableStartupRepository.findByStartupNameIgnoreCaseContainingAndSectorIgnoreCaseContainingOrderByStartupNameAsc(searchName, filterSector, new PageRequest(pageNumber, 50));
			}
			
		}
		return returnPage;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value="/Sectors")
	@PreAuthorize("hasPermission('Assessors Menu/Available Startups', '')")
	public Set<String> getAllSectors(){
		TreeSet<String> returnSet = new TreeSet<>();
		List<AvailableStartup> allStartups = availableStartupRepository.findAll();
		for (AvailableStartup startup : allStartups){
			if (!startup.getSector().isEmpty())	returnSet.add(startup.getSector());
		}
		return returnSet;
	}
}
