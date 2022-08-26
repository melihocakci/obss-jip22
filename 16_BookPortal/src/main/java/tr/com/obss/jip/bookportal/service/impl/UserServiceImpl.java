package tr.com.obss.jip.bookportal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tr.com.obss.jip.bookportal.dto.*;
import tr.com.obss.jip.bookportal.exception.ConflictException;
import tr.com.obss.jip.bookportal.exception.NotFoundException;
import tr.com.obss.jip.bookportal.mapper.MyMapper;
import tr.com.obss.jip.bookportal.mapper.MyMapperImpl;
import tr.com.obss.jip.bookportal.model.Book;
import tr.com.obss.jip.bookportal.model.Image;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.other.Gender;
import tr.com.obss.jip.bookportal.other.RoleType;
import tr.com.obss.jip.bookportal.repository.BookRepository;
import tr.com.obss.jip.bookportal.repository.UserRepository;
import tr.com.obss.jip.bookportal.service.ImageService;
import tr.com.obss.jip.bookportal.service.RoleService;
import tr.com.obss.jip.bookportal.service.UserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ImageService imageService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final MyMapper mapper = new MyMapperImpl();

    @Override
    public PaginationResponse getPage(PaginationRequest paginationRequest) {
        Pageable pageable;
        String sortField = paginationRequest.getSortField();
        String sortOrder = paginationRequest.getSortOrder();

        if (!sortField.isEmpty() && !sortOrder.isEmpty()) {
            Sort sort = Sort.by(sortField);

            if (sortOrder.equals("descend")) {
                sort = sort.descending();
            }

            pageable =
                    PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), sort);
        } else {
            pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        }

        Page<User> userPage;
        String searchParam = paginationRequest.getSearchParam();

        if (!searchParam.isEmpty()) {
            userPage = userRepository.search(pageable, searchParam);
        } else {
            userPage = userRepository.findAll(pageable);
        }

        List<User> users = userPage.getContent();

        List<UserDto> userDtoList = new ArrayList<>();

        for (User user : users) {
            userDtoList.add(mapper.toUserDto(user));
        }

        return new PaginationResponse(userDtoList, userPage.getTotalElements());
    }

    @Override
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
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
    public void createUser(CreateUserDto createUserDto, RoleType roleType) {
        if (userRepository.findByUsername(createUserDto.getUsername()).isPresent()) {
            throw new ConflictException("Username is already in use");
        }

        if (userRepository.findByEmail(createUserDto.getEmail()).isPresent()) {
            throw new ConflictException("Email is already in use");
        }

        User user = mapper.toUser(createUserDto);
        user.setRole(roleService.getRole(roleType));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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
        String newEmail = updateUserDto.getEmail();
        Gender newGender = updateUserDto.getGender();

        if (newUsername != null) {
            if (userRepository.findByUsername(newUsername).isPresent()) {
                throw new ConflictException("Username already in use");
            }

            user.setUsername(newUsername);
        }

        if (newPassword != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        if (newEmail != null) {
            if (userRepository.findByEmail(newEmail).isPresent()) {
                throw new ConflictException("Email already in use");
            }

            user.setEmail(newEmail);
        }

        if (newGender != null) {
            user.setGender(newGender);
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
    public void removeFavoriteBook(Long id, Long bookId) {
        Optional<User> optionalUser = userRepository.findById(id);

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
    public void removeReadBook(Long id, Long bookId) {
        Optional<User> optionalUser = userRepository.findById(id);

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
    public void addImage(Long userId, MultipartFile multipartImage) {
        Image image = new Image();
        image.setName(multipartImage.getName());

        try {
            image.setContent(multipartImage.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        imageService.save(image);

        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new NotFoundException("User does not exist"));

        user.setImage(image);

        userRepository.save(user);
    }

    @Transactional
    @Override
    public byte[] getImage(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()) {
            throw new NotFoundException("User does not exist");
        }

        User user = optionalUser.get();

        return user.getImage().getContent();
    }

    @Override
    public void addFavoriteBook(Long id, Long bookId) {
        Optional<User> optionalUser = userRepository.findById(id);

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
    public void addReadBook(Long id, Long bookId) {
        Optional<User> optionalUser = userRepository.findById(id);

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
