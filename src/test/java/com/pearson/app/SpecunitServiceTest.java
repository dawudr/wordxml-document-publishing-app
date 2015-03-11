package com.pearson.app;


import com.pearson.app.model.Specunit;
import com.pearson.app.services.SpecUnitService;
import com.pearson.config.root.RootContextConfig;
import com.pearson.config.root.TestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class})
public class SpecunitServiceTest {


    @Autowired
    private SpecUnitService specUnitService;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testFindSpecunitById() {
        Specunit specunit = new Specunit();
        specunit.setQanNo("S/123/1234");
        specunit.setUnitXML(TransformationServiceTest.textXml);

        specUnitService.addSpecUnit(specunit);

        Specunit resultSpecunit = specUnitService.getSpecUnitById(1L);
        assertNotNull("User is mandatory",resultSpecunit);
        System.out.println("specunit -> " +  resultSpecunit);
    }


    @Test
    public void testFindUserByQan() {
        Specunit specunit = new Specunit();
        specunit.setQanNo("S/123/1234");
        specunit.setUnitXML(TransformationServiceTest.textXml);
        specUnitService.addSpecUnit(specunit);
        Specunit newSpecunit = specUnitService.getSpecUnitByQanNo("S/123/1234");

        Specunit resultSpecunit = specUnitService.getSpecUnitByQanNo("S/123/1234");
        assertNotNull("specunit is mandatory",resultSpecunit);
        assertTrue("Unexpected specunit " + resultSpecunit.getQanNo(), resultSpecunit.getQanNo().equals("S/123/1234"));
        System.out.println("specunit -> " +  resultSpecunit);

    }

    @Test
    public void testUserNotFound() {
        Specunit specunit = specUnitService.getSpecUnitByQanNo("doesnotexist");

        assertNull("specunit must be null", specunit);
    }

    @Test
    public void testCreateValidSpecunit() {
        Specunit specunit = new Specunit();
        specunit.setQanNo("S/123/1234");
        specunit.setUnitXML(TransformationServiceTest.textXml);
        specUnitService.addSpecUnit(specunit);
        Specunit newSpecunit = specUnitService.getSpecUnitByQanNo("S/123/1234");

        assertTrue("Specunit not expected " + newSpecunit.getQanNo(), "S/123/1234".equals(newSpecunit.getQanNo()) );
        assertTrue("Specunit not expected " + Specunit.truncate(newSpecunit.getUnitXML(), 50), TransformationServiceTest.textXml.equals(newSpecunit.getUnitXML()));

        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //assertTrue("password not expected " + user.getPasswordDigest(), passwordEncoder.matches("Password123",user.getPasswordDigest()) );
    }
}
