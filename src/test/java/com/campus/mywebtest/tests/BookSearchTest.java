package com.campus.mywebtest.tests;

import com.campus.mywebtest.tests.BaseTest;
import com.campus.mywebtest.pages.BookSearchPage;
import com.campus.mywebtest.pages.LoginPage;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BookSearchTest extends BaseTest {
    
    @BeforeMethod
    public void loginBeforeSearch() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin", "123456"); // 先执行登录，跳转到欢迎页
    }

    @Test
    @Description("1. 精确搜索存在的图书名称")
    public void testSearchExactMatch() {
        BookSearchPage searchPage = new BookSearchPage(driver);
        searchPage.searchBook("软件测试的艺术");
        Assert.assertEquals(searchPage.getResultCount(), 1, "精确搜索应该只返回 1 条结果");
    }

    @Test
    @Description("2. 模糊搜索匹配多本图书")
    public void testSearchPartialMatch() {
        BookSearchPage searchPage = new BookSearchPage(driver);
        searchPage.searchBook("软件测试");
        Assert.assertTrue(searchPage.getResultCount() > 1, "模糊搜索应该返回多条包含'软件测试'的结果");
    }

    @Test
    @Description("3. 搜索不存在的图书")
    public void testSearchNoMatch() {
        BookSearchPage searchPage = new BookSearchPage(driver);
        searchPage.searchBook("这是一本绝对不存在的书12345");
        Assert.assertEquals(searchPage.getResultCount(), 0, "不存在的书返回结果应为 0");
        Assert.assertEquals(searchPage.getEmptyHintMessage(), "未找到匹配的图书", "未找到书籍的提示语不正确");
    }

    @Test
    @Description("4. 搜索框为空时执行搜索")
    public void testSearchEmpty() {
        BookSearchPage searchPage = new BookSearchPage(driver);
        searchPage.searchBook("");
        Assert.assertTrue(searchPage.getResultCount() > 5, "搜索框为空应返回默认图书列表");
    }

    @Test
    @Description("5. 包含特殊字符的搜索")
    public void testSearchSpecialCharacters() {
        BookSearchPage searchPage = new BookSearchPage(driver);
        searchPage.searchBook("@#$%^&*");
        Assert.assertEquals(searchPage.getResultCount(), 0, "特殊字符通常匹配不到书名，返回应为0");
    }
}