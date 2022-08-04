package tr.com.obss.jip.bookportal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.dto.BookDto;
import tr.com.obss.jip.bookportal.dto.CreateBookDto;
import tr.com.obss.jip.bookportal.mapper.MyMapper;
import tr.com.obss.jip.bookportal.mapper.MyMapperImpl;
import tr.com.obss.jip.bookportal.model.Book;
import tr.com.obss.jip.bookportal.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final MyMapper mapper = new MyMapperImpl();

    @Override
    public List<BookDto> getBooks() {
        List<Book> books = (List<Book>) bookRepository.findAll();

        List<BookDto> bookDtos = new ArrayList<>();

        for(Book book : books) {
            bookDtos.add(mapper.toBookDto(book));
        }

        return bookDtos;
    }

    @Override
    public void createBook(CreateBookDto createBookDto) {
        Book book = mapper.toBook(createBookDto);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.deleteBookById(id);
    }

    @Override
    public BookDto getBook(Long id) {
        Book book = bookRepository.findBookById(id);
        return mapper.toBookDto(book);
    }

    @Override
    public void updateBook(Long id, CreateBookDto newBook) {
        Book book = mapper.toBook(newBook);
        book.setId(id);
        bookRepository.save(book);
    }

    @Override
    public List<BookDto> findBooksByName(String name) {
        List<Book> books = bookRepository.findBooksByName(name);
        List<BookDto> bookDtoList = new ArrayList<>();

        for(Book book : books) {
            bookDtoList.add(mapper.toBookDto(book));
        }

        return bookDtoList;
    }
}
