package tr.com.obss.jip.bookportal.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.bookportal.dto.BookDto;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;
import tr.com.obss.jip.bookportal.dto.CreateUserDto;
import tr.com.obss.jip.bookportal.dto.UserDto;
import tr.com.obss.jip.bookportal.model.Book;
import tr.com.obss.jip.bookportal.model.User;

@Mapper
public interface MyMapper {
    Book toBook(CreateBookDto createBookDto);

    BookDto toBookDto(Book book);

    UserDto toUserDto(User user);

    User toUser(CreateUserDto createUserDto);
}