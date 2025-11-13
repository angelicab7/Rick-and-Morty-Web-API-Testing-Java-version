package dev.angelicab7.rickandmorty.app.hooks;

import com.microsoft.playwright.*;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    private static Playwright playwright;
    private static Browser browser;
    private static ThreadLocal<BrowserContext> context = new ThreadLocal<>();
    private static ThreadLocal<Page> page = new ThreadLocal<>();

    public static Page getPage() {
        return page.get();
    }

    @Before
    public void setUp(Scenario scenario) {
        logger.info("═══════════════════════════════════════════════════");
        logger.info("Starting scenario: {}", scenario.getName());
        logger.info("═══════════════════════════════════════════════════");

        if (playwright == null) {
            logger.info("Initializing Playwright");
            playwright = Playwright.create();
            browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false)
                    .setSlowMo(500));
        }

        BrowserContext browserContext = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1920, 1080));
        context.set(browserContext);

        Page newPage = browserContext.newPage();
        page.set(newPage);

        logger.info("Browser context and page created");
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("───────────────────────────────────────────────────");
        logger.info("Finishing scenario: {} - Status: {}",
                scenario.getName(),
                scenario.getStatus());

        if (scenario.isFailed()) {
            logger.error("❌ Scenario FAILED: {}", scenario.getName());

            // Take screenshot on failure
            try {
                byte[] screenshot = page.get().screenshot(new Page.ScreenshotOptions()
                        .setFullPage(true));
                scenario.attach(screenshot, "image/png", "failure-screenshot");
                logger.info("Screenshot captured and attached to report");
            } catch (Exception e) {
                logger.error("Failed to capture screenshot: {}", e.getMessage());
            }
        } else {
            logger.info("✓ Scenario PASSED: {}", scenario.getName());
        }

        if (context.get() != null) {
            context.get().close();
            context.remove();
        }

        if (page.get() != null) {
            page.remove();
        }

        logger.info("═══════════════════════════════════════════════════");
    }

    @AfterAll
    public static void tearDownAll() {
        logger.info("Closing browser and Playwright");
        if (browser != null) {
            browser.close();
            logger.info("Browser closed");
        }
        if (playwright != null) {
            playwright.close();
            logger.info("Playwright closed");
        }
    }
}