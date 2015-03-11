package com.pearson.app.dao;


import com.pearson.app.model.Transformation;
import com.pearson.app.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * Repository class for the Meal entity
 *
 */
@Repository
public class TransformationRepository implements TransformationDAOInterface {

    private final Logger LOGGER = LoggerFactory.getLogger(TransformationRepository.class);

    @PersistenceContext
    EntityManager em;


    /**
     * create new transformation
     */
    public void addTransformation(Transformation transformation) {
        em.persist(transformation);
        LOGGER.debug("Successfully created transformation[{}]", transformation);
    }

    /**
     * list of transformation
     * @return -  a list of matching meals, or an empty collection if no match found
     */
    public List<Transformation> listTransformations() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // the actual search query that returns one page of results
        CriteriaQuery<Transformation> searchQuery = cb.createQuery(Transformation.class);
        Root<Transformation> searchRoot = searchQuery.from(Transformation.class);

        List<Order> orderList = new ArrayList();
        orderList.add(cb.desc(searchRoot.get("date")));
//        orderList.add(cb.asc(searchRoot.get("time")));
        orderList.add(cb.asc(searchRoot.get("id")));
        searchQuery.orderBy(orderList);

        TypedQuery<Transformation> filterQuery = em.createQuery(searchQuery);
        return filterQuery.getResultList();
    }


    /**
     * list of transformation
     * @return -  a list of recent unread transformations
     */
    public List<Transformation> listUnreadTransformations() {
        List<Transformation> transformations = em.createNamedQuery(Transformation.LIST_WHERE_GENERAL_STATUS_UNREAD, Transformation.class)
                .getResultList();
        LOGGER.debug("List all Transformation where GENERAL_STATUS_UNREAD matching -> Transformation number of records[{}]", (transformations != null && transformations.size() > 0) ? transformations.size() : "None");
        return transformations;
    }


    /**
     * finds a transformation given its id
     */
    public Transformation getTransformationById(Long id) {
        return em.find(Transformation.class, id);
    }


    /**
     * finds a transformation given its QanNo
     */
    public Transformation getTransformationByQan(String qanNo) {
        List<Transformation> transformations = em.createNamedQuery(Transformation.FIND_BY_QAN, Transformation.class)
                .setParameter("qanNo", qanNo)
                .getResultList();
        LOGGER.debug("Found matching qanNo[{}] -> Transformation[{}]", qanNo, (transformations != null && transformations.size() == 1) ? transformations.get(0).toString() : "None");
        return transformations.size() == 1 ? transformations.get(0) : null;
    }


    /**
     * finds a transformation given its SpecUnit
     */
    public Long getTransformationSpecunitById(Long id) {
        Long specUnitId = 0L;
        List<Long> results = em.createNamedQuery(Transformation.FIND_SPECUNIT_BY_ID)
                .setParameter("id", id)
                .getResultList();
        if(!results.isEmpty()) {
            specUnitId = results.get(0);
        }
        LOGGER.debug("Found matching id[{}] -> SpecUnitId[{}]", id, specUnitId);
        return specUnitId;
    }



    /**
     * save changes made to a transformation, or create the transformation if its a new transformation.
     */
    public void updateTransformation(Transformation transformation) {
        em.merge(transformation);
    }


    /**
     * Delete a transformation, given its identifier
     * @param transformId - the id of the transformation to be deleted
     */
    public void removeTransformation(Long transformId) {
        Transformation delete = em.find(Transformation.class, transformId);
        em.remove(delete);
    }

    private Predicate[] getCommonWhereCondition(CriteriaBuilder cb, String username, Root<Transformation> searchRoot, Date fromDate, Date toDate,
                                                Time fromTime, Time toTime) {

        List<Predicate> predicates = new ArrayList<>();
        Join<Transformation, User> user = searchRoot.join("user");

        predicates.add(cb.equal(user.<String>get("username"), username));
        predicates.add(cb.greaterThanOrEqualTo(searchRoot.<Date>get("date"), fromDate));

        if (toDate != null) {
            predicates.add(cb.lessThanOrEqualTo(searchRoot.<Date>get("date"), toDate));
        }

        return predicates.toArray(new Predicate[]{});
    }


    /**
     *
     * counts the matching transformations, given the bellow criteria
     *
     * @return -  a list of matching transformations, or an empty collection if no match found
     */
    public Long countAllTransformations() {

        CriteriaBuilder cb = em.getCriteriaBuilder();

        // query for counting the total results
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Transformation> countRoot = cq.from(Transformation.class);
        cq.select((cb.count(countRoot)));
        Long resultsCount = em.createQuery(cq).getSingleResult();

        LOGGER.info("Found " + resultsCount + " results.");

        return resultsCount;
    }


    /**
     *
     * finds all list of transformation
     *
     * @return -  a paginated list of matching meals, or an empty collection if no match found
     */
    public List<Transformation> listTransformation(int pageNumber) {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // the actual search query that returns one page of results
        CriteriaQuery<Transformation> searchQuery = cb.createQuery(Transformation.class);
        Root<Transformation> searchRoot = searchQuery.from(Transformation.class);

        List<Order> orderList = new ArrayList();
        orderList.add(cb.desc(searchRoot.get("date")));
        //orderList.add(cb.asc(searchRoot.get("time")));
        searchQuery.orderBy(orderList);

        TypedQuery<Transformation> filterQuery = em.createQuery(searchQuery)
                .setFirstResult((pageNumber - 1) * 10)
                .setMaxResults(10);

        return filterQuery.getResultList();
    }


}
