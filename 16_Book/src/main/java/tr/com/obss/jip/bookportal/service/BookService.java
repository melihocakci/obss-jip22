package tr.com.obss.jip.bookportal.service;

import tr.com.obss.jip.bookportal.dto.BookDto;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;
import tr.com.obss.jip.bookportal.model.Book;

import java.util.List;

public interface BookService {
    List<BookDto> getBooks();

    void createBook(CreateBookDto createBookDto);

    void deleteBook(Long id);

    BookDto getBook(Long id);

    void updateBook(Long id, CreateBookDto book);

    List<BookDto> findBooksByName(String name);
}
