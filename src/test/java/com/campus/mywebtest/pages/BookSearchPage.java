package com.campus.mywebtest.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BookSearchPage {
    private WebDriver driver;


    private By searchInput = By.id("searchInput");
    private By searchButton = By.id("searchBtn");
    private By searchResultItems = By.className("book-row");
    private By emptyHint = By.id("noResult");

    public BookSearchPage(WebDriver driver) {
        this.driver = driver;
    }

    public void searchBook(String keyword) {
        driver.findElement(searchInput).clear();
        driver.findElement(searchInput).sendKeys(keyword);
        driver.findElement(searchButton).click();
    }

    public int getResultCount() {
        return driver.findElements(searchResultItems).size();
    }

    public String getEmptyHintMessage() {
        return driver.findElement(emptyHint).getText();
    }
}