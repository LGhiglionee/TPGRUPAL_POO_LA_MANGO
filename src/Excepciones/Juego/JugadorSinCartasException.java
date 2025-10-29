package Excepciones.Juego;

import Excepciones.Base.BaseException;

public class JugadorSinCartasException extends BaseException {
    public JugadorSinCartasException(String mensaje){
        super(mensaje);
    }
}
