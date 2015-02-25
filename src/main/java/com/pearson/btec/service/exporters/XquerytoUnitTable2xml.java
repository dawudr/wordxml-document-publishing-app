package com.pearson.btec.service.exporters;

import com.pearson.btec.model.UnitTable;
import org.jdom2.Element;


/**
 * Created by dawud on 09/11/2014.
 */
public class XquerytoUnitTable2xml {

    private UnitTable unitTable;

    public XquerytoUnitTable2xml(UnitTable unitTable) {
        this.unitTable = unitTable;
    }


    public Element toXml() {

        Element tableElement = null;
        try {
            //tableElement = TableContentPreProcessor.loadXMLFromString(unitTable.getTableStr());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableElement;
    }


}