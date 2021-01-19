package pl.lodz.p.it.isdp.wm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pl.lodz.p.it.isdp.wm.Config;


public class UserRegisterPage extends NavbarPage {

    private final By firstNameBy = By.name("RegisterForm:name");
    private final By lastNameBy = By.name("RegisterForm:surname");
    private final By emailBy = By.name("RegisterForm:email");
    private final By loginBy = By.name("RegisterForm:login");
    private final By passwordBy = By.name("RegisterForm:password");
    private final By repeatPasswordBy = By.name("RegisterForm:passwordRepeat");
    private final By questionBy = By.name("RegisterForm:question");
    private final By answerBy = By.name("RegisterForm:answer");
    private final By submitBy  = By.xpath("//input[@class='button']");


    public UserRegisterPage(WebDriver driver) {
        super(driver);
        this.subdomain = "/faces/common/registerAccount.xhtml";
        waitForPage();

        waitUntil(d -> d.findElement(By.className("table")).isDisplayed());
    }

    public void putFirstName(String firstName) {
        driver.findElement(firstNameBy).sendKeys(firstName);
    }

    public void putLastName(String lastName) {
        driver.findElement(lastNameBy).sendKeys(lastName);
    }

    public void putEmail(String email) {
        driver.findElement(emailBy).sendKeys(email);
    }

    public void putLogin(String login) {
        driver.findElement(loginBy).sendKeys(login);
    }

    public void putPassword(String password) {
        driver.findElement(passwordBy).sendKeys(password);
        driver.findElement(repeatPasswordBy).sendKeys(password);
    }

    public void putQuestion(String question) {
        driver.findElement(questionBy).sendKeys(question);
    }

    public void putAnwser(String answer) {
        driver.findElement(answerBy).sendKeys(answer);
    }

    public boolean submit() {
        driver.findElement(submitBy).click();

        return isSuccess();
    }

    private boolean isSuccess() {

        String url = Config.subdomain("/faces/main/index.xhtml");

        try {
            fastWaitUntil(d -> d.getCurrentUrl().equals(url));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
