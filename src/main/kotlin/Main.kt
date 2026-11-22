package org.example

fun main() {
    var opcion: Int

    do {
        println(
            """
            
            ===== CATÁLOGO DE VIDEOJUEGOS =====

            --- VIDEOJUEGOS ---
            1. Ver todos los videojuegos
            2. Buscar videojuego por ID
            3. Añadir videojuego
            4. Modificar videojuego
            5. Eliminar videojuego

            --- JUGADORES ---
            6. Crear jugador
            7. Eliminar jugador

            --- LISTAS ---
            8. Crear lista
            9. Eliminar lista
            10. Añadir videojuego a una lista
            11. Ver videojuegos de una lista

            0. Salir
            ====================================
            """.trimIndent()
        )
        print("Escoge una opción: ")
        opcion = readLine()?.toIntOrNull() ?: -1

        try {
            when (opcion) {
                1 -> {
                    VideojuegoDAO.listarVideojuego().forEach {
                        println(" - [${it.id}] ${it.titulo} (${it.genero}), año: ${it.anio} nota: ${it.nota}")
                    }
                }
                2 -> {
                    print("ID: ")
                    val id = readLine()!!.toInt()
                    val v = VideojuegoDAO.consultarVideojuegoPorId(id)
                    if (v != null) {
                        println("[${v.id}] ${v.titulo} (${v.genero}), año: ${v.anio} nota: ${v.nota}")
                    } else {
                        println("No existe ese videojuego.")
                    }
                }
                3 -> {
                    print("Título: ")
                    val titulo = readLine()!!
                    print("Género: ")
                    val genero = readLine()!!
                    print("Año: ")
                    val anio = readLine()!!.toInt()
                    print("Nota: ")
                    val nota = readLine()!!.toDouble()
                    VideojuegoDAO.insertarVideojuego(Videojuego(titulo = titulo, genero = genero, anio = anio, nota = nota))
                }
                4 -> {
                    print("ID del videojuego a modificar: ")
                    val id = readLine()!!.toInt()
                    print("Nuevo título: ")
                    val titulo = readLine()!!
                    print("Nuevo género: ")
                    val genero = readLine()!!
                    print("Nuevo año: ")
                    val anio = readLine()!!.toInt()
                    print("Nueva nota: ")
                    val nota = readLine()!!.toDouble()
                    VideojuegoDAO.actualizarVideojuego(Videojuego(id, titulo, genero, anio, nota))
                }
                5 -> {
                    print("ID del videojuego a eliminar: ")
                    val id = readLine()!!.toInt()
                    VideojuegoDAO.eliminarVideojuego(id)
                }
                6 -> {
                    print("Nombre: ")
                    val nombre = readLine()!!
                    print("Email: ")
                    val email = readLine()!!
                    JugadorDAO.insertarJugador(Jugador(nombre = nombre, email = email))
                }
                7 -> {
                    print("ID del jugador a eliminar: ")
                    val id = readLine()!!.toInt()
                    JugadorDAO.eliminarJugador(id)
                }
                8 -> {
                    print("Nombre de la lista: ")
                    val nombre = readLine()!!
                    print("ID del jugador: ")
                    val idJugador = readLine()!!.toInt()
                    ListaDAO.insertarLista(Lista(nombre = nombre, idJugador = idJugador))
                }
                9 -> {
                    print("ID de la lista a eliminar: ")
                    val id = readLine()!!.toInt()
                    ListaDAO.eliminarLista(id)
                }
                10 -> {
                    print("ID de la lista: ")
                    val idLista = readLine()!!.toInt()
                    print("ID del videojuego: ")
                    val idVideojuego = readLine()!!.toInt()
                    print("Fecha (AAAA-MM-DD): ")
                    val fecha = readLine()!!
                    ListaVideojuegoDAO.anyadirALista(idLista, idVideojuego, fecha)
                }
                11 -> {
                    print("ID de la lista: ")
                    val idLista = readLine()!!.toInt()
                    ListaVideojuegoDAO.obtenerVideojuegosDeLista(idLista).forEach {
                        println(" - [${it.id}] ${it.titulo} (${it.genero}), año: ${it.anio} nota: ${it.nota}")
                    }
                }
                0 -> println("Adiós")
                else -> println("Opción no válida.")
            }
        } catch (e: Exception) {
            println("ERROR: ${e.message}")
        }
    } while (opcion != 0)
}