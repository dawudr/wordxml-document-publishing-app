package com.pearson.btec.service.exporters;

import org.jdom2.Element;
import com.pearson.btec.model.btec.UnitBody;
import com.pearson.btec.model.btec.UnitSection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dawud on 09/11/2014.
 */
public class UnitBody2xml {

    private UnitBody unitBody;

    public UnitBody2xml(UnitBody unitBody) {
        this.unitBody = unitBody;
    }

    public List<Element> toXml() {

        List<Element> elementsList = new ArrayList<Element>();
        List<UnitSection> unitSections = unitBody.getUnitSections();

        for(UnitSection unitSection : unitSections) {

            UnitSection2xml unitSection2xml = new UnitSection2xml(unitSection);
            Element dataElement = unitSection2xml.toXml();
            elementsList.add(dataElement);
        }

        return elementsList;
    }


}
