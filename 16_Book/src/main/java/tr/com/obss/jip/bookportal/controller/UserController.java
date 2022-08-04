package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.bookportal.dto.CreateUserDto;
import tr.com.obss.jip.bookportal.dto.ResponseDto;
import tr.com.obss.jip.bookportal.dto.UserDto;
import tr.com.obss.jip.bookportal.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public ResponseDto getUsers() {
        List<UserDto> userDtos = userService.getAll();
        return new ResponseDto(true, null, userDtos);
    }

    @PostMapping("/")
    public ResponseDto addUser(@RequestBody @Valid CreateUserDto createUserDto) {
        ResponseDto result;

        if (userService.createUser(createUserDto)) {
            result = new ResponseDto(true, "User created successfully", null);
        } else {
            result = new ResponseDto(false, "User creation failed", null);
        }

        return result;
    }
}
