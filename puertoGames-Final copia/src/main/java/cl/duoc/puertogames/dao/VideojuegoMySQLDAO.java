package cl.duoc.puertogames.dao;

import cl.duoc.puertogames.config.Conexion;
import cl.duoc.puertogames.model.*;
import java.sql.*;
import java.util.*;

// DAO: concentra el acceso a la tabla videodiscs de MySQL.
public class VideojuegoMySQLDAO {

    // READ: obtiene todos los videojuegos desde la base de datos.
    public List<Videojuego> listar() {
        // Lista donde guardaremos los objetos creados desde las filas SQL.
        List<Videojuego> juegos = new ArrayList<>();

        // Abre la conexión, prepara SELECT y recibe las filas.
        // try-with-resources cierra estos objetos automáticamente.
        try (Connection cn = Conexion.abrir();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM videojuegos ORDER BY id");
             ResultSet rs = ps.executeQuery()) {

            // Recorre una fila por cada iteración.
            while (rs.next()) {
                // Obtiene los campos de la fila actual.
                int id = rs.getInt("id");
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                int precio = rs.getInt("precio_base");

                // Crea el objeto de la clase hija que corresponda.
                if ("FISICO".equals(rs.getString("tipo"))) {
                    juegos.add(new JuegoFisico(id, codigo, nombre, precio));
                } else {
                    juegos.add(new JuegoDigital(id, codigo, nombre, precio));
                }
            }

        // Si falla MySQL, muestra un error comprensible al controlador.
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar: " + e.getMessage(), e);
        }

        // Devuelve la lista completa (puede estar vacía).
        return juegos;
    }

    // CREATE / UPDATE: usa el ID para distinguir insertar y editar.
    public void guardar(Videojuego j) {

        // Por defecto, la consulta inserta un videojuego nuevo.
        String sql = "INSERT INTO videojuegos(codigo,nombre,precio_base,tipo) VALUES(?,?,?,?)";

        // Si j tiene un ID existente, debemos actualizar esa fila.
        if (j.getId() != 0) {
            sql = "UPDATE videojuegos SET codigo=?,nombre=?,precio_base=?,tipo=? WHERE id=?";
        }

        // Abre una conexión y prepara la consulta seleccionada.
        try (Connection cn = Conexion.abrir();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            // Cada setXXX reemplaza uno de los signos ? en orden.
            ps.setString(1, j.getCodigo());
            ps.setString(2, j.getNombre());
            ps.setInt(3, j.getPrecioBase());
            ps.setString(4, j.getTipo());

            // En UPDATE, el quinto signo ? identifica la fila por ID.
            if (j.getId() != 0) {
                ps.setInt(5, j.getId());
            }

            // Ejecuta INSERT o UPDATE; devuelve las filas afectadas.
            int filas = ps.executeUpdate();
            // Informa si el UPDATE no encontró el registro solicitado.
            if (filas == 0) {
                throw new RuntimeException("No se encontró el videojuego.");
            }

        // Captura únicamente los problemas que informa JDBC.
        } catch (SQLException e) {
            throw new RuntimeException("Error al guardar: " + e.getMessage(), e);
        }
    }

    // DELETE: elimina solamente la fila con el ID indicado.
    public void eliminar(int id) {

        // Usa ? para incorporar el ID de manera segura.
        try (Connection cn = Conexion.abrir();
             PreparedStatement ps = cn.prepareStatement(
                     "DELETE FROM videojuegos WHERE id=?")) {

            // Reemplaza el único signo ? por el ID seleccionado.
            ps.setInt(1, id);
            // Ejecuta la eliminación en MySQL.
            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new RuntimeException("No se encontró el videojuego.");
            }

        // Si MySQL falla, propagamos un mensaje hacia el Controller.
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar: " + e.getMessage(), e);
        }
    }
}
