package pl.rstepniewski.weatherapp.openmeteo.exception;

public class ApiOpenMeteoException extends RuntimeException{

    public ApiOpenMeteoException(final String message) {
        super("Api Open Meteo Exception: " + message);
    }
}
