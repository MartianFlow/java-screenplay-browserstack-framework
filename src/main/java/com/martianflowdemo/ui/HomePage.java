package com.martianflowdemo.ui;

import net.serenitybdd.screenplay.targets.Target;

public class HomePage {

    private HomePage() {

    }

    public static final Target SHADOW_DOM_LINK = Target.the("enlace a pagina shadowDOM")
            .locatedBy("//a[@href='/shadowdom']");

}
