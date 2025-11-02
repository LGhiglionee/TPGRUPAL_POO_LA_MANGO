package Excepciones.Base;

/**
 * — Clase base para las excepciones no verificadas (unchecked) del proyecto.
 *
 * Se utiliza para errores de ejecución que no requieren manejo obligatorio,
 * como inconsistencias lógicas o violaciones de estado inesperadas.
 */
public class BaseUnchecked extends RuntimeException{
    /**
     * Constructor que recibe un mensaje descriptivo del error.
     *
     * Parámetro: mensaje Descripción de la causa de la excepción.
     */
    public BaseUnchecked(String mensaje){
        super(mensaje);
    }
}
