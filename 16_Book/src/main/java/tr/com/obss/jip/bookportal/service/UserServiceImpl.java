package tr.com.obss.jip.bookportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.mapper.RoleType;
import tr.com.obss.jip.bookportal.dto.CreateUserDto;
import tr.com.obss.jip.bookportal.dto.UserDto;
import tr.com.obss.jip.bookportal.mapper.MyMapper;
import tr.com.obss.jip.bookportal.mapper.MyMapperImpl;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final MyMapper mapper = new MyMapperImpl();


    @Override
    public List<UserDto> getAll() {
        List<User> users = (List<User>) userRepository.findAll();

        List<UserDto> userDtos = new ArrayList<>();

        for(User user : users) {
            userDtos.add(mapper.toUserDto(user));
        }

        return userDtos;
    }

    @Override
    public void saveUser(User adminUser) {
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        userRepository.save(adminUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Boolean createUser(CreateUserDto createUserDto) {
        User user = mapper.toUser(createUserDto);
        user.setRole(roleService.findByName(RoleType.ROLE_USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return Boolean.TRUE;
    }
}
