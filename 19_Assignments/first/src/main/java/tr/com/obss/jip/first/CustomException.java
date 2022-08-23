package tr.com.obss.jip.first;

public class CustomException extends Exception {
    public CustomException(Exception e) {
        super(e);
    }
}
