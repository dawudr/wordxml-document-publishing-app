package com.pearson.btec.controller;

/**
 * Created by Dawud on 15/09/14.
 */
import com.pearson.btec.form.BrowseFolderForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.xml.sax.InputSource;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

@Controller
@RequestMapping("/transform")
public class TransformerController {

    private final Logger logger = LoggerFactory.getLogger(TransformerController.class);

    @Autowired
    ServletContext context;

    @RequestMapping(method = RequestMethod.POST)
    ModelAndView transform(@RequestParam("action") String action, @RequestParam("filename") String filename, HttpServletRequest request, HttpServletResponse response)  throws Exception {
        logger.debug("In transform POST Request Params: action[{}], filename[{}]", action, filename);

        tranformOpenXmlToIqsXml(filename);

        ModelAndView modelAndView = new ModelAndView("transformsuccess");
        modelAndView.addObject("filename", filename);
        return modelAndView;
    }


    private void tranformOpenXmlToIqsXml(String xmlfile) throws TransformerException, IOException {
        logger.debug("In tranformOpenXmlToIqsXml Params: xmlfile[{}]", xmlfile);

        //TransformerFactory tFactory = TransformerFactory.newInstance();
        // http://stackoverflow.com/questions/11314604/how-to-set-saxon-as-the-xslt-processor-in-java
        // XSLT 2.0 not supported by Java default XLSTC so use Saxon implementation
        TransformerFactory tFactory = new net.sf.saxon.TransformerFactoryImpl();
        String xsltPath = context.getRealPath("/WEB-INF/xsl/iqs/wordxml_unit.xsl");

        logger.debug("Transforming xmlFile[{}] with xsl[{}] and output to [{}]", xmlfile, xsltPath, context.getRealPath("/") + File.separator + "uploads" + File.separator + xmlfile.replace(".xml", BrowseFolderForm.FILE_EXT_IQS_XML));

        StreamSource streamSourceXsltfile = new StreamSource(new File(xsltPath));

        Transformer transformer = tFactory.newTransformer(streamSourceXsltfile);
        //transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        FileInputStream fileInputStreamXmlfile = new FileInputStream(getDocumentFile(xmlfile));
        Reader reader = new InputStreamReader(fileInputStreamXmlfile, "UTF-8");
        Source source = new StreamSource(reader);

        FileWriter fileWriter = new FileWriter(context.getRealPath("/") + File.separator + "uploads" + File.separator + xmlfile.replace(".xml", BrowseFolderForm.FILE_EXT_IQS_XML));
        StreamResult streamResult = new StreamResult(fileWriter);
        transformer.transform(source, streamResult);
        transformer.reset();

        //finally close file handler
        fileWriter.flush();
        fileWriter.close();
        reader.close();
        fileInputStreamXmlfile.close();
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
                    logger.debug("File found: [{}]", listFile.getName());
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
