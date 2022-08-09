package tr.com.obss.jip.bookportal.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class AuthRequest implements Serializable {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
