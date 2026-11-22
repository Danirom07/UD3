package org.example

import java.sql.Connection

object JugadorDAO {

    fun insertarJugador(j: Jugador) {
        var conexion: Connection? = null

        try {
            conexion = ConexionBD.getConnection()
            conexion?.autoCommit = false

            val sql = "INSERT INTO jugadores (nombre, email) VALUES (?, ?)"

            conexion?.prepareStatement(sql)?.use { stmt ->
                stmt.setString(1, j.nombre)
                stmt.setString(2, j.email)
                stmt.executeUpdate()
            }

            conexion?.commit()
            println("Jugador insertado con éxito")

        } catch (e: Exception) {
            conexion?.rollback()
            println("Error al insertar el jugador. Cambios anulados.")

        } finally {
            conexion?.autoCommit = true
            conexion?.close()
        }
    }

    fun eliminarJugador(id: Int) {
        var conexion: Connection? = null

        try {
            conexion = ConexionBD.getConnection()
            conexion?.autoCommit = false

            val sql = "DELETE FROM jugadores WHERE id_jugador = ?"

            conexion?.prepareStatement(sql)?.use { stmt ->
                stmt.setInt(1, id)
                stmt.executeUpdate()
            }

            conexion?.commit()
            println("Jugador eliminado")

        } catch (e: Exception) {
            conexion?.rollback()
            println("Error al eliminar el jugador. Cambios anulados.")

        } finally {
            conexion?.autoCommit = true
            conexion?.close()
        }
    }
}