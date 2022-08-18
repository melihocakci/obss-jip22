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
import java.util.Optional;

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
        String username = fetchRequest.getSearchParam();

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
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Long getUserCount() {
        return userRepository.count();
    }

    @Override
    public UserDto getUserDto(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        return mapper.toUserDto(optionalUser.get());
    }

    @Override
    public UserDto getUserDto(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        return mapper.toUserDto(optionalUser.get());
    }

    @Override
    public void updateUser(String username, UpdateUserDto updateUserDto) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        User user = optionalUser.get();

        String newUsername = updateUserDto.getUsername();
        String newPassword = updateUserDto.getPassword();

        if (newUsername != null) {
            if (userRepository.findAnyByUsername(newUsername) != null) {
                throw new ConflictException("Username already in use");
            }

            user.setUsername(newUsername);
        }

        if (newPassword != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userRepository.save(user);
    }

    @Override
    public void updateUser(Long userId, UpdateUserDto updateUserDto) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        User user = optionalUser.get();

        String newUsername = updateUserDto.getUsername();
        String newPassword = updateUserDto.getPassword();

        if (newUsername != null) {
            if (userRepository.findAnyByUsername(newUsername) != null) {
                throw new ConflictException("Username already in use");
            }

            user.setUsername(newUsername);
        }

        if (newPassword != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        userRepository.deleteById(userId);
    }

    @Override
    public void deleteUser(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        userRepository.deleteById(optionalUser.get().getId());
    }

    @Override
    public void removeFavoriteBook(String username, Long bookId) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        User user = optionalUser.get();

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
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        User user = optionalUser.get();

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
        if (userRepository.findByUsername(createUserDto.getUsername()).isPresent()) {
            throw new ConflictException("Username is already in use");
        }

        User user = mapper.toUser(createUserDto);
        user.setRole(roleService.getRole(roleType));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
    }

    @Override
    public void addFavoriteBook(String username, Long bookId) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        User user = optionalUser.get();

        for (Book book : user.getFavoriteBooks()) {
            if (book.getId().equals(bookId)) {
                throw new ConflictException("Book already in favorite list");
            }
        }

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            throw new NotFoundException("Book does not exist");
        }

        user.getFavoriteBooks().add(optionalBook.get());

        userRepository.save(user);
    }

    @Override
    public void addReadBook(String username, Long bookId) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        User user = optionalUser.get();

        for (Book book : user.getReadBooks()) {
            if (book.getId().equals(bookId)) {
                throw new ConflictException("Book already in read list");
            }
        }

        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            throw new NotFoundException("Book does not exist");
        }

        user.getReadBooks().add(optionalBook.get());

        userRepository.save(user);
    }
}
