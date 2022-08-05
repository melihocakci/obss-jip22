package tr.com.obss.jip.bookportal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "books")
public class Book {

    @Id
    @SequenceGenerator(name = "books_seq_gen", sequenceName = "books_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "books_seq_gen")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;
}
