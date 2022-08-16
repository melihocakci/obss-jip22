package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.bookportal.dto.*;
import tr.com.obss.jip.bookportal.other.RoleType;
import tr.com.obss.jip.bookportal.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseDto getUsers(
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "") String sortField,
            @RequestParam(defaultValue = "") String sortOrder,
            @RequestParam(defaultValue = "") String username) {

        FetchRequest fetchRequest = new FetchRequest(size, page, sortField, sortOrder, username);
        return new ResponseDto(true, null, userService.getUserDtos(fetchRequest));
    }

    @GetMapping("/{id}")
    public ResponseDto getUser(@PathVariable(name = "id") Long id) {
        return new ResponseDto(true, null, userService.getUserDto(id));
    }

    @GetMapping("/count")
    public ResponseDto getUserCount() {
        return new ResponseDto(true, null, userService.getUserCount());
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

    @DeleteMapping
    public ResponseDto deleteThisUser() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteUser(username);

        return new ResponseDto(true, "User deleted successfully", null);
    }

    @PutMapping("/{id}")
    public ResponseDto updateUser(
            @PathVariable(name = "id") Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        userService.updateUser(id, updateUserDto);

        return new ResponseDto(true, "User updated successfully", null);
    }

    @PutMapping
    public ResponseDto updateThisUser(@RequestBody @Valid UpdateUserDto updateUserDto) {
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

    @DeleteMapping("/favorite/{bookId}")
    public ResponseDto removeFavoriteBook(@PathVariable Long bookId) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.removeFavoriteBook(username, bookId);

        return new ResponseDto(true, "Favorite book removed successfully", null);
    }

    @DeleteMapping("/read/{bookId}")
    public ResponseDto removeReadBook(@PathVariable Long bookId) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.removeReadBook(username, bookId);

        return new ResponseDto(true, "Read book removed successfully", null);
    }

    @GetMapping("/profile")
    public ResponseDto getProfile() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.getUserDto(username);

        return new ResponseDto(true, null, userDto);
    }
}
