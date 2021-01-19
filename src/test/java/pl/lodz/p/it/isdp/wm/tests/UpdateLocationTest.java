package pl.lodz.p.it.isdp.wm.tests;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import pl.lodz.p.it.isdp.wm.pages.LocationEditPage;
import pl.lodz.p.it.isdp.wm.pages.LocationListPage;
import pl.lodz.p.it.isdp.wm.pages.LoginPage;
import pl.lodz.p.it.isdp.wm.pages.NavbarPage;

public class UpdateLocationTest extends SeleniumBaseTest {

    @Test
    public void main() {

        LoginPage page = new LoginPage(driver);

        page.setCredentials("JDoe", "P@ssw0rd");

        NavbarPage navPage = page.login();

        LocationListPage listPage = navPage.clickListLocation();

        String id = "AA-01-03-02";

        List<String> row = listPage.findLocationRow(id);

        Assert.assertTrue(row.contains("JEDNA TRZECIA"));

        LocationEditPage editPage = listPage.clickEditLocation(id);

        String type ="CAŁA";

        editPage.setType(type);
        listPage = editPage.submit();

        row = listPage.findLocationRow(id);

        Assert.assertTrue(row.contains(type));

    }

}
