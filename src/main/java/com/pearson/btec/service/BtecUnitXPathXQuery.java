package com.pearson.btec.service;

import com.pearson.btec.model.BtecUnit;
import com.pearson.btec.model.Paragraph;
import com.pearson.btec.model.Section;
import org.docx4j.XmlUtils;
import org.docx4j.jaxb.Context;
import org.docx4j.jaxb.XPathBinderAssociationIsPartialException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.docx4j.wml.Text;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Dawud on 19/08/14.
 */
public class BtecUnitXPathXQuery {

        public static void main(String[] args) throws Exception {
            BtecUnitXPathXQuery btecUnitXPathXQuery = new BtecUnitXPathXQuery();

            //String inputfilepath = System.getProperty("user.dir") + "/sample-docs/word/sample-docx.xml";
            //String inputfilepath = System.getProperty("user.dir") + "/btecdocs/Unit_18short.docx";
            //String inputfilepath = System.getProperty("user.dir") + "/btecdocs/Unit 44_FBC.docx";
            String inputfilepath = System.getProperty("user.dir") + "/src/test/resources/Unit_18.docx";

            btecUnitXPathXQuery.convertDocumentToXML(inputfilepath);

        }


    /**
     * Process Word document and generate Open XML document
     * @param filename
     * @return
     */
    public boolean convertDocumentToXML(String filename) throws Exception {
        // Output results from BTEC Unit Model
        BtecUnit btecUnit = getWholeDocument(new java.io.File(filename));
        //System.out.println(btecUnit.toString());

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
        System.out.println(xml.outputString(jdomDoc));

        return true;
    }




        /**
         * Processes a Word Document file
         *
         * @param wordDocfilepath
         * @return ArrayList linear list of items
         */
        public BtecUnit getWholeDocument(File wordDocfilepath) throws Exception {
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(wordDocfilepath);


            org.docx4j.openpackaging.parts.DocPropsCorePart docPropsCorePart = wordMLPackage.getDocPropsCorePart();
            org.docx4j.docProps.core.CoreProperties coreProps = (org.docx4j.docProps.core.CoreProperties)docPropsCorePart.getJaxbElement();

            // What is the title of the document?
            // Note: Word for Mac 2010 doesn't set title
            String title = "Missing";

            if(coreProps != null && coreProps.getTitle() !=null) {
                List<String> list = coreProps.getTitle().getValue().getContent();
                if (list.size() > 0) {
                    title = list.get(0);
                }

                title = coreProps.getKeywords();
            }

            // Finally, the custom properties
            org.docx4j.openpackaging.parts.DocPropsCustomPart docPropsCustomPart = wordMLPackage.getDocPropsCustomPart();
            if(docPropsCustomPart==null){
                System.out.println("No DocPropsCustomPart");
            } else {
                org.docx4j.docProps.custom.Properties customProps = (org.docx4j.docProps.custom.Properties)docPropsCustomPart.getJaxbElement();

                for (org.docx4j.docProps.custom.Properties.Property prop: customProps.getProperty() ) {

                    // At the moment, you need to know what sort of value it has.
                    // Could create a generic Object getValue() method.
                    if (prop.getLpwstr()!=null) {
                        System.out.println(prop.getName() + " = " + prop.getLpwstr());
                    } else {
                        System.out.println(prop.getName() + ": \n " + XmlUtils.marshaltoString(prop, true, Context.jcDocPropsCustom));
                    }
                }
            }



            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            org.docx4j.wml.Document wmlDocumentEl = (org.docx4j.wml.Document) documentPart.getJaxbElement();

            List<Section> btecBody = getDocumentSections(documentPart);
            //List<Section> btecBody = getDocumentSectionWithHeader(documentPart);
            BtecUnit btecUnit = new BtecUnit(btecBody);
            btecUnit.setUanNumber(title);

            return btecUnit;
        }


