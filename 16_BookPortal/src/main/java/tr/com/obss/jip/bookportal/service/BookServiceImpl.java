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
    public List<BookDto> getBooks(FetchRequest fetchRequest) {
        Pageable pageable;

        if(fetchRequest.getSortField() != null && fetchRequest.getSortOrder() != null) {
            Sort sort = Sort.by(fetchRequest.getSortField());

            if (fetchRequest.getSortOrder().equals("descend")) {
                sort = sort.descending();
            }

            pageable = PageRequest.of(fetchRequest.getPage(), fetchRequest.getSize(), sort);
        } else {
            pageable = PageRequest.of(fetchRequest.getPage(), fetchRequest.getSize());
        }

        Page<Book> bookPage = bookRepository.findAll(pageable);

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
        return mapper.toBookDto(book);
    }

    @Override
    public void updateBook(Long id, UpdateBookDto updateBookDto) {
        Book book = bookRepository.findBookById(id);

        if (updateBookDto.getName() != null) {
            book.setName(updateBookDto.getName());
        }

        if (updateBookDto.getAuthor() != null) {
            book.setAuthor(updateBookDto.getAuthor());
        }

        bookRepository.save(book);
    }
}
