package tr.com.obss.jip.bookportal.service.impl;

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
import tr.com.obss.jip.bookportal.dto.UserDto;
import tr.com.obss.jip.bookportal.exception.ConflictException;
import tr.com.obss.jip.bookportal.exception.NotFoundException;
import tr.com.obss.jip.bookportal.mapper.MyMapper;
import tr.com.obss.jip.bookportal.mapper.MyMapperImpl;
import tr.com.obss.jip.bookportal.model.Book;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.other.RoleType;
import tr.com.obss.jip.bookportal.repository.BookRepository;
import tr.com.obss.jip.bookportal.repository.UserRepository;
import tr.com.obss.jip.bookportal.service.RoleService;
import tr.com.obss.jip.bookportal.service.UserService;

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
    public List<UserDto> getUserDtos(FetchRequest fetchRequest) {
        Pageable pageable;
        String sortField = fetchRequest.getSortField();
        String sortOrder = fetchRequest.getSortOrder();

        if (!sortField.isEmpty() && !sortOrder.isEmpty()) {
            Sort sort = Sort.by(sortField);

            if (sortOrder.equals("descend")) {
                sort = sort.descending();
            }

            pageable = PageRequest.of(fetchRequest.getPage(), fetchRequest.getSize(), sort);
        } else {
            pageable = PageRequest.of(fetchRequest.getPage(), fetchRequest.getSize());
        }

        Page<User> userPage;
        String username = fetchRequest.getSearch();

        if (!username.isEmpty()) {
            userPage = userRepository.findAllByUsernameContaining(pageable, username);
        } else {
            userPage = userRepository.findAll(pageable);
        }

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
    public Long getUserCount() {
        return userRepository.count();
    }

    @Override
    public UserDto getUserDto(Long userId) {
        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new NotFoundException("User does not exist");
        }

        return mapper.toUserDto(user);
    }

    @Override
    public UserDto getUserDto(String username) {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new NotFoundException("User does not exist");
        }

        return mapper.toUserDto(user);
    }

    @Override
    public void updateUser(String username, UpdateUserDto updateUserDto) {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new NotFoundException("User does not exist");
        }

        String newUsername = updateUserDto.getUsername();
        String newPassword = updateUserDto.getPassword();

        if (newUsername != null && !newUsername.isEmpty()) {
            user.setUsername(newUsername);
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userRepository.save(user);
    }

    @Override
    public void updateUser(Long userId, UpdateUserDto updateUserDto) {
        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new NotFoundException("User does not exist");
        }

        String newUsername = updateUserDto.getUsername();
        String newPassword = updateUserDto.getPassword();

        if (newUsername != null && !newUsername.isEmpty()) {
            user.setUsername(newUsername);
        }

        if (newPassword != null && !newPassword.isEmpty()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findUserById(userId);

        if (user == null) {
            throw new NotFoundException("User does not exist");
        }

        userRepository.deleteUserById(userId);
    }

    @Override
    public void deleteUser(String username) {
        User user = userRepository.findUserByUsername(username);

        if (user == null) {
            throw new NotFoundException("User does not exist");
        }

        user.getReadBooks().clear();
        user.getFavoriteBooks().clear();
        userRepository.save(user);

        userRepository.deleteUserById(user.getId());
    }

    @Override
    public void removeFavoriteBook(String username, Long bookId) {
        User user = userRepository.findUserByUsername(username);

        List<Book> favoriteBooks = user.getFavoriteBooks();
        for (int i = 0; i < favoriteBooks.size(); i++) {
            if (favoriteBooks.get(i).getId().equals(bookId)) {
                favoriteBooks.remove(i);
                userRepository.save(user);
                return;
            }
        }

        throw new NotFoundException("Book not in favorite list");
    }

    @Override
    public void removeReadBook(String username, Long bookId) {
        User user = userRepository.findUserByUsername(username);

        List<Book> readBooks = user.getReadBooks();
        for (int i = 0; i < readBooks.size(); i++) {
            if (readBooks.get(i).getId().equals(bookId)) {
                readBooks.remove(i);
                userRepository.save(user);
                return;
            }
        }

        throw new NotFoundException("Book not in read list");
    }

    @Override
    public void createUser(CreateUserDto createUserDto, RoleType roleType) {
        if (userRepository.findUserByUsername(createUserDto.getUsername()) != null) {
            throw new ConflictException("Username is already in use");
        }

        User user = mapper.toUser(createUserDto);
        user.setRole(roleService.findByName(roleType));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public void addFavoriteBook(String username, Long bookId) {
        User user = userRepository.findUserByUsername(username);

        for (Book book : user.getFavoriteBooks()) {
            if (book.getId().equals(bookId)) {
                throw new ConflictException("Book already in favorite list");
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
            if (book.getId().equals(bookId)) {
                throw new ConflictException("Book already in read list");
            }
        }

        Book book = bookRepository.findBookById(bookId);

        user.getReadBooks().add(book);

        userRepository.save(user);
    }
}
