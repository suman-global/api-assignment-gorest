/**
 * @author Suman
 * 
 * Runner class to run feature files
 */
package com.gorest.runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/features" }, 
				 glue = {"com.gorest.steps" },
				 tags = "@API", 
				 monochrome = true, 
				 plugin = { "json:target/cucumber-json-report.json",
				"html:target/cucumber-html-report", "junit:target/cucumber.xml" })
public class TestRunner {
}
