package com.pearson.app.controllers;

import com.pearson.app.model.Template;
import com.pearson.app.services.TemplateService;
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
 *  REST service for Template - allows to update, create and search for template for the currently logged in user.
 *
 */
@Controller
public class TemplateController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private TemplateService templateService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, value = "/template")
    public void addTemplate(@RequestBody Template template) {
        templateService.addTemplate(template);
        LOGGER.debug("Add new Template[{}]", template.toString());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/template")
    public List<Template> listTransformations() {
        List<Template> templates = templateService.listTemplates();
        LOGGER.debug("Found [{}] All Templates", templates.size());
        return templates;
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/template/{id}")
    public Template getTemplateById(@PathVariable int id) {
        Template template = templateService.getTemplateById(id);
        LOGGER.debug("Found Template id[{}] -> Template[{}]", id, template.toString());
        return template;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/template/name/{name}")
    public Template getTemplateByName(@PathVariable String name) {
        Template template = templateService.getTemplateByName(name);
        LOGGER.debug("Found Template Name[{}] -> Template[{}]", name, template.toString());
        return template;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.PUT, value = "/template/{id}")
    public void updateTemplate(@RequestBody Template template) {
        templateService.updateTemplate(template);
        LOGGER.debug("Update Template[{}]", template.toString());
    }


    /**
     *
     * deletes a list of templates
     *
     * @param id - the ids of the templates to be deleted
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.DELETE, value = "/template/{id}")
    public void deleteTemplates(@PathVariable Long id) {
        templateService.removeTemplate(id);
        LOGGER.debug("Remove Template Id[{}]", id);
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
