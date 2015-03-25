package com.pearson.app.controllers;

import com.pearson.app.config.PropertyPlaceholderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;

@Controller
@RequestMapping(value = "/filedownload")
@Import(PropertyPlaceholderConfig.class)
public class FileDownloadController {

    private final Logger logger = LoggerFactory.getLogger(FileDownloadController.class);
    private static final int BUFFER_SIZE = 4096;

    @Value("${file.upload.directory}")
    private String fileUploadDirectory;

    @Autowired
    ServletContext context;

	@RequestMapping(method = RequestMethod.GET)
	private void doDownload(@RequestParam("action") String action, @RequestParam("filename") String filename, HttpServletRequest request, HttpServletResponse response)
			   throws IOException, URISyntaxException {
        logger.debug("In FileDownloadController GET Request Params: Params: action[{}], filename[{}]", action, filename);

        File downloadFile = getDownloadFile(filename);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = context.getMimeType(downloadFile.getAbsolutePath());
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        // set content attributes for the response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                downloadFile.getName());
        response.setHeader(headerKey, headerValue);

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();

    }

    // construct the complete absolute path of the file
    private File getDownloadFile(String filename) {
        logger.debug("In getDownloadFile Params: filename[{}]", filename);
        // get absolute path of the application
        String root = fileUploadDirectory; //context.getRealPath("/");
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
