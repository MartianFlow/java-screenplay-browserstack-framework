package com.orangehrm.stepdefinitions;

import com.orangehrm.questions.GetElementValue;
import com.orangehrm.task.FillLoginForm;
import com.orangehrm.ui.DashboardPage;
import com.orangehrm.ui.LoginPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;



public class LoginStepsDefinitions {
    private Actor actor = CommonSteps.ACTOR;

    //********************
    //***Login Exitoso ***
    //********************
    @When("^el ingresa usuario y contraseña validos$")
    public void enterLoginInfo() {

        String usuario = GetElementValue.getTextElement(LoginPage.USER_INFO);
        String pass = GetElementValue.getTextElement(LoginPage.PASS_INFO);

        actor.attemptsTo(
            FillLoginForm
                    .with()
                    .userName(usuario.substring(usuario.indexOf(':') + 1).trim())
                    .pass(pass.substring(pass.indexOf(':') + 1).trim())
        );
    }


    @Then("^observa que es redireccionado al dashboard de forma correcta$")
    public void validateSuccessfullLogin() {
        actor.should(
                seeThat("titulo del dashboard", value -> GetElementValue
                        .getTextElement(DashboardPage.DASHBOARD_MENU_ITEM), equalTo("Dashboard"))
        );
    }

}
