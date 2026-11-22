package org.example

import java.sql.Connection

object ListaDAO {

    fun insertarLista(l: Lista) {
        var conexion: Connection? = null

        try {
            conexion = ConexionBD.getConnection()
            conexion?.autoCommit = false

            val sql = "SELECT * FROM jugadores WHERE id_jugador = ?"
            val sqlLista = "INSERT INTO listas (nombre, id_jugador) VALUES (?, ?)"
            var existe = false

            conexion?.prepareStatement(sql)?.use { stmt ->
                stmt.setInt(1, l.idJugador)
                val rs = stmt.executeQuery()

                if (rs.next()) {
                    existe = true
                }
            }

            if (!existe) {
                println("El jugador no existe")
                conexion?.rollback()
                return
            }

            conexion?.prepareStatement(sqlLista)?.use { stmt ->
                stmt.setString(1, l.nombre)
                stmt.setInt(2, l.idJugador)
                stmt.executeUpdate()
            }

            conexion?.commit()
            println("Lista insertada")

        } catch (e: Exception) {
            conexion?.rollback()
            println("Error en la transacción. Cambios anulados.")

        } finally {
            conexion?.autoCommit = true
            conexion?.close()
        }
    }

    fun eliminarLista(idLista: Int) {
        var conexion: Connection? = null

        try {
            conexion = ConexionBD.getConnection()
            conexion?.autoCommit = false

            val sql = "DELETE FROM listas WHERE id_lista = ?"

            conexion?.prepareStatement(sql)?.use { stmt ->
                stmt.setInt(1, idLista)
                stmt.executeUpdate()
            }

            conexion?.commit()
            println("Lista eliminada")

        } catch (e: Exception) {
            conexion?.rollback()
            println("Error al eliminar la lista. Cambios anulados.")

        } finally {
            conexion?.autoCommit = true
            conexion?.close()
        }
    }
}