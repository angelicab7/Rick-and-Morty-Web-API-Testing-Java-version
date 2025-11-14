package dev.angelicab7.rickandmorty.app.Web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.*;

public class CharactersPage {
    private static final Logger logger = LoggerFactory.getLogger(CharactersPage.class);
    private final Page page;

    private final Locator charactersButton;
    private final Locator characterContainer;
    private final Locator searchField;
    private final Locator searchButton;
    private final Locator characterCard;
    private final Locator orderDropdown;
    private final Locator speciesDropdown;
    private final Locator charactersSorted;
    private final Locator categoryDisplayed;

    public CharactersPage(Page page) {
        this.page = page;
        this.charactersButton = page.getByText("Characters");
        this.characterContainer = page.locator("#characters-container > .column");
        this.searchField = page.locator("#searchIn");
        this.searchButton = page.locator("#searchButton");
        this.characterCard = page.getByText("Izzy");
        this.orderDropdown = page.locator("#filter-input-order");
        this.speciesDropdown = page.locator("#filter-input-species");
        this.charactersSorted = page.getByText("Abadango Cluster Princess");
        this.categoryDisplayed = page.locator(".card-container > .card-back font-color > .card-description div:has-text('Species')");

        logger.debug("CharactersPage initialized");
    }

    public void verifyCharactersPage() {
        logger.debug("Verifying characters page is displayed");
        assertThat(charactersButton).isVisible();

        int count = characterContainer.count();
        assertTrue(count > 0, "Character container should not be empty");

        logger.info("Characters page verified - found {} character containers", count);
    }
    public void clickOrderDropdown() {
        logger.info("Clicking on order dropdown and selecting 'Order A-Z'");
        orderDropdown.selectOption("Order A-Z");
        page.waitForTimeout(1000);
    }

    public void verifyCharactersSorted() {
        logger.debug("Verifying characters are sorted");

        int containerCount = characterContainer.count();
        assertTrue(containerCount > 0, "Character container should not be empty");

        assertThat(charactersSorted).isVisible();
        logger.info("Characters sorted verification passed");
    }

    public void searchForCharacter(String characterName) {
        logger.info("Searching for character: {}", characterName);
        searchField.fill(characterName.toLowerCase());
        searchButton.waitFor();
        searchButton.click();
        page.waitForTimeout(1000);
    }

    public void verifyCharacterDisplayed() {
        logger.debug("Verifying character is displayed after search");
        characterCard.waitFor();
        assertThat(characterCard).isVisible();
        logger.info("Character card verification passed");
    }

}