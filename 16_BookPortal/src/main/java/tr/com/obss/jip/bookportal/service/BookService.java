package tr.com.obss.jip.bookportal.service;

import tr.com.obss.jip.bookportal.dto.BookDto;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;
import tr.com.obss.jip.bookportal.dto.FetchRequest;
import tr.com.obss.jip.bookportal.dto.UpdateBookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getBooks(FetchRequest fetchRequest);

    Long getBookCount();

    void createBook(CreateBookDto createBookDto);

    void deleteBook(Long id);

    BookDto getBook(Long id);

    void updateBook(Long id, UpdateBookDto updateBookDto);

}
