package com.pearson.btec.service.exporters;

import com.pearson.btec.model.Unit;
import com.pearson.btec.model.UnitTable;
import com.pearson.btec.service.importers.DocumentUtilHelper;
import org.jdom2.CDATA;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dawud on 09/11/2014.
 */
public class UnitTable2xml {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnitTable2xml.class);

    private UnitTable unitTable;

    public UnitTable2xml(UnitTable unitTable) {
        this.unitTable = unitTable;
    }

    public Element toXml() {

        Element tableElement = new Element("table");
        List headersCellText = new ArrayList();


        List rows = unitTable.getRows();
        LOGGER.debug("Unit2XML Table rows[{}]" + rows.toString());

        int rowCount = 0;


        //for (Object rowObject : rows) {
        for (int j = 0; j < rows.size(); j++) {
            LOGGER.debug("Rows=" + j + "/" + rows.size());
            //System.out.println("Rows=" + j + "/" + rows.size());
            List cells = (List) rows.get(j);
            LOGGER.debug("Cells[{}]", cells);

            Element rowElement = new Element("row");

            //for(Object cell : cells) {
            for (int i = 0; i < cells.size(); i++ ) {
                List cellItems = (List) cells.get(i);
                LOGGER.debug("Unit2XML Table getting CellItems - in cell[{}/{}] of row[{}] cellItems[{}]", i, cells.size(), j, cellItems);

                // First Row - Set the header list for the table
                if (!cellItems.isEmpty() && cellItems.get(0) instanceof List) {
                    List<HashMap> firstRowItems = (List) cellItems.get(0);
                    for(HashMap headersMap : firstRowItems) {
                        if (headersMap.containsKey(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE)
                                && headersMap.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE).equals("Tablehead")) {
                            // Skip the column count by setting back to 0 and collect the header and put it in array
                            headersCellText.add((String) headersMap.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN));
                            rowCount = 0;
                            LOGGER.debug("Unit2XML Table First Row is a Header with Tablehead style- headersCellText[{}]", headersCellText);
                        }
                    }
                }

                Element cellElement = new Element("cell");
                // Some cells have sublist of elements including tagged elements so we need to extract them
                // They can either be HashMaps or case where there are several items we used a List<HashMap>
                for (Object cellItem : cellItems) {
                    if (cellItem instanceof HashMap) {
                        LOGGER.debug("Unit2XML Table - cellItem instance of HashMap[{}]", cellItem);
                        Element paraElement = null;
                        HashMap paraMap = (HashMap) cellItem;

                        if (paraMap != null) {
                            if (paraMap.containsKey(DocumentUtilHelper.XML_TAG)) {
                                paraElement = new Element((String) paraMap.get(Unit.XML_TAG));
                                if (paraMap.containsKey(DocumentUtilHelper.XML_TAG_VALUE_ENCODING)
                                        && paraMap.get(DocumentUtilHelper.XML_TAG_VALUE_ENCODING).equals("html")) {
                                    CDATA cdata = new CDATA((String) paraMap.get(Unit.XML_TAG_VALUE));
                                    paraElement.setContent(cdata);
                                } else {
                                    paraElement.setText((String) paraMap.get(Unit.XML_TAG_VALUE));
                                }

                                // Get the style
                                if (paraMap.containsKey(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE)) {
                                    cellElement.setAttribute(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE, (String) paraMap.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE));
                                    if (paraMap.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE).equals("LAheadingtables")) {
                                        // we don't include this row in the row count
                                        rowCount = 0;
                                    }
                                }
                                cellElement.addContent(paraElement);
                                LOGGER.debug("Unit2XML Table HashMap<cellItem> is a ParaMap[{}]", paraMap);
                                //System.out.println("Unit2XML Table=" + paraMap);
                            } else {
                                LOGGER.debug("Unit2XML Table HashMap<cellItem> with No Xml Tag Content ParaMap[{}]", paraMap);
                            }
                        }
                    } else if (cellItem instanceof List) {
                        //Fixed - DR 26/02/15 Hold list of cell para hashmaps
                        LOGGER.debug("Unit2XML Table - cellItem instance of List<HashMap>[{}]", cellItem);
                        List<HashMap> paraMaps = (List) cellItem;
                        for (HashMap paraMap : paraMaps) {
                            Element paraElement = null;

                            if (paraMap != null) {
                                if (paraMap.containsKey(DocumentUtilHelper.XML_TAG)) {
                                    paraElement = new Element((String) paraMap.get(Unit.XML_TAG));
                                    if (paraMap.containsKey(DocumentUtilHelper.XML_TAG_VALUE_ENCODING)
                                            && paraMap.get(DocumentUtilHelper.XML_TAG_VALUE_ENCODING).equals("html")) {
                                        CDATA cdata = new CDATA((String) paraMap.get(Unit.XML_TAG_VALUE));
                                        paraElement.setContent(cdata);
                                    } else {
                                        paraElement.setText((String) paraMap.get(Unit.XML_TAG_VALUE));
                                    }

                                    // Get the style
                                    if (paraMap.containsKey(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE)) {
                                        cellElement.setAttribute(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE, (String) paraMap.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE));
                                        if (paraMap.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE).equals("LAheadingtables")) {
                                            // we don't include this row in the row count
                                            rowCount = 0;
                                        }
                                    }
                                    cellElement.addContent(paraElement);
                                    LOGGER.debug("Unit2XML Table List<cellItem> is a ParaMap[{}]", paraMap);
                                    //System.out.println("Unit2XML Table=" + paraMap);
                                }
                                else {
//                                    cellElement.addContent(paraElement);
                                    LOGGER.debug("Unit2XML Table List<cellItem> is a Header cell with NO Xml Tag content. ParaMap[{}]", paraMap);
                                }
                            }
                        }

                    }
                } // End of For loop


                // Add column header as cell attribute / cell datatype
                if (!headersCellText.isEmpty() && i < headersCellText.size() && headersCellText.get(i) != null) {
                    cellElement.setAttribute(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN, (String) headersCellText.get(i));
                    // Add text to header only cells if cell is empty
                    if (cellElement.getChildren().isEmpty() && !cellItems.isEmpty() && cellItems.get(0) instanceof List) {
                        List<HashMap> firstRowItems = (List) cellItems.get(0);
                        for (HashMap headersMap : firstRowItems) {
                            if (headersMap.containsKey(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE)
                                    && headersMap.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE).equals("Tablehead")) {
                                LOGGER.debug("Unit2XML Table List<cellItem> set header cell with TableHeader. headersCellText[{}] column No[{}]", headersCellText.get(i), i);
                                cellElement.setText((String) headersCellText.get(i));
                            }
                        }
                    }
                }
                cellElement.setAttribute("table-column-no", Integer.toString(i+1));
                rowElement.addContent(cellElement);
            }
            rowElement.setAttribute("data-row-no", Integer.toString(rowCount)); // row count excluding head row, restart after each header row
            rowCount++;
            rowElement.setAttribute("table-row-no", Integer.toString(j+1)); // physical table row count
            tableElement.addContent(rowElement);
        }


        return tableElement;
    }


}