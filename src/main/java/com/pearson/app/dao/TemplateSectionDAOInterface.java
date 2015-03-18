package com.pearson.app.dao;

import com.pearson.app.model.Template;
import com.pearson.app.model.TemplateSection;

import java.util.List;


/**
 * Created by dawud on 22/02/2015.
 */
public interface TemplateSectionDAOInterface {

        public void addTemplateSection(TemplateSection templateSection);
        public List<TemplateSection> listTemplateSections();
        public TemplateSection getTemplateSectionById(Long id);
        public TemplateSection getTemplateSectionByType(String sectionType);
        public void updateTemplateSection(TemplateSection templateSection);
        public void removeTemplateSection(Long id);
}
