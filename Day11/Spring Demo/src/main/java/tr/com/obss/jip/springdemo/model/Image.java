package tr.com.obss.jip.springdemo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "images")
public class Image {

    @Id
    @SequenceGenerator(name = "images_seq_gen", sequenceName = "images_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "images_seq_gen")
    private Long id;

    private int width;

    private int height;

    @OneToOne(mappedBy = "image")
    private User user;

}
