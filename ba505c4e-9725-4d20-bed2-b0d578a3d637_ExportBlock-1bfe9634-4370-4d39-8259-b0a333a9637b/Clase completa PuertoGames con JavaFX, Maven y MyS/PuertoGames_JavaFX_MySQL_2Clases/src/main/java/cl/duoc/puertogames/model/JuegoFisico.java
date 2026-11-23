package cl.duoc.puertogames.model;

// Una clase hija es una especialización de Videojuego.
public class JuegoFisico extends Videojuego {

    // Envía los datos al constructor de la clase padre.
    public JuegoFisico(int id, String codigo, String nombre, int precio) {
        super(id, codigo, nombre, precio);
    }

    // Identifica a este objeto como un videojuego físico.
    @Override
    public String getTipo() { return "FISICO"; }

    // Regla de negocio: suma $5.000 por distribución.
    @Override
    public int getPrecioFinal() { return getPrecioBase() + 5000; }
}
