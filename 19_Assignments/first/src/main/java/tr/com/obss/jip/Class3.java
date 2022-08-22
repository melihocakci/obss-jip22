package tr.com.obss.jip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Class3 {

    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Class3.class);

        try {
            Class2.methodTwo();
        } catch (Exception ex) {
            logger.error("An error occurred", new CustomException(ex));
        }
    }
}
