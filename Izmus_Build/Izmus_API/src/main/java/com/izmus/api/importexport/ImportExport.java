package com.izmus.api.importexport;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.izmus.data.domain.startups.AvailableStartup;
import com.izmus.data.domain.startups.Startup;
import com.izmus.data.repository.IAvailableStartupRepository;
import com.izmus.data.repository.IStartupRepository;

@RestController
@RequestMapping("api/ImportExport")
public class ImportExport {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(ImportExport.class);
	@Autowired
	private IStartupRepository startupRepository;
	@Autowired
	private IAvailableStartupRepository availableStartupRepository;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/StartupCSV", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Import Export', '')")
	public String uploadStartupCSV(@RequestParam("file") MultipartFile file) {
		try {
			processStartupFile(file.getInputStream());
		} catch (Exception e) {
			LOGGER.error("Could Not Load Available Startups From File: " + file.getName());
			return "{\"result\": \"fail\"}";
		}
		LOGGER.info("File Uploaded Successfully: " + file.getName());
		return "{\"result\": \"success\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void processStartupFile(InputStream inputStream) throws Exception{
		Reader reader = new InputStreamReader(inputStream);
		try {
			Iterable<CSVRecord> records = CSVFormat.RFC4180.parse(reader);
			for (CSVRecord record : records) {
			    String startupName = record.get(0);
			    if (startupNameExists(startupName)) continue;
			    String description = record.get(2);
			    description = description.replaceAll("<p>", "");
			    description = description.replaceAll("</p>", "");
			    String sector = record.get(3);
			    String site = record.get(6);
			    String founded = record.get(7);
			    String fundingStage = record.get(8);
			    String productStage = record.get(10);
			    String numberOfEmployees = record.get(11).replaceAll("'", "");
			    AvailableStartup newStartup = new AvailableStartup();
			    newStartup.setStartupName(startupName);
			    newStartup.setSector(sector);
			    newStartup.setMiscellaneous(description);
			    newStartup.setSite(site);
			    newStartup.setFounded(founded);
			    newStartup.setFundingStage(fundingStage);
			    newStartup.setProductStage(productStage);
			    newStartup.setNumberOfEmployees(numberOfEmployees);
			    availableStartupRepository.save(newStartup);
			}
		} catch (Exception e) {
			LOGGER.error("Could Not Load Available Startups From File With Exception: " + e.getMessage());
			throw e;
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private boolean startupNameExists(String startupName) {
		if (startupName.equals("name")){
			return true;
		}
		Startup startup = startupRepository.findDistinctStartupByStartupName(startupName);
		if (startup != null){
			return true;
		}
		AvailableStartup availableStartup = availableStartupRepository.findDistinctAvailableStartupByStartupName(startupName);
		if (availableStartup != null){
			return true;
		}
		return false;
	}
}
