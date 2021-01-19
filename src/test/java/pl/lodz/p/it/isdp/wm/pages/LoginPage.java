package pl.lodz.p.it.isdp.wm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By loginBy = By.name("j_username");
    private final By passwordBy = By.name("j_password");

    private final By loginButtonBy = By.xpath("//input[@type='submit']");

    public LoginPage(WebDriver driver) {
        super(driver);
        this.subdomain = "/faces/common/signIn.xhtml";

        waitForPage();

        waitUntil(d -> d.findElement(loginButtonBy).isDisplayed());
    }

    public void setCredentials(String login, String password) {

        driver.findElement(loginBy).sendKeys(login);
        driver.findElement(passwordBy).sendKeys(password);
    }

    public NavbarPage login() {

        driver.findElement(loginButtonBy).click();

        return new NavbarPage(driver);
    }
}
