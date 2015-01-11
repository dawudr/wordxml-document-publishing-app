package com.pearson.btec.service.exporters;

import org.jdom2.CDATA;
import org.jdom2.Element;
import com.pearson.btec.service.importers.DocumentUtilHelper;
import com.pearson.btec.model.btec.Unit;
import com.pearson.btec.model.btec.UnitSection;
import com.pearson.btec.model.btec.UnitTable;

import java.util.*;

/**
 * Created by dawud on 09/11/2014.
 */
public class UnitSection2xml {

    private UnitSection unitSection;

    public UnitSection2xml(UnitSection unitSection) {
        this.unitSection = unitSection;
    }

    public Element toXml() {
        Element sectionElement = null;

        if(unitSection != null && unitSection.getHeading()!=null && unitSection.getHeading().containsKey(DocumentUtilHelper.XML_SECTION_TITLE)) {
//            sectionElement = new Element("meta");
//        } else {
            sectionElement = new Element("section");
            sectionElement.setAttribute("title", (String) unitSection.getHeading().get(DocumentUtilHelper.XML_SECTION_TITLE));
            sectionElement.setAttribute("style", (String) unitSection.getHeading().get(DocumentUtilHelper.XML_SECTION_TITLE_STYLE));
        } else {
            sectionElement = new Element("meta");
        }


        List paragraphs = unitSection.getContentData();
        Iterator iterator = paragraphs.iterator();

        while (iterator.hasNext()) {
            Element paraElement;

            Object paraObject = iterator.next();

            // Deal with Paragraphs
            if (paraObject instanceof  HashMap) {
                HashMap paraMap = (HashMap) paraObject;
                //System.out.println("** PARA =" + paraMap);

                paraElement = new Element((String) paraMap.get(Unit.XML_TAG));
                if(paraMap.containsKey(DocumentUtilHelper.XML_TAG_VALUE_ENCODING)
                        && paraMap.get(DocumentUtilHelper.XML_TAG_VALUE_ENCODING).equals("html")) {
                    CDATA cdata = new CDATA((String) paraMap.get(Unit.XML_TAG_VALUE));
                    paraElement.setContent(cdata);
                } else {
                    paraElement.setText((String) paraMap.get(Unit.XML_TAG_VALUE));
                }
                sectionElement.addContent(paraElement);

            }
            // Deal with Tables
            else if (paraObject instanceof UnitTable) {
                UnitTable unitTable = (UnitTable) paraObject;
                UnitTable2xml unitTable2xml = new UnitTable2xml(unitTable);

                sectionElement.addContent(unitTable2xml.toXml());

            }
        }

        return sectionElement;
    }


}
