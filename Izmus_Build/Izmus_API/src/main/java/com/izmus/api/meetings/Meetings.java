package com.izmus.api.meetings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.izmus.data.domain.meetings.GeneralMeeting;
import com.izmus.data.domain.meetings.IzmusMeeting;
import com.izmus.data.domain.startups.StartupMeeting;
import com.izmus.data.repository.IGeneralMeetingRepository;
import com.izmus.data.repository.IStartupMeetingRepository;

@RestController
@RequestMapping("api/Meetings")
public class Meetings {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(Meetings.class);
	@Autowired
	private IStartupMeetingRepository startupMeetingRepository;
	@Autowired
	private IGeneralMeetingRepository generalMeetingRepository;
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/GeneralMeetings")
	@PreAuthorize("hasPermission('Meetings', '')")
	public List<GeneralMeeting> getAllGeneralMeetings(){
		List<GeneralMeeting> allGeneralMeetings = generalMeetingRepository.findAll();
		return allGeneralMeetings;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET, value = "/StartupMeetings")
	@PreAuthorize("hasPermission('Meetings', '')")
	public List<StartupMeeting> getAllStartupMeetings(){
		List<StartupMeeting> allStartupMeetings = startupMeetingRepository.findAll();
		return allStartupMeetings;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasPermission('Meetings', '')")
	public Map<String, List<? extends IzmusMeeting>> getAllIzmusMeetings(){
		HashMap<String, List<? extends IzmusMeeting>> returnMap = new HashMap<String, List<? extends IzmusMeeting>>();
		returnMap.put("generalMeetings", getAllGeneralMeetings());
		returnMap.put("startupMeetings", getAllStartupMeetings());
		return returnMap;
	}
}
