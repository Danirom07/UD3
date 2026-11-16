package org.example



fun main() {
    try {
    val conn = ConexionBD.getConnection()
    if (conn != null) {
        println("Conectado a la BD correctamente.")
        ConexionBD.closeConnection(conn)
    }
    // Listar todas las plantas
    println("Lista de videojuegos :")
    VideojuegoDAO.listarVideojuego().forEach {
        println(" - [${it.id}] ${it.titulo} (${it.genero}), anio: ${it.anio} nota: ${it.nota}€")

    }
        // Consultar planta por ID
        val Videojuego = VideojuegoDAO.consultarVideojuegoPorId(3)
        if (Videojuego != null) {
            println("videojuegos  encontrada: [${Videojuego.id}] ${Videojuego.titulo} (${Videojuego.genero}), anio: ${Videojuego.anio} nota: ${Videojuego.nota}€")
        } else {
            println("No se encontró ninguna videojuegos  con ese ID.")
        }

        // Insertar planta nueva
        VideojuegoDAO.insertarVideojuego(
            Videojuego(
                titulo = "Elden Ring",
                genero = "Souls-like",
                anio = 2022,
                nota = 9.6
            )
        )

        VideojuegoDAO.insertarVideojuego(
            Videojuego(
                titulo = "fifa",
                genero = "deporte",
                anio = 2022,
                nota = 7.0
            )
        )
        val fifa = VideojuegoDAO.listarVideojuego().last { it.titulo == "fifa" }

        // Actualizar planta con id=1
        VideojuegoDAO.actualizarVideojuego(
            Videojuego(
                id = fifa.id,
                titulo = "fifa",
                genero = "deportes",
                anio = 10,
                nota = 0.75
            )
        )

        // Eliminar planta con id=2
        VideojuegoDAO.eliminarVideojuego(fifa.id!!)
    } catch (e: Exception) {
        println("ERROR: ${e.message}")
        e.printStackTrace()
    }
}