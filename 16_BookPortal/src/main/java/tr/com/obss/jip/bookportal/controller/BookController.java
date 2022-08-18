package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;
import tr.com.obss.jip.bookportal.dto.FetchRequest;
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
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getBooks(
            @RequestParam(defaultValue = "200") Integer size,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "") String sortField,
            @RequestParam(defaultValue = "") String sortOrder,
            @RequestParam(defaultValue = "") String name) {

        FetchRequest fetchRequest = new FetchRequest(size, page, sortField, sortOrder, name);
        return new ResponseDto(true, null, bookService.getBookDtos(fetchRequest));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getBook(@PathVariable(name = "id") Long id) {
        return new ResponseDto(true, null, bookService.getBookDto(id));
    }

    @GetMapping("/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto getBookCount() {
        return new ResponseDto(true, null, bookService.getBookCount());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto createBook(@RequestBody @Valid CreateBookDto createBookDto) {
        bookService.createBook(createBookDto);
        return new ResponseDto(true, "Book created successfully", null);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteBook(id);

        return new ResponseDto(true, "Book deleted successfully", null);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseDto updateBook(
            @PathVariable(name = "id") Long id, @RequestBody @Valid UpdateBookDto updateBookDto) {
        bookService.updateBook(id, updateBookDto);

        return new ResponseDto(true, "Book updated successfully", null);
    }
}
