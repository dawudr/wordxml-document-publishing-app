package com.pearson.btec.controller;

/**
 * Created by Dawud on 15/09/14.
 */
import com.pearson.btec.form.BrowseFolderForm;
import com.pearson.btec.model.BtecUnit;
import com.pearson.btec.service.BtecUnitXPathXQuery;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/convert")
public class ConvertController {

    private final Logger logger = LoggerFactory.getLogger(ConvertController.class);

    @Autowired
    ServletContext context;

    @RequestMapping(method = RequestMethod.POST)
    ModelAndView convertfile(@RequestParam("filename") String filename, HttpServletRequest request, HttpServletResponse response)  throws Exception {
        logger.debug("In ConvertController POST Request");

        BtecUnitXPathXQuery btecConvert = new BtecUnitXPathXQuery();
        BtecUnit btecUnit = btecConvert.getWholeDocument(getDocumentFile(filename));

        // create the jdom
        Document jdomDoc = new Document();
        // create root element
        // add Btec Unit model
        Element rootElement = btecUnit.toXML();
        jdomDoc.setRootElement(rootElement);

        // Output as XML
        // create XMLOutputter
        XMLOutputter xml = new XMLOutputter();
        // we want to format the xml. This is used only for demonstration. pretty formatting adds extra spaces and is generally not required.
        xml.setFormat(Format.getPrettyFormat());

        // Search for other convert XML files
        // Open XML
        String openxmlFilename = filename.replace(".docx", BrowseFolderForm.FILE_EXT_OPEN_XML)
                .replace(".docm", BrowseFolderForm.FILE_EXT_OPEN_XML)
                .replace(".doc", BrowseFolderForm.FILE_EXT_OPEN_XML);

        xml.output(jdomDoc, new FileWriter(context.getRealPath("/")+"/uploads/" + openxmlFilename));



        ModelAndView modelAndView = new ModelAndView("convertsuccess");
        modelAndView.addObject("filename", openxmlFilename);
        return modelAndView;
    }


    // construct the complete absolute path of the file
    private File getDocumentFile(String filename) {
        logger.debug("In getDownloadFile Params: filename[{}]", filename);
        // get absolute path of the application
        String root = context.getRealPath("/");
        File folder = new File(root + "/uploads");
        if (folder.exists()) {
            File[] listFiles = folder.listFiles();
            for (File listFile : listFiles) {
                if (listFile.getName().equals(filename)) {
                    logger.debug("Download File found: [{}]", listFile.getName());
                    return listFile;
                }
            }
        } else {
            logger.error("File upload folder path not found [{}]", folder.getName());
        }
        logger.error("Download File not found: [{}]", filename);
        return null;
    }
}



