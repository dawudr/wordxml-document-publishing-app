package com.pearson.app.dao;

import com.pearson.app.model.Template;
import com.pearson.app.model.TemplateSection;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


/**
 * Repository class for the TemplateSection entity
 */
@Repository
public class TemplateSectionRepository implements TemplateSectionDAOInterface{

    @PersistenceContext
    private EntityManager em;

    /**
     * create new template
     */
    public void addTemplateSection(TemplateSection templateSection) {
        em.persist(templateSection);
    }


    /**
     * list all templates
     * @return  list templates, or an empty collection if no match found
     */
    public List<TemplateSection> listTemplateSections() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // the actual search query that returns one page of results
        CriteriaQuery<TemplateSection> searchQuery = cb.createQuery(TemplateSection.class);
        Root<TemplateSection> searchRoot = searchQuery.from(TemplateSection.class);

        List<Order> orderList = new ArrayList();
        orderList.add(cb.desc(searchRoot.get("sectionType")));
        searchQuery.orderBy(orderList);

        TypedQuery<TemplateSection> filterQuery = em.createQuery(searchQuery);

        return filterQuery.getResultList();
    }


    /**
     * finds a templates given its id
     */
    public TemplateSection getTemplateSectionById(Long id) {
        return em.find(TemplateSection.class, id);
    }


    /**
     * finds a user given its username
     *
     * @param sectionType - the templateName of the searched template
     * @return  a matching template, or null if no template found.
     */
    public TemplateSection getTemplateSectionByType(String sectionType) {
        List<TemplateSection> templates = em.createNamedQuery(Template.FIND_BY_TEMPLATE_NAME, TemplateSection.class)
                .setParameter("sectionType", sectionType)
                .getResultList();
        return templates.size() == 1 ? templates.get(0) : null;
    }



    /**
     * save changes made to a template, or insert it if its new
     * @param template
     */
    public void updateTemplateSection(TemplateSection template) {
        em.merge(template);
    }


    /**
     * Delete a Template, given its identifier
     * @param id - the id of the Template to be deleted
     */
    public void removeTemplateSection(Long id) {
        TemplateSection delete = em.find(TemplateSection.class, id);
        em.remove(delete);
    }


    /**
     * checks if a template is still available in the database
     *
     * @param sectionName - the template to be checked for availability
     * @return true if the template is still available
     */
    public boolean isTemplateSectionNameAvailable(String sectionName) {

        List<TemplateSection> sectionTypes = em.createNamedQuery(TemplateSection.FIND_BY_TEMPLATE_SECTION_NAME, TemplateSection.class)
                .setParameter("sectionName", sectionName)
                .getResultList();

        return sectionTypes.isEmpty();
    }





}
