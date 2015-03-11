package com.pearson.app.services;

import com.pearson.app.dao.TemplateRepository;
import com.pearson.app.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pearson.app.services.ValidationUtils.assertNotBlank;

/**
 * Created by dawud on 22/02/2015.
 */
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;


    @Transactional
    public void addTemplate(Template template) {
        assertNotBlank(template.getTemplateType(), "Template type cannot be empty.");
        assertNotBlank(template.getDescription(), "Description type cannot be empty.");
        assertNotBlank(template.getStyleTag(), "Style tag cannot be empty.");
        assertNotBlank(template.getStyleType(), "Style type cannot be empty.");
        templateRepository.addTemplate(template);
    }

    @Transactional(readOnly = true)
    public List<Template> listTemplates() {
        return templateRepository.listTemplates();
    }

    @Transactional(readOnly = true)
    public Template getTemplateById(Long id) {
        return templateRepository.getTemplateById(id);
    }

    @Transactional(readOnly = true)
    public Template getTemplateByTemplateName(String templateName) {
        return templateRepository.getTemplateByTemplateName(templateName);
    }

    @Transactional
    public void updateTemplate(Template template) {
        templateRepository.updateTemplate(template);
    }

    @Transactional
    public void removeTemplate(Long id) {
        templateRepository.removeTemplate(id);
    }
}
