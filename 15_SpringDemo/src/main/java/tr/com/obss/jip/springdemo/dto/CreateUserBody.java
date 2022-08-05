package tr.com.obss.jip.springdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserBody {
    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @NotNull
    @Min(8)
    @Max(150)
    private int age;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String location;

}
