package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.bookportal.dto.BookDto;
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
        return new ResponseDto(true, null, bookService.getBooks());
    }

    @GetMapping("/{id}")
    public ResponseDto getBook(@PathVariable(name = "id") Long id) {
        return new ResponseDto(true, null, bookService.getBook(id));
    }

    @GetMapping("/search")
    public ResponseDto findBookByName(@RequestParam(name = "name") String name) {
        return new ResponseDto(true, null, bookService.findBooksByName(name));
    }
}
