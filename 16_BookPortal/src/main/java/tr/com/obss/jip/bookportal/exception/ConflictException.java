package tr.com.obss.jip.bookportal.exception;

public class ConflictException extends RuntimeException {
  public ConflictException() {}

  public ConflictException(String message) {
    super(message);
  }
}
