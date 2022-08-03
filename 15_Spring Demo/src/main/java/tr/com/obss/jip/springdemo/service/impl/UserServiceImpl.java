package tr.com.obss.jip.springdemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.springdemo.dto.BookDto;
import tr.com.obss.jip.springdemo.dto.CreateUserBody;
import tr.com.obss.jip.springdemo.dto.UserDto;
import tr.com.obss.jip.springdemo.mapper.UserMapper;
import tr.com.obss.jip.springdemo.mapper.UserMapperImpl;
import tr.com.obss.jip.springdemo.model.*;
import tr.com.obss.jip.springdemo.repository.UserRepository;
import tr.com.obss.jip.springdemo.service.RoleService;
import tr.com.obss.jip.springdemo.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper = new UserMapperImpl();

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();

        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(userMapper.toUserDto(user));
        }

        return userDtoList;
    }

    public void createUser(CreateUserBody createUserBody) {
        User user = userMapper.toUser(createUserBody);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        for (Book book : user.getBooks()) {
            book.setUser(user);
        }

        user.getRoles().add(roleService.findByName(RoleType.ROLE_USER));

        userRepository.save(user);
    }

    public List<UserDto> findByName(String name) {
        List<User> users = userRepository.findUsersByName(name);

        if (users == null) {
            return null;
        }

        ArrayList<UserDto> userDtoList = new ArrayList<>();
        for (User user : users) {
            userDtoList.add(userMapper.toUserDto(user));
        }

        return userDtoList;
    }

    public void addBook(long id, List<BookDto> bookDtoList) {
        User user = userRepository.findUserById(id);

        if (user == null) {
            return;
        }

        for (BookDto bookDto : bookDtoList) {
            user.addBook(userMapper.toBook(bookDto));
        }

        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Boolean assignRoleToUser(String username, RoleType roleType) {
        final User user = userRepository.findUserByUsername(username);
        final Role role = roleService.findByName(roleType);

        final List<Role> roles = user.getRoles();
        if(Objects.isNull(roles)){
            user.setRoles(new ArrayList<>());
        }

        user.getRoles().add(role);
        userRepository.save(user);

        return true;
    }

    @Override
    public void createNewUser(User adminUser) {
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        userRepository.save(adminUser);
    }

}
