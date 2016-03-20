package com.hantsylabs.example.spring.test.webdriver.pages;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractPage {
	private final static Logger log = LoggerFactory.getLogger(AbstractPage.class);

	@FindBy(css= "#alert p")
	WebElement alert;

	@FindBy(css = ".page-header h1")
	WebElement pageHeader;

	protected final WebDriver driver;

	public AbstractPage(WebDriver driver) {
		this.driver = driver;
	}

	public String getErrors() {
		log.debug("=======alert element ===========@@" + alert.getText());
		return alert.getText();
	}

	public String getPageTitle() {
		return pageHeader.getText().trim();
	}

	static void get(WebDriver driver, String relativeUrl) {
		String url = System.getProperty("geb.build.baseUrl", "http://localhost:8080/") + relativeUrl;
		driver.get(url);
	}
}
