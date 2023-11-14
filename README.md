# java-screenplay-browserstack-framework
[![SonarCloud](https://sonarcloud.io/images/project_badges/sonarcloud-white.svg)](https://sonarcloud.io/summary/new_code?id=java-screenplay-browserstack-framework)



Este repositorio contiene un marco de trabajo (framework) basado en Java y Screenplay Pattern para pruebas de automatización de navegadores. Se integra con BrowserStack para permitir ejecuciones remotas y ofrece una estructura escalable y modular para escribir y mantener pruebas automatizadas de calidad.

## Comandos a Tener en cuenta para la Ejecución:

### Ejecutar Local - Navegador Chrome
```
mvn clean verify -D"webdriver.driver=chrome" -P"sample-local-test"
```

### Ejecutar Local - Navegador Firefox
```
mvn clean verify -D"webdriver.driver=firefox" -P"sample-local-test"
```

### Ejecutar Local - Navegador Edge
```
mvn clean verify -D"webdriver.driver=edge" -P"sample-local-test"
```

### Ejecutar Remoto Browserstack
```
mvn clean verify -D"webdriver.driver=remote" -D"webdriver.remote.url=https://hub.browserstack.com/wd/hub" -P"sample-test"
```

Recuerda ajustar las opciones según tus necesidades. Además, asegúrate de tener todas las dependencias y configuraciones necesarias antes de ejecutar estos comandos. ¡Feliz automatización!