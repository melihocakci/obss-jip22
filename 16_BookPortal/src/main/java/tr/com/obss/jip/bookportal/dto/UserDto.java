package tr.com.obss.jip.bookportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tr.com.obss.jip.bookportal.other.Gender;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserDto {
    private long id;

    private String username;

    private String email;

    private Gender gender;

    private List<BookDto> readBooks;

    private List<BookDto> favoriteBooks;
}
