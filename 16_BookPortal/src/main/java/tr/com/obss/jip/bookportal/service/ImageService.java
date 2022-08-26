package tr.com.obss.jip.bookportal.service;

import tr.com.obss.jip.bookportal.model.Image;

public interface ImageService {

    byte[] get(Long imageId);

    void save(Image image);
}
