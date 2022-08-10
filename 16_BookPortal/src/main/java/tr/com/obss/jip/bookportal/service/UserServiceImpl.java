package tr.com.obss.jip.bookportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.dto.CreateUserDto;
import tr.com.obss.jip.bookportal.dto.UpdateUserDto;
import tr.com.obss.jip.bookportal.model.Book;
import tr.com.obss.jip.bookportal.other.RoleType;
import tr.com.obss.jip.bookportal.dto.UserDto;
import tr.com.obss.jip.bookportal.mapper.MyMapper;
import tr.com.obss.jip.bookportal.mapper.MyMapperImpl;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.repository.BookRepository;
import tr.com.obss.jip.bookportal.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final MyMapper mapper = new MyMapperImpl();

    @Override
    public List<UserDto> getUsers() {
        List<User> users = (List<User>) userRepository.findAll();

        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : users) {
            userDtoList.add(mapper.toUserDto(user));
        }

        return userDtoList;
    }

    @Override
    public User getUser(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public UserDto getUserDto(String username) {
        User user = userRepository.findUserByUsername(username);
        return mapper.toUserDto(user);

    }

    @Override
    public void updateUser(String username, UpdateUserDto updateUserDto) {
        User user = userRepository.findUserByUsername(username);

        if (updateUserDto.getUsername() != null) {
            user.setUsername(updateUserDto.getUsername());
        }

        if (updateUserDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }

        userRepository.save(user);
    }

    @Override
    public void updateUser(Long userId, UpdateUserDto updateUserDto) {
        User user = userRepository.findUserById(userId);

        if (updateUserDto.getUsername() != null) {
            user.setUsername(updateUserDto.getUsername());
        }

        if (updateUserDto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteUserById(userId);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }

    @Override
    public void removeFavoriteBook(String username, Long bookId) {
        User user = userRepository.findUserByUsername(username);

        List<Book> readList = user.getFavorite_list();
        for (int i = 0; i < readList.size(); i++) {
            if (readList.get(i).getId() == bookId) {
                readList.remove(i);
                userRepository.save(user);
                return;
            }
        }

    }

    @Override
    public void removeReadBook(String username, Long bookId) {
        User user = userRepository.findUserByUsername(username);

        List<Book> readList = user.getRead_list();
        for (int i = 0; i < readList.size(); i++) {
            if (readList.get(i).getId() == bookId) {
                readList.remove(i);
                userRepository.save(user);
                return;
            }
        }
    }

    @Override
    public UserDto getUserDto(Long userId) {
        User user = userRepository.findUserById(userId);
        return mapper.toUserDto(user);
    }

    @Override
    public void createUser(CreateUserDto createUserDto, RoleType roleType) {
        User user = mapper.toUser(createUserDto);
        user.setRole(roleService.findByName(roleType));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void addFavoriteBook(String username, Long bookId) {
        User user = userRepository.findUserByUsername(username);
        Book book = bookRepository.findBookById(bookId);

        user.getFavorite_list().add(book);

        userRepository.save(user);
    }

    @Override
    public void addReadBook(String username, Long bookId) {
        User user = userRepository.findUserByUsername(username);
        Book book = bookRepository.findBookById(bookId);

        user.getRead_list().add(book);

        userRepository.save(user);
    }

}
