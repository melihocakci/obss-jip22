package tr.com.obss.jip.springdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springdemo.dto.UserDto;
import tr.com.obss.jip.springdemo.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public List<UserDto> allUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    public Boolean createUser(@RequestBody @Valid UserDto userDto) {
        userService.createUser(userDto);
        return true;
    }
}
