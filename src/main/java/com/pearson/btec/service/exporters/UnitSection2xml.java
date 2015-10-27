package com.pearson.btec.service.exporters;

import com.pearson.btec.model.Unit;
import com.pearson.btec.model.UnitParagraph;
import com.pearson.btec.model.UnitSection;
import com.pearson.btec.model.UnitTable;
import com.pearson.btec.service.importers.DocumentUtilHelper;
import org.jdom2.CDATA;
import org.jdom2.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

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

            //TODO: 22/06/15 I think I will replace this HashMap object with UnitParagraph
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
            else if (paraObject instanceof UnitParagraph) {
                UnitParagraph unitParagraph = (UnitParagraph) paraObject;
                UnitParagraph2xml unitParagraph2xml = new UnitParagraph2xml(unitParagraph);
                sectionElement.addContent(unitParagraph2xml.toXml());
            }
            // Deal with Tables
            else if (paraObject instanceof UnitTable) {
                UnitTable unitTable = (UnitTable) paraObject;

                //TODO: This doesn't work well with track changes future plan to switch to XquerytoUnitTable2xml
                UnitTable2xml unitTable2xml = new UnitTable2xml(unitTable);
                sectionElement.addContent(unitTable2xml.toXml());


                //TODO: New XQuery code to switch to eventually which fixes track changes
                //TODO: The li tags need surrounding with <ul> tag for each level
/*
                XquerytoUnitTable2xml xquerytoUnitTable2xml = new XquerytoUnitTable2xml(unitTable);
                Element e = xquerytoUnitTable2xml.toXml();

                if(e!=null) {
                    sectionElement.addContent(e);
                } else {
                    System.out.println("unitTable is null");
                }

                */

            }
        }

        return sectionElement;
    }


}
