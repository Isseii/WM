package pl.lodz.p.it.isdp.wm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import pl.lodz.p.it.isdp.wm.Config;

public class LocationNewPage extends NavbarPage {

    private final By nameBy = By.name("CreateLocationForm:locationSymbol");
    private final By submitBy = By.xpath("//form/input[@class='button']");
    private final By selectType = By.name("CreateLocationForm:locationType");

    public LocationNewPage(WebDriver driver) {
        super(driver);
        this.subdomain = "/faces/location/createNewLocation.xhtml";
        waitForPage();

        waitUntil(d -> d.findElement(nameBy).isDisplayed());
    }


    public void setNewLocatioName(String name) {
        driver.findElement(nameBy).sendKeys(name);
    }

    public void setType(String type) {
        Select select = new Select(driver.findElement(selectType));

        select.selectByVisibleText(type);
    }

    public boolean submit() {
        driver.findElement(submitBy).click();

        return isSuccess();
    }

    private boolean isSuccess() {

        String url = Config.subdomain("/faces/main/index.xhtml");

        try {
            fastWaitUntil(d -> d.getCurrentUrl().equals(url));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
