package com.pearson.btec.service.exporters;

import org.jdom2.CDATA;
import org.jdom2.Element;
import com.pearson.btec.service.importers.DocumentUtilHelper;
import com.pearson.btec.model.btec.Unit;
import com.pearson.btec.model.btec.UnitSection;
import com.pearson.btec.model.btec.UnitTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dawud on 09/11/2014.
 */
public class UnitTable2xml {

    private UnitTable unitTable;

    public UnitTable2xml(UnitTable unitTable) {
        this.unitTable = unitTable;
    }

    public Element toXml() {

        Element tableElement = new Element("table");
        List headersCellText = new ArrayList();


        List rows = unitTable.getRows();
        int rowCount = 0;


        //for (Object rowObject : rows) {
        for (int j = 0; j < rows.size(); j++) {

            List cells = (List) rows.get(j);

            Element rowElement = new Element("row");

            //for(Object cell : cells) {
            for (int i = 0; i < cells.size(); i++ ) {
                List c = (List) cells.get(i);

                // Set the header list for the table
                if (!c.isEmpty() && c.get(0) instanceof HashMap
                        && ((HashMap) c.get(0)).containsKey(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE)
                        && ((HashMap) c.get(0)).get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE).equals("Tablehead")) {
                    headersCellText.add((String) ((HashMap) c.get(0)).get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN));
                    rowCount = 0;
                }

                Element cellElement = new Element("cell");
                // Some cells have sublist of elements including tagged elements so we need to extract them
                for (Object o : c) {
                    if (o instanceof HashMap) {
                        Element paraElement = null;
                        HashMap paraMap = (HashMap) o;

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
                                        rowCount = 0;
                                    }
                                }
                                cellElement.addContent(paraElement);
                                //System.out.println("Unit2XML Table=" + paraMap);
                            }
                        }
                    }
                } // End of For loop

                // Add column header as cell attribute / cell datatype
                if (!headersCellText.isEmpty() && i < headersCellText.size() && headersCellText.get(i) != null) {
                    cellElement.setAttribute(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN, (String) headersCellText.get(i));
                    // Add text to header only cells if cell is empty
                    if(cellElement.getChildren().isEmpty() && !c.isEmpty() && c.get(0) instanceof HashMap
                            && ((HashMap) c.get(0)).containsKey(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE)
                            && ((HashMap) c.get(0)).get(DocumentUtilHelper.XML_TABLE_HEADER_COLUMN_STYLE).equals("Tablehead")) {
                        cellElement.setText((String)headersCellText.get(i));
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