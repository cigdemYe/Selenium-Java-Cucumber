package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pageObjects.Base_PO;
import pageObjects.BeymenPage;


public class BeymenStepDef extends Base_PO {
    private BeymenPage loginPage;

    public BeymenStepDef(BeymenPage loginPage) {
        this.loginPage = loginPage;
    }

    @When("get page url")
    public void getPageUrl() {
        getURL("https://www.beymen.com/tr");
        loginPage.acceptAllCookies();
        loginPage.closeModal();
    }

    @Then("System must display main page")
    public void checkMainPage() {
        loginPage.checkMainPage();
    }

    @When("search 'şort'")
    public void searchSort() {
        loginPage.searchShorts();

    }

    @And("select random product")
    public void selectRandomProduct() {

    }

    @Then("System must display selected product in the cart")
    public void checkCartSelectedProduct() {

    }

}
