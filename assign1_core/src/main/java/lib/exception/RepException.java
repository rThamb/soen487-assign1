package lib.exception;


import lib.web.JSONifiable;

public class RepException extends Exception {

    public RepException(){
        super();
    }
    public RepException(String message){
        super(message);
    }
}
