package com.pearson.btec.service;

import com.pearson.app.model.Transformation;
import com.pearson.btec.model.Unit;
import com.pearson.btec.service.importers.DocumentUtils;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dawud on 02/03/2015.
 */
public class ProcessWordDocument {

    @Value("${file.upload.directory}")
    private String fileUploadDirectory;
    private File wordFile;
    private Unit unit = null;

    // Statuses
    private String transformationStatus = null;
    private String transformationMessage = null;
    private String transformationUan = "";
    private String transformationUnitNo = null;
    private String transformationUnitTitle = null;
    private String transformationAuthor = "";
    private String xmlStringContent = null;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessWordDocument.class);

    public ProcessWordDocument(File newFilename) {
        this.wordFile = newFilename;
        LOGGER.debug("Initialising Docx4j extraction with filename[{}]", this.wordFile.getAbsolutePath());
    }


    public void doTransformationWork() throws Docx4JException{
        LOGGER.debug("Begin Docx4j extraction with filename[{}]", this.wordFile.getAbsolutePath());

            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(this.wordFile);

            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart.getJaxbElement();

            Body body = wmlDocumentEl.getBody();
            DocumentUtils documentUtils = new DocumentUtils();

            // Word Document Properties
            org.docx4j.openpackaging.parts.DocPropsCorePart docPropsCorePart = wordMLPackage.getDocPropsCorePart();
            org.docx4j.docProps.core.CoreProperties coreProps = (org.docx4j.docProps.core.CoreProperties)docPropsCorePart.getJaxbElement();
            HashMap docPropMap = new HashMap();

            String title = "Missing";
            if(coreProps != null &&
                    coreProps.getTitle() != null &&
                    coreProps.getTitle().getValue() != null &&
                    coreProps.getTitle().getValue().getContent() != null) {
                List<String> list = coreProps.getTitle().getValue().getContent();
                if (list.size() > 0) {
                    title = list.get(0);
                }
            }
            docPropMap.put("dc-title", title);
            if(coreProps != null &&
                    coreProps.getKeywords() != null) {
                this.transformationUan = coreProps.getKeywords();
            }
            LOGGER.debug("Found following property - transformationUan[{}]", this.transformationUan);

            docPropMap.put("uan", transformationUnitNo);
            docPropMap.put("dc-keyword", transformationUan);
            if(coreProps != null &&
                    coreProps.getCreator() != null &&
                    coreProps.getCreator().getContent() != null) {
                        List<String> authorList = coreProps.getCreator().getContent();
                        if (authorList.size() > 0) {
                            this.transformationAuthor = authorList.get(0);
                            LOGGER.debug("Found following property - author[{}]", this.transformationAuthor);

                        }
            }
            docPropMap.put("author", transformationAuthor);
            String description = "Missing";
            if(coreProps.getDescription() != null &&
                    coreProps.getDescription().getValue() != null &&
                    coreProps.getDescription().getValue().getContent() != null) {
                List<String> descriptionList = coreProps.getDescription().getValue().getContent();
                if (descriptionList.size() > 0) {
                    description = descriptionList.get(0);
                }
            }
            docPropMap.put("dc-description", description);

            // Let's look at the extended properties
            org.docx4j.openpackaging.parts.DocPropsExtendedPart docPropsExtendedPart = wordMLPackage.getDocPropsExtendedPart();
            org.docx4j.docProps.extended.Properties extendedProps = (org.docx4j.docProps.extended.Properties)docPropsExtendedPart.getJaxbElement();

            // What application was the document created with?
            docPropMap.put("dc-application", extendedProps.getApplication());

            docPropMap.put("dc-version", extendedProps.getAppVersion());
            docPropMap.put("dc-company", extendedProps.getCompany());


            // Finally, the custom properties
            org.docx4j.openpackaging.parts.DocPropsCustomPart docPropsCustomPart = wordMLPackage.getDocPropsCustomPart();
            if(docPropsCustomPart==null){
                //System.out.println("No DocPropsCustomPart");
                LOGGER.error("Missing document properties - No DocPropsCustomPart");
            } else {
                org.docx4j.docProps.custom.Properties customProps = (org.docx4j.docProps.custom.Properties)docPropsCustomPart.getJaxbElement();

                for (org.docx4j.docProps.custom.Properties.Property prop: customProps.getProperty() ) {

                    // At the moment, you need to know what sort of value it has.
                    // Could create a generic Object getValue() method.
                    if (prop.getLpwstr()!=null) {
                        //System.out.println(prop.getName() + " = " + prop.getLpwstr());
                        docPropMap.put(prop.getName(), prop.getLpwstr());
                    } else {
                        //System.out.println(prop.getName() + ": \n " + XmlUtils.marshaltoString(prop, true, Context.jcDocPropsCustom));
                        docPropMap.put(prop.getName(), XmlUtils.marshaltoString(prop, true, Context.jcDocPropsCustom));
                    }
                }
            }

            this.unit = documentUtils.traverseDocumentBody(body);

            List<HashMap<String, String>> hashMapList = this.unit.getUnitHeader().getProperties();
            for(HashMap<String, String> hashMap : hashMapList ) {
                if(hashMap.get("tag").equals("unitnumber")) {
                    this.transformationUnitNo = hashMap.get("value");
                    LOGGER.debug("Found following property - transformationUnitNo[{}]", this.transformationUnitNo);

                }
                if(hashMap.get("tag").equals("unittitle")) {
                    this.transformationUnitTitle = hashMap.get("value");
                    LOGGER.debug("Found following property - transformationUnitTitle[{}]", this.transformationUnitTitle);

                }
                if(hashMap.get("tag").equals("author")) {
                    this.transformationAuthor = hashMap.get("value");
                    LOGGER.debug("Found following property - transformationAuthor[{}]", this.transformationAuthor);

                }
            }
    }


    public void setTransformationStatus() {

        xmlStringContent = getXMLDocumentAsString();

        if(transformationUan == null || (transformationUan != null && transformationUan.isEmpty())) {
            transformationMessage += "Missing UAN number in Word Document properties\r\n";
        }

        if (transformationAuthor == null || (transformationAuthor != null && transformationAuthor.isEmpty())) {
            transformationMessage += "Missing Author in Word Document properties\r\n";
        }

        if (transformationUnitNo == null || (transformationUnitNo != null && transformationUnitNo.isEmpty())) {
            transformationMessage += "Missing Unit Number in Word Document properties\r\n";
        }

        if (transformationUnitTitle == null || (transformationUnitTitle != null && transformationUnitTitle.isEmpty())) {
            transformationMessage += "Missing Unit Title in Word Document properties\r\n";
        }

        if(xmlStringContent == null) {
            transformationStatus = Transformation.TRANSFORM_STATUS_FAIL_EXTRACT_WORD_TO_XML;
        }
        else if(transformationUan == null
                && transformationUnitNo == null
                && transformationUnitTitle == null
                && transformationAuthor == null) {
            transformationStatus = Transformation.TRANSFORM_STATUS_FAIL_VALIDATE_WORD;
        }
        else {
            transformationStatus = Transformation.TRANSFORM_STATUS_SUCCESS;
            if(transformationMessage == null) {
                Date now = Calendar.getInstance().getTime();
                transformationMessage = "Last transformation successfully completed on " + now;
            }
        }
    }


    public String getXMLDocumentAsString() {
        MainDocument mainDocument = new MainDocument(this.wordFile);

        // create the jdom
        Document jdomDoc = new Document();
        // create root element
        // add Btec Unit model
        mainDocument.convertMainDocument();
        String documentXMLString = mainDocument.exportUnitAsXMLString();
        //LOGGER.debug("Found XML Document as String [{}]", truncate(documentXMLString,100));
        return documentXMLString;
    }

    public String getTransformationStatus() {
        return transformationStatus;
    }

    public void setTransformationStatus(String transformationStatus) {
        this.transformationStatus = transformationStatus;
    }

    public String getTransformationMessage() {
        return transformationMessage;
    }

    public void setTransformationMessage(String transformationMessage) {
        this.transformationMessage = transformationMessage;
    }

    public String getTransformationUan() {
        return transformationUan;
    }

    public void setTransformationUan(String transformationUan) {
        this.transformationUan = transformationUan;
    }

    public String getTransformationUnitNo() {
        return transformationUnitNo;
    }

    public void setTransformationUnitNo(String transformationUnitNo) {
        this.transformationUnitNo = transformationUnitNo;
    }

    public String getTransformationUnitTitle() {
        return transformationUnitTitle;
    }

    public void setTransformationUnitTitle(String transformationUnitTitle) {
        this.transformationUnitTitle = transformationUnitTitle;
    }

    public String getTransformationAuthor() {
        return transformationAuthor;
    }

    public void setTransformationAuthor(String transformationAuthor) {
        this.transformationAuthor = transformationAuthor;
    }

    public String getXmlStringContent() {
        return xmlStringContent;
    }

    public void setXmlStringContent(String xmlStringContent) {
        this.xmlStringContent = xmlStringContent;
    }

    public static String truncate(String value, int length) {
        // Ensure String length is longer than requested size.
        if (value.length() > length) {
            return value.substring(0, length) + "\r\n ..... \r\n" + value.substring(value.length()- length,value.length()-1);
        } else {
            return value;
        }
    }

    public static void main(String[] args) {
        //String inputfilepath = System.getProperty("user.dir") + "/btecdocs/Unit_18short.docx";
        String inputfilepath = System.getProperty("user.dir") + "/src/test/resources/Unit 44_FBC.docx";
        //String inputfilepath = System.getProperty("user.dir") + "/src/test/resources/3DModelling.docx";
        //String inputfilepath = System.getProperty("user.dir") + "/btecdocs/Unit_18.docx";

/*
        MainDocument mainDocument = new MainDocument(new File(inputfilepath));
        mainDocument.convertMainDocument();
        String unitXMLString = mainDocument.exportUnitAsXMLString();
        System.out.println(unitXMLString);
*/
        //p.getXMLDocumentAsString();
            ProcessWordDocument p = new ProcessWordDocument(new File(inputfilepath));
            System.out.println(p.getTransformationUan());
            System.out.println(p.getTransformationUnitNo());
            System.out.println(p.getTransformationUnitTitle());
            System.out.println(p.getTransformationAuthor());
            System.out.println(truncate(p.getXMLDocumentAsString(),1000));




    }
}
