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
        //LOGGER.debug("Unit2XML Table rows[{}]" + rows.toString());

        int rowCount = 0;
        Element rowsElement = new Element("rows");

        //for (Object rowObject : rows) {
        for (int j = 0; j < rows.size(); j++) {
            LOGGER.debug("Rows=" + j + "/" + rows.size());
            //System.out.println("Rows=" + j + "/" + rows.size());
            List cells = (List) rows.get(j);
            LOGGER.debug("Cells[{}]", cells);

            Element rowElement = new Element("row");
            Element cellsElement = new Element("cells");
            int columnCount = 0;

            // First Row - Set the header list for the table
            if (j == 0) {
                cellsElement = getHeaderRow(unitTable);
                columnCount = cells.size();
            } else {

                //for(Object cell : cells) {
                for (int i = 0; i < cells.size(); i++) {
                    List cellContents = (List) cells.get(i);
                    LOGGER.debug("Unit2XML Table getting CellItems - in cell[{}/{}] of row[{}] cellContents[{}]", i, cells.size(), j, cellContents);
                    boolean hasContent = false;

                    // Remaining rows
                    Element cellElement = new Element("cell");
                    int colSpanValue = getColSpan(cellContents);
                    cellElement.setAttribute(DocumentUtilHelper.XML_TABLE_CELL_COLSPAN, String.valueOf(colSpanValue));

                    // Some cells have sublist of elements including tagged elements so we need to extract them
                    // They can either be HashMaps or case where there are several items we used a List<HashMap>
                    for (Object cellContent : cellContents) {

                        if (cellContent instanceof HashMap) {
                            LOGGER.debug("Unit2XML Table - cellContent instance of HashMap[{}]", cellContent);
                            HashMap paraMap = (HashMap) cellContent;

                            // Get the rowspan
                            if (paraMap.containsKey(DocumentUtilHelper.XML_TABLE_CELL_ROWSPAN)) {
                                if (((String) paraMap.get(DocumentUtilHelper.XML_TABLE_CELL_ROWSPAN)).equals("restart")) {
                                    int rowSpanGot = getRowSpan(unitTable, j, columnCount);
                                    LOGGER.debug("Rowspan [{}]", rowSpanGot);
                                    cellElement.setAttribute(DocumentUtilHelper.XML_TABLE_CELL_ROWSPAN, String.valueOf(rowSpanGot));
                                }
                            }

                            // Get the style
                            String styleStr = getStyle(cellContent);
                            if (styleStr != null && !styleStr.isEmpty()) {
                                cellElement.setAttribute(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE, styleStr);
                            }

                            // Get the contents Element
                            Element contentElement = getContent(cellContent);
                            if (contentElement != null) {
                                cellElement.addContent(contentElement);
                                hasContent = true;
                            }
                        } else if (cellContent instanceof List) {

                            //Fixed - DR 26/02/15 Hold list of cell para hashmaps
                            LOGGER.debug("Unit2XML Table - cellContent instance of List<HashMap>[{}]", cellContent);
                            List<HashMap> cellContentList = (List) cellContent;

                            // Create a subelement to hold these item and collect content of each
                            Element listElement = null;

                            // Cell has a list of HashMap content items
                            for (HashMap cellContentItem : cellContentList) {

                                String styleStr = getStyle(cellContentItem);
                                if (styleStr != null && !styleStr.isEmpty()) {
                                    cellElement.setAttribute(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE, styleStr);
                                }

                                // Get the contents Element
                                Element contentElement = getContent(cellContentItem);
                                if (contentElement != null) {
                                    if (listElement == null) {
                                        listElement = new Element("list");
                                    }
                                    listElement.addContent(contentElement);
                                    hasContent = true;
                                }
                                LOGGER.debug("Unit2XML Table List<cellContent> is a cellContentItem[{}]", cellContentItem);
                            }

                            if (listElement != null) {
                                cellElement.addContent(listElement);
                            }
                        }
                    } // End of For loop

                    // Get the colspan
                    if (colSpanValue > 0) {
                        columnCount += colSpanValue;
                    } else {
                        columnCount++;
                    }


                    if (hasContent) {
                        //columnCount++;
                        cellElement.setAttribute("table-column-no", Integer.toString(i + 1));
                        cellsElement.addContent(cellElement);
                    }
                }
            }


            cellsElement.setAttribute("count", Integer.toString(columnCount));
            rowElement.addContent(cellsElement);
            rowElement.setAttribute("table-row-no", Integer.toString(j + 1)); // physical table row count

            rowsElement.setAttribute("count", Integer.toString(rows.size()));
            rowsElement.addContent(rowElement);

        }
        tableElement.addContent(rowsElement);
        return tableElement;
    }

    private int getRowSpan(UnitTable unitTable, int currentRow, int currentCol) {
        LOGGER.debug("In getRowSpan for row[{}] col[{}]...", currentRow, currentCol);
        List rows = unitTable.getRows();
        // Get first row column size
        int rowSpan = 1;

        // Start at next row
        int nextRow = currentRow + 1;
        for (int j = nextRow; j < rows.size(); j++) {
            List cells = (List) rows.get(j);

            int sumCols = 0;
            // Find Cell below current column, increment rowSpan count if rowspan="continue" otherwise return the count
            for (int i = 0; i < cells.size(); i++) {
                List cell = (List) cells.get(i);
                LOGGER.debug("GetRowSpan - in cell[{}/{}] of row[{}] cell[{}]", currentCol, cells.size(), j, cell);

                // Count the cell and add colspan until = currentcol
                if (sumCols < currentCol) {
                    int colSpanValue = getColSpan(cell);
                    sumCols += colSpanValue;

                } else if (sumCols == currentCol) {
                    boolean rowSpanFound = false;
                    for (Object cellContent : cell) {
                        if (cellContent instanceof HashMap) {
                            HashMap paraMap = (HashMap) cellContent;

                            if (paraMap != null && paraMap.containsKey(DocumentUtilHelper.XML_TABLE_CELL_ROWSPAN)) {
                                if (((String) paraMap.get(DocumentUtilHelper.XML_TABLE_CELL_ROWSPAN)).equals("continue")) {
                                    rowSpan++;
                                    rowSpanFound = true;
                                }
                            }
                        }
                    }
                    // End when rowspaning not found in the next row or cell beneath
                    if (!rowSpanFound) {
                        return rowSpan;
                    }
                }
            }
        }
        return rowSpan;
    }

    private int getColSpan(List cell) {
        // initialise with 1 otherwise overwrite with actual colspan value from OpenXML
        int colspan = 1;

        for (Object cellContent : cell) {

            if (cellContent instanceof HashMap) {
                HashMap paraMap = (HashMap) cellContent;

                // Get the colspan
                if (paraMap.containsKey(DocumentUtilHelper.XML_TABLE_CELL_COLSPAN)) {
                    try {
                        colspan = (Integer) paraMap.get(DocumentUtilHelper.XML_TABLE_CELL_COLSPAN);
                    } catch (NumberFormatException e) {
                        // Colspan is not number
                        LOGGER.error("Error unable to parse Colspan as a number" + e.getMessage());
                    }
                }
            }
        }
        return colspan;
    }

    private String getStyle(Object cell) {
        String style = null;
        HashMap paraMap = (HashMap) cell;
        if (paraMap.containsKey(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE)) {
            style = (String) paraMap.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE);
        }

        return style;
    }


    private Element getHeaderRow(UnitTable unitTable) {

        List rows = unitTable.getRows();
        LOGGER.debug("Unit2XML Table getHeaderRow");
        Element cellsElement = new Element("cells");

        // First Row - Set the header list for the table
        List cells = (List) rows.get(0);
        cellsElement.setAttribute(DocumentUtilHelper.XML_TABLE_CELL_ROWSPAN, String.valueOf(getColSpan(cells)));

        for (int i = 0; i < cells.size(); i++) {
            List<HashMap> cellContents = (List) ((List) cells.get(i)).get(0);
            Element cellElement = new Element("cell");
            for (HashMap cellContent : cellContents) {
                // Search for Tableheadwhite or Tablehead
                if (cellContent.containsKey(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE)
                        && ((cellContent.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE)).equals("Tablehead")
                        || cellContent.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE).equals("Tableheadwhite"))) {

                    String headerText = (String) cellContent.get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN);
                    LOGGER.debug("HeaderRow is [{}]", headerText);

                    // Add to cell
                    if(headerText != null) {
                        cellElement.setText(headerText);
                        cellElement.setAttribute(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN, headerText);
                    }
                }
            }
            cellsElement.addContent(cellElement);
        }
        return cellsElement;
    }

    private Element getContent(Object cell) {
        HashMap paraMap = (HashMap) cell;
        Element paraElement = null;

        if (paraMap.containsKey(DocumentUtilHelper.XML_TAG)) {
            paraElement = new Element((String) paraMap.get(Unit.XML_TAG));
            if (paraMap.containsKey(DocumentUtilHelper.XML_TAG_VALUE_ENCODING)
                    && paraMap.get(DocumentUtilHelper.XML_TAG_VALUE_ENCODING).equals("html")) {
                CDATA cdata = new CDATA((String) paraMap.get(Unit.XML_TAG_VALUE));
                paraElement.setContent(cdata);
            } else {
                paraElement.setText((String) paraMap.get(Unit.XML_TAG_VALUE));
            }
            LOGGER.debug("Unit2XML Table HashMap<cellContent> is a ParaMap[{}]", paraMap);
        } else {
            LOGGER.debug("Unit2XML Table HashMap<cellContent> with No Xml Tag Content ParaMap[{}]", paraMap);
        }
        return paraElement;
    }

}