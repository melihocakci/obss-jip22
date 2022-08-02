package tr.com.obss.jip.springdemo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.springdemo.dto.BookDto;
import tr.com.obss.jip.springdemo.dto.UserDto;
import tr.com.obss.jip.springdemo.service.UserService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
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

    @GetMapping("/find")
    public List<UserDto> findByName(@RequestParam(value = "name", required = false) String name) {
        return userService.findByName(name);
    }

    @PutMapping("/{id}/addbook")
    public boolean addBook(@RequestBody @Valid List<BookDto> bookDtoList, @PathVariable("id") long id) {
        userService.addBook(id, bookDtoList);
        return true;
    }
}
