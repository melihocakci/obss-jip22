package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;
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

    @PostMapping("/")
    public ResponseDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto);

        return new ResponseDto(true, "User created successfully", null);
    }

}
