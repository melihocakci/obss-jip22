package tr.com.obss.jip.bookportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.obss.jip.bookportal.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {}
