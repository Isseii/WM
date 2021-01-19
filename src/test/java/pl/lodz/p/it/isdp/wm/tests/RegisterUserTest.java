package pl.lodz.p.it.isdp.wm.tests;

import java.util.List;
import java.util.Random;
import org.junit.Assert;
import org.junit.Test;
import pl.lodz.p.it.isdp.wm.pages.LoginPage;
import pl.lodz.p.it.isdp.wm.pages.NavbarPage;
import pl.lodz.p.it.isdp.wm.pages.UserAuthorizedListPage;
import pl.lodz.p.it.isdp.wm.pages.UserNewListPage;
import pl.lodz.p.it.isdp.wm.pages.UserRegisterPage;

public class RegisterUserTest extends SeleniumBaseTest {

    private String randomLogin(int length) {


        Random random = new Random();

        String symbols = "abcdefghijklmnopqrstuvwxyz";
        String upperSymbols = symbols.toUpperCase();

        StringBuilder str = new StringBuilder();

        str.append(upperSymbols.charAt(random.nextInt(symbols.length())));
        str.append(upperSymbols.charAt(random.nextInt(symbols.length())));

        for (int i = 0; i < length; i++) {
            str.append(symbols.charAt(random.nextInt(symbols.length())));
        }

        return str.toString();
    }

    @Test
    public void main() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.setCredentials("DMitchell", "P@ssw0rd");

        NavbarPage navPage = loginPage.login();

        UserRegisterPage registerPage = navPage.clickRegisterUser();

        String login = randomLogin(8);

        String firstName = "First";
        String lastName = "Last";
        String email = login.toLowerCase() + "@gmail.com";
        String password = "P@ssw0rd";

        registerPage.putFirstName(firstName);
        registerPage.putLastName(lastName);
        registerPage.putEmail(email);
        registerPage.putLogin(login);
        registerPage.putPassword(password);
        registerPage.putQuestion("Test question?");
        registerPage.putAnwser("Yes");

        boolean success = registerPage.submit();

        Assert.assertTrue(success);

        UserNewListPage listNew = registerPage.clickUserNewList();

        List<String> row = listNew.findUserRow(login);

        Assert.assertTrue(row.contains(login));
        Assert.assertTrue(row.contains(firstName));
        Assert.assertTrue(row.contains(lastName));
        Assert.assertTrue(row.contains(email));

        String type = "Biuro";

        listNew.setTypeForLogin(login, type);
        listNew.typeSubmitForLogin(login);

        Assert.assertTrue(listNew.findUserRow(login).isEmpty());

        UserAuthorizedListPage authorizedPage = navPage.clickUserAuthorizedList();

        authorizedPage.findUserRow(login);

        row = authorizedPage.findUserRow(login);

        Assert.assertTrue(row.contains(login));
        Assert.assertTrue(row.contains(firstName));
        Assert.assertTrue(row.contains(lastName));
        Assert.assertTrue(row.contains(email));

        navPage = authorizedPage.performLogout();

        loginPage = navPage.clickLogin();

        loginPage.setCredentials(login, password);

        navPage = loginPage.login();

        String currentLogin = navPage.getCurrentLogin();

        Assert.assertEquals(login, currentLogin);
    }

}
