package com.pearson.btec.model;

import java.util.List;

/**
 * Known defects/unfinished features:
 *
 */
public class UnitBody {

    List<UnitSection> unitSections;

    public List<UnitSection> getUnitSections() {
        return unitSections;
    }

    public void setUnitSections(List<UnitSection> unitSections) {
        this.unitSections = unitSections;
    }

    @Override
    public String toString() {
        return "UnitBody{" +
                "unitSections=" + unitSections +
                '}';
    }
}


