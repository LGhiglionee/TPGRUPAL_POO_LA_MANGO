package Excepciones.Recursos;

import Excepciones.Base.BaseUnchecked;

/**
 * — Se lanza cuando ocurre un error al intentar leer o cargar el archivo de instrucciones del juego.
 *
 */
public class ArchivoInstruccionesException extends BaseUnchecked {
    /**
     * Constructor que recibe un mensaje descriptivo del error.
     *
     * Parámetro: mensaje Texto que describe la causa del problema.
     */
    public ArchivoInstruccionesException(String mensaje){
        super(mensaje);
    }
}
