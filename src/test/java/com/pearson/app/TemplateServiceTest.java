package com.pearson.app;


import com.pearson.app.model.Template;
import com.pearson.app.model.TemplateSection;
import com.pearson.app.services.TemplateService;
import com.pearson.config.root.RootContextConfig;
import com.pearson.config.root.TestConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestConfiguration.class, RootContextConfig.class})
public class TemplateServiceTest {

    public static final String USERNAME = "btectest";

    @Autowired
    private TemplateService templateService;


    @PersistenceContext
    private EntityManager em;

    @Before
    public void setup() {

        TemplateSection templateSection = new TemplateSection();
        templateSection.setSectionName("uan");
        templateSection.setSectionStyle(null);
        templateSection.setSectionType(TemplateSection.SECTION_TYPE_META);
        templateSection.setValidateInWordDoc(false);
        templateSection.setShowSection(true);
        TemplateSection templateSection1 = new TemplateSection();
        templateSection1.setSectionName("unitnumber");
        templateSection1.setSectionStyle(null);
        templateSection1.setSectionType(TemplateSection.SECTION_TYPE_PARAGRAPH);
        templateSection1.setValidateInWordDoc(true);
        templateSection1.setShowSection(true);
        TemplateSection templateSection2 = new TemplateSection();
        templateSection2.setSectionName("unittitle");
        templateSection2.setSectionStyle(null);
        templateSection2.setSectionType(TemplateSection.SECTION_TYPE_PARAGRAPH);
        templateSection2.setValidateInWordDoc(true);
        templateSection2.setShowSection(true);
        TemplateSection templateSection3 = new TemplateSection();
        templateSection3.setSectionName("author");
        templateSection3.setSectionStyle(null);
        templateSection3.setSectionType(TemplateSection.SECTION_TYPE_META);
        templateSection3.setValidateInWordDoc(false);
        templateSection3.setShowSection(false);
        TemplateSection templateSection4 = new TemplateSection();
        templateSection4.setSectionName("All with UnitAhead");
        templateSection4.setSectionStyle("UnitAhead");
        templateSection4.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection4.setValidateInWordDoc(true);
        templateSection4.setShowSection(true);
        TemplateSection templateSection5 = new TemplateSection();
        templateSection5.setSectionName("All with UnitBhead");
        templateSection5.setSectionStyle("UnitBhead");
        templateSection5.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection5.setValidateInWordDoc(true);
        templateSection5.setShowSection(true);
        TemplateSection templateSection6 = new TemplateSection();
        templateSection6.setSectionName("Unit introduction");
        templateSection6.setSectionStyle("UnitAhead");
        templateSection6.setSectionType(TemplateSection.SECTION_TYPE_SECTION);
        templateSection6.setValidateInWordDoc(false);
        templateSection6.setShowSection(true);
        TemplateSection templateSection7 = new TemplateSection();
        templateSection7.setSectionName("Learning aims and unit content");
        templateSection7.setSectionStyle("UnitAhead");
        templateSection7.setSectionType(TemplateSection.SECTION_TYPE_SECTION);
        templateSection7.setValidateInWordDoc(false);
        templateSection7.setShowSection(true);
        TemplateSection templateSection8 = new TemplateSection();
        templateSection8.setSectionName("Outline Programme of Suggested Assignments");
        templateSection8.setSectionStyle("UnitAhead");
        templateSection8.setSectionType(TemplateSection.SECTION_TYPE_SECTION);
        templateSection8.setValidateInWordDoc(false);
        templateSection8.setShowSection(true);
        TemplateSection templateSection9 = new TemplateSection();
        templateSection9.setSectionName("Assessment criteria");
        templateSection9.setSectionStyle("UnitAhead");
        templateSection9.setSectionType(TemplateSection.SECTION_TYPE_SECTION);
        templateSection9.setValidateInWordDoc(false);
        templateSection9.setShowSection(true);
        TemplateSection templateSection10 = new TemplateSection();
        templateSection10.setSectionName("Teacher guidance");
        templateSection10.setSectionStyle("UnitAhead");
        templateSection10.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection10.setValidateInWordDoc(false);
        templateSection10.setShowSection(true);
        TemplateSection templateSection11 = new TemplateSection();
        templateSection11.setSectionName("Resources");
        templateSection11.setSectionStyle("UnitAhead");
        templateSection11.setSectionType(TemplateSection.SECTION_TYPE_SECTION);
        templateSection11.setValidateInWordDoc(false);
        templateSection11.setShowSection(true);
        TemplateSection templateSection12 = new TemplateSection();
        templateSection12.setSectionName("Assessment Guidance");
        templateSection12.setSectionStyle("UnitAhead");
        templateSection12.setSectionType(TemplateSection.SECTION_TYPE_SECTION);
        templateSection12.setValidateInWordDoc(false);
        templateSection12.setShowSection(true);
        TemplateSection templateSection13 = new TemplateSection();
        templateSection13.setSectionName("All with hb3");
        templateSection13.setSectionStyle("hb3");
        templateSection13.setSectionType(TemplateSection.SECTION_TYPE_META);
        templateSection13.setValidateInWordDoc(false);
        templateSection13.setShowSection(true);


        Template template = templateService.getTemplateByName("BTEC NATIONALS");
        if(template == null) {
            List<TemplateSection> templateSectionsBtecNational = new ArrayList<TemplateSection>();
            templateSectionsBtecNational.add(templateSection);
            templateSectionsBtecNational.add(templateSection1);
            templateSectionsBtecNational.add(templateSection2);
            templateSectionsBtecNational.add(templateSection3);
            templateSectionsBtecNational.add(templateSection4);
            templateSectionsBtecNational.add(templateSection5);
            templateSectionsBtecNational.add(templateSection6);
            templateSectionsBtecNational.add(templateSection7);
            templateSectionsBtecNational.add(templateSection8);
            templateSectionsBtecNational.add(templateSection9);
            templateSectionsBtecNational.add(templateSection10);
            templateSectionsBtecNational.add(templateSection11);
            templateSectionsBtecNational.add(templateSection12);

            Template templateBtecNational = new Template();
            templateBtecNational.setTemplateName("BTEC NATIONALS");
            templateBtecNational.setDescription("Btec Nationals");
            templateBtecNational.setRevision("1.0");
            templateBtecNational.setXsltScriptLocation("wordxml_unit.xsl");
            templateBtecNational.setxQueryScriptLocation("wordxml_unit2_table.xquery");
            templateBtecNational.setXsdScriptLocation("Unit.xsd");
            templateBtecNational.setTemplateSections(templateSectionsBtecNational);
            templateService.addTemplate(templateBtecNational);
        }

        Template template2 = templateService.getTemplateByName("BTEC L2");
        if(template2 == null) {
            List<TemplateSection> templateSectionsBtecL2 = new ArrayList<TemplateSection>();
            templateSectionsBtecL2.add(templateSection);
            templateSectionsBtecL2.add(templateSection1);
            templateSectionsBtecL2.add(templateSection2);
            templateSectionsBtecL2.add(templateSection4);
            templateSectionsBtecL2.add(templateSection5);
            templateSectionsBtecL2.add(templateSection13);

            Template templateBtecL2 = new Template();
            templateBtecL2.setTemplateName("BTEC L2");
            templateBtecL2.setDescription("Btec Level 2");
            templateBtecL2.setRevision("1.0");
            templateBtecL2.setXsltScriptLocation("wordxml_unit.xsl");
            templateBtecL2.setxQueryScriptLocation("wordxml_unit2_table.xquery");
            templateBtecL2.setXsdScriptLocation("Unit.xsd");
            templateBtecL2.setTemplateSections(templateSectionsBtecL2);
            templateService.addTemplate(templateBtecL2);
        }
    }


