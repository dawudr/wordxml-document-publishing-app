package com.pearson.app.services;

import com.pearson.app.model.Image;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by dawud on 22/02/2015.
 */
@Service
public interface ImageServiceInterface {

        public void addImage(Image image);
        public List<Image> listImages();
        public Image geImageById(Long id);
        public void updateImage(Image image);
        public void removeImage(Image image);
}
