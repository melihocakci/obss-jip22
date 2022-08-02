package tr.com.obss.jip.springdemo.service;

import tr.com.obss.jip.springdemo.dto.BookDto;
import tr.com.obss.jip.springdemo.dto.UserDto;
import tr.com.obss.jip.springdemo.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    void createUser(UserDto userDto);

    List<UserDto> findByName(String name);

    void addBook(long id, List<BookDto> bookDtoList);
}
