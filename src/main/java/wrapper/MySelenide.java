package wrapper;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class MySelenide extends Selenide {
    public static SelenideElement $(By seleniumSelector) {
        highlight(getElement(seleniumSelector));
        return getElement(seleniumSelector);
    }

    private static void highlight(WebElement element) {
        final int wait = 75;
        String originalStyle = element.getAttribute("style");
        try {
            setAttribute(element, "style",
                    "color: yellow; border: 5px solid yellow; background-color: black;");
            Thread.sleep(wait);
            setAttribute(element, "style",
                    "color: black; border: 5px solid black; background-color: yellow;");
            Thread.sleep(wait);
            setAttribute(element, "style",
                    "color: yellow; border: 5px solid yellow; background-color: black;");
            Thread.sleep(wait);
            setAttribute(element, "style",
                    "color: black; border: 5px solid black; background-color: yellow;");
            Thread.sleep(wait);
            setAttribute(element, "style", originalStyle);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void setAttribute(WebElement element, String attributeName, String value) {
        executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, attributeName, value);
    }

    public static java.lang.Object executeScript(String s, java.lang.Object... args) {
        return ((JavascriptExecutor) getWebDriver()).executeScript(s, args);
    }


}
