package com.hantsylabs.example.spring.test.webdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateTaskPage extends AbstractPage {
	private static final Logger log = LoggerFactory.getLogger(CreateTaskPage.class);

	private WebElement name;

	private WebElement description;

	@FindBy(css = "#nameField div.help-block")
	private WebElement titleError;
	
	@FindBy(css = "#descField div.help-block")
	private WebElement descriptionError;

	@FindBy(id = "submitTask")
	private WebElement submit;

	public CreateTaskPage(WebDriver driver) {
		super(driver);
	}

	public TaskListPage newTask(String name, String details) {
		this.name.sendKeys(name);
		this.description.sendKeys(details);
		this.submit.click();
		return PageFactory.initElements(driver, TaskListPage.class);
	}

	public CreateTaskPage newTaskWithEmptyFields() {
		this.name.sendKeys("");
		this.description.sendKeys("");
		this.submit.click();
		return PageFactory.initElements(driver, CreateTaskPage.class);
	}

	public String getTitleError() {
		log.debug(" title error @" + this.titleError.getText());
		return this.titleError.getText();
	}
	
	public String getDescriptionError() {
		log.debug(" description error @" + this.descriptionError.getText());
		return this.descriptionError.getText();
	}


	public static CreateTaskPage to(WebDriver driver) {
		get(driver, "tasks/new");
		return PageFactory.initElements(driver, CreateTaskPage.class);
	}

}