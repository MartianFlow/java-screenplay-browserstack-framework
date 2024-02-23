package com.martianflowdemo.ui;

import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.webdriver.shadow.ByShadow;
import org.openqa.selenium.By;

public class ShadowRootElementsPage {

    private ShadowRootElementsPage() {

    }

    public static final Target TEXT_SHADOW_ROOT = Target.the("texto dentro de un shadow root")
            .located(ByShadow.cssSelector("slot", "my-paragraph"));
}
