package pl.lodz.p.it.isdp.wm.pages;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LocationListPage extends NavbarPage {

    private final By tableBy = By.className("table");
    private final String rowXpath =
            "//table/tbody/tr[.//*[contains(text(), '$placeholder')]]";

    public LocationListPage(WebDriver driver) {
        super(driver);
        this.subdomain = "/faces/location/listLocations.xhtml";
        waitForPage();

        waitUntil(d -> d.findElement(tableBy).isDisplayed());
    }

    WebElement findRowElementByLocationName(String name) {

        try {
            By rowBy = By.xpath(rowXpath.replace("$placeholder", name));
            return  driver.findElement(rowBy);
        } catch (Exception ignored) {
            return null;
        }
    }

    public List<String> findLocationRow(String name) {

        WebElement row = findRowElementByLocationName(name);

        if (row == null) return Collections.emptyList();

        return row
                .findElements(By.tagName("td"))
                .stream()
                .map(td -> td.getText().trim())
                .collect(Collectors.toList());
    }

    public LocationRemovePage clickRemoveLocation(String name) {

        WebElement row = findRowElementByLocationName(name);

        if (row == null) return null;

        row.findElements(By.tagName("input"))
                .get(2)
                .click();

        return new LocationRemovePage(driver, name);
    }
}
