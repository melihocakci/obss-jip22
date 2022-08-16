package tr.com.obss.jip.bookportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class CreateUserDto {
    @Size(min = 3, max = 20)
    @NotNull
    private String username;

    @Size(min = 8, max = 20)
    @NotNull
    private String password;
}
