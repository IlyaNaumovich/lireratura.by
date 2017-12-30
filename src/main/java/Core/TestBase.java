package Core;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import static com.codeborne.selenide.Selenide.open;

public class TestBase {
    @BeforeMethod(alwaysRun = true)
    public static void start() {
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        Configuration.startMaximized = true;
        open("http://literatura.by/");
    }

    @AfterMethod
    public static void finish() {
    }
}
