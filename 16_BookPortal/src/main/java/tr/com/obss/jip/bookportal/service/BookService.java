package tr.com.obss.jip.bookportal.service;

import tr.com.obss.jip.bookportal.dto.*;

public interface BookService {

    PaginationResponse getPage(PaginationRequest paginationRequest);

    BookDto getBookDto(Long id);

    void createBook(CreateBookDto createBookDto);

    void deleteBook(Long id);

    void updateBook(Long id, UpdateBookDto updateBookDto);
}
