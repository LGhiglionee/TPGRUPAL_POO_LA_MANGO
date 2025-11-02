package Excepciones.Juego;

import Excepciones.Base.BaseException;

/**
 * — Se lanza cuando un jugador intenta jugar una carta pero no tiene ninguna disponible.
 */
public class JugadorSinCartasException extends BaseException {
    public JugadorSinCartasException(String mensaje){
        super(mensaje);
    }
}
