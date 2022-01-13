package fr.eql.matchingEngine.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import fr.eql.matchingEngine.dto.response.Response;

/**
 * Created by BEN MALEK S. on 25/06/2020.
 *
 */

@ControllerAdvice

public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ResponseStatus(value= HttpStatus.NOT_FOUND)
    @ExceptionHandler({HttpClientErrorException.NotFound.class})
    public ResponseEntity handleNotFoundExceptions(HttpClientErrorException.NotFound ex) {
        ex.printStackTrace();
        LOGGER.error("NOT FOUND : ", ex);
        Response response = Response.notFound();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        MDC.clear();
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpClientErrorException.BadRequest.class})
    public ResponseEntity handleBadRequestExceptions(HttpClientErrorException.BadRequest ex) {
        ex.printStackTrace();
        LOGGER.error("BAD REQUEST : ", ex);
        Response response = Response.badRequest();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        MDC.clear();
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity handleInternBadRequestExceptions(BadRequestException ex) {
        ex.printStackTrace();
        LOGGER.error("BAD REQUEST : ", ex);
        Response response = Response.badRequest();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        MDC.clear();
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(value= HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({HttpClientErrorException.Unauthorized.class})
    public ResponseEntity handleUnauthorizedExceptions(HttpClientErrorException.Unauthorized ex) {
        ex.printStackTrace();
        LOGGER.error("UNAUTHORIZED : ", ex);
        Response response = Response.unauthorized();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        MDC.clear();
        return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(value= HttpStatus.FORBIDDEN)
    @ExceptionHandler({HttpClientErrorException.Forbidden.class})
    public ResponseEntity handleForbiddenExceptions(HttpClientErrorException.Forbidden ex) {
        ex.printStackTrace();
        LOGGER.error("FORBIDDEN : ", ex);
        Response response = Response.forbidden();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        MDC.clear();
        return new ResponseEntity(response, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ResponseEntity handleExceptions(Exception ex) {
        ex.printStackTrace();
        LOGGER.error("Exception : ", ex);
        Response response = Response.exception();
        response.addErrorMsgToResponse(ex.getMessage(), ex);
        MDC.clear();
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
