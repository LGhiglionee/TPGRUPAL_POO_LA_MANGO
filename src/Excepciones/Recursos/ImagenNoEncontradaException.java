package Excepciones.Recursos;

import Excepciones.Base.BaseUnchecked;

/**
 * — Se lanza cuando una imagen no puede encontrarse o cargarse desde los recursos del juego.
 */
public class ImagenNoEncontradaException extends BaseUnchecked {
    /**
     * Constructor que recibe un mensaje descriptivo del error.
     *
     * Parámetro: mensaje Texto que describe la causa del problema.
     */
    public ImagenNoEncontradaException(String mensaje){
        super(mensaje);
    }
}
