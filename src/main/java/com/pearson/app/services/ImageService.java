package com.pearson.app.services;

import com.pearson.app.dao.ImageRepository;
import com.pearson.app.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Business service for User entity related operations
 */
@Service
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageRepository imageRepository;

    /**
     * creates a new image in the database
     * @param image - the new image
     */
    @Transactional
    public Image addImage(Image image) {
        imageRepository.create(image);
        return image;
    }

    @Transactional(readOnly = true)
    public List<Image> listImages() {
        return imageRepository.list();
    }

    @Transactional(readOnly = true)
    public Image getImage(int id) {
        return imageRepository.get(id);
    }

    @Transactional
    public void updateImage(Image image) {
        imageRepository.update(image);
    }

    @Transactional
    public void removeImage(Image image) {
        imageRepository.delete(image);
    }






}
