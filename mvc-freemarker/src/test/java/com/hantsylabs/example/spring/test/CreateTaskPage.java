package com.hantsylabs.example.spring.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateTaskPage extends AbstractPage {

	private WebElement name;
	private WebElement description;

	@FindBy(css = "button[type=submit]")
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
		driver.get("http://localhost:8080/mvc-freemarker/tasks/new");
		return PageFactory.initElements(driver, CreateTaskPage.class);
	}

}