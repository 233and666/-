package com.campus.mywebtest.tests;
import com.campus.mywebtest.tests.BaseTest;
import com.campus.mywebtest.pages.LoginPage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    @Description("1. 正确的账号密码登录成功")
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "123456");
        Assert.assertTrue(driver.getCurrentUrl().contains("welcome"), "登录成功后应跳转到欢迎页");
    }

    @Test
    @Description("2. 错误的密码导致登录失败")
    public void testLoginWrongPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "wrongpass");
        Assert.assertEquals(loginPage.getErrorMessage(), "用户名或密码错误", "错误提示信息不匹配");
    }

    @Test
    @Description("3. 用户名不存在导致登录失败")
    public void testLoginUserNotExist() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("ghost", "123456");
        Assert.assertEquals(loginPage.getErrorMessage(), "用户名或密码错误", "错误提示信息不匹配");
    }

    @Test
    @Description("4. 用户名为空时拦截登录")
    public void testLoginEmptyUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "123456");
        Assert.assertEquals(loginPage.getErrorMessage(), "用户名或密码错误", "空用户名校验失败");
    }

    @Test
    @Description("5. 密码为空时拦截登录")
    public void testLoginEmptyPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "");
        Assert.assertEquals(loginPage.getErrorMessage(), "用户名或密码错误", "空密码校验失败");
    }
}