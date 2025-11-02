package Excepciones.Juego;

import Excepciones.Base.BaseException;

/**
 * — Se lanza cuando se intenta acceder a una posición inválida dentro de una lista o mano.
 */
public class PosicionInvalidaException extends BaseException {
    public PosicionInvalidaException(String mensaje){
        super(mensaje);
    }
}
