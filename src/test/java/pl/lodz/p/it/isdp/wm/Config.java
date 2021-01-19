package pl.lodz.p.it.isdp.wm;


public class Config {

    public static final String MAIN_URL = "https://devel:8181";

    public static void initDriverPath() {
        System.setProperty("webdriver.gecko.driver","./webdrivers/geckodriver");
    }

    public static String subdomain(String path) {
        return MAIN_URL + path;
    }
}
