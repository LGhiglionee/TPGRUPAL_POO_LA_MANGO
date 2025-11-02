package Excepciones.Recursos;

import Excepciones.Base.BaseUnchecked;

/**
 * — Se lanza cuando no se encuentra o no puede cargarse una fuente tipográfica.
 */
public class FuenteNoEncontradaException extends BaseUnchecked {
    /**
     * Constructor que recibe un mensaje descriptivo del error.
     *
     * Parámetro: mensaje Texto que describe la causa del problema.
     */
    public FuenteNoEncontradaException(String mensaje){
        super(mensaje);
    }
}
