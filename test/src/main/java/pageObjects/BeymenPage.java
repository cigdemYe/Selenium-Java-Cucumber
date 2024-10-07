package pageObjects;

import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BeymenPage extends Base_PO {
    private @FindBy(id = "onetrust-accept-btn-handler")
    WebElement acceptAllCookiesButton;

    private @FindBy(xpath = "//button[contains(@class,'o-modal__closeButton')]")
    WebElement closeButton;

    private @FindBy(xpath = "(//a[@title=(\"Beymen\")])[1]")
    WebElement beymenLogo;


    private @FindBy(css = "div.o-header__search--wrapper")
    WebElement search;

    private @FindBy(css = "button.o-header__search--btn")
    WebElement searchIcon;

    private @FindBy(css = ".o-header__search--input")
    WebElement searchInput;

    private @FindBy(css = "button.dn-slide-deny-btn")
    WebElement denyButton;

    private @FindBy(css = "a.o-header__userInfo--item")
    WebElement accountButton;

    private @FindBy(id = "customerEmail")
    WebElement email;

    private @FindBy(css = "div.o-searchSuggestion__productListScrollProductScrollTitle>span>span")
    WebElement resultTitleForShort;

    private @FindBy(xpath = "//span[text()='M']")
    WebElement sizeOfM;

    private @FindBy(css = "span.m-productCard__newPrice")
    WebElement priceOnSearchResultPage;

    private @FindBy(css = "div.m-productCard__stockCartIcon>:nth-child(1)")
    WebElement cartICon;

    private @FindBy(id = "addBasket")
    WebElement addTheCart;




    public BeymenPage() {
        super();
    }

    public void acceptAllCookies() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(acceptAllCookiesButton)).click();
    }

    public void closeModal() {
        closeButton.click();
    }

    public void checkMainPage() {
        Assert.assertTrue(beymenLogo.isDisplayed());
    }

    public void searchShorts() {

        sleep(1000);
        sendKeys(searchInput, "şort");
        sendKeys(searchInput, String.valueOf(Keys.ENTER));
        safeClick(denyButton);
        sleep(1000);
        Assert.assertEquals("şort", resultTitleForShort.getText());
    }

    public void checkPrices() {
        String price1 = priceOnSearchResultPage.getText();
        Double priceOnSearchResultPage = Double.parseDouble(price1);
        click(cartICon);
        sizeOfM.click();
        addTheCart.click();
        sleep(10000);
    }

    public void register() {
        accountButton.click();
        sendKeys(email, "cigdemyelke32@gmail.com");
        sleep(1000);
    }
}