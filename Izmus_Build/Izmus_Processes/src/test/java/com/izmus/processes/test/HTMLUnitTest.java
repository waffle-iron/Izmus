package com.izmus.processes.test;


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
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class HTMLUnitTest {
	/*----------------------------------------------------------------------------------------------------*/
	@Test
	public void testHtmlUnit() {
		Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.OFF);
		try {
			final WebClient webClient = new WebClient(BrowserVersion.CHROME);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			final HtmlPage page = webClient.getPage("http://www.matimop.org.il/database.aspx");
			webClient.waitForBackgroundJavaScriptStartingBefore(10000);
			final List<FrameWindow> window = page.getFrames();
			HtmlPage pageTwo = (HtmlPage) window.get(1).getEnclosedPage();
			final HtmlInput searchButton = (HtmlInput) pageTwo.getByXPath("//input[@value='Find']").get(0);
			pageTwo = searchButton.click();
			webClient.waitForBackgroundJavaScriptStartingBefore(10000);
			final HtmlTable tableResult = (HtmlTable) pageTwo.getByXPath("//table[@id='gwResult']").get(0);
			List<HtmlTableRow> rows = tableResult.getRows();
			HtmlAnchor nextPage = (HtmlAnchor) rows.get(0).getByXPath("//a[contains(text(),'...')]").get(0);
			pageTwo = nextPage.click();
			webClient.waitForBackgroundJavaScriptStartingBefore(10000);
 			System.out.println(pageTwo.asText());
			webClient.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
