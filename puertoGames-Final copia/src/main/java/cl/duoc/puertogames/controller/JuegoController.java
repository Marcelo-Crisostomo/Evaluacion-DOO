package cl.duoc.puertogames.controller;

import cl.duoc.puertogames.dao.VideojuegoMySQLDAO;
import cl.duoc.puertogames.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

// Recibe los eventos de JavaFX, pero NO contiene consultas SQL.
public class JuegoController {

    // FXML vincula estos atributos con los controles del archivo .fxml.
    @FXML private ComboBox<String> tipo;
    @FXML private TextField codigo, nombre, precio;
    @FXML private ListView<Videojuego> lista;
    @FXML private Label mensaje;

    // El Controller pide operaciones al DAO, que trabaja con MySQL.
    private final VideojuegoMySQLDAO dao = new VideojuegoMySQLDAO();

    // Se ejecuta automáticamente al cargar el archivo FXML.
    @FXML private void initialize() {
        // Prepara las dos opciones del ComboBox.
        tipo.getItems().addAll("FISICO", "DIGITAL");

        // Intenta mostrar los datos existentes al iniciar la app.
        try {
            cargar();
        } catch (RuntimeException e) {
            mensaje.setText(e.getMessage());
        }
    }








    // Solicita al DAO todos los juegos y actualiza la lista visible.
    private void cargar() {
        lista.getItems().setAll(dao.listar());
    }

    // Se activa al hacer clic en un videojuego de la lista.
    @FXML private void seleccionar() {
        // Obtiene el objeto seleccionado (o null si no hay selección).
        Videojuego j = lista.getSelectionModel().getSelectedItem();
        // Evita intentar leer datos cuando no hay selección.
        if (j == null) return;

        // Copia sus datos actuales a los controles del formulario.
        tipo.setValue(j.getTipo());
        codigo.setText(j.getCodigo());
        nombre.setText(j.getNombre());
        precio.setText(String.valueOf(j.getPrecioBase()));
    }






    // El botón Guardar sirve tanto para registrar como para editar.
    @FXML private void guardar() {
        try {
            // Comprueba que se haya seleccionado el tipo de juego.
            if (tipo.getValue() == null) {
                mensaje.setText("Selecciona un tipo.");
                return;
            }

            // Convierte el texto del precio a un número entero.
            int monto = Integer.parseInt(precio.getText().trim());

            // Si hay una fila seleccionada, guardamos su ID para editar.
            Videojuego seleccionado = lista.getSelectionModel().getSelectedItem();
            int id = 0; // Cero significa que será un INSERT.
            if (seleccionado != null) {
                id = seleccionado.getId();
            }

            // POLIMORFISMO: una variable Videojuego admite ambas clases hijas.
            Videojuego juego;
            if ("FISICO".equals(tipo.getValue())) {
                juego = new JuegoFisico(id, codigo.getText(), nombre.getText(), monto);
            } else {
                juego = new JuegoDigital(id, codigo.getText(), nombre.getText(), monto);
            }

            // El DAO decide ejecutar INSERT o UPDATE según el ID.
            dao.guardar(juego);
            // Vuelve a consultar MySQL para actualizar la lista.
            cargar();
            // Borra el formulario y su selección para el próximo registro.
            limpiar();
            // Muestra un mensaje de confirmación al usuario.
            mensaje.setText("Videojuego guardado correctamente.");

        // Mensaje específico cuando el precio no es un entero.
        } catch (NumberFormatException e) {
            mensaje.setText("El precio debe ser un número entero.");
        // Muestra errores del modelo o de la conexión MySQL.
        } catch (RuntimeException e) {
            mensaje.setText("Error: " + e.getMessage());
        }
    }

    // El botón Eliminar borra el videojuego seleccionado.
    @FXML private void eliminar() {
        // Recupera el objeto que el usuario seleccionó en la lista.
        Videojuego j = lista.getSelectionModel().getSelectedItem();
        if (j == null) {
            mensaje.setText("Selecciona un videojuego.");
            return;
        }

        try {
            // Borra el registro indicado por su ID.
            dao.eliminar(j.getId());
            // Recupera nuevamente los registros que quedan en MySQL.
            cargar();
            // Limpia los datos usados para la operación.
            limpiar();
            mensaje.setText("Videojuego eliminado.");

        // Muestra cualquier error de persistencia durante DELETE.
        } catch (RuntimeException e) {
            mensaje.setText("Error: " + e.getMessage());
        }
    }

    // Restablece los controles del formulario a su estado vacío.
    @FXML private void limpiar() {
        // Sin selección, el próximo Guardar realizará INSERT.
        lista.getSelectionModel().clearSelection();
        tipo.setValue(null);
        codigo.clear();
        nombre.clear();
        precio.clear();
    }
}
