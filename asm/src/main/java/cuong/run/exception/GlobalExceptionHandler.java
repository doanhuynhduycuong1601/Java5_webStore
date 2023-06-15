package cuong.run.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StringException.class)
    public ResponseEntity<ErrorResponse> handleException(StringException ex) {
        var errorResponse = ErrorResponse.builder()
                .uuid(UUID.randomUUID())
                .errors(Collections.singletonList(new Error(HttpStatus.BAD_REQUEST, ex.getMessage())))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}