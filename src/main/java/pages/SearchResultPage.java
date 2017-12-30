package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static com.codeborne.selenide.Selenide.$$;
import static wrapper.MySelenide.$;

//import static com.codeborne.selenide.Selenide.$;

public class SearchResultPage {
    private static By breadCrumbItem = By.xpath("//ul[@class='breadcrumb']/li[last()]");
    private static By bookTitles = By.xpath("//a[@class='product-name']");
    private static By bookPrices = By.xpath("//div[@class='tocart']");

    public static String getBreadCrumbItemText() {
        return $(breadCrumbItem).getText();
    }

    public static String getResultNoteText(String bookName) {
        String xpathForResultNote = "//small[contains(text(), '%bookName%')]";
        xpathForResultNote = xpathForResultNote.replace("%bookName%", bookName);
        return $(By.xpath(xpathForResultNote)).getText();
    }

    public static boolean containsKeyWord(String bookName) {
        boolean containsKeyWord = false;
        for (WebElement bookTitle: $$(bookTitles)){
            if (bookTitle.getText().contains(bookName)){
                containsKeyWord = true;
                break;
            }
        }
        return containsKeyWord;
    }

    public static boolean priceIsValid() {
        boolean priceIsValid = true;
        for (WebElement bookPrice: $$(bookPrices)){
            priceIsValid = priceIsValid && (bookPrice.getText().endsWith("руб.") ||bookPrice.getText().endsWith("на складе") );
            return priceIsValid;
        }
        return priceIsValid;
    }
}
