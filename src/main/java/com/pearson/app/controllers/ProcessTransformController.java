package com.pearson.app.controllers;

import com.pearson.app.config.PropertyPlaceholderConfig;
import com.pearson.app.model.*;
import com.pearson.app.services.*;
import com.pearson.btec.service.ProcessWordDocument;
import org.apache.commons.io.IOUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.*;

//import org.imgscalr.Scalr;

@Controller
@RequestMapping
@Import(PropertyPlaceholderConfig.class)
public class ProcessTransformController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessTransformController.class);
    
    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    @Autowired
    private TransformationService transformationService;

    @Autowired
    private SpecUnitService specUnitService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    ServletContext context;

    String transformationStatus = null;

    @Value("${file.upload.directory}")
    private String fileUploadDirectory;


    @RequestMapping(value = "/processtransform/start")
    public String index() {
        LOGGER.debug("ImageController home");
        // image.jsp
        return "processtransform";
    }
/*
    //TODO: Remove
    @RequestMapping(value = "/processtransform", method = RequestMethod.GET)
    public @ResponseBody
    Map list() {
        LOGGER.debug("uploadGet called");
        List<Image> list = imageService.listImages();
        for(Image image : list) {
            image.setUrl("/processtransform/view/"+image.getId());
            //image.setThumbnailUrl("/thumbnail/"+image.getId());
            image.setDeleteUrl("/processtransform/delete/"+image.getId());
            image.setDeleteType("DELETE");
        }
        Map<String, Object> files = new HashMap<>();
        files.put("files", list);
        LOGGER.debug("Returning: {}", files);
        return files;
    }
*/

    @RequestMapping(value = "/processtransform", method = RequestMethod.POST)
    public @ResponseBody
    Map upload(MultipartHttpServletRequest request, HttpServletResponse response) {

        // Get USER
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.debug("Getting user from Session - Username[{}], Principle[{}]", principal.getName(), principal);

        User user = userService.getUserByUsername(principal.getName());
        LOGGER.debug("Retrieved user[{}]", user);


        // Setup UPLOADS path
        String root = fileUploadDirectory; //context.getRealPath("/");
        File path = new File(root + File.separator + "uploads");
        if (!path.exists()) {
            boolean status = path.mkdirs();
        }
        LOGGER.debug("uploadPost called");
        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        List<Image> list = new LinkedList<>();


        while (itr.hasNext()) {

            // Set TEMPLATE
            Template template = templateService.getTemplateByName("BTECNATIONALS");


            // Create TRANSFORMATION object
            Transformation newTransformation = new Transformation();
            newTransformation.setUser(user);
            newTransformation.setDate(new Date());
            //newTransformation.setTemplate(template);
            newTransformation.setTemplateId(0);
            //transformationService.addTransformation(newTransformation);



            // upload and create IMAGE
            mpf = request.getFile(itr.next());
            LOGGER.debug("Uploading {}", mpf.getOriginalFilename());

            String newFilenameBase = UUID.randomUUID().toString();
            String originalFileExtension = mpf.getOriginalFilename().substring(mpf.getOriginalFilename().lastIndexOf("."));
            String newFilename = newFilenameBase + originalFileExtension;
            String contentType = mpf.getContentType();

            //File newFile = new File(storageDirectory + "/" + newFilename);
            File newFile = new File(path + "/" + newFilename);
            try {
                LOGGER.debug("Moving file to newFile[{}]", path.getAbsolutePath() + "/" + newFilename);
                mpf.transferTo(newFile);
            } catch (IOException e) {
                LOGGER.error("Could not upload file " + mpf.getOriginalFilename(), e);
                transformationStatus = Transformation.TRANSFORM_STATUS_FAIL_FILE_WRITE;
                // update TRANSFORMATION
                newTransformation.setTransformStatus(transformationStatus);
                LOGGER.error("Error - transformationStatus[{}] Please contact technical support with detailed error:[{}] ", transformationStatus, e.getStackTrace());
                newTransformation.setMessage(" Please contact technical support with detailed error: " + e.getMessage() + " \n\r");
                transformationService.updateTransformation(newTransformation);
            }

            Image image = new Image();
            image.setName(mpf.getOriginalFilename());
            image.setNewFilename(newFilename);
            image.setContentType(contentType);
            image.setSize(mpf.getSize());
            image.setUrl("/processtransform/view/" + image.getId());
            image.setDeleteUrl("/processtransform/delete/" + image.getId());
            image.setDeleteType("DELETE");
            image.setDateCreated(new Date());
            image.setLastUpdated(new Date());
            //-imageService.addImage(image);
            LOGGER.debug("Adding location and properties of image file of Word Document to Database image[{}]", image);
            list.add(image);
            // update TRANSFORMATION
            newTransformation.setWordfilename(image.getName());
            //newTransformation.setImage(image);
            newTransformation.setImage_id(image.getId());
            //newTransformation.setTransformStatus(Transformation.TRANSFORM_STATUS_TRANSFORM_IN_PROGRESS);
            //transformationService.updateTransformation(newTransformation);


            // Start TRANSFORM process
            LOGGER.debug("Reading properties and extracting content from Word newFile[{}]", newFile.getAbsolutePath());
            ProcessWordDocument processWordDocument = null;
            processWordDocument = new ProcessWordDocument(newFile);

            try {
                processWordDocument.doTransformationWork();
            } catch (Docx4JException e) {
                transformationStatus = Transformation.TRANSFORM_STATUS_FAIL_EXTRACT_WORD_TO_XML;
                LOGGER.error("Error - transformationStatus[{}]. Transform has failed. Detailed Error Message - [{}]", transformationStatus, e.getMessage());
                newTransformation.setMessage(" Transform has failed with transformationStatus[" + transformationStatus
                        + "] and Detailed Error Message - " + e.getMessage() + " \n\r");
                transformationService.updateTransformation(newTransformation);
            }

            processWordDocument.setTransformationStatus();
            this.transformationStatus = processWordDocument.getTransformationStatus();
            // update TRANSFORMATION
            newTransformation.setTransformStatus(this.transformationStatus);
//          transformationService.updateTransformation(newTransformation);
            StringBuilder openXmlFileName = new StringBuilder();
            openXmlFileName.append(processWordDocument.getTransformationUan().replaceAll("/", "_")).append("-open.xml");
            StringBuilder pqsFileName = new StringBuilder();
            pqsFileName.append(processWordDocument.getTransformationUan().replaceAll("/", "_")).append(".xml");
            // update TRANSFORMATION
            newTransformation.setOpenxmlfilename(openXmlFileName.toString());
            newTransformation.setIqsxmlfilename(pqsFileName.toString());
            newTransformation.setLastmodified(new Date());
            newTransformation.setQanNo(processWordDocument.getTransformationUan());
            newTransformation.setUnitNo(processWordDocument.getTransformationUnitNo());
            newTransformation.setUnitTitle(processWordDocument.getTransformationUnitTitle());
            newTransformation.setAuthor(processWordDocument.getTransformationAuthor());
            newTransformation.setMessage(processWordDocument.getTransformationMessage());
            newTransformation.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);

            Specunit specunit = new Specunit();
            specunit.setQanNo(processWordDocument.getTransformationUan());
            specunit.setUnitXML(processWordDocument.getXmlStringContent());
            //specUnitService.addSpecUnit(specunit);
            // update TRANSFORMATION
            newTransformation.setSpecunit(specunit);
            //specunit.setTransformation(newTransformation);

            LOGGER.debug("Read Word document transformationStatus[{}], transformationMessage[{}]", this.transformationStatus, processWordDocument.getTransformationMessage());
            transformationService.addTransformation(newTransformation);
        }
        
        Map<String, Object> files = new HashMap<>();
        files.put("files", list);
        return files;
    }

/*
    @RequestMapping(value = "/processtransform/view/{id}", method = RequestMethod.GET)
    public void picture(HttpServletResponse response, @PathVariable int id) {
        Image image = imageService.getImage(id);
        File imageFile = new File(fileUploadDirectory + File.separator + "uploads" + File.separator + image.getNewFilename());
        response.setContentType(image.getContentType());
        response.setContentLength(image.getSize().intValue());
        try {
            InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());
        } catch(IOException e) {
            LOGGER.error("Could not show document " + id, e);
        }
    }
*/


    @RequestMapping(value = "/processtransform/delete/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    List delete(@PathVariable int id) {
        Image image = imageService.getImage(id);
        File imageFile = new File(fileUploadDirectory + File.separator + "uploads" + File.separator + image.getNewFilename());
        imageFile.delete();
        //File thumbnailFile = new File(fileUploadDirectory+"/"+image.getThumbnailFilename());
        //thumbnailFile.delete();
        imageService.removeImage(image);
        List<Map<String, Object>> results = new ArrayList<>();
        Map<String, Object> success = new HashMap<>();
        success.put("success", true);
        results.add(success);
        return results;
    }
}