    /**
     * Traverse the document for Section headings to be the parent element node of XML
     */
    public List<Section> getDocumentSectionWithHeader(MainDocumentPart documentPart) throws JAXBException, XPathBinderAssociationIsPartialException {
        List<Section> btecBody = new ArrayList<Section>();

        // Iterate through Paragraphs and
        // then call getDocumentSections code - move this code up here


        return btecBody;
    }


    /**
     * Traverse the document for w:sdt and place valid section into ArrayList
     * @param documentPart
     * @return
     * @throws javax.xml.bind.JAXBException
     * @throws XPathBinderAssociationIsPartialException
     */
        private List<Section> getDocumentSections(MainDocumentPart documentPart) throws JAXBException, XPathBinderAssociationIsPartialException {
            List<Section> btecBody = new ArrayList<Section>();

            // Section
            // w:sdt
            String xpath = "//w:sdt";
            List<Object> sdtList = documentPart.getJAXBNodesViaXPath(xpath, false);

            for(Object sdtObject : sdtList) {
                // w:sdt
                // JAXBElement<org.docx4j.wml.SdtRun>
                if (sdtObject instanceof JAXBElement
                        && ((JAXBElement) sdtObject).getValue() instanceof SdtRun) {
                    SdtRun sdtRun = (org.docx4j.wml.SdtRun) ((JAXBElement) sdtObject).getValue();
                    Section section = traverseSectionBlocks(sdtRun);
                    if(section != null) {
                        btecBody.add(section);
                    }
                // w:sdt
                // JAXBElement<org.docx4j.wml.SdtBlock>
                } else if(sdtObject instanceof SdtBlock) {
                    Section section = traverseSectionBlocks((SdtBlock) sdtObject);
                    if(section != null) {
                        btecBody.add(section);
                    }
                }
            }
            return btecBody;
        }


    /**
     * Overloaded method
     * Traverse into SdtBlock until we find Alias and SdtContent with Paragraph
     *
     * @param sdtRun
     * @return
     */
    private Section traverseSectionBlocks(SdtRun sdtRun) {
        Section section = null;

        SdtPr sdtPr = sdtRun.getSdtPr();
        if (sdtPr.getByClass(SdtPr.Alias.class) != null) {
            // w:alias <-- xml tag
            // JAXBElement<org.docx4j.wmlSdtPr.Alias>
            SdtPr.Alias alias = (SdtPr.Alias) sdtPr.getByClass(SdtPr.Alias.class);
            String xmlAliasKey = alias.getVal();
            //System.out.println("xmlAliasKey[" + xmlAliasKey + "]");
            section = new Section();
            section.setAlias(xmlAliasKey);

            // Get content
            ContentAccessor contentAccessor = sdtRun.getSdtContent();
            List<Object> contentTextList = contentAccessor.getContent();

            StringBuilder sbAliasValue = new StringBuilder();
            for (Object contentTextObject : contentTextList) {
                // w:r
                if (contentTextObject instanceof R) {
                    R r = (R) contentTextObject;

                    List rList = r.getContent();
                    for (Object rObject : rList) {

                        // JAXBElement<org.docx4j.wml.Text>
                        if (rObject instanceof JAXBElement
                                && ((JAXBElement) rObject).getValue() instanceof Text) {
                            Text text = (org.docx4j.wml.Text) ((JAXBElement) rObject).getValue();
                            String xmlAliasValue = text.getValue();
//                            System.out.println("xmlAliasValue[" + xmlAliasValue + "]");
                            sbAliasValue.append(text.getValue());
                        }
                    }
                    // Recurse into SdtBlocks
                    // w:sdt
                }
                /*else if (contentTextObject instanceof SdtBlock) {
                        SdtBlock nestedSdtBlock = (SdtBlock) contentTextObject;
                        return traverseSdtBlocks(nestedSdtBlock);
                    }*/
            }
            // Finally concatenate all text
            section.setAliasValue(sbAliasValue.toString());
        }
        return section;
    }

