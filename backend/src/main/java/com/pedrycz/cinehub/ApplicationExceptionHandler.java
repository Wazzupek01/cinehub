package com.pedrycz.cinehub;

import com.pedrycz.cinehub.exceptions.*;
import io.minio.errors.MinioException;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PageNotFoundException.class})
    public ResponseEntity<Object> handlePageNotFoundException(PageNotFoundException e){
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PosterNotFoundException.class})
    public ResponseEntity<Object> handlePosterNotFoundException(PosterNotFoundException e){
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({BadFileException.class})
    public ResponseEntity<Object> handleBadFileException(MinioException e){
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage()));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({DocumentNotFoundException.class})
    public ResponseEntity<Object> handleDocumentNotFoundException(DocumentNotFoundException e){
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage(), "Document doesn't exist"));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<Object> handleNullPointerException(NullPointerException e){
        ErrorResponse error = new ErrorResponse(List.of(e.getMessage(),"Body of request is invalid"));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> errors.add(error.getDefaultMessage()));
        return new ResponseEntity<>(new ErrorResponse(errors), HttpStatus.BAD_REQUEST);
    }
}
