package tr.com.obss.jip.bookportal.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "images")
@SQLDelete(sql = "UPDATE images SET deleted = true WHERE id = ?")
@Where(clause = "deleted=false")
public class Image {

    @Id
    @SequenceGenerator(name = "images_seq_gen", sequenceName = "images_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_seq_gen")
    private Long id;

    @Lob
    private byte[] content;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean deleted = Boolean.FALSE;
}
