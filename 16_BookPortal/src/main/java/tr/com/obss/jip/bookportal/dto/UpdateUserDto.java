package tr.com.obss.jip.bookportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.bookportal.other.Gender;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserDto {
    @Size(min = 3, max = 20)
    private String username;

    @Size(min = 8, max = 20)
    private String password;

    @Size(min = 5, max = 30)
    private String email;

    private Gender gender;
}
