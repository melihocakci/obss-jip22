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

    @GetMapping
    public ResponseDto getUsers() {
        List<UserDto> userDtoList = userService.getAll();
        return new ResponseDto(true, null, userDtoList);
    }

    @GetMapping("/{id}")
    public ResponseDto getUser(@PathVariable(name = "id") Long id) {
        return new ResponseDto(true, null, userService.getUser(id));
    }

    @PostMapping
    public ResponseDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto);

        return new ResponseDto(true, "User created successfully", null);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);

        return new ResponseDto(true, "User deleted successfully", null);
    }

    @PutMapping("/{id}")
    public ResponseDto updateUser(@PathVariable(name = "id") Long id,
                                  @RequestBody @Valid CreateUserDto newUser) {
        userService.updateUser(id, newUser);

        return new ResponseDto(true, "User updated successfully", null);
    }

}
