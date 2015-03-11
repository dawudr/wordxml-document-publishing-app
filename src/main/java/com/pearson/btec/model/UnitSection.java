package com.pearson.btec.model;


import java.util.List;
import java.util.Map;

/**
 * UnitSection
 *
 */
public class UnitSection {

    Map heading;
    //List<HashMap> paragraphs;
    // Can be List with Table or HashMap
    List<Object> contentData;

    public UnitSection(Map heading) {
        this.heading = heading;
    }

    public List<Object> getContentData() {
        return contentData;
    }

    public void setContentData(List<Object> contentData) {
        this.contentData = contentData;
    }

    public Map getHeading() {
        return heading;
    }

    public void setHeading(Map heading) {
        this.heading = heading;
    }

    @Override
    public String toString() {
        return "UnitSection{" +
                "heading='" + heading + '\'' +
                ", contentData=" + contentData.toString() +
                '}';
    }
}
