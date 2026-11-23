package cl.duoc.puertogames.model;

// JuegoDigital también hereda los atributos y validaciones de Videojuego.
public class JuegoDigital extends Videojuego {

    // Usa super(...) para aprovechar el constructor de la clase padre.
    public JuegoDigital(int id, String codigo, String nombre, int precio) {
        super(id, codigo, nombre, precio);
    }

    // Identifica al juego como digital.
    @Override
    public String getTipo() { return "DIGITAL"; }

    // Regla de negocio: descuenta el 10 % del precio base.
    @Override
    public int getPrecioFinal() { return Math.round(getPrecioBase() * 0.90f); }
}
