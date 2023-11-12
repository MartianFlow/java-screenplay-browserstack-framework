package com.orangehrm.questions;


import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;

/* Implementation Doc: https://serenity-bdd.github.io/docs/screenplay/screenplay_webdriver#querying-the-web-page */
public class GetElementValue implements Question<String> {

    private static Target questElement;

    private GetElementValue() {
    }

    @Override
    public String answeredBy(Actor actor) {
        return questElement.resolveFor(actor).getText();
    }

    public static Question<String> fromTarget(Target element) {
        questElement = element;
        return new GetElementValue();
    }
}
