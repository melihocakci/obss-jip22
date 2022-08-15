package tr.com.obss.jip.bookportal.service;

import tr.com.obss.jip.bookportal.dto.CreateUserDto;
import tr.com.obss.jip.bookportal.dto.FetchRequest;
import tr.com.obss.jip.bookportal.dto.UpdateUserDto;
import tr.com.obss.jip.bookportal.dto.UserDto;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.other.RoleType;

import java.util.List;

public interface UserService {

  List<UserDto> getUserDtos(FetchRequest fetchRequest);

  User getUser(String username);

  UserDto getUserDto(String username);

  UserDto getUserDto(Long userId);

  Long getUserCount();

  void createUser(CreateUserDto createUserDto, RoleType roleType);

  void addFavoriteBook(String username, Long bookId);

  void addReadBook(String username, Long bookId);

  void updateUser(String username, UpdateUserDto updateUserDto);

  void updateUser(Long id, UpdateUserDto updateUserDto);

  void deleteUser(Long userId);

  void deleteUser(String username);

  void removeFavoriteBook(String username, Long bookId);

  void removeReadBook(String username, Long bookId);
}
