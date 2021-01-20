package pl.lodz.p.it.isdp.wm.tests;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pl.lodz.p.it.isdp.wm.Config;

public abstract class SeleniumBaseTest {


    protected WebDriver driver;

    @Before
    public void before() {

        Config.initDriverPath();

        driver = new FirefoxDriver();

        driver.get(Config.subdomain("/faces/common/signIn.xhtml"));
    }

    @After
    public void after() {
        driver.quit();
    }
}
