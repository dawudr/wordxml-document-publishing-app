package com.pearson.btec.model;

import org.jdom2.CDATA;
import org.jdom2.Element;

import java.util.List;

/**
 * Created by Dawud on 18/08/14.
 */
public class Section {
    private String alias;
    private String aliasValue;
    private List<Paragraph> paragraphs;
    private Section section;

    public Section() {};

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAliasValue() {
        return aliasValue;
    }

    public void setAliasValue(String aliasValue) {
        this.aliasValue = aliasValue;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(aliasValue != null) {
            sb.append("<" + alias + ">" + aliasValue );


        if(paragraphs != null && paragraphs.size() > 0) {
            for(Paragraph text : paragraphs) {
                sb.append(text + "\r\n");
            }
        }

            sb.append("</" + alias + ">\r\n");
        }

        return sb.toString();
    }

    public Element toXML() {
        Element sectionElement = null;
        StringBuilder sb = new StringBuilder();
        String validAlias = alias.replaceAll(" ", "").toLowerCase();

        // Create <elementname>
        sectionElement = new Element(validAlias);

        // Create elementValue from SAME section or nested sections with paragraphs
        if(aliasValue != null) {
            sectionElement.setText(aliasValue);
            //sb.append(aliasValue);
        } else {
            if(paragraphs != null && paragraphs.size() > 0) {
                for(Paragraph paragraph : paragraphs) {
                    sb.append(paragraph.getText() + "\r\n");
                }
                CDATA cdata = new CDATA(sb.toString());
                sectionElement.addContent(cdata);
            }
        }

        //System.out.println("TEXT="+ sb.toString());

        return sectionElement;
    }
}
