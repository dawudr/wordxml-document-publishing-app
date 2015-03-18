package com.pearson.app.services;

import com.pearson.app.dao.SpecUnitRepository;
import com.pearson.app.model.Specunit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pearson.app.services.ValidationUtils.assertNotBlank;

@Service
public class SpecUnitService {

    @Autowired
    private SpecUnitRepository specUnitRepository;


    @Transactional
    public void addSpecUnit(Specunit specunit) {
        //assertNotBlank(specunit.getQanNo(), "SpecUnit QanNo type cannot be empty.");
        specUnitRepository.addSpecUnit(specunit);
    }

    @Transactional(readOnly = true)
    public List<Specunit> listSpecUnits() {
        return specUnitRepository.listSpecUnits();
    }

    @Transactional(readOnly = true)
    public Specunit getSpecUnitById(Long id) {
        return specUnitRepository.getSpecUnitById(id);
    }

    @Transactional(readOnly = true)
    public Specunit getSpecUnitByQanNo(String qanNo) {
        return specUnitRepository.getSpecUnitByQanNo(qanNo);
    }

    @Transactional
    public void updateSpecUnit(Specunit specunit) {
        specUnitRepository.updateSpecUnit(specunit);
    }

    @Transactional
    public void removeSpecUnit(Long id) {
        specUnitRepository.removeSpecUnit(id);
    }
}
