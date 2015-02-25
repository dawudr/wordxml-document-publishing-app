package com.pearson.app.controllers;

/**
 * Created by Dawud on 15/09/14.
 */
import com.pearson.app.form.BrowseFolderForm;
import com.pearson.btec.service.MainDocument;
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

@Controller
@RequestMapping("/convert")
public class ConvertController {

    private final Logger logger = LoggerFactory.getLogger(ConvertController.class);

    @Autowired
    ServletContext context;

    @RequestMapping(method = RequestMethod.POST)
    ModelAndView convertfile(@RequestParam("filename") String filename, HttpServletRequest request, HttpServletResponse response)  throws Exception {
        logger.debug("In ConvertController POST Request");

        MainDocument mainDocument = new MainDocument(getDocumentFile(filename));

        // create the jdom
        Document jdomDoc = new Document();
        // create root element
        // add Btec Unit model
        mainDocument.convertMainDocument();
        Element rootElement = mainDocument.exportUnitAsXMLDOM();
        jdomDoc.setRootElement(rootElement);

        // Output as XML
        // create XMLOutputter
        XMLOutputter xml = new XMLOutputter();
        // we want to format the xml. This is used only for demonstration. pretty formatting adds extra spaces and is generally not required.
        xml.setFormat(Format.getPrettyFormat());

        // Search for other convert XML files
        // Open XMLl
        String openxmlFilename = filename.replace(".docx", BrowseFolderForm.FILE_EXT_OPEN_XML)
                .replace(".docm", BrowseFolderForm.FILE_EXT_OPEN_XML)
                .replace(".doc", BrowseFolderForm.FILE_EXT_OPEN_XML);

        FileWriter fileWriter = new FileWriter(context.getRealPath("/")+ File.separator + "uploads" + File.separator + openxmlFilename);

        xml.output(jdomDoc, fileWriter);

        fileWriter.flush();
        fileWriter.close();

        ModelAndView modelAndView = new ModelAndView("convertsuccess");
        modelAndView.addObject("filename", openxmlFilename);
        return modelAndView;
    }


    // construct the complete absolute path of the file
    private File getDocumentFile(String filename) {
        logger.debug("In getDownloadFile Params: filename[{}]", filename);
        // get absolute path of the application
        String root = context.getRealPath("/");
        File folder = new File(root + File.separator + "uploads");
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



