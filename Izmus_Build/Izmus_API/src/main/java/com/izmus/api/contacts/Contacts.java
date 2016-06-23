package com.izmus.api.contacts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izmus.data.domain.contacts.GeneralContact;
import com.izmus.data.domain.contacts.IzmusContact;
import com.izmus.data.domain.startups.StartupContact;
import com.izmus.data.repository.IGeneralContactRepository;
import com.izmus.data.repository.IStartupContactRepository;

@RestController
@RequestMapping("api/Contacts")
public class Contacts {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(Contacts.class);
	@Autowired
	private IStartupContactRepository startupContactRepository;
	@Autowired
	private IGeneralContactRepository generalContactRepository;
	@Autowired
	private ObjectMapper jacksonObjectMapper;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/GeneralContacts")
	@PreAuthorize("hasPermission('Contacts', '')")
	public List<GeneralContact> getAllGeneralContacts(){
		List<GeneralContact> allGeneralContacts = generalContactRepository.findAll();
		return allGeneralContacts;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/StartupContacts")
	@PreAuthorize("hasPermission('Contacts', '')")
	public List<StartupContact> getAllStartupContacts(){
		List<StartupContact> allStartupContacts = startupContactRepository.findAll();
		return allStartupContacts;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Contacts', '')")
	public Map<String, List<? extends IzmusContact>> getAllIzmusContacts(){
		HashMap<String, List<? extends IzmusContact>> returnMap = new HashMap<String, List<? extends IzmusContact>>();
		returnMap.put("generalContacts", getAllGeneralContacts());
		returnMap.put("startupContacts", getAllStartupContacts());
		return returnMap;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Contacts', '')")
	public String saveGeneralContact(@RequestParam(value = "contact", required = true) String contactJson) {
		GeneralContact contact = null;
		try {
			contact = jacksonObjectMapper.readValue(contactJson, GeneralContact.class);
			contact = generalContactRepository.save(contact);
			LOGGER.info("Contact Saved Successfully To The Database");
		} catch (Exception e) {
			LOGGER.error("Could Not Save Contact With Error: "
					+ e.getMessage());
			return "{\"result\": \"fail\"}";
		}
		return "{\"result\": \"success\", \"contactId\":" + contact.getContactId() + "}";
	}
}
