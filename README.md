# java-screenplay-browserstack-framework
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=bbog-eys-site-automated-testing&metric=alert_status&token=2024c4693e33f9155b50753659cffa53f69afe35)](https://sonarcloud.io/summary/new_code?id=bbog-eys-site-automated-testing)

Llene aquí la descripción ampliada arquitect@ o desarrollador@!

Comandos a Tener en cuenta para la Ejecucion:

Ejecutar Local - Navegador Chrome
mvn clean verify -D"webdriver.driver=chrome" -P"sample-local-test"

Ejecutar Local - Navegador Firefox
mvn clean verify -D"webdriver.driver=firefox" -P"sample-local-test"

Ejecutar Local - Navegador Edge
mvn clean verify -D"webdriver.driver=edge" -P"sample-local-test"

Ejecutar Remoto Browserstack
mvn clean verify -D"webdriver.driver=remote" -D"webdriver.remote.url=https://hub.browserstack.com/wd/hub" -P"sample-test"