package com.pearson.btec.controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
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
import org.springframework.web.servlet.ModelAndView;

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
        ModelAndView modelAndView = new ModelAndView("fileupload");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    ModelAndView upload(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        logger.debug("In FileUploadController POST Request");

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        List fileNames = new ArrayList();
        if (isMultipart) {
            // Create a factory for disk-based file items
            FileItemFactory factory = new DiskFileItemFactory();

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            try {
                // Parse the request
                List items = upload.parseRequest(request);
                Iterator iterator = items.iterator();
                while (iterator.hasNext()) {
                    FileItem item = (FileItem) iterator.next();
                    if (!item.isFormField() && !item.getName().equals("")) {
                        String fileName = item.getName();
                        String root = context.getRealPath("/");
                        File path = new File(root + "/uploads");
                        if (!path.exists()) {
                            boolean status = path.mkdirs();
                        }

                        File uploadedFile = new File(path + "/" + fileName);
                        fileNames.add(fileName);
                        logger.debug("File Path:-"
                                + uploadedFile.getAbsolutePath());

                        item.write(uploadedFile);
                    }
                }
            } catch (FileUploadException e) {
                logger.error("FileUploadException:- " + e.getMessage());
            } catch (Exception e) {
                logger.error("Exception:- " + e.getMessage());
            }
        }

        ModelAndView modelAndView = new ModelAndView("fileuploadsuccess");
        modelAndView.addObject("files", fileNames);
        return modelAndView;

    }
}