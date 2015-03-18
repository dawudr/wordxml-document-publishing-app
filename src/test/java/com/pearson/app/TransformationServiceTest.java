package com.pearson.app;

import com.pearson.app.model.*;
import com.pearson.app.services.*;
import com.pearson.config.root.RootContextConfig;
import com.pearson.config.root.TestConfiguration;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {TestConfiguration.class, RootContextConfig.class})
public class TransformationServiceTest extends TestCase {

    @Autowired
    private TransformationService transformationService;
    @Autowired
    private UserService userService;
    @Autowired
    private SpecUnitService specUnitService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private ImageService imageService;

    private Template template;

    @PersistenceContext
    private EntityManager em;

    @Before
    public void setup() {

        this.template = templateService.getTemplateByName("BTEC NATIONALS");
        if(this.template == null) {
            TemplateSection templateSection = new TemplateSection();
            templateSection.setSectionName("uan");
            templateSection.setSectionType(TemplateSection.SECTION_TYPE_META);
            templateSection.setRequiredInValidateWordDoc(false);

            TemplateSection templateSection1 = new TemplateSection();
            templateSection1.setSectionName("unitnumber");
            templateSection1.setSectionType(TemplateSection.SECTION_TYPE_META);
            templateSection1.setRequiredInValidateWordDoc(false);

            Set<TemplateSection> templateSectionsBtecNational = new HashSet<TemplateSection>();
            templateSectionsBtecNational.add(templateSection);
            templateSectionsBtecNational.add(templateSection1);

            this.template = new Template();
            this.template.setTemplateName("BTEC NATIONALS");
            this.template.setDescription("Btec Nationals");
            this.template.setRevision("1.0");
            this.template.setXsltScriptLocation("wordxml_unit.xsl");
            this.template.setxQueryScriptLocation("wordxml_unit2_table.xquery");
            this.template.setXsdScriptLocation("Unit.xsd");
            this.template.setTemplateSections(templateSectionsBtecNational);
            templateService.addTemplate(this.template);
        }
    }

    private Image createImageRecord(String qanNo) {

            Image image = new Image();
            image.setName(qanNo + ".docx");
            //image.setThumbnailFilename(thumbnailFilename);
            image.setNewFilename("NewDocument.docx");
            image.setContentType("application/msword");
            image.setSize(1L);
            image.setUrl("/processtransform/view/");
            image.setDeleteUrl("/processtransform/delete/");
            image.setDeleteType("DELETE");
            image.setDateCreated(new Date());
            image.setLastUpdated(new Date());
            image = imageService.addImage(image);

        return image;

    }

    @Test
    public void firstTransformationsExist() {
        User user = userService.getUserByUsername("btectest1");
        if(user == null) {
            userService.addUser(new User("btectest1", "Password123", "test@email.com", "Btec", "Test", User.ROLE_ADMIN));
            user = userService.getUserByUsername("btectest1");
        }


        Specunit specunit = new Specunit();
        specunit.setQanNo("S/123/1234");
        specunit.setUnitXML(textXml);
//        specunit.setTransformation(newTransformation);
        specUnitService.addSpecUnit(specunit);


        Transformation newTransformation = new Transformation();
        newTransformation.setUser(user);
        newTransformation.setDate(new Date());
        newTransformation.setQanNo("S/123/1234");
        newTransformation.setUnitNo("44");
        newTransformation.setUnitTitle("Manufacturing Secondary Machining Processes");
        newTransformation.setAuthor("Paul Winser");
        newTransformation.setTemplate(this.template);
        newTransformation.setWordfilename("Unit 44_FBC.doc");
        newTransformation.setOpenxmlfilename("Unit 44_FBC-open.xml");
        newTransformation.setIqsxmlfilename("S_123_1234.xml");
        newTransformation.setLastmodified(new Date());
        newTransformation.setTransformStatus(Transformation.TRANSFORM_STATUS_SUCCESS);
        newTransformation.setMessage("No errors were found");
        newTransformation.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);
        newTransformation.setSpecunit(specunit);
        newTransformation.setImage(createImageRecord("S/123/1234"));
        transformationService.addTransformation(newTransformation);



        Transformation test = transformationService.getTransformationByQan("S/123/1234");
        Long testId = test.getId();


