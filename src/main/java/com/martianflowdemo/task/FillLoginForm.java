//package com.martianflowdemo.task;
//
//import com.martianflowdemo.ui.ShadowRootElementsPage;
//import net.serenitybdd.core.steps.Instrumented;
//import net.serenitybdd.screenplay.Actor;
//import net.serenitybdd.screenplay.Performable;
//import net.serenitybdd.screenplay.Task;
//import net.serenitybdd.screenplay.actions.Click;
//import net.serenitybdd.screenplay.actions.Enter;
//
//public class FillLoginForm implements Task {
//
//    private String userName;
//    private String pass;
//
//    public FillLoginForm(String userName, String pass) {
//        this.userName = userName;
//        this.pass = pass;
//    }
//
//
//    public static FillLoginFormBuild with() {
//        return new FillLoginFormBuild();
//    }
//
//    @Override
//    public <T extends Actor> void performAs(T actor) {
//        actor.attemptsTo(
//
//                Enter.keyValues(userName).into(ShadowRootElementsPage.USER_INPUT),
//                Enter.keyValues(pass).into(ShadowRootElementsPage.PASS_INPUT),
//                Click.on(ShadowRootElementsPage.LOGIN_BUTTON)
//
//        );
//    }
//
//
//    public static class FillLoginFormBuild {
//        private String userNameBuilder;
//
//        public FillLoginFormBuild userName(String userName) {
//            this.userNameBuilder = userName;
//            return this;
//        }
//
//        public Performable pass(String pass) {
//            return Instrumented.instanceOf(FillLoginForm.class).withProperties(userNameBuilder, pass);
//        }
//    }
//}
