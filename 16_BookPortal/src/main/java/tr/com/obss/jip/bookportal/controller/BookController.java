package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;
import tr.com.obss.jip.bookportal.dto.ResponseDto;
import tr.com.obss.jip.bookportal.dto.UpdateBookDto;
import tr.com.obss.jip.bookportal.service.BookService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    @GetMapping
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

    @PostMapping("/")
    public ResponseDto createBook(@RequestBody @Valid CreateBookDto createBookDto) {
        bookService.createBook(createBookDto);
        return new ResponseDto(true, "Book created successfully", null);
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteBook(id);

        return new ResponseDto(true, "Book deleted successfully", null);
    }

    @PutMapping("/{id}")
    public ResponseDto updateBook(@PathVariable(name = "id") Long id,
                                  @RequestBody @Valid UpdateBookDto updateBookDto) {
        bookService.updateBook(id, updateBookDto);

        return new ResponseDto(true, "Book updated successfully", null);
    }
}
