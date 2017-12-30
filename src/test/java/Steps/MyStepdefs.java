package Steps;

import com.codeborne.selenide.Configuration;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.testng.Assert;
import pages.BookDetailedPage;
import pages.CartPage;
import pages.HomePage;
import pages.SearchResultPage;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;

public class MyStepdefs {
    String bookName;
    String bookPrice1;
    String classNameBefore;
    int newQty;

    @When("^I search book which is only one$")
    public void iSearchBookWhichIsOnlyOne() throws Throwable {
        HomePage.searchBook("Hello, world!");
    }

    @Then("^URL should contain '/kniga/'$")
    public void urlShouldContainKniga() throws Throwable {
        String currentURL = url();
        Assert.assertTrue(currentURL.contains("/kniga/"), "URL should contain '/kniga/'");
    }

    @And("^Link should contain 'Книги'$")
    public void linkShouldContainКниги() throws Throwable {
        String bookName = BookDetailedPage.getBookSectionName();
        Assert.assertEquals(bookName, "Книги", "Link should contain 'Книги'");
    }

    @Given("^I go to Literatura_by$")
    public void iGoToLiteratura_by() throws Throwable {
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        Configuration.startMaximized = true;
        open("http://literatura.by/");
    }

    @Given("^Я иду на Literatura_by$")
    public void яИдуНаLiteratura_by() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        Configuration.startMaximized = true;
        open("http://literatura.by/");
    }

    @When("^I search book named \"([^\"]*)\"$")
    public void iSearchBookNamed(String bookName) throws Throwable {
        HomePage.searchBook(bookName);
    }

    @When("^I select first book with price$")
    public void iSelectFirstBookWithPrice() throws Throwable {
        bookName = HomePage.getFirstBookName();
        bookPrice1 = HomePage.getFirstBookPrice();
        HomePage.selectBookWithPrice();
    }

    @Then("^Book names should be the same$")
    public void bookNamesShouldBeTheSame() throws Throwable {
        String newBookName = BookDetailedPage.getBookName();
        Assert.assertEquals(bookName, newBookName, "Book names should be the same");
    }

    @When("^I click on add to cart icon$")
    public void iClickOnAddToCartIcon() throws Throwable {
        classNameBefore = BookDetailedPage.getCartStateClassName();
        BookDetailedPage.addToCart();
    }

    @Then("^Add to cart icon should change$")
    public void addToCartIconShouldChange() throws Throwable {
        String classNameAfter = BookDetailedPage.getCartStateClassName();
        Assert.assertNotEquals(classNameBefore, classNameAfter, "Class names should be different");
    }

    @And("^Quantity in the cart should change by (\\d+)$")
    public void quantityInTheCartShouldChangeBy(int qty) throws Throwable {
        String qtyInCart = BookDetailedPage.getQtyInCart();
        Assert.assertTrue(qtyInCart.endsWith(new Integer(qty).toString()), "Should be one book in the cart");
    }

    @And("^Price in the cart should be the same$")
    public void priceInTheCartShouldBeTheSame() throws Throwable {
        String bookPrice2 = BookDetailedPage.getBookPrice();
        Assert.assertEquals(bookPrice1, bookPrice2, "Prices should be the same");
    }

    @When("^I set promo \"([^\"]*)\"$")
    public void iSetPromo(String promo) throws Throwable {
        BookDetailedPage.clickPromoCodeButton();
        BookDetailedPage.sendPromoCode(promo);
    }

    @Then("^Discount banner should appear$")
    public void discountBannerShouldAppear() throws Throwable {
        boolean isDiscountBannerDisplayed = BookDetailedPage.isDiscountBannerDisplayed();
        Assert.assertTrue(isDiscountBannerDisplayed, "Discount banner should be displayed");
    }

    @And("^Discount is calculated correctly$")
    public void discountIsCalculatedCorrectly() throws Throwable {
        int discountRate = BookDetailedPage.getDiscountRate();

        float oldPriceValue = BookDetailedPage.getOldPrice();
        float newPriceValue = BookDetailedPage.getNewPrice();

        float diff = oldPriceValue*(100-discountRate)/100-newPriceValue;
        Assert.assertTrue(diff<0.01 &&diff>-0.01, "Discount should be calculated right way");
    }

    @And("^I go to cart page$")
    public void iGoToCartPage() throws Throwable {
        BookDetailedPage.goToCartPage();
    }

    @And("^I set new quantity \"([^\"]*)\"$")
    public void iSetNewQuantity(String qty) throws Throwable {
        newQty = Integer.valueOf(qty);
        CartPage.setQtyToFirstLine(qty);
    }

    @Then("^I verify that the amount is correct$")
    public void iVerifyThatTheAmountIsCorrect() throws Throwable {
        float price = CartPage.getPrice();
        float amount = CartPage.getAmount();

        Assert.assertEquals(amount, (float) (price * newQty), "Amount should be correct");

    }

    @Then("^URL should contain '/poisk/'$")
    public void urlShouldContainPoisk() throws Throwable {
        String currentURL = HomePage.getCurrentURL();
        Assert.assertTrue(currentURL.contains("/poisk/"), "URL should contain '/poisk/'");
    }

    @And("^Text should contain \"([^\"]*)\"$")
    public void textShouldContain(String searchPhrase) throws Throwable {
        String resultNoteText = SearchResultPage.getResultNoteText(searchPhrase);
        Assert.assertTrue(resultNoteText.contains(searchPhrase), "Text should contain '" + searchPhrase + "'");
    }

    @When("^I search the book ([^\"]*)$")
    public void iSearchTheBookBookTitle(String bookTitle) throws Throwable {
        HomePage.searchBook(bookTitle);
    }

    @And("^Text should contain book name ([^\"]*)$")
    public void textShouldContainBookNameBookTitle(String bookTitle) throws Throwable {
        String breadCrumbItemText = SearchResultPage.getBreadCrumbItemText();
        Assert.assertTrue(breadCrumbItemText.contains(bookTitle), "Text should contain '" + bookTitle + "'");
    }

    @And("^Book name should contain ([^\"]*)$")
    public void bookNameShouldContainBookTitle(String bookTitle) throws Throwable {
        String resultNoteText = SearchResultPage.getResultNoteText(bookTitle);
        Assert.assertTrue(resultNoteText.contains(bookTitle), "Text should contain '" + bookTitle + "'");
    }


}
