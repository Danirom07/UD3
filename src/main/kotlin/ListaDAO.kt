package org.example

import java.sql.Connection

object ListaDAO {

    fun anyadirALista(idLista: Int, idVideojuego: Int, fecha: String){
        var conexion: Connection? = null

        try {
            conexion = ConexionBD.getConnection()
            conexion?.autoCommit = false
            var lista : Videojuego? = null
            val sql = "SELECT * FROM videojuegos WHERE id = ?"
            val sqlLista = "INSERT INTO listas_videojuegos (id_lista, id_videojuego, fecha_añadido ) VALUES (?, ?, ?)"
            var exsiste = false



                conexion?.prepareStatement(sql)?.use { stmt ->
                    stmt.setInt(1, idVideojuego)
                    val rs = stmt.executeQuery()
                        if (rs.next()){
                            exsiste = true
                        }
                     }

                if (!exsiste){
                    println("El id no exsiste")
                    conexion?.rollback()
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
            conexion?.close()
            conexion?.autoCommit = true
        }

    }
}