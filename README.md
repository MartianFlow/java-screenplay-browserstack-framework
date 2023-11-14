# java-screenplay-browserstack-framework
[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=java-screenplay-browserstack-framework)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=java-screenplay-browserstack-framework&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=java-screenplay-browserstack-framework)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=java-screenplay-browserstack-framework&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=java-screenplay-browserstack-framework)


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