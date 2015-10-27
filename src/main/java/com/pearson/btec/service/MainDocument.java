package com.pearson.btec.service;

import com.pearson.btec.model.Unit;
import com.pearson.btec.service.exporters.Unit2xml;
import com.pearson.btec.service.importers.DocumentUtils;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.jdom2.Element;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;


/**
 * MainDocument
 */
public class MainDocument {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MainDocument.class);

    public static void main(String[] args) {
        //String inputfilepath = System.getProperty("user.dir") + "/btecdocs/Unit_18short.docx";
       //String inputfilepath = System.getProperty("user.dir") + "/src/test/resources/Unit 44_FBC.docx";
       String inputfilepath = System.getProperty("user.dir") + "/src/test/resources/230715 U16 - Astronomy and Space Science - Pre-Panel.docx";
       // String inputfilepath = System.getProperty("user.dir") + "/src/test/resources/Documentwithrowspanoverfourrows2.docx";

        //String inputfilepath = System.getProperty("user.dir") + "/src/test/resources/Unit 44_FBC_Metadata_LA_Paragraphs_only.docx";
        //String inputfilepath = System.getProperty("user.dir") + "/src/test/resources/3DModelling.docx";
        //String inputfilepath = System.getProperty("user.dir") + "/btecdocs/Unit_18.docx";

        MainDocument mainDocument = new MainDocument(new File(inputfilepath));
        mainDocument.convertMainDocument();
        String unitXMLString = mainDocument.exportUnitAsXMLString();
        System.out.println(unitXMLString);
    }

    private File wordDocument;
    private Unit unit;

    public MainDocument (File inputfilepath) {
        this.wordDocument = inputfilepath;
    }

    public void convertMainDocument () {

        WordprocessingMLPackage wordMLPackage = null;
        // Model Objects
        Unit unit = null;

        try {
            wordMLPackage = WordprocessingMLPackage.load(wordDocument);


            // Word Document Properties
            org.docx4j.openpackaging.parts.DocPropsCorePart docPropsCorePart = wordMLPackage.getDocPropsCorePart();
            org.docx4j.docProps.core.CoreProperties coreProps = (org.docx4j.docProps.core.CoreProperties)docPropsCorePart.getJaxbElement();
            HashMap docPropMap = new HashMap();

            if(coreProps != null) {
                String title = "Missing";
                if(coreProps.getTitle() != null && coreProps.getTitle().getValue() !=null && coreProps.getTitle().getValue().getContent() != null) {
                    List<String> list = coreProps.getTitle().getValue().getContent();
                    if (list.size() > 0) {
                        title = list.get(0);
                    }
                }
                docPropMap.put("dc-title", title);
                String keyword = "Missing";
                keyword = coreProps.getKeywords();
                docPropMap.put("uan", keyword);
                docPropMap.put("dc-keyword", keyword);
                String author = "Missing";
                List<String> authorList = coreProps.getCreator().getContent();
                if (authorList.size() > 0) {
                    author = authorList.get(0);
                }
                docPropMap.put("author", author);
                String description = "Missing";
                if (coreProps.getDescription() != null) {
                    List<String> descriptionList = coreProps.getDescription().getValue().getContent();
                    if (descriptionList.size() > 0) {
                        description = descriptionList.get(0);
                    }
                }
                docPropMap.put("dc-description", description);
            }


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


            // Parse Word Document
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart.getJaxbElement();

//            Prints out the OpenXML as is:
//            String xml = org.docx4j.XmlUtils.marshaltoString(wmlDocumentEl, true);
//            System.out.println(xml);

            //String xpath = "w:body";
            //List<Object> documentBody = documentPart.getJAXBNodesViaXPath(xpath, false);

            //String xml = org.docx4j.XmlUtils.marshaltoString(wmlDocumentEl, true);

/*
            List<Object> documentBody = wmlDocumentEl.getContent();

            for (Object o : documentBody) {
                //Object o2 = XmlUtils.unwrap(o);
                //System.out.println( XmlUtils.marshaltoString(o2, true, true));

                if(o instanceof Body) {
                    DocumentUtils documentUtils = new DocumentUtils();
                    unit = documentUtils.traverseDocumentBody((Body) o);
                    unit.setUnitMetaData(docPropMap);
                }
            }
*/

            Body body = wmlDocumentEl.getBody();
            DocumentUtils documentUtils = new DocumentUtils();
            unit = documentUtils.traverseDocumentBody(body);
            unit.setUnitMetaData(docPropMap);

            //LOGGER.debug("Unit Object [{}]", unit.toString());


//        } catch (JAXBException e) {
//            e.printStackTrace();
        } catch (Docx4JException e) {
            e.printStackTrace();
        }

        this.unit = unit;

    }


    // Export to XML DOM
    public Element exportUnitAsXMLDOM() {
        Unit2xml unit2xml = new Unit2xml(this.unit);
        Element element = unit2xml.toXml();
        return element;
    }

    // Export as XML String
    public String exportUnitAsXMLString() {
        if(this.unit != null) {
            Unit2xml unit2xml = new Unit2xml(this.unit);
            return unit2xml.toString();
        } else {
            LOGGER.error("In exportUnitAsXMLString() - Word Document is empty");
            return "Word Document is empty";
        }
    }
}


