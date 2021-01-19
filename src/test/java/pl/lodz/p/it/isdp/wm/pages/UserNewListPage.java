package pl.lodz.p.it.isdp.wm.pages;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class UserNewListPage extends NavbarPage {

    private final String rowXpath =
            "//table/tbody/tr[.//*[contains(text(), '$placeholder')]]";

    public UserNewListPage(WebDriver driver) {
        super(driver);
        this.subdomain = "/faces/account/listNewAccounts.xhtml";

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

    public void setTypeForLogin(String login, String type) {
        WebElement row = findRowElementByUserLogin(login);

        Select select = new Select(row.findElement(By.tagName("select")));

        select.selectByVisibleText(type);
    }

    public void typeSubmitForLogin(String login) {
        WebElement row = findRowElementByUserLogin(login);

        row.findElements(By.tagName("input")).get(0).click();
    }
}
