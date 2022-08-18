package tr.com.obss.jip.bookportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UpdateBookDto {
    @Size(min = 5, max = 20)
    private String name;

    @Size(min = 5, max = 20)
    private String author;
}
