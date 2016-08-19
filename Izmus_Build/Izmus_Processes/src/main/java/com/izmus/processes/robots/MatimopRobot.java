package com.izmus.processes.robots;

import java.io.IOException;
import java.util.List;

import org.activiti.engine.runtime.Execution;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.TopLevelWindow;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.FrameWindow;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.izmus.data.domain.startups.AvailableStartup;
import com.izmus.data.domain.startups.Startup;
import com.izmus.data.repository.IAvailableStartupRepository;
import com.izmus.data.repository.IStartupRepository;

@Component("MatimopRobot")
public class MatimopRobot {
	/*----------------------------------------------------------------------------------------------------*/
	private static final Logger LOGGER = LoggerFactory.getLogger(MatimopRobot.class);
	private int currentPageCount;
	@Autowired
	private IStartupRepository startupRepository;
	@Autowired
	private IAvailableStartupRepository availableStartupRepository;
	/*----------------------------------------------------------------------------------------------------*/
	public void execute(Execution execution) {
		Thread robotThread = new Thread(new Runnable() {
			public void run() {
				WebClient webClient = null;
				currentPageCount = 0;
				LOGGER.info("Matimop Robot Started Fetching Data");
				try {
					webClient = new WebClient(BrowserVersion.CHROME);
					webClient.setAjaxController(new NicelyResynchronizingAjaxController());
					webClient.getOptions().setThrowExceptionOnScriptError(false);
					final HtmlPage page = webClient.getPage("http://www.matimop.org.il/database.aspx");
					webClient.waitForBackgroundJavaScriptStartingBefore(1000);
					final List<FrameWindow> window = page.getFrames();
					HtmlPage iframe = (HtmlPage) window.get(1).getEnclosedPage();
					final HtmlInput searchButton = (HtmlInput) iframe.getByXPath("//input[@value='Find']").get(0);
					iframe = searchButton.click();
					webClient.waitForBackgroundJavaScriptStartingBefore(1000);
					HtmlTable tableResult = (HtmlTable) iframe.getByXPath("//table[@id='gwResult']").get(0);
					currentPageCount++;
					goThrowRows(tableResult, webClient);
					while (true) {
						iframe = getNextPage(tableResult, webClient);
						tableResult = (HtmlTable) iframe.getByXPath("//table[@id='gwResult']").get(0);
						goThrowRows(tableResult, webClient);
						System.gc();
					}
				} catch (Exception e) {
					webClient.close();
					if (currentPageCount > 100) {
						LOGGER.info("The robot completed 100 pages successfully");
					} else {
						LOGGER.info("There was an error in the matimop robot process: " + e.getMessage());
					}
				}
			}
		});
		robotThread.setName("Matimop Robot");
		robotThread.start();
	}
	/*----------------------------------------------------------------------------------------------------*/
	private void goThrowRows(HtmlTable tableResult, WebClient webClient) {
		LOGGER.info("Going Through Page: " + currentPageCount);
		List<HtmlTableRow> rows = tableResult.getRows();
		for (int rowNumber = 2; rowNumber < rows.size() - 1; rowNumber++) {
			try {
				HtmlTableRow currentRow = rows.get(rowNumber);
				HtmlTableCell linkCell = currentRow.getCell(0);
				for (DomElement element : linkCell.getChildElements()) {
					HtmlAnchor companyDetailLick = (HtmlAnchor) element;
					HtmlPage companyDetailPage = companyDetailLick.click();
					webClient.waitForBackgroundJavaScriptStartingBefore(1000);
					processCompanyDetailPage(companyDetailPage);
					companyDetailPage.cleanUp();
					((TopLevelWindow)companyDetailPage.getEnclosingWindow().getTopWindow()).close();
					break;
				}
			} catch (IOException e) {
				LOGGER.error(e.getMessage());
			}
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private void processCompanyDetailPage(HtmlPage companyDetailPage) {
		Document doc = Jsoup.parse(companyDetailPage.asXml());

		String companyName = doc.getElementById("lblLocCompName").text();
		
		if (startupNameExists(companyName)) return;
		
		String mainContactPerson = doc.getElementById("lblMainContact1PersonName").text();
		String technicalContactPerson = doc.getElementById("lblTechnicalContactPersonName").text();
		String mainConactPosition = doc.getElementById("lblMainContact1Position").text();
		String technicalContactPersonPosition = doc.getElementById("lblTechnicalContactPersonPosition").text();
		String ownership = doc.getElementById("lblOwnership").text();
		String established = doc.getElementById("lblEstablished").text();
		String numberOfEmployees = doc.getElementById("lblNumberOfEmployees").text();

		String address = doc.getElementById("lblAddress").text();
		String telephone = doc.getElementById("lblPhone").text();
		String fax = doc.getElementById("lblFax").text();
		String email = doc.getElementById("lblEmail").text();
		String website = doc.getElementById("hplSite").attr("href");

		String application = doc.getElementById("lblApplication").text();
		String subApplication = doc.getElementById("lblSubApplication").text();

		String companyOverview = doc.getElementById("lblDesc").text();

		String technology = doc.getElementById("lblTechnology").text();

		String targetMarkets = doc.getElementById("lblTargetMarkets").text();
		
		AvailableStartup newStartup = new AvailableStartup();
	    newStartup.setStartupName(companyName);
	    newStartup.setSector(application);
	    newStartup.setMiscellaneous(companyOverview);
	    newStartup.setSite(website);
	    newStartup.setFounded(established);
	    newStartup.setNumberOfEmployees(numberOfEmployees);
	    LOGGER.info("Adding New Startup: " + newStartup);
	    availableStartupRepository.save(newStartup);
	}
	/*----------------------------------------------------------------------------------------------------*/
	private boolean startupNameExists(String startupName) {
		String[] split = startupName.split(" ");
		startupName = split[0] + (split.length > 1 ? " " : "");
		if (startupName.equals("name")){
			return true;
		}
		List<Startup> startups = startupRepository.findByStartupNameIgnoreCaseContaining(startupName);
		if (startups != null && startups.size() > 0){
			return true;
		}
		List<AvailableStartup> availableStartups = availableStartupRepository.findByStartupNameIgnoreCaseContaining(startupName);
		if (availableStartups != null && availableStartups.size() > 0){
			return true;
		}
		return false;
	}
	/*----------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	private HtmlPage getNextPage(HtmlTable tableResult, WebClient webClient) throws Exception {
		int calculatedCurrentPage = getPage(tableResult);
		if (calculatedCurrentPage != currentPageCount) {
			throw new Exception("page not synched");
		}
		List<HtmlTableRow> rows = tableResult.getRows();
		HtmlAnchor nextPageClick;
		try {
			nextPageClick = (HtmlAnchor) rows.get(0)
					.getByXPath("//a[contains(text(),'" + (calculatedCurrentPage + 1) + "')]").get(0);
		} catch (Exception e) {
			List<HtmlAnchor> nextPageList = (List<HtmlAnchor>) rows.get(0).getByXPath("//a[contains(text(),'...')]");
			if (nextPageList.size() > 1) {
				nextPageClick = nextPageList.get(1);
			} else {
				nextPageClick = nextPageList.get(0);
			}
		}
		HtmlPage nextPage = nextPageClick.click();
		webClient.waitForBackgroundJavaScriptStartingBefore(1000);
		currentPageCount++;
		return nextPage;
	}

	/*----------------------------------------------------------------------------------------------------*/
	private int getPage(HtmlTable tableResult) {
		List<HtmlTableRow> rows = tableResult.getRows();
		HtmlSpan currentPage = (HtmlSpan) rows.get(0).getByXPath("//span").get(1);
		int returnPageNumber;
		try {
			returnPageNumber = Integer.valueOf(currentPage.getTextContent());
		} catch (Exception e) {
			returnPageNumber = 0;
		}
		return returnPageNumber;
	}
}
