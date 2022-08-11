package tr.com.obss.jip.bookportal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import tr.com.obss.jip.bookportal.dto.ResponseDto;
import tr.com.obss.jip.bookportal.exception.BadRequestException;
import tr.com.obss.jip.bookportal.exception.ConflictException;
import tr.com.obss.jip.bookportal.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

@ResponseBody
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto ExceptionHandler(RuntimeException ex, HttpServletRequest request) {
        return new ResponseDto(false, ex.getClass().getSimpleName() + ": " + ex.getMessage(), null);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto BadRequestExceptionHandler(BadRequestException ex, HttpServletRequest request) {
        return new ResponseDto(false, ex.getClass().getSimpleName() + ": " + ex.getMessage(), null);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseDto ConflictExceptionHandler(ConflictException ex, HttpServletRequest request) {
        return new ResponseDto(false, ex.getClass().getSimpleName() + ": " + ex.getMessage(), null);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto NotFoundExceptionHandler(NotFoundException ex, HttpServletRequest request) {
        return new ResponseDto(false, ex.getClass().getSimpleName() + ": " + ex.getMessage(), null);
    }
}
