package pl.lodz.p.it.isdp.wm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pl.lodz.p.it.isdp.wm.Config;

public class NavbarPage extends BasePage {

    private final int locationPosition = 3;
    private final int registerPosition = 2;
    private final int usersPosition = 3;
    private final int loginPosition = 1;

    private final By navbarBy = By.className("navbar");
    private final By navbarIdBy = By.id("myNavbar");
    private final By navbarItemsBy = By.xpath("./ul[1]/li");
    private final By dropdownItemsBy = By.xpath(".//ul/li");
    private final By footerTextBy = By.tagName("h4");
    private final By confirmButtonBy = By.xpath("//input[@class='button']");
    private final By logoutItemBy = By.xpath("./ul[2]/li");


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


    public UserRegisterPage clickRegisterUser() {
        WebElement option = getNavbarOptionByIndex(registerPosition);

        option.click();

        return new UserRegisterPage(driver);
    }

    public UserNewListPage clickUserNewList() {
        WebElement dropdown = getNavbarOptionByIndex(usersPosition);

        dropdown.click();

        dropdown
                .findElements(dropdownItemsBy)
                .get(0)
                .click();

        return new UserNewListPage(driver);
    }

    public UserAuthorizedListPage clickUserAuthorizedList() {

        WebElement dropdown = getNavbarOptionByIndex(usersPosition);

        dropdown.click();

        dropdown
                .findElements(dropdownItemsBy)
                .get(1)
                .click();

        return new UserAuthorizedListPage(driver);
    }

    public NavbarPage performLogout() {

        waitUntil(d -> d.findElement(navbarIdBy).isDisplayed());

        WebElement navbar = driver.findElement(navbarIdBy);

        navbar.findElement(logoutItemBy).click();

        String url = Config.subdomain("/faces/common/logout.xhtml");
        waitUntil(d -> d.getCurrentUrl().equals(url));

        driver.findElement(confirmButtonBy).click();

        return new NavbarPage(driver);
    }

    public LoginPage clickLogin() {

        WebElement option = getNavbarOptionByIndex(loginPosition);

        option.click();

        return new LoginPage(driver);
    }

    public String getCurrentLogin() {
        String text = driver.findElement(footerTextBy).getText();

        int position = text.indexOf(": ");

        return text.substring(position + 2);
    }

}
