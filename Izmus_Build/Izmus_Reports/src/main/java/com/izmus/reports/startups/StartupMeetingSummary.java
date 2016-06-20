package com.izmus.reports.startups;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.startups.Startup;
import com.izmus.data.domain.startups.StartupMeeting;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Component("StartupMeetingSummary")
public class StartupMeetingSummary {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(StartupMeetingSummary.class);
	@Autowired
	private ServletContext context;
	@Autowired
	private MessageSource messageSource;
	/*----------------------------------------------------------------------------------------------------*/
	public JasperPrint createMeetingSummaryReport(StartupMeeting meeting, Startup startup){
		try {
			InputStream inputStream = context.getResourceAsStream("/WEB-INF/reports/startup-meeting/StartupMeetingLTR.jasper");
			JasperReport report = (JasperReport) JRLoader.loadObject(inputStream);
			Map<String, Object> parameters = new HashMap<>();
			buildParameters(parameters, meeting, startup);
			JasperPrint print = JasperFillManager.fillReport(report, parameters);
			LOGGER.info("Report Created Successfully For Meeting: " + meeting);
			return print;
		} catch (Exception e) {
			LOGGER.error("Could Not Create Report For Meeting: " + meeting + " With Error: " +  e.getMessage());
			return null;
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void buildParameters(Map<String, Object> parameters, StartupMeeting meeting, Startup startup) {
		parameters.put("startupLogo", createLogo(startup.getLogo()));
		parameters.put("startup", startup);
		parameters.put("meeting", meeting);
		parameters.put("sector", messageSource.getMessage("navBar.menu.startupAssessment.sector", null,
				LocaleContextHolder.getLocale()));
		parameters.put("address", messageSource.getMessage("navBar.menu.startupAssessment.address", null,
				LocaleContextHolder.getLocale()));
		parameters.put("officePhone", messageSource.getMessage("navBar.menu.startupAssessment.officePhone", null,
				LocaleContextHolder.getLocale()));
		parameters.put("site", messageSource.getMessage("navBar.menu.startupAssessment.site", null,
				LocaleContextHolder.getLocale()));
	}
	/*----------------------------------------------------------------------------------------------------*/
	private Object createLogo(String image) {
		BufferedImage output = null;
		try {
			InputStream stream = new ByteArrayInputStream(Base64.decodeBase64((image.split(",")[1]).getBytes()));
			BufferedImage logoImage = ImageIO.read(stream);
			int w = logoImage.getWidth();
		    int h = logoImage.getHeight();
		    output = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g2 = output.createGraphics();
		    g2.setComposite(AlphaComposite.Src);
		    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g2.setColor(new Color(160, 195, 215));
		    g2.setColor(Color.WHITE);
		    g2.fillOval(0, 0, w, h);
		    g2.setComposite(AlphaComposite.SrcAtop);
		    g2.drawImage(logoImage, 0, 0, null);
		    g2.setColor(new Color(160, 195, 215));
		    g2.setStroke(new BasicStroke(6));
		    g2.drawOval(0, 0, w-1, h-1);
		    g2.dispose();
		} catch (Exception e) {
			LOGGER.error("Could Not Create Logo Image For Image With Error: " +  e.getMessage());
		}
		return output;
	}
}
