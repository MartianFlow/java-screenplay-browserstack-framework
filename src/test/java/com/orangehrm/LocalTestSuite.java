package com.orangehrm;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features",
                plugin =    {
                "pretty", "html:target/serenity-reports//serenity-html-report",
                "json:target/serenity-reports/cucumber_report.json",
                "rerun:target/serenity-reports/rerun.txt"
                })
public class LocalTestSuite {
}
