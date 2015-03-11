package com.pearson.btec.service.importers;

import org.docx4j.wml.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dawud on 02/11/2014.
 */
public class ParagraphUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParagraphUtils.class);


    public static String traverseParagraphBlocks(List<Object> contentTextList) {
        //List<String> paragraphs = new ArrayList<String>();
        StringBuilder paragraphSb = new StringBuilder();

        P previousPObject = null;
        StringBuilder unorderedSb = new StringBuilder();
        Iterator<Object> contentIterator = contentTextList.iterator();
        while (contentIterator.hasNext()) {
            Object contentTextObject = contentIterator.next();

            // w:p
            if (contentTextObject instanceof P) {
                //System.out.println("Writing P BLOCK...");

                P pObject = (P) contentTextObject;
                StringBuilder runSb = new StringBuilder();
                String paragraphText = processDocumentPBlock(pObject);

                //if(!paragraphText.isEmpty()) {
                //} // End of If !paragraphText.isEmpty()
                runSb.append(paragraphText);

                //w:p normal text
                String xmlTag = "p";
                String xmlTagOpen = "<" + xmlTag + ">";
                String xmlTagClose = "</" + xmlTag + ">";
                String xmlTagValue = xmlTagOpen + runSb.toString() + xmlTagClose;

                // w:numPr - this is for list items
                if (pObject.getPPr() != null && pObject.getPPr().getNumPr() != null && runSb.length() > 0) {
                    // Just collect unordered Lists content items
                    xmlTagValue = "<li>" + runSb.toString() + "</li>";
                    unorderedSb.append(xmlTagValue);
                }

                // Surround <li></li> with <ul> and </ul>
                // previous paragraph was last list item within paragraphs
                // or end of list is last item in Iterator
                if(unorderedSb.length() >0) {
                    if ((previousPObject != null
                            && previousPObject.getPPr() != null
                            && previousPObject.getPPr().getNumPr() != null
                            && pObject.getPPr() != null
                            && pObject.getPPr().getNumPr() == null)
                            ||
                            (pObject.getPPr() != null
                                    && pObject.getPPr().getNumPr() != null
                                    && !contentIterator.hasNext())) {
                        unorderedSb.insert(0, "<ul>");
                        unorderedSb.append("</ul>");

                        //paragraphs.add(unorderedSb.toString());

                        // Add a completed list <ul><li></li>...<li></li></ul>
                        paragraphSb.append(unorderedSb.toString());
                        // reset list stringbuilder
                        unorderedSb = new StringBuilder();
                    }
                }

                // Add all except for lists
                if (pObject.getPPr() != null && pObject.getPPr().getNumPr() == null) {
                    //System.out.println("SDTBLOCK:xmlTagValue[" + xmlTagValue + "]");
                    //paragraphs.add(xmlTagValue);

                    if (!paragraphText.isEmpty())
                        paragraphSb.append(xmlTagValue);
                }


                // update previous pointer
                previousPObject = pObject;

            }
        }
        //System.out.println("PBLOCK:xmlTagValue[" + paragraphSb.toString() + "]");
        return paragraphSb.toString();
    }




    public static String traverseRunBlocks(List<Object> contentTextList) {
        StringBuilder runSb = new StringBuilder();
        Iterator<Object> contentIterator = contentTextList.iterator();
        while (contentIterator.hasNext()) {
            Object contentTextObject = contentIterator.next();

            // w:r only in the root of SDT Blocks
            if(contentTextObject instanceof R){
                //System.out.println("Writing R BLOCK...");
                R rObject = (R) contentTextObject;
                runSb.append(processDocumentRBlock(rObject));
            } else if (contentTextObject instanceof RunIns) {
                RunIns runIns = (RunIns) contentTextObject;
                List<Object> runInsList = runIns.getCustomXmlOrSmartTagOrSdt();

                for (Object runInsObject : runInsList) {
                    if (runInsObject instanceof R) {
                        R rObject = (R) runInsObject;
                        runSb.append(processDocumentRBlock(rObject));
                    }
                }
            }
        }
        LOGGER.debug("RBLOCK:xmlTagValue [{}]", runSb.toString() );
        //System.out.println("RBLOCK:xmlTagValue[" + runSb.toString() + "]");
        return runSb.toString();
    }


    /**
     * Get all Data as HashMaps from Paragraphs
     * Method used in: Table Cell data - Tc
     * @param paragraphObject
     * @return
     */
    public static List<HashMap> getDataMapFromPBlock(P paragraphObject) {

        // Fixed -DR 26/02/15 - Cells can contain several tagged data items. We need to keep hashmap in order hence list is used.
        List<HashMap> hashMapListCellData = new ArrayList<HashMap>();

        // Paragraphs with Run data i.e Table headers
        HashMap map = new HashMap();
        map.put(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN, ParagraphUtils.processDocumentPBlock(paragraphObject));
        // Fixed- DR: 26/02/15 Handle where there is no style
        if(paragraphObject.getPPr() != null
                && paragraphObject.getPPr().getPStyle() != null
                && paragraphObject.getPPr().getPStyle().getVal() != null) {
            map.put(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE, (paragraphObject.getPPr().getPStyle().getVal()));
        }
        //TODO: check if we have several duplicated keys, if so just add to list instead of Hashmap since its gets over written
        LOGGER.debug("getDataMapFromPBlock Cell P SdtRun map[{}]", map);
        hashMapListCellData.add(map);

        LOGGER.debug("getDataMapFromPBlock Cell P [{}]", map);
        //System.out.println("getDataMapFromPBlock Cell P =" + map);

        // Paragraphs with SdtRun data i.e Table cell data
        List<Object> paragraphContents = paragraphObject.getContent();
        for (Object paragraphContent : paragraphContents) {
            if (paragraphContent instanceof JAXBElement && ((JAXBElement) paragraphContent).getValue() instanceof SdtRun) {

                SdtRun sdtRun = (SdtRun) ((JAXBElement) paragraphContent).getValue();
                HashMap hasMapOfCellItem = DocumentUtils.traverseSectionBlocks(sdtRun);
                LOGGER.debug("traverseSectionBlocks Paragraph[Cell P SdtRun] Found content - hasMapOfCellItem[{}]", hasMapOfCellItem);

                // Fixed- DR: 26/02/15 Handle where there is no style
                if(paragraphObject.getPPr() != null
                        && paragraphObject.getPPr().getPStyle() != null
                        && paragraphObject.getPPr().getPStyle().getVal() != null) {
                    hasMapOfCellItem.put(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE, paragraphObject.getPPr().getPStyle().getVal());
                    LOGGER.debug("Getting Style from Paragraph[Cell P SdtRun] - Added style [{}]", hasMapOfCellItem);
                }
                // Fixed- DR: 26/02/15 Several cellItems were being over written in Hashmap so a list is used instead
                hashMapListCellData.add(hasMapOfCellItem);
                LOGGER.debug("Adding content to List<HashMap> Paragraph[Cell P SdtRun] Found content - hasMapOfCellItem[{}]", hasMapOfCellItem);

            }
        }
        LOGGER.debug("Return List<HashMap> of all [Cell P SdtRun] - hashMapListCellData[{}]", hashMapListCellData);
        return hashMapListCellData;
    }



    /**
     * Traverse content from Paragraphs
     * @param paragraphObject
     * @return
     */
    public static String processDocumentPBlock(P paragraphObject) {
        List<Object> pList = paragraphObject.getContent();
        StringBuilder runSb = new StringBuilder();

        for (Object pContentObject : pList) {
            // w:r - concatenate all the R' blocks and RunIns blocks
            if (pContentObject instanceof R) {
                R r = (R) pContentObject;
                runSb.append(processDocumentRBlock(r));
            } else if (pContentObject instanceof RunIns) {
                RunIns runIns = (RunIns) pContentObject;
                List<Object> runInsList = runIns.getCustomXmlOrSmartTagOrSdt();

                for (Object runInsObject : runInsList) {
                    if (runInsObject instanceof R) {
                        R r = (R) runInsObject;
                        runSb.append(processDocumentRBlock(r));
                    }
                }
            }
        }
        //System.out.println("P=" + runSb.toString());
        return runSb.toString();
    }

    /**
     * Traverse content from Run
     * @param runObject
     * @return
     */
    public static String processDocumentRBlock(R runObject) {
        //System.out.println("In processing Document R BLOCK");
        StringBuilder runSb = new StringBuilder();
        boolean fontBold = false;

        List rList = runObject.getContent();
        for (Object rObject : rList) {
            // JAXBElement<org.docx4j.wml.Text>
            if (rObject instanceof JAXBElement) {

                if (((JAXBElement) rObject).getValue() instanceof Text){
                    Text text = (Text) ((JAXBElement) rObject).getValue();
                    runSb.append(text.getValue());
                } else {
                    //System.out.println("*** TAB=" + ((JAXBElement) rObject).getValue().getClass().toString());
                    //System.out.println("*** TAB VALUE=" + ((JAXBElement) rObject).getValue());
                    runSb.append("\t");
                }

                // Add bold tags if found <w:b/> or <w:bCs/>
                if(runObject.getRPr() != null && (runObject.getRPr().getB() !=null) && (runObject.getRPr().getB().isVal())) {
                    fontBold = true;
                }
            }
        }

        // Add font bold tags
        if(fontBold) {
            runSb.append("</b>");
            runSb.insert(0,"<b>");
        }

        //System.out.println("R=" + runSb.toString());
        return runSb.toString();
    }


}
