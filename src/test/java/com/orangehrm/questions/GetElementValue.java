package com.orangehrm.questions;

import com.orangehrm.stepdefinitions.CommonSteps;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

public class GetElementValue {

    private GetElementValue() {
    }

    private static Actor actor = CommonSteps.ACTOR;

    public static <T> String getTextElement(T ei) {
        return actor.asksFor(Text.of((Target) ei)).trim();
    }
}
