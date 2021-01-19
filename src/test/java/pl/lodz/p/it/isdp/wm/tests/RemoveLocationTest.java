package pl.lodz.p.it.isdp.wm.tests;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import pl.lodz.p.it.isdp.wm.pages.LocationListPage;
import pl.lodz.p.it.isdp.wm.pages.LocationRemovePage;
import pl.lodz.p.it.isdp.wm.pages.LoginPage;
import pl.lodz.p.it.isdp.wm.pages.NavbarPage;

public class RemoveLocationTest extends SeleniumBaseTest {

    @Test
    public void main() {

        LoginPage page = new LoginPage(driver);

        page.setCredentials("JDoe", "P@ssw0rd");

        NavbarPage navPage = page.login();

        LocationListPage listPage = navPage.clickListLocation();

        String id = "AA-01-02-02";

        LocationRemovePage removePage = listPage.clickRemoveLocation(id);

        Assert.assertNotNull(removePage);

        listPage = removePage.accept();

        List<String> row = listPage.findLocationRow(id);

        Assert.assertTrue(row.isEmpty());

    }
}
