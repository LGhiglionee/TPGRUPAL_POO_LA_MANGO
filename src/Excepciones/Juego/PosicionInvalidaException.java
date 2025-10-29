package Excepciones.Juego;

import Excepciones.Base.BaseException;

public class PosicionInvalidaException extends BaseException {
    public PosicionInvalidaException(String mensaje){
        super(mensaje);
    }
}
