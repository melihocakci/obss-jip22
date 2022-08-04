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
    public List<BookDto> getAll() {
        List<Book> books = (List<Book>) bookRepository.findAll();

        List<BookDto> bookDtos = new ArrayList<>();

        for(Book book : books) {
            bookDtos.add(mapper.toBookDto(book));
        }

        return bookDtos;
    }

    @Override
    public Boolean createBook(CreateBookDto createBookDto) {
        Book book = mapper.toBook(createBookDto);
        bookRepository.save(book);
        return true;
    }
}
