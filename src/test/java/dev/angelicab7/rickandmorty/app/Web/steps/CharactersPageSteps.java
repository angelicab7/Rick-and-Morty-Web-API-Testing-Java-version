package dev.angelicab7.rickandmorty.app.Web.steps;

import com.microsoft.playwright.Page;
import dev.angelicab7.rickandmorty.app.Web.hooks.Hooks;
import dev.angelicab7.rickandmorty.app.Web.pages.CharactersPage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharactersPageSteps {
    private static final Logger logger = LoggerFactory.getLogger(CharactersPageSteps.class);
    private static final String CHARACTERS_URL = "https://angelicab7.github.io/BOG001-data-lovers/characters.html";

    private CharactersPage charactersPage;

    @Given("I am on Characters Page")
    public void iAmOnCharactersPage() {
        logger.info("Step: I am on Characters Page");
        Page page = Hooks.getPage();
        page.navigate(CHARACTERS_URL);
        charactersPage = new CharactersPage(page);
        charactersPage.verifyCharactersPage();
    }

    @When("I click on the sort dropdown and select {string} option")
    public void iClickOnTheSortDropdownAndSelectOption(String sortOption) {
        logger.info("Step: I click on the sort dropdown and select '{}' option", sortOption);
        charactersPage.clickOrderDropdown();
    }

    @Then("I should see characters sorted from A to Z")
    public void iShouldSeeCharactersSortedFromAToZ() {
        logger.info("Step: I should see characters sorted from A to Z");
        charactersPage.verifyCharactersSorted();
    }

    @When("I search for character {string}")
    public void iSearchForCharacter(String character) {
        logger.info("Step: I search for character '{}'", character);
        charactersPage.searchForCharacter(character);
    }

    @Then("I should see search results for {string}")
    public void iShouldSeeSearchResultsFor(String character) {
        logger.info("Step: I should see search results for '{}'", character);
        charactersPage.verifyCharacterDisplayed();
    }

}
