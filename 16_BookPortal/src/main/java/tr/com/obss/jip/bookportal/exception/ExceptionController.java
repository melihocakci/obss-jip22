package tr.com.obss.jip.bookportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionController {

    private Map<String, String> responseBody(Exception ex, HttpServletRequest request) {
        Map<String, String> responseBody = new LinkedHashMap<>();

        responseBody.put(
                "timestamp", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
        responseBody.put("error", ex.getClass().getSimpleName());
        responseBody.put("message", ex.getMessage());
        responseBody.put("path", request.getRequestURI());

        return responseBody;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> ExceptionHandler(RuntimeException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseBody(ex, request));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> AuthenticationExceptionHandler(
            RuntimeException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody(ex, request));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequestExceptionHandler(
            BadRequestException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody(ex, request));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> ConflictExceptionHandler(
            ConflictException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody(ex, request));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> NotFoundExceptionHandler(
            NotFoundException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody(ex, request));
    }
}
