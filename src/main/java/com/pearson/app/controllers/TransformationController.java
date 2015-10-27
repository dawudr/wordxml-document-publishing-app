package com.pearson.app.controllers;

import com.pearson.app.dto.TransformationDTO;
import com.pearson.app.dto.TransformationsDTO;
import com.pearson.app.model.*;
import com.pearson.app.services.*;
import com.pearson.btec.service.ProcessWordDocument;
import com.pearson.btec.service.TransformXmlDocument;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import net.sf.json.JSONArray;
import org.apache.commons.io.IOUtils;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transactional;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.security.Principal;
import java.util.Date;
import java.util.List;


/**
 *
 *  REST service for transformation - allows to update, create and search for transformation for the currently logged in user.
 *
 */
@Controller
public class TransformationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationController.class);
    private static final int BUFFER_SIZE = 4096;

    @Value("${file.upload.directory}")
    private String fileUploadDirectory;

    @Autowired
    private TransformationService transformationService;

    @Autowired
    private UserService userService;

    @Autowired
    private TemplateService templateService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private SpecUnitService specUnitService;

    @Autowired
    ServletContext context;

//    @Transactional
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/transformation")
    public void addTransformation(@RequestBody TransformationDTO transformationDTO) {
        LOGGER.debug("POST new TransformationDTO[{}]", transformationDTO.toString());

        // Date to for lastModified
        Date lastModified = new Date();

        // Get USER
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.debug("Getting user from Session - Username[{}], Principle[{}]", principal.getName(), principal);

        User user = userService.getUserByUsername(principal.getName());
        LOGGER.debug("Retrieved user[{}]", user);

        // Set Image
        Image image = imageService.getImage(transformationDTO.getImageId());
        //LOGGER.debug("Found Image id[{}] -> Image[{}]", transformationDTO.getImageId(), image);
        image.setLastUpdated(lastModified);
        imageService.updateImage(image);
        LOGGER.debug("Updated LastUpdated Date for Image id[{}] -> Image[{}]", transformationDTO.getImageId(), image);


        // Set TEMPLATE
        //Template template = templateService.getTemplateById(transformationDTO.getTemplateId());
        //LOGGER.debug("Found Template id[{}] -> Image[{}]", transformationDTO.getTemplateId(), template);

        // Create TRANSFORMATION object
        Transformation newTransformation = new Transformation();
        newTransformation.setUser(user);
        newTransformation.setDate(image.getDateCreated());
        newTransformation.setLastmodified(lastModified);
        newTransformation.setTemplateId(transformationDTO.getTemplateId());
        newTransformation.setImage_id(transformationDTO.getImageId());
        newTransformation.setWordfilename(transformationDTO.getWordfilename());
        newTransformation.setOpenxmlfilename(transformationDTO.getOpenxmlfilename());
        newTransformation.setIqsxmlfilename(transformationDTO.getIqsxmlfilename());
        newTransformation.setTransformStatus(transformationDTO.getTransformStatus());
        newTransformation.setQanNo(transformationDTO.getQanNo());
        newTransformation.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);

        int newTransformationId = transformationService.addTransformation(newTransformation);
        LOGGER.debug("Added new Transformation[{}]", newTransformation.toString());

        // Get the newTransform id so we can set the transformation result now
        newTransformation = transformationService.getTransformationById(newTransformationId);
        newTransformation = processTransform(newTransformation);

        // Update Transformation Record
        transformationService.updateTransformation(newTransformation);

    }

    /**
     * Process transform, fail on Docx4j exception recording the error message
     * @param newTransformation
     * @return
     */
    public Transformation processTransform(Transformation newTransformation) {
        String transformationStatus = null;
        Image uploadedWordDoc = imageService.getImage(newTransformation.getImage_id());

        // Start TRANSFORM process
        LOGGER.debug("Reading properties and extracting content from Word newFile[{}]", uploadedWordDoc.getNewFilename());
        ProcessWordDocument processWordDocument = null;
        processWordDocument = new ProcessWordDocument(new File(fileUploadDirectory + "/" + uploadedWordDoc.getNewFilename()));

        try {
            processWordDocument.doTransformationWork();
            processWordDocument.setTransformationStatus();
            transformationStatus = processWordDocument.getTransformationStatus();
            // update TRANSFORMATION
            newTransformation.setTransformStatus(transformationStatus);
            newTransformation.setQanNo(processWordDocument.getTransformationUan());
            newTransformation.setUnitNo(processWordDocument.getTransformationUnitNo());
            newTransformation.setUnitTitle(processWordDocument.getTransformationUnitTitle());
            newTransformation.setAuthor(processWordDocument.getTransformationAuthor());
            newTransformation.setMessage(processWordDocument.getTransformationMessage());
            newTransformation.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);

            Specunit specunit = new Specunit();
            specunit.setQanNo(processWordDocument.getTransformationUan());
            specunit.setUnitXML(processWordDocument.getXmlStringContent());
            // update TRANSFORMATION
            newTransformation.setSpecunit(specunit);

        } catch (Docx4JException e) {
            transformationStatus = Transformation.TRANSFORM_STATUS_FAIL;
            LOGGER.error("Error - transformationStatus[{}]. Transform has failed. Detailed Error Message - [{}]", transformationStatus, e.getMessage());
            newTransformation.setMessage(" Transform has failed with transformationStatus[" + transformationStatus
                    + "] and Detailed Error Message - " + e.getMessage() + " \n\r");
            // update TRANSFORMATION
            newTransformation.setTransformStatus(transformationStatus);
            return newTransformation;
        }

        LOGGER.debug("Read Word document transformationStatus[{}], transformationMessage[{}]", transformationStatus, processWordDocument.getTransformationMessage());
        return newTransformation;
    }



    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/transformation")
    public List<TransformationDTO> listTransformations() {
        List<Transformation> transformations = transformationService.listTransformations();
        LOGGER.debug("Found [{}] All Transformations", transformations.size());
        return TransformationDTO.mapFromTransformationsEntities(transformations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/transformation/list")
    public List<TransformationDTO> listRecentTransformations() {
        List<Transformation> transformations = transformationService.listTransformations();
        LOGGER.debug("Found [{}] Recent Transformations", transformations.size());
        return TransformationDTO.mapFromTransformationsEntities(transformations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/transformation/{id}")
    public TransformationDTO getTransformationById(@PathVariable Integer id) {
        Transformation transformation = transformationService.getTransformationById(id);

        LOGGER.debug("Found Transformation id[{}] -> Transformation[{}]", id, transformation.toString());

        if(transformation.getGeneralStatus().equals(Transformation.GENERAL_STATUS_UNREAD)) {
            transformation.setGeneralStatus(Transformation.GENERAL_STATUS_READ);
            transformation.setLastmodified(new Date());
            transformationService.updateTransformation(transformation);
            LOGGER.debug("Setting GENERAL_STATUS_READ for User id[{}] -> Transformation[{}]", id, transformation.toString());
        }
        return TransformationDTO.mapFromTransformationEntity(transformation);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            //produces = MediaType.APPLICATION_XML_VALUE,
            method = RequestMethod.GET,
            value = "/transformation/xml/{id}")
    //public String getTransformationSpecUnitByIdAsXml(@PathVariable Integer id) {
    public HttpEntity<byte[]> getTransformationSpecUnitByIdAsXml(@PathVariable Integer id) {
        Transformation transformation = transformationService.getTransformationById(id);
        Specunit specunit = transformation.getSpecunit();
        LOGGER.debug("Returning XML for User id[{}] -> SpecunitById[{}]", specunit);
        String xml = specunit.getUnitXML().toString();
        byte[] documentBody = xml.getBytes();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "xml"));
        header.setContentLength(documentBody.length);
        return new HttpEntity<byte[]>(documentBody, header);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/transformation/json/{id}")
    public JSON getTransformationSpecUnitByIdAsJson(@PathVariable Integer id) {
        Transformation transformation = transformationService.getTransformationById(id);
        Specunit specunit = transformation.getSpecunit();


        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON objJson = xmlSerializer.read(specunit.getUnitXML());
        //JSONArray json = (JSONArray) objJson;

        LOGGER.debug("Returning JSON for User id[{}] -> SpecunitById[{}]", specunit);
        return objJson;


    }

    @RequestMapping(value = "/transformation/xml/{id}/download", method = RequestMethod.GET)
    public void downloadTransformationSpecUnitByIdAsXMLFile (
            @PathVariable Integer id,
            HttpServletResponse response) {

        Transformation transformation = transformationService.getTransformationById(id);
        Specunit specunit = transformation.getSpecunit();
        LOGGER.debug("Streaming xml content for Transformation id[{}] -> SpecunitById[{}]", id, specunit);
        InputStream inputStream = IOUtils.toInputStream(specunit.getUnitXML());


        try {
            LOGGER.debug("Beginning XML file download of TransformationId[{}] QanNo[{}] -> OpenXmlFileName[{}]",
                    transformation.getId(), transformation.getQanNo(), transformation.getOpenxmlfilename());

            response.setHeader("Content-Disposition", "inline;filename=\"" +transformation.getOpenxmlfilename() + "\"");
            // get output stream of the response
            OutputStream outStream = response.getOutputStream();
            response.setContentType("application/octet-stream");


            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            // write bytes read from the input stream into the output stream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outStream.close();
            LOGGER.debug("Successfully completed XML file download of TransformationId[{}] QanNo[{}] -> OpenXmlFileName[{}]",
                    transformation.getId(), transformation.getQanNo(), transformation.getOpenxmlfilename());
        } catch (IOException e) {
            LOGGER.error("Error with XML file download of TransformationId[{}] QanNo[{}] -> OpenXmlFileName[{}], Exception[{}]",
                    transformation.getId(), transformation.getQanNo(), transformation.getOpenxmlfilename(), e.getStackTrace());
        }
    }






    @RequestMapping(value = "/transformation/pqs/{id}/download", method = RequestMethod.GET)
    public void downloadTransformationSpecUnitByIdAsPQSXMLFile (
            @PathVariable Integer id,
            HttpServletResponse response) {

        Transformation transformation = transformationService.getTransformationById(id);
        Specunit specunit = transformation.getSpecunit();
        LOGGER.debug("Streaming PQS xml content for Transformation id[{}] -> SpecunitById[{}]", id, specunit);

        TransformXmlDocument transformXmlDocument = new TransformXmlDocument(specunit.getUnitXML());
        try {
            LOGGER.debug("Beginning XML file download of TransformationId[{}] QanNo[{}] -> PqsFileName[{}]",
                    transformation.getId(), transformation.getQanNo(), transformation.getIqsxmlfilename());

            String root = context.getRealPath("/");
            String pqsXmlString = transformXmlDocument.doTranformOpenXmlToIqsXml(root);
            InputStream inputStream = IOUtils.toInputStream(pqsXmlString);

            response.setHeader("Content-Disposition", "inline;filename=\"" +transformation.getIqsxmlfilename() + "\"");
            // get output stream of the response
            OutputStream outStream = response.getOutputStream();
            response.setContentType("application/octet-stream");


            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = -1;

            // write bytes read from the input stream into the output stream
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }

            inputStream.close();
            outStream.close();
            LOGGER.debug("Successfully completed XML file download of TransformationId[{}] QanNo[{}] -> PqsFileName[{}]",
                    transformation.getId(), transformation.getQanNo(), transformation.getIqsxmlfilename());
        } catch (IOException e) {
            LOGGER.error("Error with XML file download of TransformationId[{}] QanNo[{}] -> PqsFileName[{}], Exception[{}]",
                    transformation.getId(), transformation.getQanNo(), transformation.getIqsxmlfilename(), e.getStackTrace());
        } catch (TransformerException e) {
            LOGGER.error("Error with XSLT Transform of TransformationId[{}] QanNo[{}] -> PqsFileName[{}], Exception[{}]",
                    transformation.getId(), transformation.getQanNo(), transformation.getIqsxmlfilename(), e.getStackTrace());
        }

    }





    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT, value = "/transformation/{id}")
    public void updateTransformation(@RequestBody TransformationDTO transformationDTO) {

        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.debug("Getting user from Session - Username[{}], Principle[{}]", principal.getName(), principal);

        User user = userService.getUserByUsername(principal.getName());
        LOGGER.debug("Retrieved user[{}]", user);

        //Template template = templateService.getTemplateById(transformationDTO.getTemplateId());


        Transformation transformation = transformationService.getTransformationById(transformationDTO.getId());
        transformation.setDate(transformationDTO.getDate());
        transformation.setQanNo(transformationDTO.getQanNo());
        transformation.setWordfilename(transformationDTO.getWordfilename());
        transformation.setOpenxmlfilename(transformationDTO.getOpenxmlfilename());
        transformation.setIqsxmlfilename(transformationDTO.getIqsxmlfilename());
        transformation.setUnitNo(transformationDTO.getUnitNo());
        transformation.setUnitTitle(transformationDTO.getUnitTitle());
        transformation.setAuthor(transformationDTO.getAuthor());
        transformation.setLastmodified(new Date());
        transformation.setTransformStatus(transformationDTO.getTransformStatus());
        transformation.setMessage(transformationDTO.getMessage());
        transformation.setGeneralStatus(Transformation.GENERAL_STATUS_MODIFIED);
        //transformation.setTemplate(template);
        transformation.setTemplateId(transformationDTO.getTemplateId());

        // Now update the XML from the SpecUnit Table
        //specUnitService.updateSpecUnitMetadata(transformationDTO);
        Specunit result_specunit = transformation.getSpecunit();
        result_specunit.setQanNo(transformationDTO.getQanNo());
        String result_unit_xml = specUnitService.updateSpecUnitXMLNode(result_specunit.getUnitXML(), transformationDTO);
        result_specunit.setUnitXML(result_unit_xml);

        transformation.setSpecunit(result_specunit);

        transformationService.updateTransformation(transformation);
        LOGGER.debug("Updating Transformation[{}]", transformation.toString());
    }


 /*   @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/transformation/update/list")
    public List<TransformationDTO> saveTransformations(Principal principal, @RequestBody List<TransformationDTO> transformations) {
        List<Transformation> savedTransformations = transformationService.updateTransformation(transformations);
        return savedTransformations.stream()
                .map(TransformationDTO::mapFromTransformationEntity)
                .collect(Collectors.toList());
    }*/

    /**
     *
     * deletes a list of transformations
     *
     * @PathVariable id - the ids of the transformations to be deleted
     */
    //@ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/transformation/{id}")
    public void removeTransformations(@PathVariable("id") Integer id) {
        transformationService.removeTransformation(id);
        LOGGER.debug("Successfully deleted Transformation Id[{}]", id);
    }



    /**
     *
     * error handler for backend errors - a 400 status code will be sent back, and the body
     * of the message contains the exception text.
     *
     * @param exc - the exception caught
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }


    /**
     *
     * deletes a list of transformations
     *
     * @param ids - the ids of the transformations to be deleted
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/transformation/delete/list")
    public void removeTransformations(@RequestBody List<Integer> ids) {
        transformationService.removeTransformation(ids);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/transformation/page")
    public TransformationsDTO listPaginatedTransformations(
            @RequestParam(value = "pageNumber") Integer pageNumber) {

        SearchResult<Transformation> result = transformationService.listTransformations(pageNumber);
        Long resultsCount = result.getResultsCount();
        Long totalPages = resultsCount / 10;

        if (resultsCount % 10 > 0) {
            totalPages++;
        }
        LOGGER.debug("Found {} Transformations", resultsCount);
        return new TransformationsDTO(pageNumber, totalPages, TransformationDTO.mapFromTransformationsEntities(result.getResult()));
    }


}
