package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;
import tr.com.obss.jip.bookportal.dto.ResponseDto;
import tr.com.obss.jip.bookportal.service.BookService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/")
    public ResponseDto getBooks() {
        return new ResponseDto(true, null, bookService.getAll());
    }

    @PostMapping("/")
    public ResponseDto addBook(@RequestBody @Valid CreateBookDto createBookDto) {
        ResponseDto result;

        if (bookService.createBook(createBookDto)) {
            result = new ResponseDto(true, "Book created successfully", null);
        } else {
            result = new ResponseDto(false, "Book creation failed", null);
        }

        return result;
    }
}
