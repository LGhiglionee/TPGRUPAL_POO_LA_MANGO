package Excepciones.Recursos;

import Excepciones.Base.BaseUnchecked;

public class FuenteNoEncontradaException extends BaseUnchecked {
    public FuenteNoEncontradaException(String mensaje){
        super(mensaje);
    }
}
