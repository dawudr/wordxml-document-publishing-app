package com.pearson.app.services;

import com.pearson.app.dao.ImageRepository;
import com.pearson.app.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Business service for User entity related operations
 */
@Service
public class ImageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageService.class);

    @Value("${file.upload.directory}")
    private String fileUploadDirectory;

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

    @Transactional(rollbackFor = Exception.class)
    public void removeImage(Image image) throws Exception{
        // delete from filesystem first
        if(removeImage(image.getNewFilename())) {
            // then delete from database
            imageRepository.delete(image);
        }
    }

    public boolean removeImage(String filenameStr) throws IOException{
        LOGGER.debug("Removing Word Document file on filesystem: Filename[{}]", filenameStr);

        boolean deleted = false;
        File imageFile = new File(fileUploadDirectory+ File.separator + filenameStr);
        if(imageFile.exists()) {
            String filenameAbsolutePath = imageFile.getAbsolutePath();
            LOGGER.debug("Found Word Document file exists on filesystem: File[{}]", filenameAbsolutePath);
            deleted = imageFile.delete();
            //File thumbnailFile = new File(fileUploadDirectory+"/"+image.getThumbnailFilename());
            //thumbnailFile.delete();
            if (!deleted) {
                LOGGER.error("Failed to delete file from filesystem: File[{}] Technical Error: [{}]", filenameAbsolutePath, getReasonForFileDeletionFailureInPlainEnglish(imageFile));
                throw new IOException("Failed to delete file from filesystem: " + filenameAbsolutePath);
            } else {
                LOGGER.debug("Successfully deleted file from filesystem: File[{}]", imageFile.getAbsolutePath());
            }
        } else {
            deleted = true;
        }
        return deleted;
    }


    private String getReasonForFileDeletionFailureInPlainEnglish(File file) {
        try {
            if (!file.exists())
                return "It doesn't exist in the first place.";
            else if (file.isDirectory() && file.list().length > 0)
                return "It's a directory and it's not empty.";
            else
                return "Somebody else has it open, we don't have write permissions, or somebody stole my disk.";
        } catch (SecurityException e) {
            return "We're sandboxed and don't have filesystem access.";
        }
    }

}