        /**
         * Overloaded method
         * Traverse into SdtBlock until we find Alias and SdtContent with Paragraph
         *
         * @param sdtBlock
         * @return
         */
        private Section traverseSectionBlocks(SdtBlock sdtBlock) {
            List<Paragraph> paragraphs = new ArrayList<Paragraph>();
            Section section = null;

            SdtPr sdtPr = sdtBlock.getSdtPr();
            if (sdtPr.getByClass(SdtPr.Alias.class) != null) {
                // w:alias <-- xml tag
                // JAXBElement<org.docx4j.wmlSdtPr.Alias>
                SdtPr.Alias alias = (SdtPr.Alias) sdtPr.getByClass(SdtPr.Alias.class);
                String xmlAliasKey = alias.getVal();
//                System.out.println("xmlAliasKey[" + xmlAliasKey + "]");

                // Get content
                ContentAccessor contentAccessor = sdtBlock.getSdtContent();
                List<Object> contentTextList = contentAccessor.getContent();


                //TODO:
                P previousPObject = null;
                StringBuilder unorderedSb = new StringBuilder();

                Iterator<Object> contentIterator = contentTextList.iterator();

                while (contentIterator.hasNext()) {
                    Object contentTextObject = contentIterator.next();

                    // w:p
                    if (contentTextObject instanceof P) {
                        P pObject = (P) contentTextObject;


                        List<Object> pList = pObject.getContent();
                        StringBuilder runSb =new StringBuilder();

                        for (Object pContentObject : pList) {

                            // w:r
                            if (pContentObject instanceof R) {
                                R r = (R) pContentObject;

                                List rList = r.getContent();
                                for (Object rObject : rList) {

                                    // JAXBElement<org.docx4j.wml.Text>
                                    if (rObject instanceof JAXBElement
                                            && ((JAXBElement) rObject).getValue() instanceof Text) {
                                        Text text = (org.docx4j.wml.Text) ((JAXBElement) rObject).getValue();
                                        runSb.append(text.getValue());

                                        // Add bold tags if found <w:b/> or <w:bCs/>
                                        if(r.getRPr() != null && (r.getRPr().getB() !=null || r.getRPr().getBCs() !=null)) {
                                            runSb.append("</b>");
                                            runSb.insert(0,"<b>");
                                        }
                                    }
                                }
                            }
                        }



                        // w:numPr - this is for list items
                        String xmlTag = "p";
                        String xmlTagOpen = "<" + xmlTag + ">";
                        String xmlTagClose = "</" + xmlTag + ">";


                        if(pObject.getPPr().getNumPr() != null) {
                            // Just collect unordered Lists content items
                            String xmlAliasValue = "<li>" + runSb.toString() + "</li>";
                            unorderedSb.append(xmlAliasValue);
                        }

                        String xmlAliasValue = xmlTagOpen + runSb.toString() + xmlTagClose;

                        // previous paragraph was last list item within paragraphs
                        // or end of list is last item in Iterator
                        if((previousPObject != null
                                && previousPObject.getPPr().getNumPr() != null
                                && pObject.getPPr().getNumPr() == null)
                                ||
                                (pObject.getPPr() != null
                                        && pObject.getPPr().getNumPr() != null
                                        && !contentIterator.hasNext())) {
                                unorderedSb.insert(0, "<ul>");
                                unorderedSb.append("</ul>");
                                paragraphs.add(new Paragraph(unorderedSb.toString()));
                                // reset
                                unorderedSb = new StringBuilder();
                        }

                        // Add all except for lists
                        if(pObject.getPPr().getNumPr() == null) {
                            //System.out.println("xmlAliasValue[" + xmlAliasValue + "]");
                            paragraphs.add(new Paragraph(xmlAliasValue));
                        }

                        // update previous pointer
                        previousPObject = pObject;

                    }
                }


                // Finally add to Section
                if(paragraphs.size()>0) {
                    section = new Section();
                    section.setAlias(xmlAliasKey);
                    section.setParagraphs(paragraphs);
                }
            }
            return section;
        }
    }


