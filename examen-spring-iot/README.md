# Examen Spring - IoT (Device / Measurement) — resuelto como práctica

Resuelto siguiendo el examen de ejemplo, en el estilo real del curso
(XML puro por defecto, jakarta.servlet, ContextLoaderListener + @WebServlet).

## ⚠️ Nota sobre una línea rara del PDF

El examen de ejemplo dice en un punto: *"El atributo de ubicación ponle
'Ubicación' y el de estado ponle 'Estate'"*. El diagrama UML de `Device`
**no** incluye esos atributos, así que probablemente es un resto de otra
versión del documento. Si tu examen real sí los pide, agrégalos a
`Device.java` como dos `String` más (`ubicacion`, `estado`) y sus
getters/setters — es mecánico, no cambia nada de la arquitectura.

## Mapeo de las reglas de negocio → código

Todo vive en `MeasurementServiceImpl.registrarMeasurement()`:

| Regla del examen | Dónde está en el código |
|---|---|
| (a) Valor fuera del rango del dispositivo | `if (value < minValue \|\| value > maxValue)` |
| (b) Timestamp duplicado para el mismo dispositivo | `medicionesDelDispositivo.stream().anyMatch(...)` |
| (c) Diferencia de tiempo fuera de samplingPeriod ± timeTolerance | Se busca la medición con mayor timestamp del mismo dispositivo (`.max(Comparator...)`) y se compara la diferencia contra `[samplingPeriod - timeTolerance, samplingPeriod + timeTolerance]` |
| Serial > 20 caracteres | `DeviceServiceImpl.registrarDevice()` |

Todas lanzan `IllegalArgumentException` con un mensaje claro. El servlet
(`AddMeasurementServlet.doPost`) atrapa esa excepción y muestra la alerta
en HTML en vez de un stacktrace — así es como el examen pide "alertar al
usuario por medio de una vista en HTML".

## Datos semilla (cumplen las reglas desde el inicio)

- 1 `Device` (id=1): rango -100 a 300, samplingPeriod=2000ms, timeTolerance=200ms
- 2 `Measurement` para ese dispositivo: timestamps 2000 y 4000
  (diferencia = 2000ms, exactamente el samplingPeriod → válido)

## Para probar manualmente los 3 casos de error

Con el dispositivo id=1 (rango -100 a 300, samplingPeriod=2000, tolerance=200,
última medición en timestamp=4000):

- **Fuera de rango:** value = 500 → dispara la regla (a)
- **Timestamp duplicado:** timestamp = 4000 (ya existe) → dispara la regla (b)
- **Fuera de tolerancia de tiempo:** timestamp = 5000 (diferencia = 1000ms,
  fuera de [1800, 2200]) → dispara la regla (c)
- **Caso válido:** timestamp = 6000 (diferencia = 2000ms) → se registra bien

## Estructura

```
src/main/java/com/compunet/
├── model/      -> Device.java, Measurement.java
├── repository/ -> DeviceRepository(+InMemory), MeasurementRepository(+InMemory)
├── service/    -> DeviceService(+Impl), MeasurementService(+Impl con las reglas)
└── servlet/    -> AddMeasurementServlet.java, ListMeasurementsServlet.java

src/main/resources/applicationContext.xml -> 4 beans (2 repos + 2 services)
src/main/webapp/WEB-INF/web.xml            -> ContextLoaderListener
```

## Cómo probarlo

```bash
mvn clean package
```

Despliega el `.war` en Tomcat y abre:
- `http://localhost:8080/examen_spring_iot/` — página de inicio
- `.../measurements` — lista de mediciones
- `.../measurements/add` — formulario para agregar (y ver las alertas)
