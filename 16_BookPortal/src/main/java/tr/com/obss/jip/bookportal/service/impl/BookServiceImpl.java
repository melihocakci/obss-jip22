package tr.com.obss.jip.bookportal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.dto.*;
import tr.com.obss.jip.bookportal.exception.NotFoundException;
import tr.com.obss.jip.bookportal.mapper.MyMapper;
import tr.com.obss.jip.bookportal.mapper.MyMapperImpl;
import tr.com.obss.jip.bookportal.model.Book;
import tr.com.obss.jip.bookportal.repository.BookRepository;
import tr.com.obss.jip.bookportal.service.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final MyMapper mapper = new MyMapperImpl();

    @Override
    public PaginationResponse getPage(PaginationRequest paginationRequest) {
        Pageable pageable;
        String sortField = paginationRequest.getSortField();
        String sortOrder = paginationRequest.getSortOrder();

        if (!sortField.isEmpty() && !sortOrder.isEmpty()) {
            Sort sort = Sort.by(sortField);

            if (sortOrder.equals("descend")) {
                sort = sort.descending();
            }

            pageable =
                    PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize(), sort);
        } else {
            pageable = PageRequest.of(paginationRequest.getPage(), paginationRequest.getSize());
        }

        Page<Book> bookPage;
        String searchParam = paginationRequest.getSearchParam();

        if (!searchParam.isEmpty()) {
            bookPage = bookRepository.search(pageable, searchParam);
        } else {
            bookPage = bookRepository.findAll(pageable);
        }

        List<Book> books = bookPage.getContent();

        List<BookDto> bookDtoList = new ArrayList<>();

        for (Book book : books) {
            bookDtoList.add(mapper.toBookDto(book));
        }

        return new PaginationResponse(bookDtoList, bookPage.getTotalElements());
    }

    @Override
    public void createBook(CreateBookDto createBookDto) {
        Book book = mapper.toBook(createBookDto);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            throw new NotFoundException("Book does not exist");
        }

        bookRepository.deleteById(bookId);
    }

    @Override
    public BookDto getBookDto(Long bookId) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            throw new NotFoundException("Book does not exist");
        }

        return mapper.toBookDto(optionalBook.get());
    }

    @Override
    public void updateBook(Long bookId, UpdateBookDto updateBookDto) {
        Optional<Book> optionalBook = bookRepository.findById(bookId);

        if (optionalBook.isEmpty()) {
            throw new NotFoundException("Book does not exist");
        }

        Book book = optionalBook.get();

        String name = updateBookDto.getName();
        String author = updateBookDto.getAuthor();

        if (name != null) {
            book.setName(name);
        }

        if (author != null) {
            book.setAuthor(author);
        }

        bookRepository.save(book);
    }
}
