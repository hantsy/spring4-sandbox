package com.hantsylabs.example.spring.test.webdriver.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateTaskPage extends AbstractPage {

	@FindBy(id = "name")
	private WebElement name;

	@FindBy(id = "description")
	private WebElement description;

	@FindBy(id = "submitTask")
	private WebElement submit;

	public CreateTaskPage(WebDriver driver) {
		super(driver);
	}

	public TaskListPage newTask(String name, String details) {
		this.name.sendKeys(name);
		this.description.sendKeys(details);
		this.submit.click();
		return TaskListPage.to(driver);
	}

	public CreateTaskPage newTaskWithEmptyFields() {
		this.name.sendKeys("");
		this.description.sendKeys("");
		this.submit.click();
		return CreateTaskPage.to(driver);
	}

	public static CreateTaskPage to(WebDriver driver) {
		get(driver, "tasks/new");
		return PageFactory.initElements(driver, CreateTaskPage.class);
	}

}