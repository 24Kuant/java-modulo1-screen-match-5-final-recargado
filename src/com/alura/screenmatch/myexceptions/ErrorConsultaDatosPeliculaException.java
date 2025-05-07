package com.alura.screenmatch.myexceptions;

public class ErrorConsultaDatosPeliculaException extends RuntimeException {  //originalmente extiende de: Throwable, lo que ace que la clase titulo extienda de throws, otra forma de expandir el mensaje hacia las clases padre.
    private String mensaje;

    public ErrorConsultaDatosPeliculaException(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String getMessage() {
        return this.mensaje;
    }
}
