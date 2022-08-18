package tr.com.obss.jip.bookportal.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorDto {

    private Boolean success;

    private Integer status;

    private String error;

    private String message;

    private String path;

    private String method;

    private String timestamp;

}
