package com.hantsylabs.example.spring.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AbstractPage {

	@FindBy(css = ".alert")
	WebElement alert;

	public AbstractPage(WebDriver driver) {
		this.driver = driver;
	}

	protected final WebDriver driver;
}
