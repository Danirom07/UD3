
package org.example

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object ConexionBD {

    private val url = "jdbc:postgresql://localhost:5432/videojuegos"
    private val usuario = "postgres"
    private val password = "qwerty2"

    fun getConnection(): Connection? {
        return try {
            DriverManager.getConnection(url, usuario, password)
        } catch (e: SQLException) {
            println("Error al conectar con la base de datos: ${e.message}")
            null
        }
    }

    fun testConnection(): Boolean {
        return getConnection()?.use { conn ->
            println("Conexión establecida con éxito a PostgreSQL")
            true
        } ?: false
    }

    fun closeConnection(conn: Connection?) {
        try {
            conn?.close()
            println("Conexión cerrada correctamente.")
        } catch (e: SQLException) {
            println("Error al cerrar la conexión: ${e.message}")
        }
    }
}
