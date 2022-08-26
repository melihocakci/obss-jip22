package tr.com.obss.jip.bookportal.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.bookportal.exception.NotFoundException;
import tr.com.obss.jip.bookportal.model.Image;
import tr.com.obss.jip.bookportal.repository.ImageRepository;
import tr.com.obss.jip.bookportal.service.ImageService;

@RequiredArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Override
    public byte[] get(Long imageId) {
        return imageRepository
                .findById(imageId)
                .orElseThrow(() -> new NotFoundException("Image does not exist"))
                .getContent();
    }

    @Override
    public void save(Image image) {
        imageRepository.save(image);
    }
}
