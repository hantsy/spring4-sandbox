package com.hantsylabs.example.spring.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TaskListPage extends AbstractPage {

	@FindBy(css="#todolist")
	WebElement todolist;
	
	WebElement doinglist;

	WebElement donelist;
	
	
	@FindBy(css="#todolist button#start")
	WebElement startBtn;
	
	@FindBy(css="#doinglist button#done")
	WebElement doneBtn;
	
	@FindBy(css="#donelist button#del")
	WebElement delBtn;
		
	public TaskListPage(WebDriver driver) {
		super(driver);
	}

	public TaskListPage startTask() {
		this.startBtn.click();
		return TaskListPage.to(driver);
	}
	
	public TaskListPage doneTask() {
		this.doneBtn.click();
		return TaskListPage.to(driver);
	}
	
	public TaskListPage delTask() {
		this.delBtn.click();
		return TaskListPage.to(driver);
	}

	public static TaskListPage to(WebDriver driver) {
		driver.get("http://localhost:8080/mvc-freemarker/tasks");
		return PageFactory.initElements(driver, TaskListPage.class);
	}

}