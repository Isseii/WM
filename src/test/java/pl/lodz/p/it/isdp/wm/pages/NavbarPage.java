package pl.lodz.p.it.isdp.wm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NavbarPage extends BasePage {

    private final int locationPosition = 3;

    private final By navbarBy = By.className("navbar");
    private final By navbarIdBy = By.id("myNavbar");
    private final By navbarItemsBy = By.xpath("./ul[1]/li");
    private final By dropdownItemsBy = By.xpath(".//ul/li");


    public NavbarPage(WebDriver driver) {
        super(driver);

        waitUntil(d -> d.findElement(navbarBy).isDisplayed());
    }

    protected WebElement getNavbarOptionByIndex(int index) {

        waitUntil(d -> d.findElement(navbarIdBy).isDisplayed());

        WebElement navbar = driver.findElement(navbarIdBy);

        return navbar.findElements(navbarItemsBy).get(index);
    }

    public LocationNewPage clickNewLocation() {


        WebElement dropdown = getNavbarOptionByIndex(locationPosition);

        dropdown.click();

        dropdown
                .findElements(dropdownItemsBy)
                .get(0)
                .click();

        return new LocationNewPage(driver);
    }

    public LocationListPage clickListLocation() {

        WebElement dropdown = getNavbarOptionByIndex(locationPosition);

        dropdown.click();

        dropdown
                .findElements(dropdownItemsBy)
                .get(1)
                .click();


        return new LocationListPage(driver);
    }

}
