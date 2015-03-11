package com.pearson.app;


import com.pearson.app.dao.TransformationRepository;
import com.pearson.app.dao.UserRepository;
import com.pearson.app.model.Transformation;
import com.pearson.app.model.User;
import com.pearson.config.root.RootContextConfig;
import com.pearson.config.root.TestConfiguration;
import com.pearson.config.servlet.ServletContextConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import sun.security.acl.PrincipalImpl;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ActiveProfiles("test")
@ContextConfiguration(classes={TestConfiguration.class, RootContextConfig.class, ServletContextConfig.class})
public class TransformationRestWebServiceTest {

    private MockMvc mockMvc;

    @Autowired
    private TransformationRepository transformationRepository;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void init()  {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/transformation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\":\"1\", " +
                                "\"date\": \"2015/01/01\", " +
                                "\"qanNo\": \"\"T/999/9999\"\"," +
                                "\"wordfilename\": \"btectest2-2.docx\"," +
                                "\"openxmlfilename\": \"btectest2-2.xml\"," +
                                "\"iqsxmlfilename\": \"btectest2-2-iqs.xml\"," +
                                "\"unitNo\": \"99\"," +
                                "\"unitTitle\": \"Manufacturing Secondary Machining Processes\"," +
                                "\"author\": \"Paul Winser\"," +
                                "\"lastmodified\": \"2015/01/01\"," +
                                "\"templatename\": \"btecnationals\"," +
                                "\"transformStatus\": \"test\"," +
                                "\"message\": \"test\"," +
                                "\"transformStatus\": \"TRANSFORM_STATUS_SUCCESS\"," +
                                " \"generalStatus\": \"GENERAL_STATUS_UNREAD\" }]")
                        .accept(MediaType.APPLICATION_JSON)
                        .principal(new PrincipalImpl("btectest")))
                        .andDo(print())
                        .andExpect(status().isOk());

        Transformation transformation = transformationRepository.getTransformationByQan("T/999/9999");
        assertTrue("Unit No not correct: " + transformation.getUnitNo(), "99".equals(transformation.getUnitNo()));
        assertTrue("Qan No not correct: " + transformation.getQanNo(), "T/999/9999".equals(transformation.getQanNo()));

    }


}
