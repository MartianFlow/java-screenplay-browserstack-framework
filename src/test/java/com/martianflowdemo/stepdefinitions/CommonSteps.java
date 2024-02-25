package com.martianflowdemo.stepdefinitions;

import org.openqa.selenium.WebDriver;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonSteps {

    @Managed
    private WebDriver driver;

    @Before
    public void setTheStage() {
        driver.manage().window().maximize();
        ACTOR.can(BrowseTheWeb.with(driver));
    }

    public static final Actor ACTOR = Actor.named("usuario administrador");
    public static final Logger LOGGER = LoggerFactory.getLogger(CommonSteps.class);

    @Given("^usuario abre el site en su navegador$")
    public void openBrowser() {
        LOGGER.info("abriendo el site en el navegador");
        ACTOR.attemptsTo(
                Open.url("https://the-internet.herokuapp.com/"));
    }

}

