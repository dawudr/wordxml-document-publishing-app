package com.pearson.btec.service.exporters;

import com.pearson.btec.model.Unit;
import com.pearson.btec.model.UnitParagraph;
import org.jdom2.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dawud on 09/11/2014.
 */
public class UnitParagraph2xml {

    private UnitParagraph unitParagraph;

    public UnitParagraph2xml(UnitParagraph unitParagraph) {
        this.unitParagraph = unitParagraph;
    }

    public Element toXml() {

        List<Map> paragraphs = unitParagraph.getParagraphs();
        Element paragraphElement = new Element("paragraph");

        for(Map paragraph : paragraphs ) {
            if (paragraph != null && !paragraph.isEmpty()) {
                Element dataElement = new Element((String) paragraph.get(Unit.XML_TAG));
                dataElement.setText((String) paragraph.get(Unit.XML_TAG_VALUE));
                paragraphElement.addContent(dataElement);
            }
        }

        return paragraphElement;
    }
}
