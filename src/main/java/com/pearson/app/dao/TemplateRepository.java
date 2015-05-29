package com.pearson.app.dao;

import com.pearson.app.model.Template;
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
 * Repository class for the Template entity
 */
@Repository
public class TemplateRepository implements TemplateDAOInterface{

    @PersistenceContext
    private EntityManager em;

    /**
     * create new template
     */
    public void addTemplate(Template template) {
        em.persist(template);
    }


    /**
     * list all templates
     * @return  list templates, or an empty collection if no match found
     */
    public List<Template> listTemplates() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // the actual search query that returns one page of results
        CriteriaQuery<Template> searchQuery = cb.createQuery(Template.class);
        Root<Template> searchRoot = searchQuery.from(Template.class);

        List<Order> orderList = new ArrayList();
        orderList.add(cb.desc(searchRoot.get("templateName")));
        searchQuery.orderBy(orderList);

        TypedQuery<Template> filterQuery = em.createQuery(searchQuery);

        return filterQuery.getResultList();
    }


    /**
     * finds a templates given its id
     */
    public Template getTemplateById(int id) {
        return em.find(Template.class, id);
    }


    /**
     *
     * @param templateName - the templateName of the searched template
     * @return  a matching template, or null if no template found.
     */
    public Template getTemplateByName(String templateName) {
        List<Template> templates = em.createNamedQuery(Template.FIND_BY_TEMPLATE_NAME, Template.class)
                .setParameter("templateName", templateName)
                .getResultList();
        return templates.size() == 1 ? templates.get(0) : null;
    }



    /**
     * save changes made to a template, or insert it if its new
     * @param template
     */
    public void updateTemplate(Template template) {
        em.merge(template);
    }


    /**
     * Delete a Template, given its identifier
     * @param id - the id of the Template to be deleted
     */
    public void removeTemplate(Long id) {
        Template delete = em.find(Template.class, id);
        em.remove(delete);
    }


    /**
     * checks if a template is still available in the database
     *
     * @param templateName - the template to be checked for availability
     * @return true if the template is still available
     */
    public boolean isTemplateNameAvailable(String templateName) {

        List<Template> users = em.createNamedQuery(Template.FIND_BY_TEMPLATE_NAME, Template.class)
                .setParameter("templatetype", templateName)
                .getResultList();

        return users.isEmpty();
    }





}
