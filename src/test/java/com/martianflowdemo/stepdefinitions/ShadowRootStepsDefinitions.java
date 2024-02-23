package com.martianflowdemo.stepdefinitions;

import com.martianflowdemo.questions.GetElementValue;
import com.martianflowdemo.ui.HomePage;
import com.martianflowdemo.ui.ShadowRootElementsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;



public class ShadowRootStepsDefinitions {
    private Actor actor = CommonSteps.ACTOR;

    //**********************************
    //***Obtener texto de Shadow Root***
    //*********************************
    @When("^el navega a la pagina de los elementos shadowDOM$")
    public void navigateToShadowDOMPage() {

        actor.attemptsTo(
                Click.on(HomePage.SHADOW_DOM_LINK)
        );
    }


    @Then("^observa que el texto dentro del primer shadow root es (.*)$")
    public void validateShadowRootText(String expectedShadowText) {
        actor.should(
                seeThat("titulo dentro del shadow root",
                        GetElementValue.fromTarget(ShadowRootElementsPage.TEXT_SHADOW_ROOT),
                            equalTo(expectedShadowText))
        );
    }

}
