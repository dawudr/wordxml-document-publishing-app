package com.pearson.btec.service.importers;

import org.docx4j.TraversalUtil;
import org.docx4j.XmlUtils;
import org.docx4j.finders.ClassFinder;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.util.*;


/**
 * Known defects/unfinished features:
 * - Second level + <ul><li></li></ul> list item not captured. Combined into one single long list
 */
public class DocumentUtilHelper {

    public static final String XML_TAG = "tag";
    public static final String XML_TAG_VALUE = "value";
    public static final String XML_TAG_VALUE_ENCODING = "encoding";

    public static final String XML_SECTION_TITLE_STYLE = "style";
    public static final String XML_SECTION_TITLE = "title";
    public static final String XML_TABLE_HEADER_COLUMN_STYLE = "style";
    public static final String XML_TABLE_HEADER_COLUMN = "column-name";


    public static String findTagFromSdtBoth(Object sdtObject) {
        // Find Tag
        String tagStr = null;
        ClassFinder tagfinder = new ClassFinder(Tag.class); // <----- change this to suit
        if(sdtObject instanceof SdtBlock) {
            SdtBlock sdtBlock = (SdtBlock) sdtObject;
            new TraversalUtil(sdtBlock.getSdtPr(), tagfinder);
        } else if(sdtObject instanceof SdtRun) {
            SdtRun sdtRun = (SdtRun) sdtObject;
            new TraversalUtil(sdtRun.getSdtPr(), tagfinder);
        } else if(sdtObject instanceof CTSdtCell) {
            CTSdtCell cTSdtCell = (CTSdtCell) sdtObject;
            new TraversalUtil(cTSdtCell.getSdtPr(), tagfinder);
        }

        for (Object tagFound : tagfinder.results) {
            Object tagFoundObject = XmlUtils.unwrap(tagFound);
            // this is ok, provided the results of the Callback
            // won't be marshalled
            if (tagFoundObject instanceof Tag) {
                Tag tag = (Tag)tagFoundObject;
                tagStr = tag.getVal();
                System.out.println("\r\nTC:xmlTagKey[" + tag.getVal() + "]");
            }
        }
        return tagStr;
    }


    public static String findTextFromSdtBoth(Object sdtObject) {
        // Find Text
        StringBuilder textSb = new StringBuilder();

        ClassFinder textfinder = new ClassFinder(P.class); // <----- change this to suit
        if(sdtObject instanceof SdtBlock) {
            SdtBlock sdtBlock = (SdtBlock) sdtObject;
            new TraversalUtil(sdtBlock.getSdtContent(), textfinder);
        } else if(sdtObject instanceof SdtRun) {
            SdtRun sdtRun = (SdtRun) sdtObject;
            new TraversalUtil(sdtRun.getSdtContent(), textfinder);
        } else if(sdtObject instanceof CTSdtCell) {
            CTSdtCell cTSdtCell = (CTSdtCell) sdtObject;
            new TraversalUtil(cTSdtCell.getSdtContent(), textfinder);
        } else if(sdtObject instanceof RunIns) {
            RunIns runIns = (RunIns) sdtObject;
            new TraversalUtil(runIns.getCustomXmlOrSmartTagOrSdt(),textfinder);
        }



        List<Object> pFinderResults = textfinder.results;
        if(!pFinderResults.isEmpty()) {
            //System.out.println("** OUTSIDE OF P BLOCKS **");
            textSb.append(ParagraphUtils.traverseParagraphBlocks(pFinderResults));
            // No P Blocks found so search for R Blocks only.
        } else {

            //TODO: Remove if not required
            //System.out.println("** OUTSIDE of P BLOCK = " + sdtObject.getClass().toString());
            ClassFinder rfinder = new ClassFinder(R.class); // <----- change this to suit
            if(sdtObject instanceof SdtBlock) {
                SdtBlock sdtBlock = (SdtBlock) sdtObject;
                new TraversalUtil(sdtBlock.getSdtContent(), rfinder);
            } else if(sdtObject instanceof SdtRun) {
                SdtRun sdtRun = (SdtRun) sdtObject;
                new TraversalUtil(sdtRun.getSdtContent(), rfinder);
            } else if(sdtObject instanceof CTSdtCell) {
                CTSdtCell cTSdtCell = (CTSdtCell) sdtObject;
                new TraversalUtil(cTSdtCell.getSdtContent(), rfinder);
            } else if(sdtObject instanceof RunIns) {
                RunIns runIns = (RunIns) sdtObject;
                new TraversalUtil(runIns.getCustomXmlOrSmartTagOrSdt(), rfinder);
            }

            List<Object> rFinderResults = rfinder.results;
            if(!rFinderResults.isEmpty()) {
                //System.out.println("** R BLOCKS **");
                textSb.append(ParagraphUtils.traverseRunBlocks(rFinderResults));
            }
        }

        return textSb.toString();
    }





}


