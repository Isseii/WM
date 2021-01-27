package pl.lodz.p.it.isdp.wm.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pl.lodz.p.it.isdp.wm.Config;

public abstract class SeleniumBaseTest {


    protected WebDriver driver;

    @Before
    public void before() {

        Config.initDriverPath();

        FirefoxOptions firefoxOptions = new FirefoxOptions();
        FirefoxBinary firefoxBinary = new FirefoxBinary();

        firefoxBinary.addCommandLineOptions("--headless");
        firefoxOptions.setBinary(firefoxBinary);
        firefoxOptions.addPreference("intl.accept_languages", "pl");

        driver = new FirefoxDriver(firefoxOptions);

        driver.get(Config.subdomain("/faces/common/signIn.xhtml"));
    }

    @After
    public void after() {
        try {
            driver.quit();
        } catch (Exception e) {
            // ignored
        }
    }
}
