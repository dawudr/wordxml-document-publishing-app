package com.pearson.btec.service.exporters;

import com.pearson.btec.model.Unit;
import com.pearson.btec.model.UnitBody;
import com.pearson.btec.model.UnitHeader;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by dawud on 09/11/2014.
 */
public class Unit2xml {

    private Unit unit;

    public Unit2xml(Unit unit) {
        this.unit = unit;
    }




    public Element toXml() {
        Element btecElement = new Element("unit");

        if(unit != null && unit.getUnitMetaData() != null) {
            HashMap docPropMap = unit.getUnitMetaData();

            Iterator iterator = docPropMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry pairs = (Map.Entry) iterator.next();
                Element docPropElement = new Element((String) pairs.getKey());
                docPropElement.setText((String) pairs.getValue());
                btecElement.addContent(docPropElement);
            }

            UnitHeader unitHeader = unit.getUnitHeader();
            UnitHeader2xml unitHeader2xml = new UnitHeader2xml(unitHeader);

            List<Element> unitHeaders = unitHeader2xml.toXml();
            for (Content data : unitHeaders) {
                btecElement.addContent(data);
            }

            UnitBody unitBody = unit.getUnitBody();
            UnitBody2xml unitBody2xml = new UnitBody2xml(unitBody);
            List<Element> unitBodies = unitBody2xml.toXml();
            for (Content data : unitBodies) {
                btecElement.addContent(data);
            }
        }
        return btecElement;
    }


    public String toString() {
        // Output results from BTEC Unit Model
        // create the jdom
        Document jdomDoc = new Document();
        // create root element
        // add Btec Unit model
        Element rootElement = toXml();
        jdomDoc.setRootElement(rootElement);

        // Output as XML
        // create XMLOutputter
        XMLOutputter xml = new XMLOutputter();
        // we want to format the xml. This is used only for demonstration. pretty formatting adds extra spaces and is generally not required.
        xml.setFormat(Format.getPrettyFormat());
        return xml.outputString(jdomDoc);
    }

}