package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.bookportal.dto.*;
import tr.com.obss.jip.bookportal.other.RoleType;
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
        List<UserDto> userDtoList = userService.getUsers();
        return new ResponseDto(true, null, userDtoList);
    }

    @GetMapping("/{id}")
    public ResponseDto getUser(@PathVariable(name = "id") Long id) {
        return new ResponseDto(true, null, userService.getUserDto(id));
    }

    @PostMapping
    public ResponseDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto, RoleType.ROLE_USER);

        return new ResponseDto(true, "User created successfully", null);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);

        return new ResponseDto(true, "User deleted successfully", null);
    }

    @PutMapping("/{id}")
    public ResponseDto updateUser(@PathVariable(name = "id") Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        userService.updateUser(id, updateUserDto);

        return new ResponseDto(true, "User updated successfully", null);
    }

    @PutMapping("/")
    public ResponseDto updateSelfUser(@RequestBody @Valid UpdateUserDto updateUserDto) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateUser(username, updateUserDto);

        return new ResponseDto(true, "User updated successfully", null);
    }

    @PostMapping("/favorite/{bookId}")
    public ResponseDto addFavoriteBook(@PathVariable Long bookId) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.addFavoriteBook(username, bookId);

        return new ResponseDto(true, "Favorite book added successfully", null);
    }

    @PostMapping("/read/{bookId}")
    public ResponseDto addReadBook(@PathVariable Long bookId) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.addReadBook(username, bookId);

        return new ResponseDto(true, "Read book added successfully", null);
    }

    @GetMapping("/profile")
    public ResponseDto getProfile() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.getUserDto(username);

        return new ResponseDto(true, null, userDto);
    }

}
