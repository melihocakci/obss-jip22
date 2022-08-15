package tr.com.obss.jip.bookportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class BookDto {
    @NotNull private Long id;

    @NotNull private String name;

    @NotNull private String author;
}
