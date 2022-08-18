package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getUsers(
            @RequestParam(defaultValue = "200") Integer size,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "") String sortField,
            @RequestParam(defaultValue = "") String sortOrder,
            @RequestParam(defaultValue = "") String username) {

        FetchRequest fetchRequest = new FetchRequest(size, page, sortField, sortOrder, username);
        return new ResponseDto(true, null, userService.getUserDtos(fetchRequest));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getUser(@PathVariable(name = "id") Long id) {
        return new ResponseDto(true, null, userService.getUserDto(id));
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getUserCount() {
        return new ResponseDto(true, null, userService.getUserCount());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto, RoleType.ROLE_USER);

        return new ResponseDto(true, "User created successfully", null);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);

        return new ResponseDto(true, "User deleted successfully", null);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto deleteThisUser() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.deleteUser(username);

        return new ResponseDto(true, "User deleted successfully", null);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto updateUser(
            @PathVariable(name = "id") Long id, @RequestBody @Valid UpdateUserDto updateUserDto) {
        userService.updateUser(id, updateUserDto);

        return new ResponseDto(true, "User updated successfully", null);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto updateThisUser(@RequestBody @Valid UpdateUserDto updateUserDto) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.updateUser(username, updateUserDto);

        return new ResponseDto(true, "User updated successfully", null);
    }

    @PostMapping("/favorite/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto addFavoriteBook(@PathVariable Long bookId) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.addFavoriteBook(username, bookId);

        return new ResponseDto(true, "Favorite book added successfully", null);
    }

    @PostMapping("/read/{bookId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto addReadBook(@PathVariable Long bookId) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.addReadBook(username, bookId);

        return new ResponseDto(true, "Read book added successfully", null);
    }

    @DeleteMapping("/favorite/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto removeFavoriteBook(@PathVariable Long bookId) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.removeFavoriteBook(username, bookId);

        return new ResponseDto(true, "Favorite book removed successfully", null);
    }

    @DeleteMapping("/read/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto removeReadBook(@PathVariable Long bookId) {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.removeReadBook(username, bookId);

        return new ResponseDto(true, "Read book removed successfully", null);
    }

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getProfile() {
        final String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDto userDto = userService.getUserDto(username);

        return new ResponseDto(true, null, userDto);
    }
}
