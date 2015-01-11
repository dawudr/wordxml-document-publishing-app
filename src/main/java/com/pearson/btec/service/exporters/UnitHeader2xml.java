package com.pearson.btec.service.exporters;

import org.jdom2.Element;
import com.pearson.btec.model.btec.Unit;
import com.pearson.btec.model.btec.UnitHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by dawud on 09/11/2014.
 */
public class UnitHeader2xml {

    private UnitHeader unitHeader;

    public UnitHeader2xml(UnitHeader unitHeader) {
        this.unitHeader = unitHeader;
    }

    public List<Element> toXml() {

        List<Element> elementsList = new ArrayList<Element>();
        List<Object> objectList = unitHeader.getProperties();


        for(Object object : objectList ) {
            if (object instanceof HashMap) {
                HashMap map = (HashMap) object;
                if (map != null && !map.isEmpty()) {
                    Element dataElement = new Element((String) map.get(Unit.XML_TAG));
                    dataElement.setText((String) map.get(Unit.XML_TAG_VALUE));
                    elementsList.add(dataElement);
                }
            }
        }

        return elementsList;
    }
}
