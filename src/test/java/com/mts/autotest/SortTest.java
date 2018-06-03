package com.mts.autotest;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import utils.ConfigProperties;

import static com.codeborne.selenide.Selectors.byXpath;

import static com.codeborne.selenide.Selenide.*;

/**
 * Created by Домашний ПК on 03.06.2018.
 */

public class SortTest {

    private static String productNameFirstPage;
    private static String productPriceFirstPage;
    private static String productNameLastPage;
    private static String productPriceLastPage;

    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", ConfigProperties.getTestProperty("chromeDriver"));
        Configuration.browser = "chrome";
        open(ConfigProperties.getTestProperty("url"));

        $(byXpath("//*[@id=\"header-top\"]/div/div[1]/ul/li[1]/div/a[1]")).click();
        //li[i] отвечет за номер категории на сайте
        // изменить номер категории можно в проперти файле
        $(byXpath("//*[@id=\"menu-catalog\"]/li["+ConfigProperties.getTestProperty("categoryNumber")+"]/a")).click();
        $(byXpath("/html/body/div[1]/main/div[2]/a[1]")).click();
        $(byXpath("/html/body/div[1]/main/div[2]/a[1]")).click();
        $(byXpath("//*[@id=\"sort-filter\"]/div[1]/div/button/span[2]")).click();


        SelenideElement listFirstPage = $(getElement(By.className("catalog-items-list")));
        productNameFirstPage = listFirstPage.find(By.className("item"), 0).find(By.tagName("h3")).getText();
        productPriceFirstPage = listFirstPage.find(By.className("item"), 0).find(By.className("price_g")).getText();

        $(byXpath("//*[@id=\"sort-filter\"]/div[1]/div/ul/li[2]/a")).click();
        SelenideElement endButton = $(byXpath("//*[@id=\"catalog-items-page\"]/div[6]/div[5]/div[1]/div[3]/div/span[10]"));
        endButton.click();
        $(By.className("loader hide"));
        sleep(5000);

        SelenideElement listLastPage = $(getElement(By.className("catalog-items-list")));
        int listSize = listLastPage.findAll(By.className("item")).size();
        productNameLastPage = listLastPage.find(By.className("item"), listSize - 1).find(By.tagName("h3")).getText();
        productPriceLastPage = listLastPage.find(By.className("item"), listSize - 1).find(By.className("price_g")).getText();
    }

    @Test
    public void compareNames() {
        Assert.assertEquals(productNameFirstPage, productNameLastPage);
    }

    @Test
    public void comparePrices() {
        Assert.assertEquals(productPriceFirstPage, productPriceLastPage);
    }
}
