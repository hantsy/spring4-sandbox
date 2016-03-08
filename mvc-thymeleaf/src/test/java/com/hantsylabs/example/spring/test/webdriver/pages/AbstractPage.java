package com.hantsylabs.example.spring.test.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AbstractPage {

	@FindBy(css = "#main")
	WebElement alertText;

	public AbstractPage(WebDriver driver) {
		this.driver = driver;
	}

	protected final WebDriver driver;
	
	public String getErrors() {
		return alertText.getText();
	}

    static void get(WebDriver driver, String relativeUrl) {
        String url = System.getProperty("geb.build.baseUrl","http://localhost:8080/mvc-freemarker/") + relativeUrl;
        driver.get(url);
    }
}
