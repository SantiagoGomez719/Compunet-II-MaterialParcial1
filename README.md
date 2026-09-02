# Compunet-II-MaterialParcial1

Sí, la base quedó bien — es sólida y reutilizable. La idea es que el patrón (repo → service → servlet, con las 3 formas de wiring) no cambia nunca; lo único que cambia entre ejercicios es el contenido de 4-5 archivos puntuales. Te explico qué se queda igual y qué tienes que tocar cada vez.

# Lo que NO cambia (queda igual siempre)

- La estructura de carpetas (model / repository / service / servlet / config)
- pom.xml (las dependencias son las mismas para cualquier ejercicio)
- web.xml con el ContextLoaderListener
- El patrón del Servlet: @WebServlet + init() con WebApplicationContextUtils.getRequiredWebApplicationContext(...) + getBean("...")
- La forma de mostrar la alerta HTML (try/catch de IllegalArgumentException → imprimir el mensaje)
- Los 3 estilos de wiring (XML / anotaciones / JavaConfig) — solo cambias los nombres de las clases dentro

# Lo que SÍ cambias en cada ejercicio nuevo

# 1. El modelo — lee el enunciado y copia los atributos exactos (nombres y tipos), como en el UML. Esto es lo más fácil de perder puntos por descuido (ej. assetId en vez de deviceId como en el examen de ejemplo — léelo con cuidado).

# 2. La relación entre entidades — define si es 1-a-muchos o muchos-a-muchos, y cómo se representa:

1 a muchos (como Device→Measurement): basta con guardar el id del "padre" dentro del "hijo" (assetId).
muchos a muchos (como Artist↔Track del taller): cada entidad necesita una lista de ids (o de objetos) del otro lado, y el repositorio de creación debe permitir "vincular" ambos lados al crear.

# 3. Los métodos del repositorio/servicio — el enunciado siempre dice explícitamente qué operaciones necesita (crear, listar, buscar por X, eliminar por id). Solo agregas un método por cada verbo que pidan; el patrón interno (stream().filter(...)) es siempre el mismo.

# 4. Las reglas de negocio del Service — esta es la parte que cambia más y la que más puntos vale (35% en el examen de ejemplo). Aquí sí tienes que leer con cuidado y traducir cada regla en español a una validación if con su excepción. Un truco: subraya en el enunciado cada oración que diga "no puede", "debe", "en caso de que", "se debe garantizar" — cada una de esas es una regla que va en el Service.

# 5. Los datos semilla — siempre ajusta los valores de ejemplo para que efectivamente cumplan las reglas que acabas de escribir (si no, tu propia app arranca rota).

# Checklist mental para el día del examen
- Leo el enunciado completo una vez, sin escribir código.
- Copio el modelo (atributos + tipos) tal cual, revisando nombres raros que puedan pedir distinto a lo obvio.
- Decido la relación (1-N o N-N) y cómo la voy a guardar.
- Escribo el repositorio (copiar/pegar de la plantilla, cambiar nombres).
- Escribo el service con cada regla de negocio como un if + excepción.
- Ajusto los datos semilla para que pasen las reglas.
- Escribo/copio el servlet(s) que pidan, cambiando el nombre del bean.
- Pruebo cada regla a mano (un caso que la rompa, un caso válido).
- Hago commit — recuerda que en el examen real te van a pedir commits frecuentes como evidencia.
