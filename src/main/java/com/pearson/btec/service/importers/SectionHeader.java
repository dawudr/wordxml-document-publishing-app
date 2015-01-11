package com.pearson.btec.service.importers;

import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * SectionHeader
 */
public class SectionHeader {

    /**
     * Traverse content from Paragraphs
     * @param paragraphObject
     * @return
     */
    public static HashMap getDocumentPBlockHeadingTags(P paragraphObject) {
        List<Object> pList = paragraphObject.getContent();
        StringBuilder runSb = new StringBuilder();

        HashMap map = new HashMap();

        if(paragraphObject.getPPr() != null && paragraphObject.getPPr().getPStyle() !=null &&
                (paragraphObject.getPPr().getPStyle().getVal().equalsIgnoreCase("UnitAhead") ||
                        paragraphObject.getPPr().getPStyle().getVal().equalsIgnoreCase("UnitBhead") ||
                        paragraphObject.getPPr().getPStyle().getVal().equalsIgnoreCase("hb3"))) {

            map.put(DocumentUtilHelper.XML_SECTION_TITLE_STYLE, paragraphObject.getPPr().getPStyle().getVal());

            for (Object pContentObject : pList) {
                // w:r
                if (pContentObject instanceof R) {
                    R r = (R) pContentObject;
                    runSb.append(ParagraphUtils.processDocumentRBlock(r).trim());
                }
            }


            if(runSb.length() == 0) {
                return null;
            }

            map.put(DocumentUtilHelper.XML_SECTION_TITLE, runSb.toString());

            return map;
        }
        return null;
    }


    /**
     * P Blocks in header
     * @param p
     */
    public static List processPBlock(P p) {
        List mapList = new ArrayList();

        List<Object> pList = p.getContent();
        for(Object pObject : pList) {

            if(pObject instanceof JAXBElement && ((JAXBElement) pObject).getValue() instanceof SdtRun) {
                SdtRun sdtRun = (SdtRun)((JAXBElement) pObject).getValue();


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
                                if (rObject instanceof JAXBElement) {
                                    if (((JAXBElement) rObject).getValue() instanceof Text) {
                                        Text text = (Text) ((JAXBElement) rObject).getValue();
                                        sbTagValue.append(text.getValue());
                                    }
                                }
                            }
                        } else if (sdtContentObject instanceof RunIns) {
                            RunIns runIns = (RunIns) sdtContentObject;
                            List<Object> runInsList = runIns.getCustomXmlOrSmartTagOrSdt();

                            for(Object runInsObject : runInsList) {
                                if(runInsObject instanceof R) {
                                    R r = (R) runInsObject;

                                    List rList = r.getContent();
                                    for (Object rObject : rList) {

                                        // JAXBElement<org.docx4j.wml.Text>
                                        if (rObject instanceof JAXBElement
                                                && ((JAXBElement) rObject).getValue() instanceof Text) {
                                            Text text = (Text) ((JAXBElement) rObject).getValue();
                                            sbTagValue.append(text.getValue());
                                        }
                                    }                                }
                            }
                        }

                        if(sbTagValue.length() > 0) {
                            map.put(DocumentUtilHelper.XML_TAG_VALUE, sbTagValue.toString());
                            System.out.println("HeaderProperties:" + map);
                        }

                    }
                    mapList.add(map);

                    // Finally concatenate all text
                    //System.out.println("SDTRUN:xmlTagValue[" + sbTagValue.toString() + "]");
                    //section.setAliasValue(sbTagValue.toString());
                }
            }


//            // Get the Sdt's and Tbl's
//            if (pObject instanceof JAXBElement) {
//                List dataMapList = DocumentUtils.traverseDocumentJAXBElementTable(pObject);
//                //for(HashMap dataMap : dataMapList) {
//                //    mapList.add(dataMap);
//                //}
//                mapList.add(dataMapList);
//                // Get the SdtBlock's
//            } else if (pObject instanceof SdtBlock) {
//                HashMap map = DocumentUtils.traverseSectionBlocks((SdtBlock) pObject);
//                System.out.println("SdtBlock=" + map);
//                mapList.add(map);
//            }
//
        }
        return mapList;
    }
}
