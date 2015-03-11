package com.pearson.btec.model;


import java.util.HashMap;

/**
 * Unit
 *
 */
public class Unit {

    private HashMap unitMetaData;
    private UnitHeader unitHeader;
    private UnitBody unitBody;
    public static final String XML_TAG = "tag";
    public static final String XML_TAG_VALUE = "value";

    public UnitHeader getUnitHeader() {
        return unitHeader;
    }

    public void setUnitHeader(UnitHeader unitHeader) {
        this.unitHeader = unitHeader;
    }

    public UnitBody getUnitBody() {
        return unitBody;
    }

    public void setUnitBody(UnitBody unitBody) {
        this.unitBody = unitBody;
    }

    public HashMap getUnitMetaData() {
        return unitMetaData;
    }

    public void setUnitMetaData(HashMap unitMetaData) {
        this.unitMetaData = unitMetaData;
    }

    @Override
    public String toString() {
        return "\r\n" +
               "{ \"unit\" :\r\n" +
               "\t{ \"unitHeader\" :\r\n" +
               "\t\t{ \r\n" +
               unitHeader.toString() +
               "\t\t}\r\n" +
               "\t},\r\n" +
               "\t{ \"unitBody=\" : \r\n" + unitBody.toString() + "\r\n" +
               "\t}\r\n" +
               "}";
    }
}


