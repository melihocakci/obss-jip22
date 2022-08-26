package tr.com.obss.jip.bookportal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tr.com.obss.jip.bookportal.dto.ResponseDto;
import tr.com.obss.jip.bookportal.model.Image;
import tr.com.obss.jip.bookportal.service.ImageService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/image")
public class ImageController {
    private final ImageService imageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseDto uploadImage(@RequestParam("image") MultipartFile multipartImage) throws Exception {
        Image image = new Image();
        image.setName(multipartImage.getName());
        image.setContent(multipartImage.getBytes());

        imageService.save(image);

        return new ResponseDto(true, "Image saved successfully", null);
    }

    @GetMapping(value = "/{imageId}", produces = MediaType.IMAGE_JPEG_VALUE)
    Resource downloadImage(@PathVariable Long imageId) {
        byte[] image = imageService.get(imageId);

        return new ByteArrayResource(image);
    }
}
