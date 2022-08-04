package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;
import tr.com.obss.jip.bookportal.dto.CreateUserDto;
import tr.com.obss.jip.bookportal.dto.ResponseDto;
import tr.com.obss.jip.bookportal.dto.UserDto;
import tr.com.obss.jip.bookportal.service.BookService;
import tr.com.obss.jip.bookportal.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final BookService bookService;

    @GetMapping("/user")
    public ResponseDto getUsers() {
        List<UserDto> userDtoList = userService.getAll();
        return new ResponseDto(true, null, userDtoList);
    }

    @GetMapping("/user/{id}")
    public ResponseDto getUser(@PathVariable(name = "id") Long id) {
        return new ResponseDto(true, null, userService.getUser(id));
    }

    @DeleteMapping("/user/{id}")
    public ResponseDto deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);

        return new ResponseDto(true, "User deleted successfully", null);
    }

    @PutMapping("/user/{id}")
    public ResponseDto updateUser(@PathVariable(name = "id") Long id,
                                  @RequestBody @Valid CreateUserDto newUser) {
        userService.updateUser(id, newUser);

        return new ResponseDto(true, "User updated successfully", null);
    }

    @PostMapping("/book")
    public ResponseDto createBook(@RequestBody @Valid CreateBookDto createBookDto) {
        bookService.createBook(createBookDto);
        return new ResponseDto(true, "Book created successfully", null);
    }

    @DeleteMapping("/book/{id}")
    public ResponseDto deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteBook(id);

        return new ResponseDto(true, "Book deleted successfully", null);
    }

    @PutMapping("/book/{id}")
    public ResponseDto updateBook(@PathVariable(name = "id") Long id,
                                  @RequestBody @Valid CreateBookDto newBook) {
        bookService.updateBook(id, newBook);

        return new ResponseDto(true, "Book updated successfully", null);
    }
}
