import com.google.common.base.Predicate;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestTest {

    @Test
    public void test() {
        System.setProperty("webdriver.gecko.driver","./webdrivers/geckodriver");

        WebDriver driver = new FirefoxDriver();

        driver.get("https://devel:8181/faces/common/signIn.xhtml");

        new WebDriverWait(driver, 3).until((Predicate<WebDriver>) d -> false);
    }
}
