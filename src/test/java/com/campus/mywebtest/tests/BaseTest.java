package com.campus.mywebtest.tests;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.nio.file.Paths;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // 1. 配置自定义的 Chrome 浏览器路径
        ChromeOptions options = new ChromeOptions();
        options.setBinary("F:\\chrome-win64\\chrome.exe");

        // 启动 Chrome 浏览器
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        // System.getProperty("user.dir") 获取当前项目的根目录
        String loginPageUrl = Paths.get(System.getProperty("user.dir"),
                "src", "test", "resources", "pages", "login.html").toUri().toString();

        // 访问本地网页
        driver.get(loginPageUrl);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        // 失败自动截图并附加到 Allure
        if (ITestResult.FAILURE == result.getStatus()) {
            takeScreenshot(driver);
        }
        // 释放资源，关闭浏览器
        if (driver != null) {
            driver.quit();
        }
    }

    @Attachment(value = "失败时的页面截图", type = "image/png")
    public byte[] takeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}