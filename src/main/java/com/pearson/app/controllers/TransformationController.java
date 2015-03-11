package com.pearson.app.controllers;

import com.pearson.app.dto.TransformationDTO;
import com.pearson.app.dto.TransformationsDTO;
import com.pearson.app.model.SearchResult;
import com.pearson.app.model.Specunit;
import com.pearson.app.model.Transformation;
import com.pearson.app.model.User;
import com.pearson.app.services.TransformationService;
import com.pearson.app.services.UserService;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import net.sf.json.JSONArray;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

    @Autowired
    private TransformationService transformationService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/transformation")
    public void addTransformation(@RequestBody Transformation transformation) {
        transformationService.addTransformation(transformation);
        LOGGER.debug("Add new Transformation[{}]", transformation.toString());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "transformation/list")
    public List<TransformationDTO> listTransformations() {
        List<Transformation> transformations = transformationService.listTransformations();
        LOGGER.debug("Found [{}] All Transformations", transformations.size());
        return TransformationDTO.mapFromTransformationsEntities(transformations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "transformation/listrecent")
    public List<TransformationDTO> listRecentTransformations() {
        List<Transformation> transformations = transformationService.listTransformations();
        LOGGER.debug("Found [{}] Recent Transformations", transformations.size());
        return TransformationDTO.mapFromTransformationsEntities(transformations);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/transformation/{id}")
    public TransformationDTO getTransformationById(@PathVariable Long id) {
        Transformation transformation = transformationService.getTransformationById(id);
        LOGGER.debug("Found User id[{}] -> Transformation[{}]", id, transformation.toString());

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
    @RequestMapping(method = RequestMethod.GET, value = "/transformation/xml/{id}")
    public String getTransformationSpecUnitByIdAsXml(@PathVariable Long id) {
        Transformation transformation = transformationService.getTransformationById(id);
        Specunit specunit = transformation.getSpecunit();
        LOGGER.debug("Returning XML for User id[{}] -> SpecunitById[{}]", specunit);
        return specunit.getUnitXML().toString();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/transformation/json/{id}")
    public JSON getTransformationSpecUnitByIdAsJson(@PathVariable Long id) {
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
            @PathVariable Long id,
            HttpServletResponse response) {

        Transformation transformation = transformationService.getTransformationById(id);
        Specunit specunit = transformation.getSpecunit();
        LOGGER.debug("Streaming xml content for Transformation id[{}] -> SpecunitById[{}]", id, specunit);
        InputStream inputStream = IOUtils.toInputStream(specunit.getUnitXML());


        try {
            LOGGER.debug("Beginning XML file download of TransformationId[{}] QanNo[{}] -> PqsFileName[{}]",
                    transformation.getId(), transformation.getQanNo(), transformation.getIqsxmlfilename());

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
        }

    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/transformation/update")
    public void updateTransformation(@RequestBody TransformationDTO transformationDTO) {

        User tempuser = new UserService().getUserById(1L);
        Transformation transformation = transformationService.getTransformationById(transformationDTO.getId());


        Transformation newTransformation = new Transformation(
                tempuser,
                transformationDTO.getDate(),
                transformationDTO.getQanNo(),
                transformationDTO.getWordfilename(),
                transformation.getSpecunit(),
                transformationDTO.getIqsxmlfilename(),
                transformationDTO.getUnitNo(),
                transformationDTO.getUnitTitle(),
                transformationDTO.getAuthor(),
                transformationDTO.getTemplatename(),
                transformationDTO.getLastmodified(),
                transformationDTO.getTransformStatus(),
                transformationDTO.getMessage(),
                Transformation.GENERAL_STATUS_MODIFIED
                );
        transformationService.updateTransformation(newTransformation);
        LOGGER.debug("Update Transformation[{}]", newTransformation.toString());
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
     * @param id - the ids of the transformations to be deleted
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/transformation/remove/{id}")
    public void deleteTransformations(@RequestBody Long id) {
        transformationService.removeTransformation(id);
        LOGGER.debug("Remove Transformation Id[{}]", id);
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
    public void deleteTransformations(@RequestBody List<Long> ids) {
        transformationService.removeTransformation(ids);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/transformation")
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
