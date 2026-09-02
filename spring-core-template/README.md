# Plantilla Spring Core + Servlets (v2 — ajustada al estilo real del curso)

Ajustada después de revisar las ramas `intro-spring`, `intro-spring-annotations`,
`intro-spring-SpEL`, `intro-spring-beans-scopes-and-lifecycle` y `servlets`
del repo del curso. Usa `jakarta.servlet` (no `javax`), `ContextLoaderListener`
estándar de Spring, y `@WebServlet` + `WebApplicationContextUtils` como en
`EstudianteServlet`.

## Estructura

```
src/main/java/com/compunet/
├── model/      -> Item.java (cámbialo por el modelo real del examen)
├── repository/ -> interfaz + ItemRepositoryImpl (Collection en memoria,
│                  datos iniciales cargados en @PostConstruct)
├── service/    -> ItemService + dos implementaciones:
│                    - ItemServiceImpl        (inyección por constructor)
│                    - ItemServiceSetterImpl  (inyección por setter)
├── config/     -> AppConfig.java (@Configuration + @ComponentScan + @PropertySource)
└── servlet/    -> ItemServlet.java (@WebServlet + WebApplicationContextUtils)

src/main/resources/applicationContext.xml -> versión XML del wiring
src/main/resources/application.properties -> valores para @Value/SpEL
src/main/webapp/WEB-INF/web.xml            -> ContextLoaderListener
```

## Cómo cambiar de versión de DI

Todo se controla en **`web.xml`**, cambiando `contextConfigLocation`:

**Versión XML (activa por defecto):**
```xml
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
</context-param>
```

**Versión Anotaciones / JavaConfig:**
```xml
<context-param>
    <param-name>contextClass</param-name>
    <param-value>org.springframework.web.context.support.AnnotationConfigWebApplicationContext</param-value>
</context-param>
<context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>com.compunet.config.AppConfig</param-value>
</context-param>
```

En el Servlet, cambia `context.getBean("itemServiceBean")` por
`context.getBean("itemServiceSetterBean")` o por tipo
(`context.getBean(ItemService.class)`) según qué bean quieras usar.

## Cosas del curso que NO están en el taller/examen de ejemplo,
## pero SÍ vimos en clase (por si el parcial las toca)

- **SpEL / `@Value`**: revisa `LiquidacionMatriculaService` y
  `SistemaConfigService` en la rama `intro-spring-SpEL`. Ideas clave:
  - `@Value("${prop}")` lee de `application.properties` (necesita `@PropertySource`)
  - `@Value("#{expresión}")` es SpEL puro: aritmética, `T(Clase).metodo()`,
    referencia a otro bean por su nombre, ternarios, `and`/`or`.
- **Scopes**: `@Scope("prototype")` (o `scope="prototype"` en XML) crea
  una instancia nueva cada vez que se pide el bean, en vez de reusar
  siempre la misma (que es el comportamiento por defecto, `singleton`).
- **Lifecycle**: `@PostConstruct`/`@PreDestroy` (anotaciones) o
  `init-method`/`destroy-method` (XML) — útil para separar "crear el
  objeto" de "cargar sus datos iniciales".

## Cómo probarlo en Tomcat

1. `mvn clean package` → genera el `.war` en `target/`
2. Despliega el `.war` en Tomcat (o desde tu IDE)
3. Abre `http://localhost:8080/spring_core_template/`
4. En consola deberías ver el mensaje de `[LIFECYCLE] Inicializando...`
   **una sola vez** al arrancar — si aparece en cada request, el
   contexto se está recreando mal.

## Checklist antes de entregar

- [ ] `ContextLoaderListener` registrado en `web.xml`, contexto se crea una sola vez
- [ ] Servlets con `@WebServlet`, obtienen el bean en `init()` (no en doGet/doPost)
- [ ] Service depende del Repository por inyección (constructor y/o setter)
- [ ] Reglas de negocio viven en el Service, no en el Servlet
- [ ] Violación de regla → vista HTML clara, no stacktrace
- [ ] Repositorio inicia con datos válidos (constructor, `@PostConstruct` o `init-method`)
- [ ] `mvn clean package` sin errores y el `.war` despliega bien en Tomcat
