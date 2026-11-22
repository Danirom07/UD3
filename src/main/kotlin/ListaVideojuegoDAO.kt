package org.example

import java.sql.Connection

object ListaVideojuegoDAO {

    fun anyadirALista(idLista: Int, idVideojuego: Int, fecha: String) {
        var conexion: Connection? = null

        try {
            conexion = ConexionBD.getConnection()
            conexion?.autoCommit = false

            val sql = "SELECT * FROM videojuegos WHERE id = ?"
            val sqlLista = "INSERT INTO listas_videojuegos (id_lista, id_videojuego, fecha_añadido) VALUES (?, ?, ?)"
            var existe = false

            conexion?.prepareStatement(sql)?.use { stmt ->
                stmt.setInt(1, idVideojuego)
                val rs = stmt.executeQuery()

                if (rs.next()) {
                    existe = true
                }
            }

            if (!existe) {
                println("El id no existe")
                conexion?.rollback()
                return
            }

            conexion?.prepareStatement(sqlLista)?.use { stmt ->
                stmt.setInt(1, idLista)
                stmt.setInt(2, idVideojuego)
                stmt.setString(3, fecha)
                stmt.executeUpdate()
            }

            conexion?.commit()
            println("Juego insertado en lista")

        } catch (e: Exception) {
            conexion?.rollback()
            println("Error en la transacción. Cambios anulados.")

        } finally {
            conexion?.autoCommit = true
            conexion?.close()
        }
    }

    fun obtenerVideojuegosDeLista(idLista: Int): List<Videojuego> {
        val lista = mutableListOf<Videojuego>()
        val conexion = ConexionBD.getConnection()

        val sql = """
            SELECT v.id, v.titulo, v.genero, v.anio, v.nota
            FROM videojuegos v
            JOIN listas_videojuegos lv ON lv.id_videojuego = v.id
            WHERE lv.id_lista = ?
        """

        conexion?.prepareStatement(sql)?.use { stmt ->
            stmt.setInt(1, idLista)
            val rs = stmt.executeQuery()

            while (rs.next()) {
                lista.add(
                    Videojuego(
                        id = rs.getInt("id"),
                        titulo = rs.getString("titulo"),
                        genero = rs.getString("genero"),
                        anio = rs.getInt("anio"),
                        nota = rs.getDouble("nota")
                    )
                )
            }
        }

        conexion?.close()
        return lista
    }
}