Feature: interaccion con elemento Shadow DOM

    Background:Navegar a pagina de elementos theInternet herokuapp
        Given usuario abre el site en su navegador

    @Regresion @Testing
    Scenario: Obtener texto dentro de un shadow root
        When el navega a la pagina de los elementos shadowDOM
        Then observa que el texto dentro del primer shadow root es My default text