Feature: pruebas Login orangehrm site

    Background:Navegar a pagina orangehrm
        Given usuario abre el site orangehrm en su navegador

    @Regresion @Testing
    Scenario: login exitoso
        When el ingresa usuario y contraseña validos
        Then observa que es redireccionado al dashboard de forma correcta