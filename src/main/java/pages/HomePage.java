package pages;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.codeborne.selenide.WebDriverRunner.url;
import static wrapper.MySelenide.$;

//import static com.codeborne.selenide.Selenide.*;

public class HomePage {
    static By searchField = By.name("searchPhrase");
    static By searchIcon = By.name("icon-search");
    static By bookList = By.xpath("//div[@class='thumbnail small']");
    static By sendMessageIcon = By.xpath("//jdiv[@class='hoverl_6R']");
    static By messageField = By.id("message");
    static By sendMessageButton = By.id("submit");
    static By frame = By.id("jivo_container");
    static By subscribeButton = By.xpath("//div[@class='wcommunity_footer']/button");
    static By vkFrame = By.xpath("//iframe[contains(@src, 'vk.com')]");

    public static void sendMessage(String messageText){
        $(sendMessageIcon).click();
        switchTo().frame($(frame));
        $(messageField).waitUntil(Condition.visible, 1000).sendKeys(messageText);
        $(sendMessageButton).click();
        int d = 0;
    }

    public static void subscribeButtonClick() throws InterruptedException {
        Actions actions = new Actions(getWebDriver());
        actions.moveToElement($(vkFrame).waitUntil(Condition.enabled, 5));
        actions.perform();
        switchTo().frame($(vkFrame).waitUntil(Condition.visible, 5));

        $(subscribeButton).click();
    }

    public static void searchBook(String bookName) {
        $(searchField).sendKeys(bookName);
        $(By.className("icon-search")).click();
    }

    public static String getCurrentURL() {
        return url();
    }


    public static void selectBookWithPrice() {
        WebElement bookForBuying = null;
        for (WebElement book:$$(bookList)) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookForBuying = book;
                break;
            }
        }
        Assert.assertNotEquals(bookForBuying, null, "Noting to buy");

        bookForBuying.findElement(By.xpath(".//a[img]")).click();
    }

    public static String getFirstBookName() {
        String bookName = "";
        for (WebElement book:$$(bookList)) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookName = bookText.split("\n")[0];
                return bookName;
            }
        }
        return bookName;
    }

    public static String getFirstBookPrice() {
        String bookPrice = "";
        for (WebElement book:$$(bookList)) {
            String bookText = book.getText();
            if(bookText.endsWith("руб.")){
                bookPrice = bookText.split("\n")[1];
                return bookPrice;
            }
        }
        return bookPrice;
    }
}
