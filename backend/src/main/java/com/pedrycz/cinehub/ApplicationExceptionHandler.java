package com.pedrycz.cinehub;

import com.pedrycz.cinehub.exceptions.*;
import io.minio.errors.MinioException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ReviewAlreadyExistsException.class})
    private ResponseEntity<Object> handleReviewAlreadyExistsException(ReviewAlreadyExistsException e){
        log.error(e.getMessage());
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ReviewNotOwnedException.class})
    private ResponseEntity<Object> handleReviewNotOwnedException(ReviewNotOwnedException e){
        log.error(e.getMessage());
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({PageNotFoundException.class})
    private ResponseEntity<Object> handlePageNotFoundException(PageNotFoundException e){
        log.error(e.getMessage());
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PosterNotFoundException.class})
    private ResponseEntity<Object> handlePosterNotFoundException(PosterNotFoundException e){
        log.error(e.getMessage());
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadFileException.class})
    private ResponseEntity<Object> handleBadFileException(MinioException e){
        log.error(e.getMessage());
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DocumentNotFoundException.class})
    private ResponseEntity<Object> handleDocumentNotFoundException(DocumentNotFoundException e){
        log.error(e.getMessage());
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage(), "Document doesn't exist"));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NullPointerException.class})
    private ResponseEntity<Object> handleNullPointerException(NullPointerException e){
        
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage(),"Body of request is invalid"));
        log.error(String.join(",", error.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage()));
        log.error(String.join(",", errors));
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
}
