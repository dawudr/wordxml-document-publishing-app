package com.pearson.app.controllers;

import com.pearson.app.form.BrowseFolderForm;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/browsefolder")
public class BrowseFolderController {

    private final Logger logger = LoggerFactory.getLogger(BrowseFolderController.class);

    @Autowired
    ServletContext context;

    @RequestMapping(method = RequestMethod.GET)
    ModelAndView browsefolder(HttpServletRequest request, HttpServletResponse response)  throws Exception {
        logger.debug("In BrowseFolderController GET Request");

        BrowseFolderForm browseFolderForm = new BrowseFolderForm();

        String root = context.getRealPath("/");
        File path = new File(root + File.separator + "uploads");
        if (path.exists()) {
            logger.error("File upload path found [{}]", path.getName());

            File[] listFiles = path.listFiles();
            ArrayList<String> arrayListFiles = new ArrayList(Arrays.asList(path.list()));

            // Finally add to formdata
            browseFolderForm.setBrowseList(renderbrowselist(arrayListFiles));
        }

        ModelAndView modelAndView = new ModelAndView("/browsefolder");
        modelAndView.addObject("browse", browseFolderForm.getBrowseList());
        return modelAndView;
    }


    @RequestMapping(method = RequestMethod.POST)
    ModelAndView browseaction(@RequestParam("action") String action, @RequestParam("filename") String filename, HttpServletRequest request, HttpServletResponse response)  throws Exception {
        logger.debug("In BrowseFolderController POST Request Params: action[{}], filename[{}]", action, filename);

        if(action.equals("delete")) {
            browseactiondelete(filename);
        }

        ModelAndView modelAndView = new ModelAndView("redirect:/browsefolder");
//        modelAndView.addObject("message", "Action process: " + action);
        return modelAndView;
    }






    private List renderbrowselist(ArrayList<String> arrayListFiles) {
        List fileNamesRows = new ArrayList<String>();

        for (String listFilename : arrayListFiles) {

            if(listFilename.endsWith(".docx") || listFilename.endsWith(".docm") ||
                    listFilename.endsWith(".doc")) {

                // Put Word and its convert XML files in a Map
                HashMap rowFileMap = new HashMap();
                rowFileMap.put(BrowseFolderForm.KEY_WORD_DOC, listFilename);

                // Search for other convert XML files
                // Open XML
                String openxmlFilename = listFilename.replace(".docx", BrowseFolderForm.FILE_EXT_OPEN_XML)
                        .replace(".docm", BrowseFolderForm.FILE_EXT_OPEN_XML)
                        .replace(".doc", BrowseFolderForm.FILE_EXT_OPEN_XML);
                if(arrayListFiles.contains(openxmlFilename)) {
                    rowFileMap.put(BrowseFolderForm.KEY_OPEN_XML, openxmlFilename);
                }

                // IQS XML
                String iqsxmlFilename =  listFilename.replace(".docx", BrowseFolderForm.FILE_EXT_IQS_XML)
                        .replace(".docm", BrowseFolderForm.FILE_EXT_IQS_XML)
                        .replace(".doc", BrowseFolderForm.FILE_EXT_IQS_XML);
                if(arrayListFiles.contains(iqsxmlFilename)) {
                    rowFileMap.put(BrowseFolderForm.KEY_IQS_XML, iqsxmlFilename);
                }
                fileNamesRows.add(rowFileMap);
            }
        }
        return fileNamesRows;
    }



     private boolean browseactiondelete(String filename) {
        logger.debug("In browseactiondelete Params: filename[{}]", filename);

        // Open XML
        String openxmlFilename = filename.replace(".docx", BrowseFolderForm.FILE_EXT_OPEN_XML)
                .replace(".docm", BrowseFolderForm.FILE_EXT_OPEN_XML)
                .replace(".doc", BrowseFolderForm.FILE_EXT_OPEN_XML);

        // IQS XML
        String iqsxmlFilename =  filename.replace(".docx", BrowseFolderForm.FILE_EXT_IQS_XML)
                .replace(".docm", BrowseFolderForm.FILE_EXT_IQS_XML)
                .replace(".doc", BrowseFolderForm.FILE_EXT_IQS_XML);

        String root = context.getRealPath("/");
        File folder = new File(root + File.separator + "uploads");
        if (folder.exists()) {
            File[] listFiles = folder.listFiles();
            for (File listFile : listFiles) {

                if (listFile.getName().equals(filename) || listFile.getName().equals(openxmlFilename) || listFile.getName().equals(iqsxmlFilename)) {
                    if (!listFile.delete()) {
                        logger.error("Unable to Delete File: [{}]", listFile.getName());
                        return false;
                    } else {
                        logger.debug("Deleted File: [{}]", listFile.getName());
                    }
                }
            }
        } else {
            logger.error("File upload folder path not found [{}]", folder.getName());
            return false;
        }
        return true;
    }



}