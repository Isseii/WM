package pl.lodz.p.it.isdp.wm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class LocationEditPage extends NavbarPage {

    private final By selectBy = By.name("EditLocationForm:locationType");
    private final By editButtonBy = By.xpath("//input[@class='button']");
    private final By locationInputBy = By.name("EditLocationForm:locationSymbol");

    public LocationEditPage(WebDriver driver, String name) {
        super(driver);
        this.subdomain = "/faces/location/editLocation.xhtml";

        waitForPage();

        waitUntil(d -> {

            WebElement input = d.findElement(locationInputBy);

            return input.isDisplayed() && input.getAttribute("value").equals(name);
        });
    }


    public void setType(String type) {
        Select select = new Select(driver.findElement(selectBy));

        select.selectByVisibleText(type);
    }

    public LocationListPage submit() {

        driver.findElement(editButtonBy).click();

        return new LocationListPage(driver);
    }
}
