package com.pearson.btec.model;

import java.util.List;

/**
 * Created by dawud on 16/11/2014.
 */
public class UnitTable {

    private List rows;
    private String tableStr;
    //private List cols;

    public UnitTable (List rows) {
        this.rows = rows;
    }

    public UnitTable(String tableStr) {
        this.tableStr = tableStr;
    }


    public String getTableStr() {
        return tableStr;
    }

    public void setTableStr(String tableStr) {
        this.tableStr = tableStr;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "UnitTable{" +
                "rows=" + rows.toString() +
                ", tableStr='" + tableStr + '\'' +
                '}';
    }
}
