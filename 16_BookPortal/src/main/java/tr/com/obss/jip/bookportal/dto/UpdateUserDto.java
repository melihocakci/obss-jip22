package tr.com.obss.jip.bookportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserDto {
    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 8, max = 20)
    private String password;
}
