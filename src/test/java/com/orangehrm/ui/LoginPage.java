package com.orangehrm.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class LoginPage {

    private LoginPage() {

    }

    //Login Info
    public static final Target USER_INFO = Target.the("user info")
            .locatedBy("//div[contains(@class, 'oxd-sheet')]/p[1]");
    public static final Target PASS_INFO = Target.the("pass info")
            .locatedBy("//div[contains(@class, 'oxd-sheet')]/p[2]");

    public static final Target USER_INPUT = Target.the("user input").located(By.name("username"));
    public static final Target PASS_INPUT = Target.the("pass input").located(By.name("password"));
    public static final Target LOGIN_BUTTON = Target.the("login button").located(By.className("oxd-button"));
}
