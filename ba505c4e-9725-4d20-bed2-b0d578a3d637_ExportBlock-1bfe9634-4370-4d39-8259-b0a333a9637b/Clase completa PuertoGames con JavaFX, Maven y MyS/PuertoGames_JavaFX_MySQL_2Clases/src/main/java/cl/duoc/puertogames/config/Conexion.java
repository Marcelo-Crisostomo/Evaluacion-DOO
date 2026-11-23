package cl.duoc.puertogames.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Centraliza los datos de conexión; no ejecuta consultas CRUD.
public class Conexion {

    // Dirección del servidor MySQL y base de datos que utilizaremos.
    private static final String URL = "jdbc:mysql://localhost:3306/puertogames?useSSL=false";
    // Usuario local de ejemplo (XAMPP/Laragon); cambiar si es necesario.
    private static final String USUARIO = "root";
    // Contraseña local de ejemplo; cambiar según la instalación.
    private static final String CLAVE = "";

    // Devuelve una conexión abierta; quien la solicita debe cerrarla.
    public static Connection abrir() throws SQLException {
        // DriverManager usa los parámetros para conectarse a MySQL.
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
