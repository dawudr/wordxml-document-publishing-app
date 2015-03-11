package com.pearson.app.dao;

import com.pearson.app.model.Specunit;
import java.util.List;


/**
 * Created by dawud on 22/02/2015.
 */
public interface SpecUnitDAOInterface {

        public void addSpecUnit(Specunit specunit);
        public List<Specunit> listSpecUnits();
        public Specunit getSpecUnitById(Long id);
        public void updateSpecUnit(Specunit specunit);
        public void removeSpecUnit(Long id);
}
