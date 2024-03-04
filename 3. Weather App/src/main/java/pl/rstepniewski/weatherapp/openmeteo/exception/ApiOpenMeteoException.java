package pl.rstepniewski.weatherapp.openmeteo.exception;

public class ApiOpenMeteoException extends RuntimeException{

    public ApiOpenMeteoException(String message) {
        super("Api Open Meteo Exception: " + message);
    }
}
