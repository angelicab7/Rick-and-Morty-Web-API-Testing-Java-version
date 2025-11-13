package dev.angelicab7.rickandmorty.app.Web.pages;

import com.microsoft.playwright.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
    private final Page page;

    private static final String FUN_FACTS_TEXT = "text=Fun Facts";
    private static final String CHARACTERS_BUTTON = "button:has-text('Characters')";

    public HomePage(Page page) {
        this.page = page;
        logger.debug("HomePage initialized");
    }

    public void navigateTo(String url) {
        logger.info("Navigating to home page: {}", url);
        page.navigate(url);
        page.waitForLoadState();
    }

    public void verifyHomePage() {
        logger.debug("Verifying home page is displayed");
        assertThat(page.locator(FUN_FACTS_TEXT)).isVisible();
        logger.info("Home page verified successfully");
    }

    public CharactersPage proceedToCharactersPage() {
        logger.info("Clicking on Characters button");
        page.locator(CHARACTERS_BUTTON).click();
        return new CharactersPage(page);
    }
}
