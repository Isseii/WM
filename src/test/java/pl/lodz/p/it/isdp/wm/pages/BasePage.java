package pl.lodz.p.it.isdp.wm.pages;

import com.google.common.base.Predicate;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pl.lodz.p.it.isdp.wm.Config;

public class BasePage {

    protected WebDriver driver;
    protected String subdomain;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    final protected void waitForPage() {
        if (subdomain != null) {

            String url = Config.subdomain(subdomain);
            waitUntil(d -> d.getCurrentUrl().equals(url));
        }
    }

    final protected void waitUntil(Predicate<WebDriver> predicate) {

        new WebDriverWait(driver, 10).until(predicate);
    }

    final protected void fastWaitUntil(Predicate<WebDriver> predicate) {

        new WebDriverWait(driver, 5).until(predicate);
    }
}
