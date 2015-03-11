package com.pearson.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mvc.extensions.ajax.AjaxUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

    private final Logger logger = LoggerFactory.getLogger(FileUploadController.class);

    @Autowired
    ServletContext context;

    @ModelAttribute
    public void ajaxAttribute(WebRequest request, Model model) {
        model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
    }

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView uploadform(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        logger.debug("In FileUploadController GET Request");
        ModelAndView modelAndView = new ModelAndView("/fileupload");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    ModelAndView upload(MultipartHttpServletRequest request, HttpServletResponse response)  throws Exception {
        logger.debug("In FileUploadController POST Request");

        Iterator<String> itr = request.getFileNames();
        MultipartFile mpf;
        List<String> list = new LinkedList<>();

        while (itr.hasNext()) {
            mpf = request.getFile(itr.next());
            logger.debug("Uploading {}", mpf.getOriginalFilename());

            String newFilename = mpf.getOriginalFilename().replaceAll("[^a-zA-Z0-9.-]", "_"); //UUID.randomUUID().toString();
            String root = context.getRealPath("/");
            File path = new File(root + File.separator + "uploads");
            if (!path.exists()) {
                boolean status = path.mkdirs();
            }
            File newFile = new File(path + "/" + newFilename);
            try {
                mpf.transferTo(newFile);
                logger.debug("File Path:-" + newFile.getAbsolutePath());
                list.add(newFilename);

            } catch (IOException e) {
                logger.error("Exception:- Could not upload file " + mpf.getOriginalFilename(), e);
            }
        }

        ModelAndView modelAndView = new ModelAndView("/fileuploadsuccess");
        modelAndView.addObject("files", list);
        return modelAndView;

    }
}