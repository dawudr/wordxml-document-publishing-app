package com.pearson.btec.service.importers;

import org.docx4j.wml.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.Doc;
import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dawud on 23/11/2014.
 */
public class TableContent {
    private static final Logger LOGGER = LoggerFactory.getLogger(TableContent.class);

    public static List getTableData(Tbl tbl) {
        List tableData = new ArrayList();
        List<Object> tblObjects = tbl.getContent();


        // Get table header texts
        List tblHeaders = new ArrayList(); //getTableHeaders(tbl);

        List tableRow = null;

        for(Object tblObject : tblObjects) {

            if(tblObject instanceof Tr) {
                tableRow = new ArrayList();
                LOGGER.debug("TableData Tr [{}]", ((Tr) tblObject).getParaId() );
                //System.out.println("TableData Tr = " + ((Tr) tblObject).getParaId());

                // First item of row is header text i.e learning aim
//                tableRow.add((String)tblHeaders.get(0));

                Tr tblRow = (Tr) tblObject;
                List<Object> tblRowContents = tblRow.getContent();
                // For RowSpan see VMerge
                // http://www.docx4java.org/svn/docx4j/trunk/docx4j/src/main/java/org/docx4j/model/table/Cell.java


                //for (Object tblRowContent : tblRowContents) {
                for (int i = 0; i < tblRowContents.size(); i++) {

                    Object tblRowContent = tblRowContents.get(i);

                    if(tblRowContent != null
                            && tblRowContent instanceof JAXBElement
                            && ((JAXBElement) tblRowContent).getValue() != null) {

                        if (((JAXBElement) tblRowContent).getValue() instanceof Tc) {
                            Tc cell = (Tc) ((JAXBElement) tblRowContent).getValue();

                            //System.out.println("TableData Tc Col Number=" + i);

                            // tableColumn can hold either another List of HashMap
                            List tableColumn = new ArrayList();


                            // colspan
                            int colSpan = 1;
                            try {
                                colSpan = cell.getTcPr().getGridSpan().getVal().intValue();
                            } catch (NullPointerException ne) {
                                // no gridSpan
                            }

                            // Add colspan to list cellsData
                            if(colSpan > 1) {
                                HashMap<String, Integer> colSpanMap = new HashMap<String, Integer>();
                                colSpanMap.put(DocumentUtilHelper.XML_TABLE_CELL_COLSPAN, colSpan);
                                tableColumn.add(colSpanMap);
                            }

                            // Add rowspan
                            String rowSpan = null;
                            try {
                                if(cell.getTcPr().getVMerge() != null && cell.getTcPr().getVMerge().getVal() != null) {
                                    // Check if vMerge - 'restart' value which is start of rowspan
                                    rowSpan = cell.getTcPr().getVMerge().getVal();
                                } else if(cell.getTcPr().getVMerge() != null) {
                                    // Check if vMerge - cell is included in a rowspan
                                    rowSpan = "continue";
                                }
                            } catch (NullPointerException ne) {
                                // no vMerge
                            }

                            // Add rowspan to list cellsData
                            if(rowSpan !=null) {
                                HashMap<String, String> rowSpanMap = new HashMap<String, String>();
                                rowSpanMap.put(DocumentUtilHelper.XML_TABLE_CELL_ROWSPAN, rowSpan);
                                tableColumn.add(rowSpanMap);
                            }

                            //Call getTcData
                            List<Object> cellItems = cell.getContent();

                            // Add cell content to list cellsData
                            for (Object cellItem : cellItems) {
                                //System.out.println("TableData Tc = " +  cellItem.getClass());

                                if (cellItem instanceof P) {

                                    // Get Table Headings to use as row parent container
                                    // Get Table Contents
                                    // Fixed- DR 26/02/15 Now returns a list of cell items since there are several sdtruns with items of content.
                                    List cellsData = ParagraphUtils.getDataMapFromPBlock((P) cellItem);
                                    LOGGER.debug("Adding to tableColum - TableData Cell P List<cellsData>[{}]", cellsData);
                                    tableColumn.add(cellsData);


                                } else if (cellItem instanceof SdtBlock) {

                                    SdtBlock sdtBlock = (SdtBlock) cellItem;
                                    HashMap map = DocumentUtils.traverseSectionBlocks(sdtBlock);
                                    map.put(DocumentUtilHelper.XML_TAG_VALUE_ENCODING, "html");
                                    tableColumn.add(map);
                                    LOGGER.debug("TableData Tc SdtBlock [{}]", map);
                                    //System.out.println("TableData Tc SdtBlock = " + map);
                                } else {
                                    LOGGER.debug("TableData Tc other [{}]", cellItem.getClass());
                                    //System.out.println("TableData Tc other = " +  cellItem.getClass());
                                }
                            }
                            tableRow.add(tableColumn);

                        } else if (((JAXBElement) tblRowContent).getValue() instanceof CTSdtCell) {
                            List tableColumn = new ArrayList();

                            CTSdtCell cell = (CTSdtCell) ((JAXBElement) tblRowContent).getValue();

                            // Call SdtContent
                            HashMap map = new HashMap();
                            map.put(DocumentUtilHelper.XML_TAG, DocumentUtilHelper.findTagFromSdtBoth(cell));
                            // Find Text
                            map.put(DocumentUtilHelper.XML_TAG_VALUE, DocumentUtilHelper.findTextFromSdtBoth(cell));

                            map.put(DocumentUtilHelper.XML_TAG_VALUE_ENCODING, "html");
                            LOGGER.debug("TableData Tc2 [{}]", map);
                            //System.out.println("TableData Tc2 = " +  map);
                            tableColumn.add(map);
                            tableRow.add(tableColumn);
                        } else {
                            LOGGER.debug("TableData Tc3 [{}]", ((JAXBElement) tblRowContent).getValue().getClass());
                            //System.out.println("TableData Tc3 = " +  ((JAXBElement) tblRowContent).getValue().getClass());
                        }
                    } //End of if tblRowContent is JAXBElement

                } // End for loop
            }

            LOGGER.debug("Adding tablerow[List<tableColumn>] to List<TableData> where tableRow[{}]", tableRow);
            tableData.add(tableRow);
        }
        LOGGER.debug("Return ArrayList() tableData[{}]", tableData);
        return tableData;

    }


    private static List getTableHeaders(Tbl tbl) {
        List<Object> tblObjects = tbl.getContent();
        List<String> tblHeaders = new ArrayList<String>();

        // Get table header texts

        for(Object tblObject : tblObjects) {
            if (tblObject instanceof Tr) {
                LOGGER.debug("TableData Tr [{}]", ((Tr) tblObject).getParaId());
                //System.out.println("TableData Tr = " + ((Tr) tblObject).getParaId());
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
                                    LOGGER.debug("TableData Th P [{}]", ParagraphUtils.processDocumentPBlock((P) cellItem));
                                    //System.out.println("TableData Th P=" + ParagraphUtils.processDocumentPBlock((P) cellItem));
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
