package tr.com.obss.jip.bookportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import tr.com.obss.jip.bookportal.dto.ErrorDto;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class ExceptionController {

    private ErrorDto responseBody(Exception ex, HttpServletRequest request, Integer status) {
        ErrorDto errorDto = new ErrorDto();

        errorDto.setSuccess(Boolean.FALSE);
        errorDto.setStatus(status);
        errorDto.setError(ex.getClass().getSimpleName());
        errorDto.setMessage(ex.getMessage());
        errorDto.setPath(request.getRequestURI());
        errorDto.setMethod(request.getMethod());
        errorDto.setTimestamp(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));

        return errorDto;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> ExceptionHandler(RuntimeException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(responseBody(ex, request, 500));
    }

    @ExceptionHandler({BadRequestException.class, AuthenticationException.class})
    public ResponseEntity<?> BadRequestHandler(
            BadRequestException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(responseBody(ex, request, 400));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> ConflictHandler(
            ConflictException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(responseBody(ex, request, 409));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> NotFoundHandler(
            NotFoundException ex, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(responseBody(ex, request, 404));
    }
}
