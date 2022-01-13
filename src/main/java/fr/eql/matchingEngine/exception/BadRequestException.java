package fr.eql.matchingEngine.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by BEN MALEK S. on 06/07/2020.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {
    public BadRequestException() {
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }
    
    public BadRequestException(String msg){
    	super(msg);
    }
}
