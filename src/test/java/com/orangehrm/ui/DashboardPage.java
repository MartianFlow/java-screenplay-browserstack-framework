package com.orangehrm.ui;

import net.serenitybdd.screenplay.targets.Target;

public class DashboardPage {

    private DashboardPage() {

    }

    public static final Target DASHBOARD_MENU_ITEM = Target.the("Dashboard item")
            .locatedBy("//a[@class='oxd-main-menu-item active']/span");

}
