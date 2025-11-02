package Excepciones.Juego;

/**
 * — Se lanza cuando un jugador realiza una acción no permitida en el contexto actual del juego.
 *
 */
public class OperacionNOPermitidaException extends Exception {
    public OperacionNOPermitidaException(String mensaje) {
        super(mensaje);
    }
}
