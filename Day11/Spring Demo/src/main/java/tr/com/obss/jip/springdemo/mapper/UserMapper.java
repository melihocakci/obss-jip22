package tr.com.obss.jip.springdemo.mapper;

import org.mapstruct.Mapper;
import tr.com.obss.jip.springdemo.dto.BookDto;
import tr.com.obss.jip.springdemo.dto.ImageDto;
import tr.com.obss.jip.springdemo.dto.UserDto;
import tr.com.obss.jip.springdemo.model.Book;
import tr.com.obss.jip.springdemo.model.Image;
import tr.com.obss.jip.springdemo.model.User;

@Mapper
public interface UserMapper {
    UserDto toUserDto(User user);
    User toUser(UserDto userDto);

    ImageDto toImageDto(Image image);
    Image toImage(ImageDto imageDto);

    BookDto toBookDto(Book book);
    Book toBook(BookDto bookDto);
}
