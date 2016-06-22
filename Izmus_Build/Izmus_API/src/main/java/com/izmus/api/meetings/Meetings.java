package com.izmus.api.meetings;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izmus.data.domain.meetings.GeneralMeeting;
import com.izmus.data.domain.meetings.IzmusMeeting;
import com.izmus.data.domain.meetings.MeetingReport;
import com.izmus.data.domain.startups.StartupMeeting;
import com.izmus.data.domain.users.User;
import com.izmus.data.repository.IGeneralMeetingRepository;
import com.izmus.data.repository.IMeetingReportRepository;
import com.izmus.data.repository.IStartupMeetingRepository;
import com.izmus.reports.meetings.MeetingSummaryReport;

import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("api/Meetings")
public class Meetings {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(Meetings.class);
	@Autowired
	private IStartupMeetingRepository startupMeetingRepository;
	@Autowired
	private IGeneralMeetingRepository generalMeetingRepository;
	@Autowired
	private ObjectMapper jacksonObjectMapper;
	@Autowired
	private MeetingSummaryReport meetingSummaryReport;
	@Autowired
	private IMeetingReportRepository meetingReportRepository;
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
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(value = "/MeetingReport", method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Meetings', '')")
	public String getMeetingReport(@RequestParam(value = "meeting", required = true) String meetingJson) {
		GeneralMeeting meeting = null;
		MeetingReport meetingReport = null;
		try {
			meeting = jacksonObjectMapper.readValue(meetingJson, GeneralMeeting.class);
			JasperPrint report = meetingSummaryReport.createMeetingReport(meeting);
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			meetingReport = new MeetingReport();
			meetingReport.setCreatingUserId(user.getUserId());
			meetingReport.setReport(report);
			meetingReport.setMeetingId(meeting.getMeetingId());
			meetingReport.setReportDate(new Date());
			meetingReport.setReportName("IZMUS_Meeting_Report_"	+ new SimpleDateFormat("yyyyMMdd").format(meetingReport.getReportDate()));
			meetingReport = meetingReportRepository.save(meetingReport);
			LOGGER.info("Meeting Report Saved Successfully To The Database");
		} catch (Exception e) {
			LOGGER.error("Could Not Create Meeting Report With Error: "
					+ e.getMessage());
		}
		return "{\"reportId\": \"" + meetingReport.getReportId() + "\"}";
	}
	/*----------------------------------------------------------------------------------------------------*/
	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasPermission('Meetings', '')")
	public String saveGeneralMeeting(@RequestParam(value = "meeting", required = true) String meetingJson) {
		GeneralMeeting meeting = null;
		try {
			meeting = jacksonObjectMapper.readValue(meetingJson, GeneralMeeting.class);
			meeting = generalMeetingRepository.save(meeting);
			LOGGER.info("Meeting Saved Successfully To The Database");
		} catch (Exception e) {
			LOGGER.error("Could Not Save Meeting With Error: "
					+ e.getMessage());
			return "{\"result\": \"fail\"}";
		}
		return "{\"result\": \"success\", \"meetingId\":" + meeting.getMeetingId() + "}";
	}
}
