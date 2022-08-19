package tr.com.obss.jip;

public class CustomException extends Exception {
    public CustomException(Exception e) {
        super(e);
    }
}
