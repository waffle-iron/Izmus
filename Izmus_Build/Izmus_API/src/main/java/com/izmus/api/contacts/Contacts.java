package com.izmus.api.contacts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izmus.data.domain.contacts.FinderContact;
import com.izmus.data.domain.contacts.GeneralContact;
import com.izmus.data.domain.contacts.InvestorContact;
import com.izmus.data.domain.contacts.IzmusContact;
import com.izmus.data.domain.startups.StartupContact;
import com.izmus.data.domain.users.IzmusFinder;
import com.izmus.data.domain.users.IzmusInvestor;
import com.izmus.data.repository.IFinderContactRepository;
import com.izmus.data.repository.IGeneralContactRepository;
import com.izmus.data.repository.IInvestorContactRepository;
import com.izmus.data.repository.IIzmusFinderRepository;
import com.izmus.data.repository.IIzmusInvestorRepository;
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
	private IFinderContactRepository finderContactRepository;
	@Autowired
	private IInvestorContactRepository investorContactRepository;
	@Autowired
	private IIzmusFinderRepository izmusFinderRepository;
	@Autowired
	private IIzmusInvestorRepository izmusInvestorRepository;
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
	@RequestMapping(method = RequestMethod.GET, value = "/FinderContacts")
	@PreAuthorize("hasPermission('Contacts', '')")
	public List<FinderContact> getAllFinderContacts(){
		List<FinderContact> allFinderContacts = finderContactRepository.findAll();
		for (FinderContact contact: allFinderContacts){
			if (contact.getEntityId() != null){
				IzmusFinder finderEntity = izmusFinderRepository.findDistinctIzmusFinderByEntityId(contact.getEntityId());
				contact.setContactAvatar(finderEntity.getUser().getUserAvatar());
			}
		}
		return allFinderContacts;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/InvestorContacts")
	@PreAuthorize("hasPermission('Contacts', '')")
	public List<InvestorContact> getAllInvestorContacts(){
		List<InvestorContact> allInvestorContacts = investorContactRepository.findAll();
		for (InvestorContact contact: allInvestorContacts){
			if (contact.getEntityId() != null){
				IzmusInvestor investorEntity = izmusInvestorRepository.findDistinctIzmusInvestorByEntityId(contact.getEntityId());
				contact.setContactAvatar(investorEntity.getUser().getUserAvatar());
			}
		}
		return allInvestorContacts;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/InvestorContacts/{contactId}")
	@PreAuthorize("hasPermission('Contacts', '')")
	public InvestorContact getInvestorContact(@PathVariable("contactId") Integer contactId){
		InvestorContact returnInvestor = null;
		try {
			returnInvestor = investorContactRepository.findDistinctInvestorContactByContactId(contactId);
		} catch (Exception e) {
			LOGGER.error("Could Not Load Contact With Error: "
					+ e.getMessage());
		}
		return returnInvestor;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.POST, value = "/InvestorContacts")
	@PreAuthorize("hasPermission('Contacts', '')")
	public String saveInvestorContacts(@RequestParam(value = "investorContact", required = true) String investorContactJson){
		InvestorContact contact = null;
		try {
			contact = jacksonObjectMapper.readValue(investorContactJson, InvestorContact.class);
			contact = investorContactRepository.save(contact);
			LOGGER.info("Contact Saved Successfully To The Database");
		} catch (Exception e) {
			LOGGER.error("Could Not Save Contact With Error: "
					+ e.getMessage());
			return "{\"result\": \"fail\"}";
		}
		return "{\"result\": \"success\", \"contactId\":" + contact.getContactId() + "}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/StartupContacts")
	@PreAuthorize("hasPermission('Contacts', '')")
	public List<StartupContact> getAllStartupContacts(){
		List<StartupContact> allStartupContacts = startupContactRepository.findAll();
		for (StartupContact contact : allStartupContacts){
			contact.setCompanyAvatar(contact.getStartup().getLogo());
			contact.setCompanyName(contact.getStartup().getStartupName());
		}
		return allStartupContacts;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/StartupContacts/{contactId}")
	@PreAuthorize("hasPermission('Contacts', '')")
	public StartupContact getStartupContact(@PathVariable("contactId") Integer contactId){
		StartupContact returnContact = null;
		try {
			returnContact = startupContactRepository.findDistinctStartupContactByContactId(contactId);
		} catch (Exception e) {
			LOGGER.error("Could Not Load Contact With Error: "
					+ e.getMessage());
		}
		return returnContact;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Contacts', '')")
	public Map<String, List<? extends IzmusContact>> getAllIzmusContacts(){
		HashMap<String, List<? extends IzmusContact>> returnMap = new HashMap<String, List<? extends IzmusContact>>();
		returnMap.put("generalContacts", getAllGeneralContacts());
		returnMap.put("startupContacts", getAllStartupContacts());
		returnMap.put("finderContacts", getAllFinderContacts());
		returnMap.put("investorContacts", getAllInvestorContacts());
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