        Transformation result = transformationService.getTransformationById(testId);
        assertTrue("Result with same Qan expected, but got Qan - " + result.getQanNo(), result.getQanNo().equals("S/123/1234"));

    }


    @Test
    public void severalTransformationsExist() {
        Specunit specunit = new Specunit();
        specunit.setQanNo("T/999/0001");
        specunit.setUnitXML(textXml);
        specUnitService.addSpecUnit(specunit);

        Specunit specunit2 = new Specunit();
        specunit2.setQanNo("T/999/0002");
        specunit2.setUnitXML(textXml);
        specUnitService.addSpecUnit(specunit2);

        User user = userService.getUserByUsername("btectest2");
        if(user == null) {
            userService.addUser(new User("btectest2", "Password123", "test@email.com", "Btec", "Test", User.ROLE_ADMIN));
            user = userService.getUserByUsername("btectest2");
        }

        Transformation newTransformation = new Transformation();
        newTransformation.setUser(user);
        newTransformation.setDate(new Date());
        newTransformation.setQanNo("T/999/0001");
        newTransformation.setUnitNo("44");
        newTransformation.setUnitTitle("Manufacturing Secondary Machining Processes");
        newTransformation.setAuthor("Paul Winser");
        newTransformation.setTemplate(this.template);
        newTransformation.setWordfilename("Unit 44_FBC.doc");
        newTransformation.setOpenxmlfilename("Unit 44_FBC-open.xml");
        newTransformation.setIqsxmlfilename("S_123_1234.xml");
        newTransformation.setLastmodified(new Date());
        newTransformation.setTransformStatus(Transformation.TRANSFORM_STATUS_SUCCESS);
        newTransformation.setMessage("No errors were found");
        newTransformation.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);
        newTransformation.setSpecunit(specunit);
        newTransformation.setImage(createImageRecord("T/999/0001"));
        transformationService.addTransformation(newTransformation);


        Transformation newTransformation1 = new Transformation();
        newTransformation1.setUser(user);
        newTransformation1.setDate(new Date());
        newTransformation1.setQanNo("T/999/0002");
        newTransformation1.setUnitNo("45");
        newTransformation1.setUnitTitle("Manufacturing Secondary Machining Processes");
        newTransformation1.setAuthor("Paul Winser");
        newTransformation.setTemplate(this.template);
        newTransformation1.setWordfilename("Unit 45_FBC.doc");
        newTransformation1.setOpenxmlfilename("Unit 45_FBC-open.xml");
        newTransformation1.setIqsxmlfilename("S_123_1239.xml");
        newTransformation1.setLastmodified(new Date());
        newTransformation1.setTransformStatus(Transformation.TRANSFORM_STATUS_SUCCESS);
        newTransformation1.setMessage("No errors were found");
        newTransformation1.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);
        newTransformation1.setSpecunit(specunit2);
        newTransformation1.setImage(createImageRecord("T/999/0002"));
        transformationService.addTransformation(newTransformation1);

        //List<Transformation> result = transformationService.listTransformations();
        //assertTrue("Several result expected, total " + result.size(), result.size() > 1);
    }


    @Test
    public void testCreateValidTransformation() {

        Specunit specunit = new Specunit();
        specunit.setQanNo("T/999/0004");
        specunit.setUnitXML(textXml);
        specUnitService.addSpecUnit(specunit);

        User user = userService.getUserByUsername("btectest3");
        if(user == null) {
            userService.addUser(new User("btectest3", "Password123", "test@email.com", "Btec", "Test", User.ROLE_ADMIN));
            user = userService.getUserByUsername("btectest3");
        }

        Transformation newTransformation = new Transformation();
        newTransformation.setUser(user);
        newTransformation.setDate(new Date());
        newTransformation.setQanNo("T/999/0004");
        newTransformation.setUnitNo("44");
        newTransformation.setUnitTitle("Manufacturing Secondary Machining Processes");
        newTransformation.setAuthor("Paul Winser");
        newTransformation.setTemplate(this.template);
        newTransformation.setWordfilename("Unit 44_FBC.doc");
        newTransformation.setSpecunit(specunit);
        newTransformation.setOpenxmlfilename("Unit 44_FBC-open.xml");
        newTransformation.setIqsxmlfilename("S_123_1234.xml");
        newTransformation.setLastmodified(new Date());
        newTransformation.setTransformStatus(Transformation.TRANSFORM_STATUS_SUCCESS);
        newTransformation.setMessage("No errors were found");
        newTransformation.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);
        newTransformation.setImage(createImageRecord("T/999/0004"));


        transformationService.addTransformation(newTransformation);

        Transformation transformation = transformationService.getTransformationByQan("T/999/0004");

        assertTrue("UAN not expected " + transformation.getQanNo(), "T/999/0004".equals(transformation.getQanNo()));
        assertTrue("UnitNo not expected " + transformation.getUnitNo(), "44".equals(transformation.getUnitNo()));
        assertTrue("UnitNo not expected " + transformation.getUnitTitle(), "Manufacturing Secondary Machining Processes".equals(transformation.getUnitTitle()));
        assertTrue("UnitNo not expected " + transformation.getAuthor(), "Paul Winser".equals(transformation.getAuthor()));

    }

    @Test
    public void deleteTransformations() {

        Specunit specunit = new Specunit();
        specunit.setQanNo("T/999/0007");
        specunit.setUnitXML(textXml);
        specUnitService.addSpecUnit(specunit);

        User user = userService.getUserByUsername("btectest4");
        if(user == null) {
            userService.addUser(new User("btectest4", "Password123", "test@email.com", "Btec", "Test", User.ROLE_ADMIN));
            user = userService.getUserByUsername("btectest4");
        }

        Transformation newTransformation = new Transformation();
        newTransformation.setUser(user);
        newTransformation.setDate(new Date());
        newTransformation.setQanNo("T/999/0007");
        newTransformation.setUnitNo("7");
        newTransformation.setUnitTitle("Manufacturing Secondary Machining Processes");
        newTransformation.setAuthor("Paul Winser");
        newTransformation.setTemplate(this.template);
        newTransformation.setWordfilename("Unit 44_FBC.doc");
        newTransformation.setSpecunit(specunit);
        newTransformation.setOpenxmlfilename("Unit 44_FBC-open.xml");
        newTransformation.setIqsxmlfilename("S_123_1234.xml");
        newTransformation.setLastmodified(new Date());
        newTransformation.setTransformStatus(Transformation.TRANSFORM_STATUS_SUCCESS);
        newTransformation.setMessage("No errors were found");
        newTransformation.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);
        newTransformation.setImage(createImageRecord("T/999/0007"));


        transformationService.addTransformation(newTransformation);

        Transformation transformationBefore = transformationService.getTransformationByQan("T/999/0007");

        transformationService.removeTransformation(transformationBefore.getId());

        Transformation transformationAfter = transformationService.getTransformationByQan("T/999/0007");
        assertNull("Transformation was not deleted with id" + transformationBefore.getId(), transformationAfter);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteTransformationsNull() {
        transformationService.removeTransformation(999L);
    }

    @Test
    public void saveTransformations() {

        Specunit specunit = new Specunit();
        specunit.setQanNo("T/999/0008");
        specunit.setUnitXML(textXml);
        specUnitService.addSpecUnit(specunit);

        Specunit specunit2 = new Specunit();
        specunit2.setQanNo("T/999/0009");
        specunit2.setUnitXML(textXml);
        specUnitService.addSpecUnit(specunit2);

        User user = userService.getUserByUsername("btectest5");
        if(user == null) {
            userService.addUser(new User("btectest5", "Password123", "test@email.com", "Btec", "Test", User.ROLE_ADMIN));
            user = userService.getUserByUsername("btectest5");
        }


        Transformation newTransformation = new Transformation();
        newTransformation.setUser(user);
        newTransformation.setDate(new Date());
        newTransformation.setQanNo("T/999/0008");
        newTransformation.setUnitNo("44");
        newTransformation.setUnitTitle("Manufacturing Secondary Machining Processes");
        newTransformation.setAuthor("Paul Winser");
        newTransformation.setTemplate(this.template);
        newTransformation.setWordfilename("Unit 44_FBC.doc");
        newTransformation.setOpenxmlfilename("Unit 44_FBC-open.xml");
        newTransformation.setIqsxmlfilename("S_123_1234.xml");
        newTransformation.setLastmodified(new Date());
        newTransformation.setTransformStatus(Transformation.TRANSFORM_STATUS_SUCCESS);
        newTransformation.setMessage("No errors were found");
        newTransformation.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);
        newTransformation.setSpecunit(specunit);
        newTransformation.setImage(createImageRecord("T/999/0008"));
        transformationService.addTransformation(newTransformation);

        Transformation newTransformation1 = new Transformation();
        newTransformation1.setUser(user);
        newTransformation1.setDate(new Date());
        newTransformation1.setQanNo("T/999/0009");
        newTransformation1.setUnitNo("45");
        newTransformation1.setUnitTitle("Manufacturing Secondary Machining Processes");
        newTransformation1.setAuthor("Paul Winser");
        newTransformation1.setTemplate(this.template);
        newTransformation1.setWordfilename("Unit 45_FBC.doc");
        newTransformation1.setSpecunit(specunit);
        newTransformation1.setOpenxmlfilename("Unit 45_FBC-open.xml");
        newTransformation1.setIqsxmlfilename("S_123_1239.xml");
        newTransformation1.setLastmodified(new Date());
        newTransformation1.setTransformStatus(Transformation.TRANSFORM_STATUS_SUCCESS);
        newTransformation1.setMessage("No errors were found");
        newTransformation1.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);
        newTransformation1.setSpecunit(specunit2);
        newTransformation1.setImage(createImageRecord("T/999/0009"));
        transformationService.addTransformation(newTransformation1);


