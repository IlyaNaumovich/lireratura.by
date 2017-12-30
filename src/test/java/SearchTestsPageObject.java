import Core.TestBase;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.BookDetailedPage;
import pages.CartPage;
import pages.HomePage;
import pages.SearchResultPage;

import static com.codeborne.selenide.WebDriverRunner.url;

public class SearchTestsPageObject extends TestBase{
//    @BeforeMethod(alwaysRun = true)
//    public static void start() {
//        Configuration.browser = "chrome";
//        Configuration.timeout = 10000;
//        Configuration.startMaximized = true;
//        open("http://literatura.by/");
//    }
//
//    @AfterMethod
//    public static void finish() {
//    }

    @Test
    public static void cartVerification() {

        String bookName = HomePage.getFirstBookName();
        String bookPrice1 = HomePage.getFirstBookPrice();
        HomePage.selectBookWithPrice();

        String newBookName = BookDetailedPage.getBookName();
        Assert.assertEquals(bookName, newBookName, "Book names should be the same");

        String classNameBefore = BookDetailedPage.getCartStateClassName();
        BookDetailedPage.addToCart();
        String classNameAfter = BookDetailedPage.getCartStateClassName();

        Assert.assertNotEquals(classNameBefore, classNameAfter, "Class names should be different");

        String qtyInCart = BookDetailedPage.getQtyInCart();
        Assert.assertTrue(qtyInCart.endsWith("1"), "Should be one book in the cart");

        String bookPrice2 = BookDetailedPage.getBookPrice();
        Assert.assertEquals(bookPrice1, bookPrice2, "Prices should be the same");

        BookDetailedPage.clickPromoCodeButton();

        BookDetailedPage.sendPromoCode("813524");

        boolean isDiscountBannerDisplayed = BookDetailedPage.isDiscountBannerDisplayed();
        Assert.assertTrue(isDiscountBannerDisplayed, "Discount banner should be displayed");

        int discountRate = BookDetailedPage.getDiscountRate();

        float oldPriceValue = BookDetailedPage.getOldPrice();
        float newPriceValue = BookDetailedPage.getNewPrice();

        float diff = oldPriceValue * (100 - discountRate) / 100 - newPriceValue;
        Assert.assertTrue(diff < 0.01 && diff > -0.01, "Discount should be calculated right way");

    }

    @Test
    public static void sendMessage() {
        HomePage.sendMessage("Hello!");

    }

    @Test
    public static void cartPageVerification() throws InterruptedException {
        HomePage.selectBookWithPrice();

        BookDetailedPage.addToCart();
        BookDetailedPage.goToCartPage();

        int newQty = 2;

        CartPage.setQtyToFirstLine(new Integer(newQty).toString());

        float price = CartPage.getPrice();
        float amount = CartPage.getAmount();

        Assert.assertEquals(amount, (float) (price * newQty), "Amount should be correct");

    }

    @Test
    public static void promoVerification() {
        HomePage.selectBookWithPrice();

        String classNameBefore = BookDetailedPage.getCartStateClassName();
        BookDetailedPage.addToCart();
        String classNameAfter = BookDetailedPage.getCartStateClassName();

        Assert.assertNotEquals(classNameBefore, classNameAfter, "Class names should be different");

    }

    @Test(priority = 0)
    public static void HelloWorldTest() {
        HomePage.searchBook("Hello, world!");

        String currentURL = url();

        Assert.assertTrue(currentURL.contains("/kniga/"), "URL should contain '/kniga/'");

        String bookName = BookDetailedPage.getBookSectionName();
        Assert.assertEquals(bookName, "Книги", "Link should contain 'Книги'");
    }

    @Test(priority = 1, groups = {"group1"})
    public static void JavaTest() {
        HomePage.searchBook("Java");

        String currentURL = HomePage.getCurrentURL();
        Assert.assertTrue(currentURL.contains("/poisk/"), "URL should contain '/poisk/'");

        String resultNoteText = SearchResultPage.getResultNoteText("Java");
        Assert.assertTrue(resultNoteText.contains("Java"), "Text should contain 'Java'");
    }

    @Test(priority = 0, groups = {"search", "group1"})
    public static void windowsTest() {
        HomePage.searchBook("Windows");

        String currentURL = HomePage.getCurrentURL();
        Assert.assertTrue(currentURL.contains("/poisk/"), "URL should contain '/poisk/'");

        String resultNoteText = SearchResultPage.getResultNoteText("Windows");
        Assert.assertTrue(resultNoteText.contains("Windows"), "Text should contain 'Windows'");
    }

    @Test(dataProvider = "getBookNames", priority = 5, groups = {"search"})
    public static void bookTest(String bookName) {
        HomePage.searchBook(bookName);
        String currentURL = HomePage.getCurrentURL();
        Assert.assertTrue(currentURL.contains("/poisk/"), "URL should contain '/poisk/'");

        String breadCrumbItemText = SearchResultPage.getBreadCrumbItemText();
        Assert.assertTrue(breadCrumbItemText.contains(bookName), "Text should contain '" + bookName + "'");

        String resultNoteText = SearchResultPage.getResultNoteText(bookName);
        Assert.assertTrue(resultNoteText.contains(bookName), "Text should contain '" + bookName + "'");

        boolean containsKeyWord = SearchResultPage.containsKeyWord(bookName);
        Assert.assertTrue(containsKeyWord, "Book titles should contain '" + bookName + "'");

        boolean priceIsValid = SearchResultPage.priceIsValid();
        Assert.assertTrue(priceIsValid, "Price should be valid");
    }




    @DataProvider
    public Object[][] getBookNames() {
        return new Object[][]{
                {"Java"}, {"Windows"}, {"USA"}, {"Scrum"}
        };
    }


}
