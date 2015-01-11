package com.pearson.btec.service.importers;

import org.docx4j.wml.*;
import com.pearson.btec.model.btec.*;

import javax.xml.bind.JAXBElement;
import java.util.*;


/**
 * Known defects/unfinished features:
 *
 */
public class DocumentUtils {

    /**
     * Traverse Body. This is the starting point switch at level one before calling recursive methods to dig deeper
     * @param body
     */
    public Unit traverseDocumentBody(Body body) {

        Unit unitModel = new Unit();

        List<Object> bodyContent = body.getContent();
        Iterator<Object> bodyIterator = bodyContent.iterator();
        String headingText = null;

        List unitHeaderProperties = new ArrayList();

        // Get Headers
        // Find first w:p's before w:p UnitAHead's
        while(headingText == null && bodyIterator.hasNext()) {
            Object bodyObject = bodyIterator.next();

            if(bodyObject instanceof P) {
                P pBlock = (P) bodyObject;
                Map headingMap = SectionHeader.getDocumentPBlockHeadingTags(pBlock);
                //System.out.println("\r\nFirst HEADINGMAP: " + headingMap);

                if(headingMap == null) {
                    if (headingText == null) {
                        // addAll join arrays due to several paragraphs in one section
                        unitHeaderProperties.addAll(SectionHeader.processPBlock(pBlock));
                    }
                } else {
                    if(!headingMap.isEmpty() && headingMap.containsKey(DocumentUtilHelper.XML_SECTION_TITLE)) {
                        headingText = (String) headingMap.get(DocumentUtilHelper.XML_SECTION_TITLE);
                        System.out.println("\r\nFirst HEADING: " + headingText);
                    }
                }
            }
        }

        UnitHeader unitHeader = new UnitHeader();
        unitHeader.setProperties(unitHeaderProperties);



        // Data containers
        List<UnitSection> unitSections = new ArrayList<UnitSection>();
        UnitSection unitSection = null;
        List<Object> contentDataList = new ArrayList<Object>();

        String newHeadingText = null;
        UnitSection currentUnitSection = null;

        // Get Content
        while (headingText != null && bodyIterator.hasNext()) {
            Object bodyObject = bodyIterator.next();

            if (bodyObject instanceof P) {
                P pBlock = (P) bodyObject;

                Map newHeadingMap = SectionHeader.getDocumentPBlockHeadingTags(pBlock);
                if(newHeadingMap != null && !newHeadingMap.isEmpty() && newHeadingMap.containsKey(DocumentUtilHelper.XML_SECTION_TITLE)) {
                    newHeadingText = (String) newHeadingMap.get(DocumentUtilHelper.XML_SECTION_TITLE);
                }

                if (newHeadingText != null && !newHeadingText.equals(headingText)) { //do we need 2nd check?
                    headingText = newHeadingText;
                    System.out.println("\r\nHEADING: " + headingText);
                    // Initialise a new Section with header after closing up and adding prev section
                    if(unitSection !=null) {
                        unitSection.setContentData(contentDataList);
                        unitSections.add(unitSection);
                        contentDataList = new ArrayList<Object>();
                    }

                    unitSection = new UnitSection(newHeadingMap);
                    currentUnitSection = unitSection;

                } else {
                    // Process P Block child off the Root Parent block.
                    List pObjects = pBlock.getContent();
                    for(Object pObject : pObjects) {
                        if(pObject instanceof JAXBElement) {

                            if (((JAXBElement) pObject).getValue() instanceof SdtRun) {

                                System.out.println("--------------> SDTRUN");
                                HashMap dataMap = DocumentUtils.traverseDocumentJAXBElementSdtBlock((JAXBElement) pObject);
                                contentDataList.add(dataMap);
                            }
                        }
                    }
                }
            } else if (bodyObject instanceof JAXBElement) {
                System.out.println("--------------> JAXBELEMENT");
                //TODO: Check for Tables only
                // Search for tables
                List dataList = DocumentUtils.traverseDocumentJAXBElementTable(bodyObject);
                //for(HashMap dataMap : dataList) {
                //    paragraphDataList.add(dataMap);
                //}
                UnitTable unitTable = new UnitTable(dataList);
                contentDataList.add(unitTable);

            } else if (bodyObject instanceof SdtBlock) {

                System.out.println("--------------> SDTBLOCK");
                HashMap dataMap = DocumentUtils.traverseSectionBlocks((SdtBlock) bodyObject);
                dataMap.put(DocumentUtilHelper.XML_TAG_VALUE_ENCODING, "html");
                System.out.println("Paragraph SdtBlock = " + dataMap);
                contentDataList.add(dataMap);

            }

            // Last heading section left
            if(!bodyIterator.hasNext()) {
                // Add this as a new section
                currentUnitSection.setContentData(contentDataList);
                unitSections.add(currentUnitSection);
            }
        }




//            if (parseObject instanceof P) {
//
//                // Pretty print the main document part
//                System.out.println();
//
//                for(Object listItem : ((P) parseObject).getContent()) {
//                    System.out.println(XmlUtils.marshaltoString(listItem, true, true));
//                }
//
//            }


        UnitBody unitBody = new UnitBody();
        unitBody.setUnitSections(unitSections);
        unitModel.setUnitHeader(unitHeader);
        unitModel.setUnitBody(unitBody);


        return unitModel;

    }


