package Excepciones.Base;

/**
 * — Clase base para las excepciones verificadas (checked) del proyecto.
 *
 * Sirve como clase padre para todas las excepciones que deben
 * manejarse explícitamente mediante bloques try/catch.
 */
public class BaseException extends Exception{
    /**
     * Constructor que recibe un mensaje descriptivo del error.
     *
     * Parámetro: mensaje Texto que describe la causa de la excepción.
     */
    public BaseException(String mensaje){
        super(mensaje);
    }
}
