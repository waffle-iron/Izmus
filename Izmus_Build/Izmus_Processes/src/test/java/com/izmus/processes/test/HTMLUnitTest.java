package com.izmus.processes.test;


import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.FrameWindow;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class HTMLUnitTest {
	private int currrentPageCount = 0;
	/*----------------------------------------------------------------------------------------------------*/
	@Test
	public void testHtmlUnit() {
		Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
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
			currrentPageCount++;
			while (true){
				iframe = getNextPage(tableResult, webClient);
				tableResult = (HtmlTable) iframe.getByXPath("//table[@id='gwResult']").get(0);
			}
		} catch (Exception e) {
			webClient.close();
			System.out.println(currrentPageCount);
			System.out.println(new Date());
		}
	}
	/*----------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	private HtmlPage getNextPage(HtmlTable tableResult, WebClient webClient) throws Exception{
		int calculatedCurrentPage = getPage(tableResult);
		if (calculatedCurrentPage != currrentPageCount){ 
			throw new Exception("not synched");
		}
		List<HtmlTableRow> rows = tableResult.getRows();
		HtmlAnchor nextPageClick;
		try {
			nextPageClick = (HtmlAnchor) rows.get(0).getByXPath("//a[contains(text(),'" + (calculatedCurrentPage + 1) + "')]").get(0);
		} catch (Exception e) {
			List<HtmlAnchor> nextPageList= (List<HtmlAnchor>) rows.get(0).getByXPath("//a[contains(text(),'...')]");
			if (nextPageList.size() > 1){
				nextPageClick = nextPageList.get(1);
			}
			else {
				nextPageClick = nextPageList.get(0);
			}
		}
		HtmlPage nextPage = nextPageClick.click();
		webClient.waitForBackgroundJavaScriptStartingBefore(1000);
		currrentPageCount++;
		System.out.println(currrentPageCount);
		return nextPage;
	}
	/*----------------------------------------------------------------------------------------------------*/
	private int getPage(HtmlTable tableResult) {
		List<HtmlTableRow> rows = tableResult.getRows();
		HtmlSpan currentPage = (HtmlSpan) rows.get(0).getByXPath("//span").get(1);
		int returnPageNumber;
		try {
			returnPageNumber = Integer.valueOf(currentPage.getTextContent());
		}
		catch (Exception e){
			returnPageNumber = 0;
		}
		return returnPageNumber;
	}
}
