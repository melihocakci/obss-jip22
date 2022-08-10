package tr.com.obss.jip.bookportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.bookportal.model.Book;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    @NotNull
    private long id;

    @NotNull
    private String username;

    private List<BookDto> readBooks;

    private List<BookDto> favoriteBooks;
}
