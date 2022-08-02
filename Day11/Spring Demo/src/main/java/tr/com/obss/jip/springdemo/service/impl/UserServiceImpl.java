package tr.com.obss.jip.springdemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.springdemo.dto.BookDto;
import tr.com.obss.jip.springdemo.dto.ImageDto;
import tr.com.obss.jip.springdemo.dto.UserDto;
import tr.com.obss.jip.springdemo.mapper.UserMapper;
import tr.com.obss.jip.springdemo.mapper.UserMapperImpl;
import tr.com.obss.jip.springdemo.model.Book;
import tr.com.obss.jip.springdemo.model.Image;
import tr.com.obss.jip.springdemo.model.User;
import tr.com.obss.jip.springdemo.repository.UserRepository;
import tr.com.obss.jip.springdemo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

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

    public void createUser(UserDto userDto){
        User user = userMapper.toUser(userDto);

        for (Book book : user.getBooks()) {
            book.setUser(user);
        }

        userRepository.save(user);
    }

    public List<UserDto> findByName(String name) {
        List<User> users = userRepository.findUsersByName(name);

        if(users == null) {
            return null;
        }

        ArrayList<UserDto> userDtoList = new ArrayList<>();
        for(User user : users) {
            userDtoList.add(userMapper.toUserDto(user));
        }

        return userDtoList;
    }

    public void addBook(long id, List<BookDto> bookDtoList) {
        User user = userRepository.findUserById(id);

        if(user == null) {
            return;
        }

        for (BookDto bookDto : bookDtoList) {
            user.addBook(userMapper.toBook(bookDto));
        }

        userRepository.save(user);
    }
}