//        transformationService.updateTransformation(newTransformation);
//        transformationService.updateTransformation(newTransformation1);

        Transformation transformation1 = transformationService.getTransformationByQan("T/999/0008");
        Transformation transformation2 = transformationService.getTransformationByQan("T/999/0009");


        assertTrue("Transformation1 Qan not as expected: " + transformation1.getQanNo(), "T/999/0008".equals(transformation1.getQanNo()));
        assertTrue("Transformation2 Qan not as expected: " + transformation2.getQanNo(), "T/999/0009".equals(transformation2.getQanNo()));

    }

    @Test
    public void saveTransformationWithXmlDocumentAsString() {
        Specunit specunit = new Specunit();
        specunit.setQanNo("T/999/0010");
        specunit.setUnitXML(textXml);
        specUnitService.addSpecUnit(specunit);

        User user = userService.getUserByUsername("btectest6");
        if(user == null) {
            userService.addUser(new User("btectest6", "Password123", "test@email.com", "Btec", "Test", User.ROLE_ADMIN));
            user = userService.getUserByUsername("btectest6");
        }

        Transformation newTransformation = new Transformation();
        newTransformation.setUser(user);
        newTransformation.setDate(new Date());
        newTransformation.setQanNo("T/999/0010");
        newTransformation.setUnitNo("44");
        newTransformation.setUnitTitle("Manufacturing Secondary Machining Processes");
        newTransformation.setAuthor("Paul Winser");
        newTransformation.setTemplate(this.template);
        newTransformation.setWordfilename("Unit 44_FBC.doc");
        newTransformation.setSpecunit(specunit);
        newTransformation.setOpenxmlfilename("Unit 44_FBC-open.xml");
        newTransformation.setIqsxmlfilename("S_123_1234.xml");
        newTransformation.setLastmodified(new Date());
        newTransformation.setTransformStatus(Transformation.TRANSFORM_STATUS_SUCCESS);
        newTransformation.setMessage("No errors were found");
        newTransformation.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);
        newTransformation.setImage(createImageRecord("T/999/0010"));


        transformationService.updateTransformation(newTransformation);

        Transformation transformation1 = transformationService.getTransformationByQan("T/999/0010");

        assertTrue("Transformation Xml not as expected: " + transformation1.getSpecunit() + "Xml returned: " + Specunit.truncate(transformation1.getSpecunit().getUnitXML(), 50), textXml.equals(transformation1.getSpecunit().getUnitXML()));

    }


    @Test
    public void testSpecUnitIdInTransformation() {
        Specunit specunit = new Specunit();
        specunit.setQanNo("T/999/0011");
        specunit.setUnitXML(textXml);
        specUnitService.addSpecUnit(specunit);

        User user = userService.getUserByUsername("btectest7");
        if(user == null) {
            userService.addUser(new User("btectest7", "Password123", "test@email.com", "Btec", "Test", User.ROLE_ADMIN));
            user = userService.getUserByUsername("btectest7");
        }


        Transformation newTransformation = new Transformation();
        newTransformation.setUser(user);
        newTransformation.setDate(new Date());
        newTransformation.setQanNo("T/999/0011");
        newTransformation.setUnitNo("44");
        newTransformation.setUnitTitle("Manufacturing Secondary Machining Processes");
        newTransformation.setAuthor("Paul Winser");
        newTransformation.setTemplate(this.template);
        newTransformation.setWordfilename("Unit 44_FBC.doc");
        newTransformation.setSpecunit(specunit);
        newTransformation.setOpenxmlfilename("Unit 44_FBC-open.xml");
        newTransformation.setIqsxmlfilename("S_123_1234.xml");
        newTransformation.setLastmodified(new Date());
        newTransformation.setTransformStatus(Transformation.TRANSFORM_STATUS_SUCCESS);
        newTransformation.setMessage("No errors were found");
        newTransformation.setGeneralStatus(Transformation.GENERAL_STATUS_UNREAD);
        newTransformation.setImage(createImageRecord("T/999/0011"));

        transformationService.updateTransformation(newTransformation);

        Transformation transformation1 = transformationService.getTransformationByQan("T/999/0011");

        assertTrue("Transformation Xml not as expected: " + transformation1.getSpecunit() + "Xml returned: " +
                Specunit.truncate(transformation1.getSpecunit().getUnitXML(), 50), transformation1.getSpecunit().getQanNo().equals("T/999/0011"));
        System.out.println("SpecUnitId=" + transformation1.getSpecunit().getId());
        System.out.println("SpecUnit XML=" + transformation1.getSpecunit().getUnitXML());
    }

    public static final String textXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<unit>\n" +
            "  <uan>H/123/1234</uan>\n" +
            "  <dc-description>Missing</dc-description>\n" +
            "  <dc-title>BTEC 2016 Specification</dc-title>\n" +
            "  <author>lober_h;winser_p</author>\n" +
            "  <dc-keyword>H/123/1234</dc-keyword>\n" +
            "  <dc-version>15.0000</dc-version>\n" +
            "  <dc-company>Pearson Education Limited 2011</dc-company>\n" +
            "  <dc-application>Microsoft Office Word</dc-application>\n" +
            "  <unitnumber>44</unitnumber>\n" +
            "  <unittitle>Manufacturing Secondary Machining Processes</unittitle>\n" +
            "  <level>3</level>\n" +
            "  <unittype>Optional</unittype>\n" +
            "  <glhvalue>60</glhvalue>\n" +
            "  <section title=\"Unit introduction\" style=\"UnitAhead\">\n" +
            "    <unitinbrief><![CDATA[<p>This unit covers how machining processes can be used to manufacture complicated shapes by the removal (cutting) of material.  This will involve the set-up and use of secondary processing machines to manufacture a component.</p>]]></unitinbrief>\n" +
            "    <introduction><![CDATA[<p>Many of the products and components we use daily would not be available without secondary machining processes. The use of these processes to manufacture a product or component is sometimes easy to spot, like a machine bearing or the nut holding in place a brake shoe on a bicycle. For other products or components, like curtains, it is less easy to spot. This is because you need to first think about how the curtains have been manufactured. Hence, the curtains are manufactured using machines containing components that have been subjected to secondary machining processes.</p><p>As a future engineer you will need to understand and acquire practical skills in a range of machining processes. The knowledge and practical skills are required to enable engineers to design feasible solutions to engineering problems. For example, a feasible solution is one that can be manufactured using secondary machining processes. This unit will prepare learners well for a mechanical or manufacturing engineering apprenticeship or degree course and for technician level role as a machine setter and setter-operators.</p><p>In this unit you will cover the technology and characteristics of a range of traditional, such as turning, and specialist, such as broaching, machining processes. You will develop health and safety skills required to work on secondary machining processes and gain practical skills and understanding to set-up and operate traditional secondary machining processes to manufacture a component. Finally learners will reflect on their skills and understanding applied to and behaviours whilst manufacturing a prototype component.</p>]]></introduction>\n" +
            "  </section>\n" +
            "  <section title=\"Learning aims\" style=\"UnitAhead\">\n" +
            "    <learningobjectiveref>A</learningobjectiveref>\n" +
            "    <learningobjectivetext>Understand the technology and characteristics of secondary machining processes that are widely used in industry.</learningobjectivetext>\n" +
            "    <learningobjectiveref>B</learningobjectiveref>\n" +
            "    <learningobjectivetext>Set-up traditional secondary processing machines safely to manufacture a component.</learningobjectivetext>\n" +
            "    <learningobjectiveref>C</learningobjectiveref>\n" +
            "    <learningobjectivetext>Carry out traditional secondary machining processes to safely manufacture a component.</learningobjectivetext>\n" +
            "    <learningobjectiveref>D</learningobjectiveref>\n" +
            "    <learningobjectivetext>Review the processes used to machine a component and reflect on own performance.</learningobjectivetext>\n" +
            "    <table>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"1\">\n" +
            "        <cell column-name=\"&#x9;Learning Aim\" table-column-no=\"1\">Learning Aim</cell>\n" +
            "        <cell column-name=\"Key teaching areas\" table-column-no=\"2\">Key teaching areas</cell>\n" +
            "        <cell column-name=\"Suggested summary of assessment evidence\" table-column-no=\"3\">Suggested summary of assessment evidence</cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"2\">\n" +
            "        <cell style=\"LearningObjective\" column-name=\"&#x9;Learning Aim\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>A</learningobjectiveref>\n" +
            "          <learningobjectivetext>Understand the technology and characteristics of secondary machining processes that are widely used in industry</learningobjectivetext>\n" +
            "        </cell>\n" +
            "        <cell style=\"KeyTeachingcoltext\" column-name=\"Key teaching areas\" table-column-no=\"2\">\n" +
            "          <keyteachingref>A1</keyteachingref>\n" +
            "          <keyteachingtext>Traditional secondary machining processes</keyteachingtext>\n" +
            "          <keyteachingref>A2</keyteachingref>\n" +
            "          <keyteachingtext>Specialist secondary machining processes</keyteachingtext>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Suggested summary of assessment evidence\" table-column-no=\"3\">\n" +
            "          <keyteachingassessmentevidence><![CDATA[<p>A report focussing on three different traditional and an analysis of research case studies on three different specialist processes.</p>]]></keyteachingassessmentevidence>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"2\" table-row-no=\"3\">\n" +
            "        <cell style=\"LearningObjective\" column-name=\"&#x9;Learning Aim\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>B</learningobjectiveref>\n" +
            "          <learningobjectivetext>Set-up traditional secondary processing machines safely to manufacture a component</learningobjectivetext>\n" +
            "        </cell>\n" +
            "        <cell style=\"KeyTeachingcoltext\" column-name=\"Key teaching areas\" table-column-no=\"2\">\n" +
            "          <keyteachingref>B1</keyteachingref>\n" +
            "          <keyteachingtext>Health and safety requirements when setting up secondary process machines</keyteachingtext>\n" +
            "          <keyteachingref>B2</keyteachingref>\n" +
            "          <keyteachingtext>Risk assessment</keyteachingtext>\n" +
            "          <keyteachingref>B3</keyteachingref>\n" +
            "          <keyteachingtext>Setting up secondary process machines</keyteachingtext>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Suggested summary of assessment evidence\" table-column-no=\"3\">\n" +
            "          <KTA_AssessmentEvidence><![CDATA[<p>A practical activity involving a risk assessment and the setting up of at least two traditional machining processes and the machining of a component.</p><p>Evidence will be a risk assessment, observation records/witness statements, annotated photographs and drawings, set-up planning notes, completed quality control documents/annotated drawings, notes explaining the health and safety requirements.</p>]]></KTA_AssessmentEvidence>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"3\" table-row-no=\"4\">\n" +
            "        <cell style=\"LearningObjective\" column-name=\"&#x9;Learning Aim\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>C</learningobjectiveref>\n" +
            "          <learningobjectivetext>Carry out traditional secondary machining processes to safely manufacture a component</learningobjectivetext>\n" +
            "        </cell>\n" +
            "        <cell style=\"KeyTeachingcoltext\" column-name=\"Key teaching areas\" table-column-no=\"2\">\n" +
            "          <keyteachingref>C1</keyteachingref>\n" +
            "          <keyteachingtext>Features of traditional secondary machining processes</keyteachingtext>\n" +
            "          <keyteachingref>C2</keyteachingref>\n" +
            "          <keyteachingtext>Parameters of traditional secondary machining processes</keyteachingtext>\n" +
            "          <keyteachingref>C3</keyteachingref>\n" +
            "          <keyteachingtext>Quality control methods</keyteachingtext>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Suggested summary of assessment evidence\" table-column-no=\"3\" />\n" +
            "      </row>\n" +
            "      <row data-row-no=\"4\" table-row-no=\"5\">\n" +
            "        <cell style=\"LearningObjective\" column-name=\"&#x9;Learning Aim\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>D</learningobjectiveref>\n" +
            "          <learningobjectivetext>Review the processes used to machine a component and reflect on own performance</learningobjectivetext>\n" +
            "        </cell>\n" +
            "        <cell style=\"KeyTeachingcoltext\" column-name=\"Key teaching areas\" table-column-no=\"2\">\n" +
            "          <keyteachingref>D1</keyteachingref>\n" +
            "          <keyteachingtext>Lessons learnt from machining a component</keyteachingtext>\n" +
            "          <keyteachingref>D2</keyteachingref>\n" +
            "          <keyteachingtext>Personal performance whilst machining a component</keyteachingtext>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Suggested summary of assessment evidence\" table-column-no=\"3\">\n" +
            "          <KTA_AssessmentEvidence><![CDATA[<p>A report focussing on what went well and what did not go so well when machining a component and a conclusion of improvements that could be made.</p><p>The report to show a professional understanding of traditional secondary machining processes.</p>]]></KTA_AssessmentEvidence>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "    </table>\n" +
            "  </section>\n" +
            "  <section title=\"Learning aims and unit content\" style=\"UnitAhead\">\n" +
            "    <table>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"1\">\n" +
            "        <cell style=\"LAheadingtables\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>A</learningobjectiveref>\n" +
            "          <learningobjectivetext>Understand the technology and characteristics of secondary machining processes that are widely used in industry</learningobjectivetext>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"2\">\n" +
            "        <cell table-column-no=\"1\">\n" +
            "          <unitcontent><![CDATA[<p>A1\tTraditional secondary machining processes</p><p>Technology and characteristics of secondary machining processes:</p><ul><li>Drilling: </li><li>machine type and batch size including single spindle machines e.g. pillar (one off to small batch sizes) and radial (small to medium batch sizes).</li><li>features of the component e.g. countersinking, counter boring, spot facing, taping, through holes, blind holes, reamed holes</li><li>accuracy of components - typical dimensional tolerances = ±0.3mm to ±0.05mm and typical surface texture = 6.3µm to 1.6µm.</li><li>Turning:</li><li>machine type and batch size including centre lathe (one off to small batch size) and turret (small to large batch size)</li><li>features of the component e.g. flat faces, diameters (such as parallel, stepped, tapered), holes (such as drilled, bored, reamed), profile forms, threads (such as internal, external), parting off, chamfers, knurls, grooves, undercuts.</li><li>accuracy of components - typical dimensional tolerances = ±0.05mm to ±0.0125mm and typical surface texture = 3.2µm to 0.8µm</li><li>Milling:</li><li>machine type and batch size including horizontal (up-cut, down-cut) (one off to small batch size), vertical (one off to small batch size), universal (one off to small batch size)</li><li>features of the component e.g. faces (such as flat, square, parallel, angular), steps/shoulders, slots (such as open ended, enclosed/recesses, tee), holes (such as drilled, bored), profile forms (such as vee, concave, convex, gear)</li><li>accuracy of components - typical dimensional tolerances = ±0.1mm to ±0.025mm and typical surface texture = 3.2µm to 0.8µm</li><li>Grinding: </li><li>machine type and batch size including surface (such as horizontal, vertical) (one off to small batch size), cylindrical (such as external, internal) (one off to small batch size), centreless (medium to large batch size), universal (one off to small batch size)</li><li>features of the component e.g. faces (such as flat, vertical, parallel, square to each other, shoulders and faces), slots, diameters (such as parallel, tapered), bores (such as counterbores, tapered, parallel)</li><li>accuracy of components - typical dimensional tolerances = ±0.0125mm to ±0.002mm and typical surface texture = 0.8µm to 0.2µm</li></ul><p>Sustainability characteristics of traditional secondary machining processes:</p><ul><li>Energy consumption to remove material including power requirements to operate the machine, condition of machine, condition of tooling</li></ul><p>\tUse and disposal of cutting fluids and waste materials.</p><p>A2\tSpecialist secondary machining processes</p><p>Technology and characteristics of specialist machining processes:</p><ul><li>Presswork: </li><li>machine type and batch size including single action (small to medium batch size), multiple action (medium batch to mass manufacturing) </li><li>features of the component e.g. blanking, notching, piercing, cropping/shearing, bending/forming</li><li>accuracy of components - typical dimensional tolerances = ±0.3mm to ±0.05mm.</li><li>Electro discharge: </li><li>machine type and batch size including spark erosion (small to large batch size), wire erosion (small to large batch size)</li><li>features of the component e.g. holes, faces (such as flat, square, parallel, angular), forms (such as concave, convex, profile, square/rectangular), other features (such as engraving, cavities, radii/arcs, slots)</li><li>accuracy of components - typical dimensional tolerances = ±0.1mm to ±0.05mm and typical surface texture = 6.3µm to 0.4µm.</li><li>Broaching: </li><li>machine type and batch size including horizontal (one off to medium batch size), vertical (one off to medium batch size)</li><li>features of the component e.g. keyways, holes (such as flat sided, square, hexagonal, octagonal), splines</li><li>accuracy of components - typical dimensional tolerances = ±0.05mm to ±0.01mm and typical surface texture = 6.3µm to 0.4µm.</li><li>Honing and lapping: </li><li>machine types and batch size including horizontal and vertical honing (one off to medium batch size), and rotary disc and reciprocating lapping (one off to medium batch size)</li><li>features of the component e.g. holes (such as through, blind, tapered), faces (such as flat, parallel, angular)</li><li>accuracy of components - typical dimensional tolerances = ±0.01mm to ±0.005mm and typical surface texture = 0.2µm to 0.03µm.</li></ul><p>Sustainability characteristics of specialist secondary machining processes:</p><ul><li>Energy consumption to remove material including power requirements to operate the machine, condition of machine, condition of tooling</li><li>Use and disposal of cutting fluids/electrolytes and waste materials</li></ul>]]></unitcontent>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "    </table>\n" +
            "    <table>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"1\">\n" +
            "        <cell style=\"LAheadingtables\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>B</learningobjectiveref>\n" +
            "          <learningobjectivetext>Set-up traditional secondary processing machines safely to manufacture a component.</learningobjectivetext>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"2\">\n" +
            "        <cell table-column-no=\"1\">\n" +
            "          <unitcontent><![CDATA[<p>B1\tHealth and safety requirements when setting up secondary process machines</p><ul><li>Health and Safety at Work Act 1974 (HASAWA) to ensure the workplace is safe to operate in.  To include: safe set up of moving parts e.g. setting stops, preventing tooling clashes, use of machine guards to protect operator and others, choice and handling of cutting fluids/dielectric flow rate, checks for insecure components, facilities for emergency stop and machine isolation.</li><li>Regulations: Personal Protective Equipment at Work Regulations 1992 (as amended) (personal safety, identification of appropriate protective clothing and equipment, work area clean and tidy); Manual Handling Operations Regulations 1992 (as amended in 2002); Control of Substances Hazardous to Health (COSHH) Regulations 2002 (as amended).</li></ul><p>B2\tRisk assessment</p><ul><li>Risk assessment of the work environment to include hazard identification and classification:</li><li>Defining a hazard including any that can cause an adverse effect e.g. moving parts of machinery, sharp objects, electricity, slippage and uneven surfaces, dust and fumes, handling and transporting, contaminants and irritation, material ejection, fire, unshielded processes.</li><li>Defining risk e.g. tools, materials or equipment in use, spillages of oil and chemicals, not reporting accidental breakages of tools or equipment, and not following working practices and procedures.</li><li>Five steps of a risk assessment; Health and Safety Executive (HSE) template</li></ul><p>B3\tSetting up secondary process machines</p><ul><li>Tooling including:</li><li>materials and form: solid high-speed steel, tungsten carbide, abrasive stone, composite wheels</li><li>for drilling: drill bit, counterboring tool, centre drill, reamer, tap</li><li>for turning: turning tools, chamfer tools, centre drills, twist drills, taps</li><li>for milling: face mills, side and face cutters, slotting cutters, end mills, slot drills</li><li>for grinding: straight sided wheel, recessed wheel, double recessed wheel and dressing of wheels</li><li>Work piece holding devices including:</li><li>chucks: hard three jaw, magnetic</li><li>for drilling: clamping direct to machine table, machine vice, vee block and clamps</li><li>for turning: drive plate and centres, faceplates, fixed steadies</li><li>for milling: clamping direct to machine table, machine vice, angle plate, vee block and clamps</li><li>for grinding: centres, face plate, machine vices, arbors</li><li>speeds and feeds including:</li><li>for drilling: tooling revolutions per minute, linear feed rate</li><li>\tfor turning: work piece revolutions per minute, linear feed rate, depth of cut for roughing and finishing</li><li>for milling: linear/table feed rate, milling cutter revolutions per minute, depth of cut for roughing and finishing</li><li>for grinding: linear/table feed rate, depth of cut for roughing and finishing, cross feed</li></ul>]]></unitcontent>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "    </table>\n" +
            "    <table>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"1\">\n" +
            "        <cell style=\"LAheadingtables\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>C</learningobjectiveref>\n" +
            "          <learningobjectivetext>Carry out traditional secondary machining processes to safely manufacture a component.</learningobjectivetext>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"2\">\n" +
            "        <cell table-column-no=\"1\">\n" +
            "          <unitcontent><![CDATA[<p>C1\tFeatures of traditional secondary machining processes.</p><ul><li>for drilling: through holes, counterboring, tapped hole, reamed hole</li><li>for turning: parallel diameters, chamfers, drilled and tapped blind hole</li><li>for milling: flat face, shoulder, slot and profile forms</li><li>for grinding: parallel diameter, flat surface</li></ul><p>C2\tParameters of traditional secondary machining processes </p><ul><li>cutting fluid application, swarf removal, workpiece removal</li><li>for drilling: tool revolutions per minute, feed rate, swarf clearance</li><li>for turning: workpiece revolutions per minute, tool feed rate, depth of cut for roughing and finishing</li><li>for milling: linear/table feed rate, tool revolutions per minute, depth of cut for roughing and finishing)</li><li>for grinding: linear/table feed rate, depth of cut for roughing and finishing, cross feed, dressing of wheels</li></ul><p>C3\tQuality control methods </p><p>Quality control methods including:</p><ul><li>Components to be free from burrs, sharp edges and false cuts</li><li>Checks for accuracy:</li><li>use of equipment to check dimensional tolerance e.g. micrometer (external, internal), depth micrometer, gap gauge, slip gauges and comparator</li><li>use of equipment to check surface texture e.g. comparators (Rubert Gauges), portable Surface Roughness Measuring Instruments</li></ul>]]></unitcontent>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "    </table>\n" +
            "    <table>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"1\">\n" +
            "        <cell style=\"LAheadingtables\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>D</learningobjectiveref>\n" +
            "          <learningobjectivetext>Review the processes used to machine a component and reflect on own performance.</learningobjectivetext>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"2\">\n" +
            "        <cell table-column-no=\"1\">\n" +
            "          <unitcontent><![CDATA[<p>Topic D1: Lessons learnt from machining a component</p><p>Scope of the lessons learnt and improvements that could be: </p><ul><li>Health and safety skills including setting and using machines, using appropriate personal protective equipment and keeping the work area clean and tidy.</li><li>\tTraditional secondary machining skills including: the effectiveness and efficiency of setting and operating machines, sustainability considerations e.g. waste materials and energy usage and the use of quality control methods. </li><li>General engineering skills e.g. mathematics and interpreting drawings</li></ul><p>D2\tPersonal performance whilst machining a component </p><p>Understand that personal characteristics cover: </p><ul><li>Attitudes and behaviours including listening to others with respect, participating in discussion, sensitive towards individuals and cultural differences, shows self reliance when working independently, motivation and integrity. </li><li>Employability skills including planning, organising, time management, self awareness, commercial awareness and innovation / creativity, communication and literacy.</li></ul>]]></unitcontent>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "    </table>\n" +
            "  </section>\n" +
            "  <section title=\"Outline Programme of Suggested Assignments\" style=\"UnitAhead\">\n" +
            "    <table>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"1\">\n" +
            "        <cell style=\"Tablehead\" column-name=\"Assignment : \" table-column-no=\"1\">\n" +
            "          <SAO_AssignmentRef>1</SAO_AssignmentRef>\n" +
            "          <SAO_AssignmentTitle>Traditional and specialist secondary machining processes</SAO_AssignmentTitle>\n" +
            "          <learningobjectiveref>A</learningobjectiveref>\n" +
            "          <learningobjective>AP1, AM1 and AD1</learningobjective>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"2\">\n" +
            "        <cell column-name=\"Assignment : \" table-column-no=\"1\">\n" +
            "          <SAOdescriptiontasks><![CDATA[<p>Demonstrate an understanding of a range of secondary machining processes by producing a written report.  The report should be professionally presented, contain case studies and focus on three different traditional and three different specialist processes and how these can be used to manufacture components. The tasks are to:</p><ul><li>Select / obtain drawings and a specification of mechanical components that can be made by traditional machining processes. If available also give the learner physical components.</li><li>Carry out research into presswork, electro discharge and broaching processes for different components.</li></ul><p>Produce a report that includes an evaluation of these traditional and specialist secondary machining processes and a contrast of their use for different batch sizes and considers sustainable manufacture.</p>]]></SAOdescriptiontasks>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"2\" table-row-no=\"3\">\n" +
            "        <cell column-name=\"Assignment : \" table-column-no=\"1\">\n" +
            "          <SAOretake><![CDATA[<p>Select a different range of components and / or to include the process that was not originally covered.</p>]]></SAOretake>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "    </table>\n" +
            "    <table>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"1\">\n" +
            "        <cell style=\"Tablehead\" column-name=\"Assignment : \" table-column-no=\"1\">\n" +
            "          <SAO_AssignmentRef>2</SAO_AssignmentRef>\n" +
            "          <SAO_AssignmentTitle>Set up and operate traditional secondary processing machines</SAO_AssignmentTitle>\n" +
            "          <learningobjectiveref>B and C</learningobjectiveref>\n" +
            "          <learningobjective>BP2, BP3, BM2, CP4, CM3 and BC.D2</learningobjective>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"2\">\n" +
            "        <cell column-name=\"Assignment : \" table-column-no=\"1\">\n" +
            "          <SAOdescriptiontasks><![CDATA[<p>Demonstrate practical skills used to set-up and operate traditional processing machines. Produce written response on the health and safety activities and how traditional secondary processing machines have been set up and used accurately, effectively and efficiently. The evidence of the practical activity should include observation records, witness statements and annotated photographs and drawings. The tasks are to:</p><ul><li>Select / obtain drawings and a specification for a component that can be made by drilling and turning processes involving at list six features.</li><li>Carry out research into the health and safety requirements when setting up drilling and turning machines and complete a risk assessment on both machining processes.</li><li>Set-up the machines and manufacture the component and record what was done.</li></ul><p>Use quality control methods to check the accuracy of the machined component and record the measurements.</p>]]></SAOdescriptiontasks>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"2\" table-row-no=\"3\">\n" +
            "        <cell column-name=\"Assignment : \" table-column-no=\"1\">\n" +
            "          <SAOretake><![CDATA[<p>Select a different component to machine and / or select one or two different machining processes.</p>]]></SAOretake>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "    </table>\n" +
            "    <table>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"1\">\n" +
            "        <cell style=\"Tablehead\" column-name=\"Assignment : \" table-column-no=\"1\">\n" +
            "          <SAO_AssignmentRef>3</SAO_AssignmentRef>\n" +
            "          <SAOassignmenttitle>Lessons learnt</SAOassignmenttitle>\n" +
            "          <learningobjectiveref>D</learningobjectiveref>\n" +
            "          <learningobjective>DP5, DP6 and U.D3</learningobjective>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"2\">\n" +
            "        <cell column-name=\"Assignment : \" table-column-no=\"1\">\n" +
            "          <SAOdescriptiontasks><![CDATA[<p>Review and reflect on the practical activity. Prepare a lessons learnt report (maximum length 1,000 words) to explain how health and safety, traditional secondary machining and general engineering skills were used to manufacture the components. Also, explain the personal characteristics (e.g. time management) that were used. The tasks are to:</p><ul><li>Review and reflect on the activities that have been completed and make notes about what went well and what improvement could be made; also what would be done differently next time?</li><li>Analyse the notes made and draw out and differentiate between facts and opinions.</li></ul><p>Produce a professional report, which explains the lessons learnt and improvements that can be made.</p>]]></SAOdescriptiontasks>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"2\" table-row-no=\"3\">\n" +
            "        <cell column-name=\"Assignment : \" table-column-no=\"1\">\n" +
            "          <SAOretake><![CDATA[<p>Reflect on a third party demonstration of the set-up and use of traditional secondary machining processes.</p>]]></SAOretake>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "    </table>\n" +
            "  </section>\n" +
            "  <section title=\"Assessment criteria\" style=\"UnitAhead\">\n" +
            "    <table>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"1\">\n" +
            "        <cell column-name=\"Pass\" table-column-no=\"1\">Pass</cell>\n" +
            "        <cell column-name=\"Merit\" table-column-no=\"2\">Merit</cell>\n" +
            "        <cell column-name=\"Distinction\" table-column-no=\"3\">Distinction</cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"2\">\n" +
            "        <cell style=\"LAheadingtables\" column-name=\"Pass\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>A</learningobjectiveref>\n" +
            "          <learningobjectivetext>Understand the technology and characteristics of secondary machining processes that are widely used in industry</learningobjectivetext>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Merit\" table-column-no=\"2\" />\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"3\">\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Pass\" table-column-no=\"1\">\n" +
            "          <AC_criteria_title>AP1</AC_criteria_title>\n" +
            "          <criteria>Explain how different traditional and specialist secondary machining processes are used to manufacture different features on components.</criteria>\n" +
            "        </cell>\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Merit\" table-column-no=\"2\">\n" +
            "          <AC_criteria_title>AM1</AC_criteria_title>\n" +
            "          <criteria>Analyse how different traditional and specialist secondary machining processes are used to sustainably manufacture different features on components to the intended accuracy</criteria>\n" +
            "        </cell>\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Distinction\" table-column-no=\"3\">\n" +
            "          <AC_criteria_title>AD1</AC_criteria_title>\n" +
            "          <criteria>Evaluate, using vocational and high quality written language, the use of contrasting traditional and specialist secondary machining processes to sustainably manufacture components in different batch sizes</criteria>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"4\">\n" +
            "        <cell style=\"LAheadingtables\" column-name=\"Pass\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>B</learningobjectiveref>\n" +
            "          <learningobjectivetext>Set-up traditional secondary processing machines safely to manufacture a component</learningobjectivetext>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Merit\" table-column-no=\"2\" />\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"5\">\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Pass\" table-column-no=\"1\">\n" +
            "          <AC_criteria_title>BP2</AC_criteria_title>\n" +
            "          <criteria>Explain what health and safety requirements apply when machining a component using traditional secondary machining processes.</criteria>\n" +
            "        </cell>\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Merit\" table-column-no=\"2\">\n" +
            "          <AC_criteria_title>BM2</AC_criteria_title>\n" +
            "          <criteria>Use the correct tooling, work holding devices and speeds and feeds to set up at least two traditional secondary processing machines and explain how any mitigation actions from the risk assessment could be applied</criteria>\n" +
            "        </cell>\n" +
            "        <cell style=\"AssessmentDistcoltext\" column-name=\"Distinction\" table-column-no=\"3\">\n" +
            "          <AC_criteria_title>BC.D2</AC_criteria_title>\n" +
            "          <criteria>Refine the set up and parameters of the traditional secondary processing machines to safely, effectively and efficiently manufacture a component and justify any mitigation actions taken from the risk assessment.</criteria>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"2\" table-row-no=\"6\">\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Pass\" table-column-no=\"1\">\n" +
            "          <AC_criteria_title>BP3</AC_criteria_title>\n" +
            "          <criteria>Set up safely at least two traditional secondary processing machines and conduct a risk assessment of the work environment.</criteria>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Merit\" table-column-no=\"2\" />\n" +
            "        <cell column-name=\"Distinction\" table-column-no=\"3\" />\n" +
            "      </row>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"7\">\n" +
            "        <cell style=\"LAheadingtables\" column-name=\"Pass\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>C</learningobjectiveref>\n" +
            "          <learningobjectivetext>Carry out traditional secondary machining processes to safely manufacture a component</learningobjectivetext>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Merit\" table-column-no=\"2\" />\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"8\">\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Pass\" table-column-no=\"1\">\n" +
            "          <AC_criteria_title>CP4</AC_criteria_title>\n" +
            "          <criteria>Manufacture safely the component using at least two different traditional secondary machining processes.</criteria>\n" +
            "        </cell>\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Merit\" table-column-no=\"2\">\n" +
            "          <AC_criteria_title>CM3</AC_criteria_title>\n" +
            "          <criteria>Accurately manufacture the component containing at least six features</criteria>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Distinction\" table-column-no=\"3\" />\n" +
            "      </row>\n" +
            "      <row data-row-no=\"0\" table-row-no=\"9\">\n" +
            "        <cell style=\"LAheadingtables\" column-name=\"Pass\" table-column-no=\"1\">\n" +
            "          <learningobjectiveref>D</learningobjectiveref>\n" +
            "          <learningobjectivetext>Review the processes used to machine a component and reflect on own performance</learningobjectivetext>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Merit\" table-column-no=\"2\" />\n" +
            "      </row>\n" +
            "      <row data-row-no=\"1\" table-row-no=\"10\">\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Pass\" table-column-no=\"1\">\n" +
            "          <AC_criteria_title>DP5</AC_criteria_title>\n" +
            "          <criteria>Explain how health and safety, traditional secondary machining and general engineering skills were applied during the manufacture of the component.</criteria>\n" +
            "        </cell>\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Merit\" table-column-no=\"2\">\n" +
            "          <AC_criteria_title>DM4</AC_criteria_title>\n" +
            "          <criteria>Recommend improvements to the set up and use of traditional secondary machining processes including health and safety conformance and the personal characteristics applied</criteria>\n" +
            "        </cell>\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Distinction\" table-column-no=\"3\">\n" +
            "          <AC_criteria_title>S.D3</AC_criteria_title>\n" +
            "          <criteria>Present facts, opinions, analysis and recommendations effectively and consistently showing technical understanding of traditional secondary machining processes, including health and safety conformance and how personal characteristics have been applied.</criteria>\n" +
            "        </cell>\n" +
            "      </row>\n" +
            "      <row data-row-no=\"2\" table-row-no=\"11\">\n" +
            "        <cell style=\"Assessmenttabletext\" column-name=\"Pass\" table-column-no=\"1\">\n" +
            "          <AC_criteria_title>DP6</AC_criteria_title>\n" +
            "          <criteria>Explain why personal characteristics were applied during the manufacture of the component</criteria>\n" +
            "        </cell>\n" +
            "        <cell column-name=\"Merit\" table-column-no=\"2\" />\n" +
            "        <cell column-name=\"Distinction\" table-column-no=\"3\" />\n" +
            "      </row>\n" +
            "    </table>\n" +
            "  </section>\n" +
            "  <section title=\"Teacher guidance\" style=\"UnitAhead\" />\n" +
            "  <section title=\"Resources\" style=\"UnitBhead\">\n" +
            "    <resources><![CDATA[<p>The special resources required for this unit are:</p><ul><li>access to pillar drills/radial drills, centre lathes/turret lathes, horizontal milling machines/vertical milling machines/universal milling machines and surface grinding machines/cylindrical grinding machines/centreless grinding machines/universal grinding machines, as required by the learning aims and unit content</li><li>auxiliary equipment (such as that listed under ‘tooling’ and ‘work piece holding devices’)</li><li>a range of equipment suitable for measuring the dimensional accuracy and surface texture of the work pieces to be machined.</li><li>access to a range of health and safety legislation (HASAWA) and regulations, as required by the learning aims and unit content.</li></ul>]]></resources>\n" +
            "  </section>\n" +
            "  <section title=\"Assessment Guidance\" style=\"UnitBhead\">\n" +
            "    <assessmentguidance><![CDATA[<p>Learning aim A: Understand the technology and characteristics of secondary machining processes that are widely used in industry.</p><p><b>For AP1:</b> Learners will write a report that will include information about both traditional and specialist secondary machining processes. Different learners should cover different processes and choose different components to research.  It must demonstrate an understanding of how three traditional secondary machining processes are used to produce a range of features in components. It should include case studies of three different specialist processes that have been researched and will explain how these are used to produce features on three different components (one component per specialist process).  The report may have some inaccuracies relating to engineering terminology and the explanations may be difficult to understand in parts, however there will be a clear indication that three traditional and three specialist processes have been covered.</p><p><b>For AM1</b>:  Additionally the learner will analyse how these different secondary machining processes are used. For example the report should highlight the required actions of the process to produce the features and accuracy required and should highlight the relationship between tool and workpiece movements for the different processes. The analysis will be consistent across all the processes covered and include details about energy consumption and disposal of fluids and waste material and the tolerances achievable. There will be few inaccuracies relating to engineering terminology and the explanations will be easy to understand.</p><p><b>For AD1</b>: The report will include a balanced evaluation of the secondary machining processes including energy consumption, disposal of fluids and waste material for different batch sizes. The report will use the correct technical engineering terms and be written using high quality language e.g. grammatically accurate.  The report will provide a balanced view of how each process will accommodate different tolerances and batch sizes and how this relates to sustainable manufacture of components.</p><p><b>Learning aim B: Set-up traditional secondary processing machines safely to manufacture a component.</b></p><p><b>\tLearning aim C: Carry out traditional secondary machining processes to safely manufacture a component.</b></p><p><b>For BP2 and BP3</b>: Learners will produce a risk assessment of two traditional machining processes and notes to show why health and safety requirements are needed.  The evidence produced from the practical activity will include a range of observation records and / or witness statements, complete with annotated photographs and drawings, and set up planning notes to show how the machining processes were set-up. For pass the majority of the speeds and feeds, tooling and work piece holding devices will be set up correctly and safely.</p><p><b>For BM2</b>: The learner evidence will show how the correct tooling, work piece holding devices and use of speeds and feeds were used and selected as part of the practical activity involving at least two traditional machining processes. The evidence will take the form of observation records and / or witness statements, complete with annotated photographs and drawings. A risk assessment and written response will show how any mitigation actions from the risk assessment would be applied when setting up and machining the component.</p><p><b>For CP4</b>: The evidence will include a range of observation records and / or witness statements, complete with annotated photographs and drawings to show how the machining processes were used safely to manufacture a component, cutting fluid applied correctly (where relevant), swarf removed correctly, and the work piece removed from the machine safely.  The records will outline the actual machining processes undertaken to create the six features on the component and that it was free from burrs, sharp edges and false cuts. However, limited reference will be made to accuracy and the finished components may not be to the desired tolerance or surface texture.</p><p><b>For CM3</b>: Additionally the evidence, such as observation records, will also show how accuracy was achieved on at least six features. The evidence will also include either an annotated drawing or a table of results to show what measurements were taken for each of the features and what adjustments were made to ensure dimensional and surface texture accuracy.</p><p><b>For BC.D2</b>: The evidence, such as witness statements, will show how the set up and parameters, e.g. application of cutting fluid and tool feed rate, were refined during the machining to ensure the process continues to operate safely and produces a component that is accurate. The evidence will make reference to: the way the manufacture was carried out safely e.g. correct tooling overhang, appropriate secure fixing of the work piece, reference made to the risk assessment, and how the parameters can be used to control the:</p><ul><li>effectiveness e.g. optimising the order of tools and distance travelled by the tools and machining the component in a realistic time,  and</li><li>efficiency, e.g. replacing worn tools, correct fluid use and of the machining processes.</li></ul><p>The evidence will also contain notes or similar that justifies how the risks identified, in the risk assessment, have been made less severe by changing/controlling actions/practices during the machining.  These notes will be understood by a third party who may or may not be an engineer.</p><p><b>Learning aim D: Review the processes used to machine a component and reflect on own performance.</b></p><p><b>For DP5 and DP6</b><b>: </b>A lessons learnt report, of no more than 1,000 words, will be presented covering the management of health and safety, the application of machining and general engineering skills and a reflection of personal performance. The report will explain what:</p><ul><li>\tactions were taken to manage health and safety in the workplace e.g. what personal protective equipment was used and whether any unforeseen issues occurred.</li><li>traditional secondary machining skills were used, such as how the intended surface texture was achieved, how dimensional accuracy was achieved and holes were centred accurately.</li><li>general engineering skills were used, such as understanding Cartesian coordinates and interpreting drawings, and recognising technical parts of machines.</li><li>personal characteristics were used, such as time management to ensure the activity was completed within the allotted time, listening to instructions from others to ensure some self reliance and integrity to own up to any mistakes or difficulties, respect other beliefs e.g. not using tallow (animal fat) as a cutting compound (fluid)..</li></ul><p><b>For DM4</b>: Additionally the evidence, such as the report, will explain what improvements can be made to the set up and use traditional secondary machining processes.  It will also explain the actions taken, the traditional secondary machining and general engineering skills, and personal characteristics that were considered as part of the reflection exercise and how health and safety was managed throughout.  Some parts of the evidence (report) may have more emphasis than others, creating an unbalanced viewpoint, and making it more difficult for a third party to understand and implement any improvements.</p><p><b>For U.D3</b>: The evidence, such as the report, will present a professional understanding of traditional secondary machining processes when giving facts, opinions and recommendations.  It will use concise written language that includes correct technical engineering terms and accurate grammar and will clearly differentiate facts from opinion.  The report will be easy to read and will be understood by a third party who may or may not be an engineer.</p><p>The report will include a balanced view about the actions taken, traditional secondary machining and general engineering skills, and personal characteristics considered to make improvements to traditional secondary machining processes and the management of health and safety.  There will be clear links to improvements in effectiveness and consistency</p>]]></assessmentguidance>\n" +
            "  </section>\n" +
            "  <section title=\"Links to other units\" style=\"UnitBhead\">\n" +
            "    <links><![CDATA[<p>Unit xx: Modern Manufacturing Principles and Systems</p><p>Unit xx: Production Planning and Computer Aided Manufacturing</p><p>Unit xx: Manufacturing Primary Forming Processes</p><p>Unit xx: Computer Numerical Control Machining Processes</p><p>Unit xx: Automotive Workshop Techniques and Practice</p>]]></links>\n" +
            "  </section>\n" +
            "</unit>";


}
