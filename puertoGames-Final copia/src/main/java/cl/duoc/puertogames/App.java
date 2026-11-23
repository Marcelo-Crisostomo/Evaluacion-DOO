package cl.duoc.puertogames;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Clase principal: inicia JavaFX y carga la vista.
public class App extends Application {

    // JavaFX llama este método para crear y mostrar la ventana.
    @Override
    public void start(Stage stage) throws Exception {
        // Busca el archivo FXML en el paquete de recursos de App.
        Parent vista = FXMLLoader.load(App.class.getResource("main-view.fxml"));
        // Crea una escena de 900 x 450 píxeles con la vista cargada.
        stage.setScene(new Scene(vista, 900, 450));
        // Coloca el título en la barra superior de la ventana.
        stage.setTitle("PuertoGames - Catálogo de videojuegos");
        // Hace visible la ventana para el usuario.
        stage.show();
    }

    // Punto de entrada: inicia el ciclo de vida de JavaFX.
    public static void main(String[] args) {
        launch(args);
    }
}
