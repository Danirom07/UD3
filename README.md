# Catálogo de Videojuegos con BD Relacional

Programa de consola en Kotlin para gestionar un catálogo de videojuegos, jugadores y listas de videojuegos.
Se conecta a una base de datos PostgreSQL mediante JDBC y utiliza el patrón DAO. Permite hacer el CRUD de videojuegos, crear y eliminar jugadores, crear y eliminar listas (cada lista pertenece a un jugador) y añadir videojuegos a una lista con una transacción.

## 1. Estructura de datos

### **Data Classes:**

```kotlin
data class Videojuego(
    val id: Int? = null,
    val titulo: String,
    val genero: String,
    val anio: Int,
    val nota: Double
)

data class Jugador(
    val idJugador: Int? = null,
    val nombre: String,
    val email: String? = null
)

data class Lista(
    val idLista: Int? = null,
    val nombre: String,
    val idJugador: Int
)

data class ListaVideojuego(
    val idLista: Int,
    val idVideojuego: Int,
    val fechaAnadido: String
)
```

### **Estructura de la Base de Datos (PostgreSQL):**

Base de datos `videojuegos`, con cuatro tablas. El esquema completo y los datos de prueba están en `datos/videojuegos.sql`.

- **videojuegos**
  - `id`: SERIAL, clave primaria
  - `titulo`: VARCHAR(150)
  - `genero`: VARCHAR(60)
  - `anio`: INTEGER
  - `nota`: REAL
- **jugadores**
  - `id_jugador`: SERIAL, clave primaria
  - `nombre`: VARCHAR(100)
  - `email`: VARCHAR(150), único
- **listas**
  - `id_lista`: SERIAL, clave primaria
  - `nombre`: VARCHAR(100)
  - `id_jugador`: INTEGER, clave foránea hacia `jugadores`
- **listas_videojuegos** (tabla intermedia)
  - `id_lista`: INTEGER, clave foránea hacia `listas`
  - `id_videojuego`: INTEGER, clave foránea hacia `videojuegos`
  - `fecha_añadido`: VARCHAR(20)
  - Clave primaria compuesta: (`id_lista`, `id_videojuego`)

### **Relaciones:**

```
jugadores (1) ---< (N) listas (1) ---< (N) listas_videojuegos (N) >--- (1) videojuegos
```

- Un jugador tiene varias listas y cada lista pertenece a un solo jugador.
- Una lista contiene varios videojuegos y un videojuego puede estar en varias listas.

Las tablas están pobladas con 5 videojuegos, 3 jugadores y 3 listas (cada una de un jugador diferente).

## 2. Instrucciones de ejecución

- **Requisitos previos**: JDK 17 o superior, IntelliJ IDEA con Gradle y PostgreSQL instalado y en marcha (pgAdmin es opcional).
- **Crear la base de datos**: En pgAdmin crea una base de datos llamada `videojuegos`. Abre el Query Tool sobre ella y ejecuta el contenido de `datos/videojuegos.sql`, que crea las tablas y las rellena con datos de prueba.
- **Configurar la conexión**: Abre `ConexionBD.kt` y cambia el usuario y la contraseña (`USER` y `PASSWORD`) por los de tu PostgreSQL. Si no usas el puerto 5432 o `localhost`, cambia también `PORT` y `HOST`.
- **Compilación**: Abre el proyecto en IntelliJ IDEA y deja que Gradle sincronice las dependencias (el driver `org.postgresql:postgresql`).
- **Ejecución principal**: Ejecuta `Main.kt`. Aparece un menú por consola con las opciones del 0 al 11, divididas en tres bloques: videojuegos, jugadores y listas.

## 3. Decisiones de diseño

- Elegí PostgreSQL porque es un sistema cliente-servidor que me permite trabajar con claves foráneas e integridad referencial de forma real, y porque los ids se generan solos con `SERIAL`.
- Hice que la tabla `jugadores` tenga solo `id_jugador`, `nombre` y `email`, porque son los datos mínimos para identificar a un jugador. El `email` es único para que no se repita el mismo jugador.
- Puse la clave foránea `id_jugador` en `listas` como `NOT NULL` para que no pueda existir una lista sin dueño. Además, antes de insertar una lista compruebo que el jugador existe y, si no existe, no la creo.
- Usé `ON DELETE CASCADE` en las claves foráneas para que, al eliminar un jugador, se borren sus listas, y al eliminar una lista o un videojuego se borren sus filas en `listas_videojuegos`, sin tener que borrarlas a mano desde Kotlin.
- Añadir un videojuego a una lista lo hice con una transacción: desactivo el `autoCommit`, compruebo que el videojuego existe, inserto la relación y hago `commit`. Si algo falla, hago `rollback` para no dejar cambios a medias.
- Usé una clave primaria compuesta (`id_lista`, `id_videojuego`) en la tabla intermedia para que un mismo videojuego no se pueda añadir dos veces a la misma lista.
- Guardé `fecha_añadido` como texto porque mi código la recibe como `String` y la inserta con `setString`.
