package tr.com.obss.jip.bookportal.service;

import tr.com.obss.jip.bookportal.dto.BookDto;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    Boolean createBook(CreateBookDto createBookDto);
}
