package dev.angelicab7.rickandmorty.app.Web.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.testng.Assert.fail;

public class VisualRegression {
    private static final Logger logger = LoggerFactory.getLogger(VisualRegression.class);

    // Visual Regression Directories
    private static final Path VISUAL_DIR = Paths.get("src/test/resources/visuals");
    private static final Path BASELINE_DIR = VISUAL_DIR.resolve("baseline");
    private static final Path CURRENT_DIR = VISUAL_DIR.resolve("current");
    private static final Path DIFF_DIR = VISUAL_DIR.resolve("diffs");

    // Pixel tolerance threshold
    private static final int PIXEL_TOLERANCE = 50;

    private final Page page;

    static {
        // Ensure directories exist
        try {
            Files.createDirectories(BASELINE_DIR);
            Files.createDirectories(CURRENT_DIR);
            Files.createDirectories(DIFF_DIR);
        } catch (IOException e) {
            logger.error("Failed to create visual regression directories", e);
        }
    }

    public VisualRegression(Page page) {
        this.page = page;
    }

    /**
     * Check visual regression for a specific element
     */
    public void checkElement(String screenshotName, Locator locator) {
        logger.info("Checking visual regression for element: {}", screenshotName);
        byte[] screenshotBytes = locator.screenshot();
        assertScreenshot(screenshotBytes, screenshotName);
    }

    /**
     * Check visual regression for full page
     */
    public void checkFullPage(String screenshotName) {
        logger.info("Checking visual regression for full page: {}", screenshotName);
        byte[] screenshotBytes = page.screenshot(new Page.ScreenshotOptions().setFullPage(true));
        assertScreenshot(screenshotBytes, screenshotName);
    }

    /**
     * Assert screenshot against baseline
     */
    private void assertScreenshot(byte[] imageBytes, String screenshotName) {
        ComparisonResult result = compareAndGenerateDiff(screenshotName, imageBytes);

        // Handle first run / no baseline
        if (result.diffCount == -1) {
            fail(String.format("BASELINE CREATED: No baseline found for '%s'. " +
                            "New baseline saved to %s. Please re-run the test.",
                    screenshotName, BASELINE_DIR));
        }

        // Handle visual failure
        if (result.diffCount > PIXEL_TOLERANCE) {
            String errorMsg = String.format(
                    "VISUAL REGRESSION: %d pixels difference found (Tolerance: %d). " +
                            "Baseline: %s, Current: %s, Diff: %s",
                    result.diffCount, PIXEL_TOLERANCE,
                    result.baselinePath, result.currentPath, result.diffPath);

            logger.error(errorMsg);
            fail(errorMsg);
        }

        // Success
        logger.info("[VISUAL SUCCESS] {} passed with {} pixels difference",
                screenshotName, result.diffCount);
    }

    /**
     * Compare current screenshot with baseline and generate diff image
     */
    private ComparisonResult compareAndGenerateDiff(String testName, byte[] currentImageBytes) {
        Path baselinePath = BASELINE_DIR.resolve(testName + ".png");
        Path currentPath = CURRENT_DIR.resolve(testName + ".png");
        Path diffPath = DIFF_DIR.resolve("DIFF_" + testName + ".png");

        try {
            // Save current screenshot
            FileUtils.writeByteArrayToFile(currentPath.toFile(), currentImageBytes);

            // Check if baseline exists
            if (!Files.exists(baselinePath)) {
                // First run: save current as baseline
                Files.copy(currentPath, baselinePath);
                return new ComparisonResult(-1, baselinePath, currentPath, null);
            }

            // Load images
            BufferedImage baselineImg = ImageIO.read(baselinePath.toFile());
            BufferedImage currentImg = ImageIO.read(new ByteArrayInputStream(currentImageBytes));

            // Ensure images are same size
            if (baselineImg.getWidth() != currentImg.getWidth() ||
                    baselineImg.getHeight() != currentImg.getHeight()) {
                logger.warn("Image sizes differ. Baseline: {}x{}, Current: {}x{}",
                        baselineImg.getWidth(), baselineImg.getHeight(),
                        currentImg.getWidth(), currentImg.getHeight());
            }

            // Calculate pixel differences
            int diffCount = calculatePixelDifference(baselineImg, currentImg, diffPath.toFile());

            return new ComparisonResult(diffCount, baselinePath, currentPath,
                    diffCount > 0 ? diffPath : null);

        } catch (IOException e) {
            logger.error("Error during visual comparison", e);
            fail("Visual comparison failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Calculate pixel-by-pixel difference between two images
     */
    private int calculatePixelDifference(BufferedImage baseline, BufferedImage current, File diffFile)
            throws IOException {
        int width = Math.min(baseline.getWidth(), current.getWidth());
        int height = Math.min(baseline.getHeight(), current.getHeight());

        BufferedImage diffImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int diffCount = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int baselineRgb = baseline.getRGB(x, y);
                int currentRgb = current.getRGB(x, y);

                if (baselineRgb != currentRgb) {
                    diffCount++;
                    // Highlight difference in red
                    diffImage.setRGB(x, y, 0xFFFF0000);
                } else {
                    // Keep original pixel (darker)
                    diffImage.setRGB(x, y, baselineRgb & 0xFF808080);
                }
            }
        }

        // Save diff image if there are differences
        if (diffCount > 0) {
            ImageIO.write(diffImage, "png", diffFile);
            logger.debug("Diff image saved to: {}", diffFile);
        } else if (diffFile.exists()) {
            // Clean up old diff if images match
            diffFile.delete();
        }

        return diffCount;
    }

    /**
     * Update baseline with current screenshot
     */
    public void updateBaseline(String testName) throws IOException {
        Path currentPath = CURRENT_DIR.resolve(testName + ".png");
        Path baselinePath = BASELINE_DIR.resolve(testName + ".png");

        if (Files.exists(currentPath)) {
            Files.copy(currentPath, baselinePath,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            logger.info("Updated baseline for: {}", testName);
        } else {
            logger.warn("Current image not found for: {}", testName);
        }
    }

    /**
     * Result of visual comparison
     */
    private static class ComparisonResult {
        final int diffCount;
        final Path baselinePath;
        final Path currentPath;
        final Path diffPath;

        ComparisonResult(int diffCount, Path baselinePath, Path currentPath, Path diffPath) {
            this.diffCount = diffCount;
            this.baselinePath = baselinePath;
            this.currentPath = currentPath;
            this.diffPath = diffPath;
        }
    }
}