    @Test
    public void testFindTemplateById() {
        Template template = templateService.getTemplateById(1);
        assertNotNull("template is mandatory", template);
        System.out.println("template -> " + template);
    }


    @Test
    public void testFindUserByUsername() {
        Template template = templateService.getTemplateByName("BTEC NATIONALS");
        assertNotNull("Template is mandatory", template);
        assertTrue("Unexpected template " + template.getTemplateName(), template.getTemplateName().equals("BTEC NATIONALS"));
        System.out.println("template -> " + template);

    }

    @Test
    public void testFindSecondUserByUsername() {
        Template template = templateService.getTemplateByName("BTEC L2");
        assertNotNull("Template is mandatory", template);
        assertTrue("Unexpected template " + template.getTemplateName(), template.getTemplateName().equals("BTEC L2"));
        System.out.println("template -> " + template);

    }

    @Test
    public void testUserNotFound() {
        Template template = templateService.getTemplateByName("doesnotexist");
        //User user = findUserByUsername("doesnotexist");
        assertNull("template must be null", template);
    }


/*
    private User findUserByUsername(String username) {
        List<User> users = em.createQuery("select u from User u where username = :username")
                .setParameter("username", username).getResultList();

        return users.size() == 1 ? users.get(0) : null;
    }
*/


}
