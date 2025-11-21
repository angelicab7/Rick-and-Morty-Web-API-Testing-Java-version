package dev.angelicab7.rickandmorty.app.Web.steps;

import com.microsoft.playwright.Page;
import dev.angelicab7.rickandmorty.app.Web.hooks.Hooks;
import dev.angelicab7.rickandmorty.app.Web.utils.VisualRegression;
import io.cucumber.java.en.Then;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VisualSteps {
    private static final Logger logger = LoggerFactory.getLogger(VisualSteps.class);

    private VisualRegression visualRegression;

    @Then("The banner and fun facts section should be visually stable")
    public void theBannerAndFunFactsSectionShouldBeVisuallyStable() {
        logger.info("Step: The banner and fun facts section should be visually stable");

        Page page = Hooks.getPage();
        visualRegression = new VisualRegression(page);

        // Take full page screenshot
        visualRegression.checkFullPage("homepage_header");

        logger.info("Visual regression check completed for homepage");
    }
}
