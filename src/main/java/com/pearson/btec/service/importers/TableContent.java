package com.pearson.btec.service.importers;

import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dawud on 23/11/2014.
 */
public class TableContent {

    public static List getTableData(Tbl tbl) {
        List tableData = new ArrayList();
        List<Object> tblObjects = tbl.getContent();


        // Get table header texts
        List tblHeaders = new ArrayList(); //getTableHeaders(tbl);

        List tableRow = null;

        for(Object tblObject : tblObjects) {

            if(tblObject instanceof Tr) {
                tableRow = new ArrayList();
                System.out.println("TableData Tr = " + ((Tr) tblObject).getParaId());

                // First item of row is header text i.e learning aim
//                tableRow.add((String)tblHeaders.get(0));

                Tr tblRow = (Tr) tblObject;
                List<Object> tblRowContents = tblRow.getContent();
                //for (Object tblRowContent : tblRowContents) {
                for (int i = 0; i < tblRowContents.size(); i++) {

                    Object tblRowContent = tblRowContents.get(i);

                    if(((JAXBElement) tblRowContent).getValue() instanceof Tc) {
                        Tc cell = (Tc) ((JAXBElement) tblRowContent).getValue();

                        //System.out.println("TableData Tc Col Number=" + i);


                        //Call getTcData
                        List<Object> cellItems = cell.getContent();
                        List tableColumn = new ArrayList();

                        for(Object cellItem : cellItems) {
                            //System.out.println("TableData Tc = " +  cellItem.getClass());

                            if(cellItem instanceof P) {

                                // Get Table Headings to use as row parent container

                                //TODO: Bug - this should be a cell data
//                                    if(((P)cellItem).getPPr().getPStyle().getVal().equals("Tablehead")) {
//                                        System.out.println("TableData Th P=" + ParagraphUtils.processDocumentPBlock((P) cellItem));
//                                        HashMap map = new HashMap();
//                                        map.put(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN, ParagraphUtils.processDocumentPBlock((P) cellItem));
//                                        map.put(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE, ((P) cellItem).getPPr().getPStyle().getVal());
//
//                                        tableColumn.add(map);
//                                    }

//                                    } else {
                                // Get Table Contents
                                HashMap map = new HashMap();
                                map =  ParagraphUtils.getDataMapFromPBlock((P) cellItem);
                                System.out.println("TableData Cell P=" + map);
                                tableColumn.add(map);




/*
                                List<Object> paragraphContents = ((P) cellItem).getContent();
                                for (Object paragraphContent : paragraphContents) {
                                    if (paragraphContent instanceof JAXBElement && ((JAXBElement) paragraphContent).getValue() instanceof SdtRun) {

                                        SdtRun sdtRun = (SdtRun) ((JAXBElement) paragraphContent).getValue();
                                        HashMap map = DocumentUtils.traverseSectionBlocks(sdtRun);
                                        map.put(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE, ((P)cellItem).getPPr().getPStyle().getVal());

                                        tableColumn.add(map);
                                        System.out.println("TableData Tc SdtRun = " + map);
                                    } else {
                                        HashMap map = new HashMap();
                                        map.put(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN, ParagraphUtils.processDocumentPBlock((P) cellItem));
                                        map.put(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE, ((P) cellItem).getPPr().getPStyle().getVal());
                                        System.out.println("TableData Th P=" + map);
                                        tableColumn.add(map);

                                    }
                                }
 */

//                                    }
                            } else if(cellItem instanceof SdtBlock) {

                                SdtBlock sdtBlock = (SdtBlock) cellItem;
                                HashMap map = DocumentUtils.traverseSectionBlocks(sdtBlock);
                                map.put(DocumentUtilHelper.XML_TAG_VALUE_ENCODING, "html");
                                tableColumn.add(map);
                                System.out.println("TableData Tc SdtBlock = " + map);
                            } else {
                                System.out.println("TableData Tc other = " +  cellItem.getClass());
                            }
                        }
                        tableRow.add(tableColumn);

                    } else if (((JAXBElement) tblRowContent).getValue() instanceof CTSdtCell) {
                        List tableColumn = new ArrayList();

                        CTSdtCell cell = (CTSdtCell)  ((JAXBElement) tblRowContent).getValue();

                        // Call SdtContent
                        HashMap map = new HashMap();
                        map.put(DocumentUtilHelper.XML_TAG, DocumentUtilHelper.findTagFromSdtBoth(cell));
                        // Find Text
                        map.put(DocumentUtilHelper.XML_TAG_VALUE, DocumentUtilHelper.findTextFromSdtBoth(cell));

                        map.put(DocumentUtilHelper.XML_TAG_VALUE_ENCODING, "html");
                        System.out.println("TableData Tc2 = " +  map);
                        tableColumn.add(map);
                        tableRow.add(tableColumn);
                    } else {
                        System.out.println("TableData Tc3 = " +  ((JAXBElement) tblRowContent).getValue().getClass());
                    }

                } // End for loop
            }

            tableData.add(tableRow);
        }

        return tableData;

    }


    private static List getTableHeaders(Tbl tbl) {
        List<Object> tblObjects = tbl.getContent();
        List<String> tblHeaders = new ArrayList<String>();

        // Get table header texts

        for(Object tblObject : tblObjects) {
            if (tblObject instanceof Tr) {

                System.out.println("TableData Tr = " + ((Tr) tblObject).getParaId());
                List tableRow = new ArrayList();

                Tr tblRow = (Tr) tblObject;
                List<Object> tblRowContents = tblRow.getContent();
                for (Object tblRowContent : tblRowContents) {
                    if (((JAXBElement) tblRowContent).getValue() instanceof Tc) {
                        Tc cell = (Tc) ((JAXBElement) tblRowContent).getValue();

                        //Call getTcData
                        List<Object> cellItems = cell.getContent();

                        for (Object cellItem : cellItems) {
                            //System.out.println("TableData Tc = " +  cellItem.getClass());

                            if (cellItem instanceof P) {
                                // Get Table Headings to use as row parent container
                                if (((P) cellItem).getPPr().getPStyle().getVal().equals("Tablehead")) {
                                    tblHeaders.add(ParagraphUtils.processDocumentPBlock((P) cellItem));
                                    System.out.println("TableData Th P=" + ParagraphUtils.processDocumentPBlock((P) cellItem));
                                }
                            }
                        }
                    }
                }
            }
        }
        return tblHeaders;
    }
}
