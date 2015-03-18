package com.pearson.app.init;

import com.pearson.app.model.Template;
import com.pearson.app.model.TemplateSection;
import com.pearson.app.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * This is a initializing bean that inserts some test data in the database. It is only active in
 * the development profile, to see the data login with user123 / PAssword2 and do a search starting on
 * 1st of January 2015.
 */
@Component
public class TestDataInitializer {

    @Autowired
    private EntityManagerFactory entityManagerFactory;


    public void init() throws Exception {

        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User("btectest", "Password123", "test@email.com", "Btec", "Test", User.ROLE_ADMIN);
        session.persist(user);
/*        User user0 = new User("test123", "Password123", "test@email.com", "test", "123", User.ROLE_ADMIN);
        session.persist(user0);
        User user1 = new User("pwinser", "Password123", "paul.winser@pearson.com", "Paul", "Winser", User.ROLE_ADMIN);
        session.persist(user1);
        User user2 = new User("drahman", "Password123", "dawud.rahman@aqovia.com", "Dawud", "Rahman", User.ROLE_ADMIN);
        session.persist(user2);
        User user3 = new User("iselimas", "Password123", "ioannis.selimas@pearson.com", "Ioannis", "Selimas", User.ROLE_EDITOR);
        session.persist(user3);
        User user4 = new User("mmoran", "Password123", "maura.moran@pearson.com", "Maura", "Moran", User.ROLE_EDITOR);
        session.persist(user4);
        User user5 = new User("avergara", "Password123", "andres.vergara@pearson.com", "Andres", "Vergara", User.ROLE_EDITOR);
        session.persist(user5);
        User user6 = new User("testviewer2", "Password123", "dawud.rahman@aqovia.com", "Paul", "Winser", User.ROLE_AUTHOR);
        session.persist(user6);
        User user7 = new User("testadmin", "Password123", "dawud.rahman@aqovia.com", "test", "Admin", User.ROLE_ADMIN);
        session.persist(user7);
        User user8 = new User("testeditor", "Password123", "dawud.rahman@aqovia.com", "test", "Editor", User.ROLE_EDITOR);
        session.persist(user8);
        User user9 = new User("testauthor", "Password123", "dawud.rahman@aqovia.com", "test", "Author", User.ROLE_AUTHOR);
        session.persist(user9);
        User user10 = new User("testviewer", "Password123", "dawud.rahman@aqovia.com", "test", "Viewer", User.ROLE_VIEWER);
        session.persist(user10);*/

        TemplateSection templateSection = new TemplateSection();
        templateSection.setSectionName("uan");
        templateSection.setSectionType(TemplateSection.SECTION_TYPE_META);
        templateSection.setRequiredInValidateWordDoc(false);

        TemplateSection templateSection1 = new TemplateSection();
        templateSection1.setSectionName("unitnumber");
        templateSection1.setSectionType(TemplateSection.SECTION_TYPE_META);
        templateSection1.setRequiredInValidateWordDoc(false);

        TemplateSection templateSection2 = new TemplateSection();
        templateSection2.setSectionName("unittitle");
        templateSection2.setSectionType(TemplateSection.SECTION_TYPE_META);
        templateSection2.setRequiredInValidateWordDoc(true);

        TemplateSection templateSection3 = new TemplateSection();
        templateSection3.setSectionName("author");
        templateSection3.setSectionType(TemplateSection.SECTION_TYPE_META);
        templateSection3.setRequiredInValidateWordDoc(false);

        TemplateSection templateSection4 = new TemplateSection();
        templateSection4.setSectionName("All with UnitAhead");
        templateSection4.setSectionStyle("UnitAhead");
        templateSection4.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection4.setRequiredInValidateWordDoc(true);
        templateSection4.setIsFilteredByValue(true); // if true then dont use section name

        TemplateSection templateSection5 = new TemplateSection();
        templateSection5.setSectionName("All with UnitBhead");
        templateSection5.setSectionStyle("UnitBhead");
        templateSection5.setSectionType(TemplateSection.SECTION_TYPE_HEADER);

        TemplateSection templateSection6 = new TemplateSection();
        templateSection6.setSectionName("Unit introduction");
        templateSection6.setSectionStyle("UnitAhead");
        templateSection6.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection6.setRequiredInValidateWordDoc(true);

        TemplateSection templateSection7 = new TemplateSection();
        templateSection7.setSectionName("Learning aims and unit content");
        templateSection7.setSectionStyle("UnitAhead");
        templateSection7.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection7.setRequiredInValidateWordDoc(true);

        TemplateSection templateSection8 = new TemplateSection();
        templateSection8.setSectionName("Outline Programme of Suggested Assignments");
        templateSection8.setSectionStyle("UnitAhead");
        templateSection8.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection8.setRequiredInValidateWordDoc(false);

        TemplateSection templateSection9 = new TemplateSection();
        templateSection9.setSectionName("Assessment criteria");
        templateSection9.setSectionStyle("UnitAhead");
        templateSection9.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection9.setRequiredInValidateWordDoc(false);

        TemplateSection templateSection10 = new TemplateSection();
        templateSection10.setSectionName("Teacher guidance");
        templateSection10.setSectionStyle("UnitAhead");
        templateSection10.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection10.setRequiredInValidateWordDoc(false);

        TemplateSection templateSection11 = new TemplateSection();
        templateSection11.setSectionName("Resources");
        templateSection11.setSectionStyle("UnitBhead");
        templateSection11.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection11.setRequiredInValidateWordDoc(false);

        TemplateSection templateSection12 = new TemplateSection();
        templateSection12.setSectionName("Assessment Guidance");
        templateSection12.setSectionStyle("UnitAhead");
        templateSection12.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSection12.setRequiredInValidateWordDoc(false);

        TemplateSection templateSectionbtecl3 = new TemplateSection();
        templateSectionbtecl3.setSectionName("All with hb3");
        templateSectionbtecl3.setSectionStyle("hb3");
        templateSectionbtecl3.setSectionType(TemplateSection.SECTION_TYPE_HEADER);
        templateSectionbtecl3.setRequiredInValidateWordDoc(true);
        templateSectionbtecl3.setIsFilteredByValue(true); // if true then dont use section name

        Set<TemplateSection> templateSectionsBtecNational = new HashSet<TemplateSection>();
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

        Set<TemplateSection> templateSectionsBtecL2 = new HashSet<TemplateSection>();
        templateSectionsBtecL2.add(templateSectionbtecl3);

            Template template = new Template();
        template.setTemplateName("BTEC L2");
        template.setDescription("Btec Level 2");
        template.setRevision("1.0");
        template.setXsltScriptLocation("wordxml_unit.xsl");
        template.setxQueryScriptLocation("wordxml_unit2_table.xquery");
        template.setXsdScriptLocation("Unit.xsd");
        template.setTemplateSections(templateSectionsBtecL2);
            session.persist(template);

        Template template2 = new Template();
        template2.setTemplateName("BTEC NATIONALS");
        template2.setDescription("Btec Nationals");
        template2.setRevision("1.0");
        template2.setXsltScriptLocation("wordxml_unit.xsl");
        template2.setxQueryScriptLocation("wordxml_unit2_table.xquery");
        template2.setXsdScriptLocation("Unit.xsd");
            template2.setTemplateSections(templateSectionsBtecNational);

            session.persist(template2);


        Calendar calendarLastWeek = Calendar.getInstance();
        calendarLastWeek.setTime(new Date());
        calendarLastWeek.add(Calendar.WEEK_OF_YEAR, -1);

        Calendar calendarLastMonth = Calendar.getInstance();
        calendarLastMonth.setTime(new Date());
        calendarLastMonth.add(Calendar.MONTH, -1);



/*

        session.persist(new Transformation(user, new Date(), "U/111/0001",
                "Unit 44_FBC.docx", "Unit 44_FBC.xml", "Unit 44_FBC-iqs.xml",
                "44", "Manufacturing Secondary Machining Processes", "Paul Winser", "btecnationals",
                new Date(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_UNREAD));

        session.persist(new Transformation(user, new Date(), "U/222/0001",
                "Unit 45_FBC.docx", "Unit 45_FBC.xml", "Unit 45_FBC-iqs.xml",
                "45", "Big Data", "Paul Winser", "btecnationals",
                new Date(), Transformation.TRANSFORM_STATUS_FAIL, "Fatal error, please try again", Transformation.GENERAL_STATUS_UNREAD));

        session.persist(new Transformation(user, new Date(), "R/111/0002",
                "Unit 46_FBC.docx", "Unit 46_FBC.xml", "Unit 46_FBC-iqs.xml",
                "46", "Business Application of Social Media", "Andres Vergara", "btecnationals",
                new Date(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, new Date(), "R/222/0002",
                "Unit 47_FBC.docx", "Unit 47_FBC.xml", "Unit 47_FBC-iqs.xml",
                "47", "Business Process Modelling Tools", "Andres Vergara", "btecnationals",
                new Date(), Transformation.TRANSFORM_STATUS_FAIL_TRANSFORM_XML_TO_IQSXML_XSLT, "Iqs transform errors", Transformation.GENERAL_STATUS_READ));



        session.persist(new Transformation(user, calendarLastWeek.getTime(), "M/111/0003",
                "Unit 48_FBC.docx", "Unit 48_FBC.xml", "Unit 48_FBC-iqs.xml",
                "48", "Communication Technologies", "Andres Vergara", "btecnationals",
                new Date(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_MODIFIED));

        session.persist(new Transformation(user, calendarLastWeek.getTime(), "M/222/0003",
                "Unit 49_FBC.docx", "Unit 49_FBC.xml", "Unit 49_FBC-iqs.xml",
                "49", "Computer Forensics", "Paul Winser", "btecnationals",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_FAIL_IQS_VALIDATION, "IQS Schema Validation failed", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, calendarLastWeek.getTime(), "M/333/0001",
                "Unit 50_FBC.docx", "Unit 50_FBC.xml", "Unit 50_FBC-iqs.xml",
                "50", "Computer Games Development", "Andres Vergara", "btecnationals",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_FAIL_VALIDATE_WORD, "Word Document Validation failed", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, calendarLastWeek.getTime(), "M/333/0002",
                "Unit 51_FBC.docx", "Unit 51_FBC.xml", "Unit 51_FBC-iqs.xml",
                "51", "Database Development", "Andres Vergara", "btecnationals",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_FAIL_TRANSFORM, "Transformation failed", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, calendarLastMonth.getTime(), "M/333/0003",
                "Unit_52.docx", "Unit_52.xml", "Unit_52-iqs.xml",
                "52", "Digital Audio", "Andres Vergara", "btecnationals",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, calendarLastMonth.getTime(), "M/444/0001",
                "Unit_53.docx", "Unit_53.xml", "Unit_53-iqs.xml",
                "53", "Digital Graphics and Animation", "Paul Winser", "btecnationals",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, calendarLastMonth.getTime(), "M/444/0002",
                "Unit_54.docx", "Unit_54.xml", "Unit_54-iqs.xml",
                "52", "Principles and Applications of Electronic Devices and Circuits", "Paul Winser", "btecnationals",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, calendarLastMonth.getTime(), "M/444/0003",
                "Unit_55.docx", "Unit_55.xml", "Unit_55-iqs.xml",
                "55", "Principles of Telecommunications", "Paul Winser", "btecnationals",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_READ));


        session.persist(new Transformation(user, new Date(), "U/123/0001",
                "Unit_18.docx", "Unit_18.xml", "Unit_18-iqs.xml",
                "18", "Computational Thinking", "Paul Winser", "bteclevel2",
                new Date(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_UNREAD));

        session.persist(new Transformation(user, new Date(), "U/123/0002",
                "Unit_19.docx", "Unit_19.xml", "Unit_19-iqs.xml",
                "19", "Digital Product Analysis", "Andres Vergara", "bteclevel2",
                new Date(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_UNREAD));

        session.persist(new Transformation(user, new Date(), "U/123/0003",
                "Unit_20.docx", "Unit_20.xml", "Unit_20-iqs.xml",
                "20", "Human Computer Interaction", "Paul Winser", "bteclevel2",
                new Date(), Transformation.TRANSFORM_STATUS_FAIL_VALIDATE_WORD, "Word Document Validation failed", Transformation.GENERAL_STATUS_UNREAD));

        session.persist(new Transformation(user, new Date(), "U/123/0004",
                "Unit_21.docx", "Unit_21.xml", "Unit_21-iqs.xml",
                "21", "IT Security", "Paul Winser", "bteclevel2",
                new Date(), Transformation.TRANSFORM_STATUS_FAIL, "Transformation failed", Transformation.GENERAL_STATUS_UNREAD));

        session.persist(new Transformation(user, calendarLastWeek.getTime(), "R/123/0005",
                "Unit_22.docx", "Unit_22.xml", "Unit_22-iqs.xml",
                "22", "Managing and Supporting Systems", "Andres Vergara", "bteclevel2",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, calendarLastWeek.getTime(), "R/123/0006",
                "Unit_23.docx", "Unit_23.xml", "Unit_23-iqs.xml",
                "23", "Mobile Apps Development", "Andres Vergara", "bteclevel2",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, calendarLastWeek.getTime(), "M/123/0007",
                "Unit_24.docx", "Unit_24.xml", "Unit_24-iqs.xml",
                "24", "Computational Thinking 8", "Paul Winser", "bteclevel2",
                new Date(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_MODIFIED));

        session.persist(new Transformation(user, calendarLastWeek.getTime(), "M/123/0008",
                "Unit_25.docx", "Unit_25.xml", "Unit_25-iqs.xml",
                "25", "Multimedia Technologies and Design", "Andres Vergara", "bteclevel2",
                new Date(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_MODIFIED));



        session.persist(new Transformation(user, calendarLastMonth.getTime(), "R/123/0009",
                "Unit_26.docx", "Unit_26.xml", "Unit_26-iqs.xml",
                "26", "Network Analysis", "Andres Vergara", "bteclevel2",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, calendarLastMonth.getTime(), "R/123/0010",
                "Unit_27.docx", "Unit_27.xml", "Unit_27-iqs.xml",
                "27", "Network Operating Systems", "Andres Vergara", "bteclevel2",
                calendarLastWeek.getTime(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_READ));

        session.persist(new Transformation(user, calendarLastMonth.getTime(), "M/123/0011",
                "Unit_28.docx", "Unit_28.xml", "Unit_28-iqs.xml",
                "28", "Network Solutions and Security", "Paul Winser", "bteclevel2",
                new Date(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_MODIFIED));

        session.persist(new Transformation(user, calendarLastMonth.getTime(), "M/123/0012",
                "Unit_29.docx", "Unit_29.xml", "Unit_29-iqs.xml",
                "29", "Object Oriented Programming", "Andres Vergara", "bteclevel2",
                new Date(), Transformation.TRANSFORM_STATUS_SUCCESS, "No errors were found", Transformation.GENERAL_STATUS_MODIFIED));

*/
        transaction.commit();
    }
}
