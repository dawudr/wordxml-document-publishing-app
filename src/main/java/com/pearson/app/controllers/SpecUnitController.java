package com.pearson.app.controllers;

import com.pearson.app.model.Specunit;
import com.pearson.app.services.SpecUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 *  REST service for SpecUnit - allows to update, create and search for specUnit for the currently logged in user.
 *
 */
@Controller
public class SpecUnitController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecUnitController.class);

    @Autowired
    private SpecUnitService specUnitService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/specunit")
    public void addSpecUnit(@RequestBody Specunit specunit) {
        specUnitService.addSpecUnit(specunit);
        LOGGER.debug("Add new SpecUnit[{}]", specunit.toString());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/specunit/list")
    public List<Specunit> listTransformations() {
        List<Specunit> specunits = specUnitService.listSpecUnits();
        LOGGER.debug("Found [{}] All SpecUnits", specunits.size());
        return specunits;
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/specunit/{id}")
    public Specunit getSpecUnitById(@PathVariable Long id) {
        Specunit specunit = specUnitService.getSpecUnitById(id);
        LOGGER.debug("Found SpecUnit id[{}] -> SpecUnit[{}]", id, specunit.toString());
        return specunit;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/specunit/qan/{qanNo}")
    public Specunit getSpecUnitByQan(@PathVariable String qanNo) {
        Specunit specunit = specUnitService.getSpecUnitByQanNo(qanNo);
        LOGGER.debug("Found SpecUnit Qan[{}] -> SpecUnit[{}]", qanNo, specunit);
        return specunit;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/specunit/update")
    public void updateSpecUnit(@RequestBody Specunit specunit) {
        specUnitService.updateSpecUnit(specunit);
        LOGGER.debug("Update Transformation[{}]", specunit.toString());
    }


    /**
     *
     * deletes a list of specUnits
     *
     * @param id - the ids of the specUnits to be deleted
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/specunit/remove/{id}")
    public void deleteSpecUnits(@RequestBody Long id) {
        specUnitService.removeSpecUnit(id);
        LOGGER.debug("Remove SpecUnit Id[{}]", id);
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


}
