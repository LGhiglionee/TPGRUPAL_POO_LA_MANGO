package Excepciones.Juego;

import Excepciones.Base.BaseException;


/**
 * — Se lanza cuando se intenta robar una carta de un mazo vacío.
 */
public class MazoVacioException extends BaseException {
    public MazoVacioException(String mensaje) {
        super(mensaje);
    }
}
