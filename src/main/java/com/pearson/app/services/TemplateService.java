package com.pearson.app.services;

import com.pearson.app.dao.TemplateRepository;
import com.pearson.app.dao.TemplateSectionRepository;
import com.pearson.app.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pearson.app.services.ValidationUtils.assertNotBlank;

/**
 * Created by dawud on 22/02/2015.
 */
@Service
public class TemplateService {

    @Autowired
    private TemplateRepository templateRepository;
    @Autowired
    private TemplateSectionRepository templateSectionRepository;

    @Transactional
    public void addTemplate(Template template) {
        assertNotBlank(template.getTemplateName(), "Template type cannot be empty.");
        assertNotBlank(template.getDescription(), "Description type cannot be empty.");
        templateRepository.addTemplate(template);
    }

    @Transactional(readOnly = true)
    public List<Template> listTemplates() {
        return templateRepository.listTemplates();
    }

    @Transactional(readOnly = true)
    public Template getTemplateById(int id) {
        return templateRepository.getTemplateById(id);
    }

    @Transactional(readOnly = true)
    public Template getTemplateByName(String templateName) {
        return templateRepository.getTemplateByName(templateName);
    }

    @Transactional
    public void updateTemplate(Template template) {
        templateRepository.updateTemplate(template);
//        List<TemplateSection> templateSections = template.getTemplateSections();
//        for(TemplateSection templateSection : templateSections)
//            templateSectionRepository.updateTemplateSection(templateSection);
    }

    @Transactional
    public void removeTemplate(Long id) {
        templateRepository.removeTemplate(id);
    }


}
