package com.pearson.app.controllers;

import com.pearson.app.dto.TransformationDTO;
import com.pearson.app.dto.TransformationsDTO;
import com.pearson.app.dto.UserInfoDTO;
import com.pearson.app.model.Transformation;
import com.pearson.app.model.SearchResult;
import com.pearson.app.model.User;
import com.pearson.app.services.TransformationService;
import com.pearson.app.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *  REST service for transformation - allows to update, create and search for transformation for the currently logged in user.
 *
 */
@Controller
public class TransformationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationController.class);

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
        LOGGER.debug("Found {} Transformations", transformations.size());
        return TransformationDTO.mapFromTransformationsEntities(transformations);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/transformation/{id}")
    public TransformationDTO getTransformationById(@PathVariable Long id) {
        Transformation transformation = transformationService.getTransformationById(id);
        LOGGER.debug("Found User id[{}] -> Transformation[{}]", id, transformation.toString());
        return TransformationDTO.mapFromTransformationEntity(transformation);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/transformation/update")
    public void updateTransformation(@RequestBody TransformationDTO transformationDTO) {

        User tempuser = new UserService().getUserById(1L);

        Transformation transformation = new Transformation(
                tempuser,
                transformationDTO.getDate(),
                transformationDTO.getQanNo(),
                transformationDTO.getWordfilename(),
                transformationDTO.getOpenxmlfilename(),
                transformationDTO.getIqsxmlfilename(),
                transformationDTO.getUnitNo(),
                transformationDTO.getUnitTitle(),
                transformationDTO.getAuthor(),
                transformationDTO.getTemplatename(),
                transformationDTO.getLastmodified(),
                transformationDTO.getTransformStatus(),
                transformationDTO.getMessage(),
                transformationDTO.getGeneralStatus()
                );
        transformationService.updateTransformation(transformation);
        LOGGER.debug("Update Transformation[{}]", transformation.toString());
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
