package Excepciones;

public class OperacionNOPermitidaException extends Exception {
    public OperacionNOPermitidaException(String mensaje) {
        super(mensaje);
    }
}
