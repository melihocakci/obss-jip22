package tr.com.obss.jip.bookportal.service;

import org.springframework.web.multipart.MultipartFile;
import tr.com.obss.jip.bookportal.dto.*;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.other.RoleType;

import java.util.Optional;

public interface UserService {

    PaginationResponse getPage(PaginationRequest paginationRequest);

    Optional<User> getUser(String username);

    UserDto getUserDto(Long userId);

    void createUser(CreateUserDto createUserDto, RoleType roleType);

    void addFavoriteBook(Long id, Long bookId);

    void addReadBook(Long id, Long bookId);

    void updateUser(Long id, UpdateUserDto updateUserDto);

    void deleteUser(Long userId);

    void removeFavoriteBook(Long id, Long bookId);

    void removeReadBook(Long id, Long bookId);

    void addImage(Long userId, MultipartFile multipartImage);

    byte[] getImage(Long userId);
}
