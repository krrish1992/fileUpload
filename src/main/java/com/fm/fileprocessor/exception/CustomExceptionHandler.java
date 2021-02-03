package com.fm.fileprocessor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class CustomExceptionHandler {
    public ResponseEntity<String> handleFileNotFound(CustomFileNotFoundException ex){
        return  new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> handleInvalidFile(InvalidFileException ex){
        return  new ResponseEntity<String>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
