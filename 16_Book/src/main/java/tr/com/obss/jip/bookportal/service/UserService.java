package tr.com.obss.jip.bookportal.service;

import tr.com.obss.jip.bookportal.dto.CreateUserDto;
import tr.com.obss.jip.bookportal.dto.UserDto;
import tr.com.obss.jip.bookportal.model.User;

import java.util.List;

public interface UserService {
    void createUser(CreateUserDto createUserDto);

    List<UserDto> getAll();

    void saveUser(User adminUser);

    User findByUsername(String username);

    void updateUser(Long id, CreateUserDto newUser);

    void deleteUser(Long id);

    User getUser(Long id);
}
