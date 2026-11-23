package cl.duoc.puertogames.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Centraliza los datos de conexión; no ejecuta consultas CRUD.
public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/puertogames?useSSL=false";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    public static Connection abrir() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
