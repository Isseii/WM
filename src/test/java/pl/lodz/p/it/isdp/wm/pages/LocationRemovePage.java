package pl.lodz.p.it.isdp.wm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LocationRemovePage extends NavbarPage {

    private final By acceptBy = By.xpath("//input[@class='button']");

    public LocationRemovePage(WebDriver driver, String name) {
        super(driver);
        this.subdomain = "/faces/location/deleteLocation.xhtml";
        waitForPage();

        String questionXpath = "//*[contains(text(), '" + name + "')]";
        waitUntil(d -> d.findElement(By.xpath(questionXpath)).isDisplayed());
    }

    public LocationListPage accept() {

        driver.findElement(acceptBy).click();

        return new LocationListPage(driver);
    }

}
