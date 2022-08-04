package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.bookportal.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