    /**
     * Traverse the document for
     * w:sdtRun - this is within Paragraphs
     * or w:tbl
     * @param jaxbElementSdtContent
     */
    public static List traverseDocumentJAXBElementTable(Object jaxbElementSdtContent) {

        List tableDataList = null;

        if(((JAXBElement) jaxbElementSdtContent).getValue() instanceof Tbl) {
            Tbl tbl = (Tbl) ((JAXBElement) jaxbElementSdtContent).getValue();
            tableDataList = TableContent.getTableData(tbl);
        }
        return tableDataList;
    }



    public static HashMap traverseDocumentJAXBElementSdtBlock(JAXBElement jaxbElementSdtContent) {
        SdtRun sdtRun = (SdtRun) ((JAXBElement) jaxbElementSdtContent).getValue();
        HashMap map = traverseSectionBlocks(sdtRun);
        System.out.println("Paragraph SdtRun = " + map );
        return map;
    }


    /**
     * Overloaded method
     * Traverse into SdtBlock until we find Tag and SdtContent with Paragraph
     *
     * @param sdtRun
     * @return
     */
    public static HashMap traverseSectionBlocks(SdtRun sdtRun) {
        HashMap map = new HashMap();

        SdtPr sdtPr = sdtRun.getSdtPr();
        if (sdtPr.getByClass(Tag.class) != null) {
            // w:tag <-- xml tag
            // JAXBElement<org.docx4j.wml.Tag>
            Tag xmlTag = (Tag) sdtPr.getByClass(Tag.class);
            String xmlTagKey = xmlTag.getVal();

            //System.out.println("\r\nSDTRUN:xmlTagKey[" + xmlTagKey + "]");
            map.put(DocumentUtilHelper.XML_TAG, xmlTagKey);

            // Get content
            ContentAccessor contentAccessor = sdtRun.getSdtContent();
            List<Object> sdtContentList = contentAccessor.getContent();

            StringBuilder sbTagValue = new StringBuilder();
            for (Object sdtContentObject : sdtContentList) {
                // w:r
                if (sdtContentObject instanceof R) {
                    R r = (R) sdtContentObject;

                    List rList = r.getContent();
                    for (Object rObject : rList) {

                        // JAXBElement<org.docx4j.wml.Text>
                        if (rObject instanceof JAXBElement
                                && ((JAXBElement) rObject).getValue() instanceof Text) {
                            Text text = (Text) ((JAXBElement) rObject).getValue();
                            sbTagValue.append(text.getValue());
                        }
                    }
                } else if (sdtContentObject instanceof RunIns) {
                    RunIns runIns = (RunIns) sdtContentObject;
                    List<Object> runInsList = runIns.getCustomXmlOrSmartTagOrSdt();

                    for (Object runInsObject : runInsList) {
                        if (runInsObject instanceof R) {
                            R r = (R) runInsObject;
                            List rList = r.getContent();
                            for (Object rObject : rList) {

                                // JAXBElement<org.docx4j.wml.Text>
                                if (rObject instanceof JAXBElement
                                        && ((JAXBElement) rObject).getValue() instanceof Text) {
                                    Text text = (Text) ((JAXBElement) rObject).getValue();
                                    sbTagValue.append(text.getValue());
                                }
                            }
                        }
                    }
                }
            }
            // Finally concatenate all text
            //System.out.println("SDTRUN:xmlTagValue[" + sbTagValue.toString() + "]");
            //section.setAliasValue(sbTagValue.toString());
            map.put(DocumentUtilHelper.XML_TAG_VALUE, sbTagValue.toString());
        }
        return map;
    }



    /**
     * Traverse into SdtBlock Table Header
     *
     * @param sdtRun
     * @return
     */
    public static String traverseSectionBlockHeadingMeta(SdtRun sdtRun) {
        // Get content
        ContentAccessor contentAccessor = sdtRun.getSdtContent();
        List<Object> sdtContentList = contentAccessor.getContent();

        StringBuilder sbTagValue = new StringBuilder();
        for (Object sdtContentObject : sdtContentList) {
            // w:r
            if (sdtContentObject instanceof R) {
                R r = (R) sdtContentObject;

                List rList = r.getContent();
                for (Object rObject : rList) {

                    // JAXBElement<org.docx4j.wml.Text>
                    if (rObject instanceof JAXBElement
                            && ((JAXBElement) rObject).getValue() instanceof Text) {
                        Text text = (Text) ((JAXBElement) rObject).getValue();
                        sbTagValue.append(text.getValue());
                    }
                }
            }
        }
        return sbTagValue.toString();
    }


    /**
     * Overloaded method
     * Traverse into SdtBlock until we find Alias and SdtContent with Paragraph
     *
     * @param sdtBlock
     * @return
     */
    public static HashMap traverseSectionBlocks(SdtBlock sdtBlock) {
        HashMap map = new HashMap();

        String xmlTagKey = DocumentUtilHelper.findTagFromSdtBoth(sdtBlock);
        //System.out.println("** SDTBLOCK ** [" + xmlTagKey + "]");
        map.put(DocumentUtilHelper.XML_TAG, xmlTagKey);


        // Print out text
        String xmlTagValue = DocumentUtilHelper.findTextFromSdtBoth(sdtBlock);
        //System.out.println("** SDTBLOCK ** [" + xmlTagValue + "]");
        map.put(DocumentUtilHelper.XML_TAG_VALUE, xmlTagValue);

        return map;
    }

}


