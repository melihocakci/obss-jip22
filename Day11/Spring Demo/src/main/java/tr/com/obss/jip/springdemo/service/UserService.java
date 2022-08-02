package tr.com.obss.jip.springdemo.service;

import tr.com.obss.jip.springdemo.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    void createUser(UserDto userDto);
}
