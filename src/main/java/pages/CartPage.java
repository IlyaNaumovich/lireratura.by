package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static wrapper.MySelenide.$;

public class CartPage {
    static By firstLineQtyInput = By.name("Lines[0].Quantity");
    static By firstLine =By.xpath("//tr[td//input[@name='Lines[0].Quantity']]");

    public static void setQtyToFirstLine(String qty){
        $(firstLineQtyInput).sendKeys(Keys.BACK_SPACE + qty);
        $(firstLineQtyInput).sendKeys(Keys.ENTER);
    }

    public static float getPrice() {
        $(firstLine).click();
        String priceText = $(firstLine).getText().split("\n")[1].split(" ")[0];
        priceText = priceText.replace(",", ".");

        return Float.valueOf(priceText);
    }

    public static float getAmount() throws InterruptedException {
        String priceText = $(firstLine).getText().split("\n")[1].split(" ")[2];
        priceText = priceText.replace(",", ".");

        return Float.valueOf(priceText);
    }
}
