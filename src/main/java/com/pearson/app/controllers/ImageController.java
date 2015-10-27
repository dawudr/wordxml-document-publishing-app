package com.pearson.app.controllers;

import com.pearson.app.config.PropertyPlaceholderConfig;
import com.pearson.app.model.Image;
import com.pearson.app.services.ImageService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Controller
@RequestMapping
@Import(PropertyPlaceholderConfig.class)
public class ImageController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageController.class);
    
    @Autowired
    private ImageService imageService;

    @Value("${file.upload.directory}")
    private String fileUploadDirectory;

    @RequestMapping(value = "/upload/start")
    public String index() {
        LOGGER.debug("ImageController home");
        // image.jsp
        return "image";
    }


    @RequestMapping(value = "/upload/list", method = RequestMethod.GET)
    public @ResponseBody
    Map listAll() {
        LOGGER.debug("Image listAll called");
        List<Image> list = imageService.listImages();
        for(Image image : list) {
            image.setUrl("/download/"+image.getId());
            //image.setThumbnailUrl("/thumbnail/"+image.getId());
            image.setDeleteUrl("/delete/"+image.getId());
            image.setDeleteType("DELETE");
        }
        Map<String, Object> files = new HashMap<>();
        files.put("files", list);
        LOGGER.debug("Returning: {}", files);
        return files;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public @ResponseBody
    Map list() {
        LOGGER.debug("Image list called");
        List<Image> list = imageService.listImages();
        List<Image> newList = new ArrayList<Image>();
        for(Image image : list) {
            if(image.getLastUpdated() == null) {
                image.setUrl("/download/" + image.getId());
                //image.setThumbnailUrl("/thumbnail/"+image.getId());
                image.setDeleteUrl("/delete/" + image.getId());
                image.setDeleteType("DELETE");
                newList.add(image);
            }
        }
        Map<String, Object> files = new HashMap<>();
        files.put("files", newList);
        LOGGER.debug("Returning: {}", files);
        return files;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    Map upload(MultipartHttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("uploadPost called");
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        List<Image> list = new LinkedList<>();
        
        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            LOGGER.debug("Uploading {}", mpf.getOriginalFilename());

            String newFilenameBase = UUID.randomUUID().toString();
            String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
            String newFilename = newFilenameBase + originalFileExtension;
            String contentType = mpf.getContentType();
            
            File newFile = new File(fileUploadDirectory + File.separator + newFilename);
            try {
                mpf.transferTo(newFile);
                
                //BufferedImage thumbnail = Scalr.resize(ImageIO.read(newFile), 290);
                //String thumbnailFilename = newFilenameBase + "-thumbnail.png";
                //File thumbnailFile = new File(storageDirectory + "/" + thumbnailFilename);
                //ImageIO.write(thumbnail, "png", thumbnailFile);
                
                Image image = new Image();
                image.setName(mpf.getOriginalFilename());
                //image.setThumbnailFilename(thumbnailFilename);
                image.setNewFilename(newFilename);
                image.setContentType(contentType);
                image.setSize(mpf.getSize());
                //image.setThumbnailSize(thumbnailFile.length());
                image.setUrl("/download/"+image.getId());
                //image.setThumbnailUrl("/thumbnail/"+image.getId());
                image.setDeleteUrl("/delete/"+image.getId());
                image.setDeleteType("DELETE");
                image.setDateCreated(new Date());
                image = imageService.addImage(image);

                list.add(image);
                
            } catch(IOException e) {
                LOGGER.error("Could not upload file "+mpf.getOriginalFilename(), e);
            }
            
        }
        
        Map<String, Object> files = new HashMap<>();
        files.put("files", list);
        return files;
    }


    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    public void download(HttpServletResponse response, @PathVariable int id) {
        Image image = imageService.getImage(id);
        File imageFile = new File(fileUploadDirectory+"/"+image.getNewFilename());
        response.setContentType(image.getContentType());
        response.setHeader("Content-Disposition", "attachment; filename =\"" + image.getName() + "\"");
        response.setContentLength(image.getSize().intValue());
        LOGGER.debug("Downloading file {}",image.getName());
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch(IOException e) {
            LOGGER.error("Could not show picture "+id, e);
        }
    }
    
/*
    @RequestMapping(value = "/thumbnail/{id}", method = RequestMethod.GET)
    public void thumbnail(HttpServletResponse response, @PathVariable Long id) {
        Image image = imageRepository.get(id);
        File imageFile = new File(fileUploadDirectory+"/"+image.getThumbnailFilename());
        response.setContentType(image.getContentType());
        response.setContentLength(image.getThumbnailSize().intValue());
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch(IOException e) {
            log.error("Could not show thumbnail "+id, e);
        }
    }
    */
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    //public @ResponseBody List delete(@PathVariable int id) {
    public void delete(@PathVariable("id") Integer id) {

        Image image = imageService.getImage(id);

        List<Map<String, Object>> results = new ArrayList<>();
        //Map<String, Object> success = new HashMap<>();
        try {
            imageService.removeImage(image);
        } catch (Exception e) {
            //success.put("success", false);
            //success.put("message", e.getMessage());
            LOGGER.debug("Failed deleting Document Id[{}]", id);
        }
/*
        success.put("success", true);
        results.add(success);
        return results;
        */
        LOGGER.debug("Successfully deleted Document Id[{}]", id);

    }
}
