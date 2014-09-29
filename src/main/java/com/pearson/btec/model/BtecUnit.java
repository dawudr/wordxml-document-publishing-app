package com.pearson.btec.model;

import org.jdom2.Element;

import java.util.List;

/**
 * Models BTEC Specification Unit Document
 */
public class BtecUnit {

    // Uan number
    private String uanNumber;
    // Body
    private List body;
    // Section
    private Section section;
    // Paragraph
    private Paragraph paragraph;

    public BtecUnit(List body) {
        this.body = body;
    }

    public List getBody() {
        return body;
    }

    public void setBody(List body) {
        this.body = body;
    }

    public String getUanNumber() {
        return uanNumber;
    }

    public void setUanNumber(String uanNumber) {
        this.uanNumber = uanNumber;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Object mpObj : body) {
            if(mpObj instanceof Paragraph) {
                sb.append(mpObj.toString());
            } else if (mpObj instanceof Section) {
                sb.append(mpObj.toString());
            } else {
                if(mpObj instanceof List) {
                    System.out.println("** List **");
                }
            }
        }

        return sb.toString();
    }

    public Element toXML() {
        Element btecElement = new Element("unit");

        if (!uanNumber.isEmpty()) {
            btecElement.addContent(new Element("uan").setText(uanNumber));
        }

        for(Object mpObj : body) {
            if(mpObj instanceof Paragraph) {
                //Paragraph p = (Paragraph) mpObj;
                //btecElement.addContent(p.toXML());
            } else if (mpObj instanceof Section) {
                Section s = (Section) mpObj;
                btecElement.addContent(s.toXML());
            } else {
                if(mpObj instanceof List) {
                    System.out.println("** List **");
                }
            }
        }


        return btecElement;
    }
}