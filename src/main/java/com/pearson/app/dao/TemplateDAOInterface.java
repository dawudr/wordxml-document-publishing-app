package com.pearson.app.dao;

import com.pearson.app.model.Template;

import java.util.List;


/**
 * Created by dawud on 22/02/2015.
 */
public interface TemplateDAOInterface {

        public void addTemplate(Template template);
        public List<Template> listTemplates();
        public Template getTemplateById(int id);
        public Template getTemplateByName(String templateName);
        public void updateTemplate(Template template);
        public void removeTemplate(Long id);
}
