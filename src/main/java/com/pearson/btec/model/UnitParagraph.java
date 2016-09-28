package com.pearson.btec.model;

import java.util.List;
import java.util.Map;

/**
 * Created by dawud on 22/06/2015.
 */
public class UnitParagraph {

    List<Map> paragraphs;

    public List<Map> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Map> paragraphs) {
        this.paragraphs = paragraphs;
    }

    @Override
    public String toString() {
        return "UnitParagraph{" +
                "paragraphs=" + paragraphs +
                '}';
    }
}
