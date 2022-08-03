package tr.com.obss.jip.springdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
public class ErrorDto {
    @NotNull
    private String message;

    @NotNull
    private long code;

    @NotNull
    private String requestUri;

}
