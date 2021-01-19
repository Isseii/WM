package pl.lodz.p.it.isdp.wm.tests;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import pl.lodz.p.it.isdp.wm.pages.LocationListPage;
import pl.lodz.p.it.isdp.wm.pages.NavbarPage;
import pl.lodz.p.it.isdp.wm.pages.LoginPage;
import pl.lodz.p.it.isdp.wm.pages.LocationNewPage;

public class NewLocationTest extends SeleniumBaseTest {

    @Test
    public void main() {

        LoginPage page = new LoginPage(driver);

        page.setCredentials("JDoe", "P@ssw0rd");

        NavbarPage navPage = page.login();

        LocationNewPage locPage = navPage.clickNewLocation();

        String id = "BB-01-02-03";
        String type = "CAŁA";

        locPage.setNewLocatioName(id);
        locPage.setType(type);
        boolean result = locPage.submit();

        Assert.assertTrue(result);

        LocationListPage listPage = locPage.clickListLocation();

        List<String> row = listPage.findLocationRow(id);

        Assert.assertTrue(row.contains(id));
        Assert.assertTrue(row.contains(type));
        Assert.assertTrue(row.contains("60.0"));
        Assert.assertTrue(row.contains("PUSTA"));
    }

}
