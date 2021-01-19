package pl.lodz.p.it.isdp.wm.pages;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class UserAuthorizedListPage extends NavbarPage {

    private final String rowXpath = "//table/tbody/tr[.//*[contains(text(), '$placeholder')]]";

    public UserAuthorizedListPage(WebDriver driver) {
        super(driver);
        this.subdomain = "/faces/account/listAuthorizedAccounts.xhtml";

        waitForPage();
        waitUntil(d -> d.findElement(By.className("table")).isDisplayed());
    }


    private WebElement findRowElementByUserLogin(String login) {

        try {
            By rowBy = By.xpath(rowXpath.replace("$placeholder", login));
            return  driver.findElement(rowBy);
        } catch (Exception ignored) {
            return null;
        }
    }

    public List<String> findUserRow(String login) {

        WebElement row = findRowElementByUserLogin(login);

        if (row == null) return Collections.emptyList();

        return row
                .findElements(By.tagName("td"))
                .stream()
                .map(td -> td.getText().trim())
                .collect(Collectors.toList());
    }
}
