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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.izmus.data.domain.startups.FinancialIndicator;
import com.izmus.data.domain.startups.FinancialIndicatorType;
import com.izmus.data.domain.startups.IndicatorPoint;
import com.izmus.data.domain.startups.Measurement;
import com.izmus.data.domain.startups.Startup;
import com.izmus.data.domain.startups.StartupContact;
import com.izmus.data.domain.startups.StartupScoreCard;
import com.izmus.data.repository.IFinancialIndicatorRepository;
import com.izmus.data.repository.IFinancialIndicatorTypeRepository;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Component("StartupReport")
public class StartupReport {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(StartupReport.class);
	@Autowired
	private ServletContext context;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private IFinancialIndicatorRepository financialIndicatorRepository;
	@Autowired
	private IFinancialIndicatorTypeRepository financialIndicatorTypeRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public JasperPrint createScoreCardReport(StartupScoreCard scoreCard, Startup startup){
		try {
			InputStream inputStream = context.getResourceAsStream("/WEB-INF/reports/startup-assessment/StartupAssessmentLTR.jasper");
			JasperReport report = (JasperReport) JRLoader.loadObject(inputStream);
			Map<String, Object> parameters = new HashMap<>();
			buildParameters(parameters, scoreCard, startup);
			JasperPrint print = JasperFillManager.fillReport(report, parameters);
			LOGGER.info("Report Created Successfully For Score Card: " + scoreCard);
			return print;
		} catch (Exception e) {
			LOGGER.error("Could Not Create Report For Score Card: " + scoreCard + " With Error: " +  e.getMessage());
			return null;
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void buildParameters(Map<String, Object> parameters, StartupScoreCard scoreCard, Startup startup) {
		parameters.put("scoreCard", scoreCard);
		parameters.put("startup", startup);
		parameters.put("contactsDatasource", createContactDatasource(startup));
		parameters.put("startupLogo", createLogo(startup.getLogo()));
		parameters.put("sector", messageSource.getMessage("navBar.menu.startupAssessment.sector", null,
				LocaleContextHolder.getLocale()));
		parameters.put("address", messageSource.getMessage("navBar.menu.startupAssessment.address", null,
				LocaleContextHolder.getLocale()));
		parameters.put("officePhone", messageSource.getMessage("navBar.menu.startupAssessment.officePhone", null,
				LocaleContextHolder.getLocale()));
		parameters.put("site", messageSource.getMessage("navBar.menu.startupAssessment.site", null,
				LocaleContextHolder.getLocale()));
		parameters.put("measurementsDatasource", createMeasurementsDatasource(scoreCard.getMeasurements()));
		parameters.put("misc", startup.getMiscellaneous());
		parameters.put("indicatorReportMap", getIndicatorReportMap(startup.getStartupId()));
		parameters.put("measurementSubreportPath", context.getRealPath("/") + "/WEB-INF/reports/startup-assessment/StartupAssessmentMeasurementsLTR.jasper");
		parameters.put("contactsSubreportPath", context.getRealPath("/") + "/WEB-INF/reports/startup-assessment/StartupAssessmentContacts.jasper");
		parameters.put("miscSubreportPath", context.getRealPath("/") + "/WEB-INF/reports/startup-assessment/StartupAssessmentMiscLTR.jasper");
		parameters.put("financialsSubreportPath", context.getRealPath("/") + "/WEB-INF/reports/startup-assessment/StartupAssessmentFinancialsLTR.jasper");
		parameters.put("disclaimerSubreportPath", context.getRealPath("/") + "/WEB-INF/reports/disclaimer/DisclaimerLTR.jasper");
		parameters.put("statementReportPath", context.getRealPath("/") + "/WEB-INF/reports/startup-assessment/StartupAssessmentStatementOfIncome.jasper");
		parameters.put("disclaimerLogo", context.getRealPath("/") + "/META-INF/views-public/core/logo/logowhite.jpg");
	}
	/*----------------------------------------------------------------------------------------------------*/
	private HashMap<String, JRTableModelDataSource> getIndicatorReportMap(Integer startupId) {
		HashMap<String, JRTableModelDataSource> returnMap = new HashMap<>();
		List<FinancialIndicator> startupFinancialIndicators = financialIndicatorRepository.findFinancialIndicatorByStartupId(startupId);
		List<FinancialIndicatorType> indicatorTypes = financialIndicatorTypeRepository.findAll();
		DefaultTableModel statementCrossTabTable = new DefaultTableModel(new Object[]{"row", "column", "value"}, 0);
		DefaultTableModel assetsCrossTabTable = new DefaultTableModel(new Object[]{"row", "column", "value"}, 0);
		for (FinancialIndicator indicator : startupFinancialIndicators){
			FinancialIndicatorType indicatorType = getFinancialIndicatorType(indicator, indicatorTypes);
			if (indicatorType == null) continue;
			switch (indicatorType.getReportName()){
			case "Statement of Income":
				addIndicatorsToTable(indicator, indicatorType, statementCrossTabTable);
				break;
			case "Assets and Liabilities":
				addIndicatorsToTable(indicator, indicatorType, assetsCrossTabTable);
				break;
			}
		}
		returnMap.put("statementOfIncome", new JRTableModelDataSource(statementCrossTabTable));
		returnMap.put("assetsAndLiabilities", new JRTableModelDataSource(assetsCrossTabTable));
		return returnMap;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void addIndicatorsToTable(FinancialIndicator indicator, FinancialIndicatorType indicatorType,
			DefaultTableModel crossTabTable) {
		for (IndicatorPoint point : indicator.getPoints()){
			String indicatorTypeName = indicatorType.getName();
			Integer indicatorTypeOrder = indicatorType.getOrderInReport();
			crossTabTable.addRow(new Object[]{indicatorTypeOrder + ". " + indicatorTypeName, point.getPeriod(), point.getValue()});
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	private FinancialIndicatorType getFinancialIndicatorType(FinancialIndicator indicator,
			List<FinancialIndicatorType> indicatorTypes) {
		for (FinancialIndicatorType indicatorType : indicatorTypes){
			if (indicatorType.getTypeId().equals(indicator.getTypeId())){
				return indicatorType;
			}
		}
		return null;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private JRTableModelDataSource createContactDatasource(Startup startup) {
		DefaultTableModel contactTableModel = new DefaultTableModel(new Object[]{"firstContactName", "firstContactPosition", "firstContactAvatar",
				"secondContactName", "secondContactPosition", "secondContactAvatar",
				"thirdContactName", "thirdContactPosition", "thirdContactAvatar"}, 0);
		for (Iterator<StartupContact> iterator = startup.getContacts().iterator(); iterator.hasNext();){
			StartupContact firstContact = iterator.next();
			StartupContact secondContact = null;
			StartupContact thirdContact = null;
			if (iterator.hasNext()){
				secondContact = iterator.next();
			}
			if (iterator.hasNext()){
				thirdContact = iterator.next();
			}
			Object[] row = new Object[9];
			if (firstContact != null){
				row[0] = firstContact.getFirstName() + " " + firstContact.getLastName();
				row[1] = firstContact.getPosition();
				row[2] = createLogo(firstContact.getContactAvatar());
			}
			if (secondContact != null){
				row[3] = secondContact.getFirstName() + " " + secondContact.getLastName();
				row[4] = secondContact.getPosition();
				row[5] = createLogo(secondContact.getContactAvatar());
			}
			if (thirdContact != null){
				row[6] = thirdContact.getFirstName() + " " + thirdContact.getLastName();
				row[7] = thirdContact.getPosition();
				row[8] = createLogo(thirdContact.getContactAvatar());
			}
			contactTableModel.addRow(row);
		}
		JRTableModelDataSource returnDatasource = new JRTableModelDataSource(contactTableModel);
		return returnDatasource;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private JRTableModelDataSource createMeasurementsDatasource(Set<Measurement> measurements) {
		DefaultTableModel tableModelDatasource = new DefaultTableModel(new String[]{"firstMeasurement", "secondMeasurement"}, 0);
		for (Iterator<Measurement> iterator = measurements.iterator(); iterator.hasNext();){
			Measurement firstMeasurement = iterator.next();
			Measurement secondMeasurement = null;
			if (iterator.hasNext()){
				secondMeasurement = iterator.next();
			}
			tableModelDatasource.addRow(new Object[]{firstMeasurement, secondMeasurement});
		}
		JRTableModelDataSource returnDataSource = new JRTableModelDataSource(tableModelDatasource);
		return returnDataSource;
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
