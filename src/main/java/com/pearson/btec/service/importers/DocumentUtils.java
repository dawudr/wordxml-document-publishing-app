package com.pearson.btec.service.importers;

import com.pearson.btec.model.*;
import org.docx4j.wml.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.*;


/**
 * Known defects/unfinished features:
 *
 */
public class DocumentUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentUtilHelper.class);

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
                LOGGER.debug("First HEADINGMAP[{}] ", headingMap);

                if(headingMap == null) {
                    if (headingText == null) {
                        // addAll join arrays due to several paragraphs in one section
                        unitHeaderProperties.addAll(SectionHeader.processPBlock(pBlock));
                    }
                } else {
                    if(!headingMap.isEmpty() && headingMap.containsKey(DocumentUtilHelper.XML_SECTION_TITLE)) {
                        headingText = (String) headingMap.get(DocumentUtilHelper.XML_SECTION_TITLE);
                        LOGGER.debug("First HEADING[{}] ", headingText);
                        //System.out.println("\r\nFirst HEADING: " + headingText);
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
                    LOGGER.debug("AFTER FIRST HEADING: [{}]", headingText);
                    //System.out.println("\r\nHEADING: " + headingText);
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

                                LOGGER.debug("SDTRUN...........................................");
                                //System.out.println("--------------> SDTRUN");
                                HashMap dataMap = DocumentUtils.traverseDocumentJAXBElementSdtBlock((JAXBElement) pObject);
                                contentDataList.add(dataMap);
                            }
                        }
                    }
                }
            } else if (bodyObject instanceof JAXBElement) {
                LOGGER.debug("JAXBELEMENT...........................................");
                //System.out.println("--------------> JAXBELEMENT");

                //TODO: Check for Tables only
                //TODO: Old code with uses Docx4J library to get tables data.
                // Search for tables
                List dataList = DocumentUtils.traverseDocumentJAXBElementTable(bodyObject);
                LOGGER.debug("UnitTable Start - Creating new UnitTable object......");
                UnitTable unitTable = new UnitTable(dataList);
                LOGGER.debug("UnitTable Finish - Adding UnitTable to contentDataList unitTable[{}]", unitTable);
                contentDataList.add(unitTable);

                //TODO: Fix by switching over to the XQuery when testing and parsing output to DOM Element in toXML() is done.
/*
                It has issue with track changes since the text tags are not w:t but instead w:del and w:ins for e.g
                The track changes issue - I've made a few work arounds to fix.
                The problem is when you have track changes on DocX4J doesn't recognise the data text tags get put into new tags I presume which stands for insert and delete....
                <w:sdtContent>
                <w:del w:author="Winser, Paul" w:date="2014-12-02T13:26:00Z"
                w:id="143">
                <w:r w:rsidDel="00757C43" w:rsidR="00757C43">
                <w:rPr>
                <w:rFonts w:eastAsia="Batang"/>
                </w:rPr>
                <w:delText>application of 3D modelling in industries.</w:delText>
                </w:r>
                </w:del>
                <w:ins w:author="Winser, Paul" w:date="2014-12-02T13:26:00Z"
                w:id="144">
                <w:r w:rsidR="00757C43">
                <w:rPr>
                <w:rFonts w:eastAsia="Batang"/>
                </w:rPr>
                <w:t>Applications of 3D modelling in industries</w:t>
                </w:r>
                </w:ins>
                </w:sdtContent>
*/


                //TODO: NEW XQUERY CODE Plan is to switch to this new code which uses Xquery to get Tables data as a String directly and parse this back as a DOM Element
                // Search for tables using Xquery and output as table structure capturing rows, columns.
/*
                String dataString = DocumentUtils.traverseDocumentJAXBElementTableXquery(bodyObject);
                if(dataString != null && !dataString.isEmpty()) {

                    String xqueriedTableData = null;
                    try {
                        xqueriedTableData = TableContentPreProcessor.getTableData(dataString);
                    } catch (SaxonApiException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    UnitTable unitTable = new UnitTable(xqueriedTableData);
                    contentDataList.add(unitTable);
                } else {
                    System.out.println("JaxbElement is empty");
                }
*/

            } else if (bodyObject instanceof SdtBlock) {
                LOGGER.debug("SDTBLOCK...........................................");

                //System.out.println("--------------> SDTBLOCK");
                HashMap dataMap = DocumentUtils.traverseSectionBlocks((SdtBlock) bodyObject);
                dataMap.put(DocumentUtilHelper.XML_TAG_VALUE_ENCODING, "html");
                LOGGER.debug("Paragraph SdtBlock [{}]", dataMap);
                //System.out.println("Paragraph SdtBlock = " + dataMap);
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
            //String xml = org.docx4j.XmlUtils.marshaltoString(tbl, true);
            //System.out.println("TABLE tbl=" + xml);
        }
        LOGGER.debug("Returning List of rows as tableDataList[{}]", tableDataList);
        return tableDataList;
    }


    /**
     * Traverse the document for
     * w:sdtRun - this is within Paragraphs
     * or w:tbl
     * @param jaxbElementSdtContent
     */
    public static String traverseDocumentJAXBElementTableXquery(Object jaxbElementSdtContent) {

        String tableDataList = null;

        if(((JAXBElement) jaxbElementSdtContent).getValue() instanceof Tbl) {
            Tbl tbl = (Tbl) ((JAXBElement) jaxbElementSdtContent).getValue();
            String xml = org.docx4j.XmlUtils.marshaltoString(tbl, true);
            //System.out.println("TABLE tbl=" + xml);
            tableDataList = xml;
        }
        return tableDataList;
    }


    public static HashMap traverseDocumentJAXBElementSdtBlock(JAXBElement jaxbElementSdtContent) {
        SdtRun sdtRun = (SdtRun) ((JAXBElement) jaxbElementSdtContent).getValue();
        HashMap map = traverseSectionBlocks(sdtRun);
        LOGGER.debug("Paragraph SdtRun [{}]", map);
        //System.out.println("Paragraph SdtRun = " + map );
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
            LOGGER.debug("SDTRUN:xmlTagKey [{}]", xmlTagKey);

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
            LOGGER.debug("SDTRUN:xmlTagValue [{}]", sbTagValue.toString());

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
        LOGGER.debug("SDTBLOCK:xmlTagKey [{}]", xmlTagKey);

        map.put(DocumentUtilHelper.XML_TAG, xmlTagKey);


        // Print out text
        String xmlTagValue = DocumentUtilHelper.findTextFromSdtBoth(sdtBlock);
        LOGGER.debug("SDTBLOCK:xmlTagValue [{}]", xmlTagValue);
        //System.out.println("** SDTBLOCK ** [" + xmlTagValue + "]");
        map.put(DocumentUtilHelper.XML_TAG_VALUE, xmlTagValue);

        return map;
    }

}


