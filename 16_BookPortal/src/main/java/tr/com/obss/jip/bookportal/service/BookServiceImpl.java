package tr.com.obss.jip.bookportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.dto.BookDto;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;
import tr.com.obss.jip.bookportal.dto.FetchRequest;
import tr.com.obss.jip.bookportal.dto.UpdateBookDto;
import tr.com.obss.jip.bookportal.exception.NotFoundException;
import tr.com.obss.jip.bookportal.mapper.MyMapper;
import tr.com.obss.jip.bookportal.mapper.MyMapperImpl;
import tr.com.obss.jip.bookportal.model.Book;
import tr.com.obss.jip.bookportal.model.User;
import tr.com.obss.jip.bookportal.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final UserService userService;
    private final MyMapper mapper = new MyMapperImpl();

    @Override
    public List<BookDto> getBookDtos(FetchRequest fetchRequest) {
        Pageable pageable;
        String sortField = fetchRequest.getSortField();
        String sortOrder = fetchRequest.getSortOrder();

        if (!sortField.isEmpty() && !sortOrder.isEmpty()) {
            Sort sort = Sort.by(sortField);

            if (sortOrder.equals("descend")) {
                sort = sort.descending();
            }

            pageable = PageRequest.of(fetchRequest.getPage(), fetchRequest.getSize(), sort);
        } else {
            pageable = PageRequest.of(fetchRequest.getPage(), fetchRequest.getSize());
        }

        Page<Book> bookPage;
        String name = fetchRequest.getSearch();

        if (!name.isEmpty()) {
            bookPage = bookRepository.findAllByNameContaining(pageable, name);
        } else {
            bookPage = bookRepository.findAll(pageable);
        }

        List<Book> books = bookPage.getContent();

        List<BookDto> bookDtoList = new ArrayList<>();

        for (Book book : books) {
            bookDtoList.add(mapper.toBookDto(book));
        }

        return bookDtoList;
    }

    @Override
    public Long getBookCount() {
        return bookRepository.count();
    }

    @Override
    public void createBook(CreateBookDto createBookDto) {
        Book book = mapper.toBook(createBookDto);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findBookById(id);

        if (book == null) {
            throw new NotFoundException("Book does not exist");
        }

        for (User user : book.getReadUsers()) {
            userService.removeReadBook(user.getUsername(), book.getId());
        }

        for (User user : book.getReadUsers()) {
            userService.removeFavoriteBook(user.getUsername(), book.getId());
        }

        bookRepository.deleteBookById(id);
    }

    @Override
    public BookDto getBook(Long id) {
        Book book = bookRepository.findBookById(id);

        if (book == null) {
            throw new NotFoundException("Book does not exist");
        }

        return mapper.toBookDto(book);
    }

    @Override
    public void updateBook(Long id, UpdateBookDto updateBookDto) {
        Book book = bookRepository.findBookById(id);

        if (book == null) {
            throw new NotFoundException("Book does not exist");
        }

        String name = updateBookDto.getName();
        String author = updateBookDto.getAuthor();

        if (name != null && !name.isEmpty()) {
            book.setName(updateBookDto.getName());
        }

        if (author != null && !author.isEmpty()) {
            book.setAuthor(updateBookDto.getAuthor());
        }

        bookRepository.save(book);
    }
}
