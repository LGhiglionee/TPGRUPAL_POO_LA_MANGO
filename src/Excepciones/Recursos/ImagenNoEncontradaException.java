package Excepciones.Recursos;

import Excepciones.Base.BaseUnchecked;

public class ImagenNoEncontradaException extends BaseUnchecked {
    public ImagenNoEncontradaException(String mensaje){
        super(mensaje);
    }
}
