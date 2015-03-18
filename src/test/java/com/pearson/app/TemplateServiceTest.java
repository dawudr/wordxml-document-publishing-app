package com.pearson.app;


import com.pearson.app.model.Template;
import com.pearson.app.services.TemplateService;
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
public class TemplateServiceTest {

    public static final String USERNAME = "btectest";

    @Autowired
    private TemplateService templateService;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testFindTemplateById() {
        Template template = templateService.getTemplateById(1L);
        assertNotNull("template is mandatory",template);
        System.out.println("template -> " +  template);
    }


    @Test
    public void testFindUserByUsername() {
        Template template = templateService.getTemplateByName("BTEC NATIONALS");
        assertNotNull("Template is mandatory",template);
        assertTrue("Unexpected template " + template.getTemplateName(), template.getTemplateName().equals("BTEC NATIONALS"));
        System.out.println("template -> " +  template);

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
