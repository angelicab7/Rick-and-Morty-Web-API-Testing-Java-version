package dev.angelicab7.rickandmorty.app.Web.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"dev.angelicab7.rickandmorty.app.Web.steps",
                "dev.angelicab7.rickandmorty.app.Web.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        monochrome = true,
        tags = "@search_character or @visual_regression"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}