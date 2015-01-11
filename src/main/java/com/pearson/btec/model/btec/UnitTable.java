package com.pearson.btec.model.btec;

import java.util.List;

/**
 * Created by dawud on 16/11/2014.
 */
public class UnitTable {

    private List rows;
    //private List cols;

    public UnitTable (List rows) {
        this.rows = rows;
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
                "rows=" + rows +
                '}';
    }
}
