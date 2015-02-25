package com.pearson.btec.model;


import java.util.HashMap;
import java.util.List;

/**
 * Properties
 *
 */
public class UnitHeader {

    List properties;

    public List getProperties() {
        return properties;
    }

    public void setProperties(List<HashMap> properties) {
        this.properties = properties;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        for(Object prop : properties) {
            if (prop instanceof HashMap) {
                HashMap propMap = (HashMap) prop;

                sb.append("\t\t\t\"" + propMap.get(Unit.XML_TAG) + "\" : \"" + propMap.get(Unit.XML_TAG_VALUE) + "\",\r\n");
            }
        }

        return sb.toString();
    }
}


