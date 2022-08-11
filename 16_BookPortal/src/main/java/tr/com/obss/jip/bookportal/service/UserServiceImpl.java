package tr.com.obss.jip.bookportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.dto.CreateUserDto;
import tr.com.obss.jip.bookportal.dto.FetchRequest;
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
    public List<UserDto> getUsers(FetchRequest fetchRequest) {
        Pageable pageable;

        if(fetchRequest.getSortField() != null && fetchRequest.getSortOrder() != null) {
            Sort sort = Sort.by(fetchRequest.getSortField());

            if (fetchRequest.getSortOrder().equals("descend")) {
                sort = sort.descending();
            }

            pageable = PageRequest.of(fetchRequest.getPage(), fetchRequest.getSize(), sort);
        } else {
            pageable = PageRequest.of(fetchRequest.getPage(), fetchRequest.getSize());
        }

        Page<User> userPage = userRepository.findAll(pageable);

        List<User> users = userPage.getContent();

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
        User user = userRepository.findUserById(userId);
        user.getReadBooks().clear();
        user.getFavoriteBooks().clear();
        userRepository.save(user);

        userRepository.deleteUserById(userId);
    }

    @Override
    public void deleteUser(String username) {
        userRepository.deleteUserByUsername(username);
    }

    @Override
    public void removeFavoriteBook(String username, Long bookId) {
        User user = userRepository.findUserByUsername(username);

        List<Book> favoriteBooks = user.getFavoriteBooks();
        for (int i = 0; i < favoriteBooks.size(); i++) {
            if (favoriteBooks.get(i).getId() == bookId) {
                favoriteBooks.remove(i);
                userRepository.save(user);
                return;
            }
        }

    }

    @Override
    public void removeReadBook(String username, Long bookId) {
        User user = userRepository.findUserByUsername(username);

        List<Book> readBooks = user.getReadBooks();
        for (int i = 0; i < readBooks.size(); i++) {
            if (readBooks.get(i).getId() == bookId) {
                readBooks.remove(i);
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
    public Long getUserCount() {
        return userRepository.count();
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

        for (Book book : user.getFavoriteBooks()) {
            if (book.getId() == bookId) {
                return;
            }
        }

        Book book = bookRepository.findBookById(bookId);

        user.getFavoriteBooks().add(book);

        userRepository.save(user);
    }

    @Override
    public void addReadBook(String username, Long bookId) {
        User user = userRepository.findUserByUsername(username);

        for (Book book : user.getReadBooks()) {
            if (book.getId() == bookId) {
                return;
            }
        }

        Book book = bookRepository.findBookById(bookId);

        user.getReadBooks().add(book);

        userRepository.save(user);
    }



}
