package com.pearson.app.services;

import com.pearson.app.model.Template;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by dawud on 22/02/2015.
 */
@Service
public interface TemplateServiceInterface {

        public void addTemplate(Template template);
        public List<Template> listTemplates();
        public Template getTemplateById(Long id);
        public Template getTemplateByName(String templateName);
        public void updateTemplate(Template template);
        public void removeTemplate(Long id);
}
