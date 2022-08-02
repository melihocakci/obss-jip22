package tr.com.obss.jip.springdemo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.springdemo.dto.UserDto;
import tr.com.obss.jip.springdemo.model.User;
import tr.com.obss.jip.springdemo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final CrudRepository<User, Long> crudRepository;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = (List<User>) crudRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<UserDto>();

        for (User user : users) {
            userDtoList.add(new UserDto(user.getName(), user.getAge(), user.getLocation()));
        }

        return userDtoList;
    }

    public void createUser(UserDto userDto){
        User user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setLocation(userDto.getLocation());

        crudRepository.save(user);
    }
}
