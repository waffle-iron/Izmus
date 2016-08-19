package com.izmus.processes.test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.TopLevelWindow;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.FrameWindow;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class HTMLUnitTest {
	private int currentPageCount = 0;
	private DefaultTableModel informationTable = new DefaultTableModel(new String[] { "companyName",
			"mainContactPerson", "technicalContactPerson", "mainConactPosition", "technicalContactPersonPosition",
			"ownership", "established", "numberOfEmployees", "address", "telephone", "fax", "email", "website",
			"application", "subApplication", "companyOverview", "technology", "targetMarkets" }, 0);

	/*----------------------------------------------------------------------------------------------------*/
	@Test
	public void testHtmlUnit() {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.OFF);
		WebClient webClient = null;
		System.out.println(new Date());
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
			goThroughRows(tableResult, webClient);
			currentPageCount++;
			while (currentPageCount < 10) {
				iframe = getNextPage(tableResult, webClient);
				tableResult = (HtmlTable) iframe.getByXPath("//table[@id='gwResult']").get(0);
				goThroughRows(tableResult, webClient);
				List<TopLevelWindow> windows = webClient.getTopLevelWindows();
				System.out.println(windows.size());
				System.gc();
			}
		} catch (Exception e) {
			webClient.close();
			e.printStackTrace();
			System.out.println(new Date());
			outputToFile();
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	private void outputToFile() {
		try {
			Workbook wb = new XSSFWorkbook();
			FileOutputStream fileOut = new FileOutputStream("/home/lior/test.xlsx");
			String safeName = WorkbookUtil.createSafeSheetName("[Startups]");
			Sheet sheet = wb.createSheet(safeName);
			CreationHelper createHelper = wb.getCreationHelper();
			Vector<Vector<Object>> tableVector = informationTable.getDataVector();
			int rowNumber = 0;
			for (Vector<Object> row : tableVector){
				Row excelRow = sheet.createRow(rowNumber++);
				int cellNumber = 0;
				for (Object cell : row){
					excelRow.createCell(cellNumber++).setCellValue(createHelper.createRichTextString(cell.toString()));
				}
			}
			wb.write(fileOut);
			wb.close();
			fileOut.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private void goThroughRows(HtmlTable tableResult, WebClient webClient) {
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
					((TopLevelWindow)companyDetailPage.getEnclosingWindow().getTopWindow()).close();
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*----------------------------------------------------------------------------------------------------*/
	private void processCompanyDetailPage(HtmlPage companyDetailPage) {
		Document doc = Jsoup.parse(companyDetailPage.asXml());

		String companyName = doc.getElementById("lblLocCompName").text();
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
		
		String[] row = new String[]{
				companyName,
				mainContactPerson,
				technicalContactPerson,
				mainConactPosition,
				technicalContactPersonPosition,
				ownership,
				established,
				numberOfEmployees,
				address,
				telephone,
				fax,
				email,
				website,
				application,
				subApplication,
				companyOverview,
				technology,
				targetMarkets
		};
		System.out.println("Company Name: " + companyName);
		informationTable.addRow(row);
	}

	/*----------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	private HtmlPage getNextPage(HtmlTable tableResult, WebClient webClient) throws Exception {
		int calculatedCurrentPage = getPage(tableResult);
		if (calculatedCurrentPage != currentPageCount) {
			throw new Exception("not synched");
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
		System.out.println("-------------------------------------------------------");
		System.out.println(currentPageCount - 1);
		System.out.println("-------------------------------------------------------");
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
