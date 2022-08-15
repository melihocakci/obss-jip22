package tr.com.obss.jip.bookportal.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "readBooks")
  private List<User> readUsers = new ArrayList<>();

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "favoriteBooks")
  private List<User> favoriteUsers = new ArrayList<>();
}
