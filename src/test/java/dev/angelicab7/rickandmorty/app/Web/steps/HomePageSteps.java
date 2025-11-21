package dev.angelicab7.rickandmorty.app.Web.steps;

import com.microsoft.playwright.Page;
import dev.angelicab7.rickandmorty.app.Web.hooks.Hooks;
import dev.angelicab7.rickandmorty.app.Web.pages.CharactersPage;
import dev.angelicab7.rickandmorty.app.Web.pages.HomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePageSteps {
    private static final Logger logger = LoggerFactory.getLogger(HomePageSteps.class);
    private static final String BASE_URL = "https://angelicab7.github.io/BOG001-data-lovers/";

    public HomePage homePage;
    private CharactersPage charactersPage;

    @Given("I am on the Rick and Morty web page")
    public void iAmOnTheRickAndMortyWebPage() {
        logger.info("Step: I am on the Rick and Morty web page");
        Page page = Hooks.getPage();
        homePage = new HomePage(page);
        homePage.navigateTo(BASE_URL);
        homePage.verifyHomePage();
    }

    @Then("I should see the list of Characters displayed")
    public void iShouldSeeTheListOfCharactersDisplayed() {
        logger.info("Step: I should see the list of Characters displayed");
        charactersPage = homePage.proceedToCharactersPage();
        charactersPage.verifyCharactersPage();
    }
}
