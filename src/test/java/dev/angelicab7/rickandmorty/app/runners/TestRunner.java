package dev.angelicab7.rickandmorty.app.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"dev.angelicab7.rickandmorty.app.steps",
                "dev.angelicab7.rickandmorty.app.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json",
                "junit:target/cucumber-reports/cucumber.xml"
        },
        monochrome = true,
        tags = "@search_character"
)
public class TestRunner extends AbstractTestNGCucumberTests {
}