package com.pearson.app.dao;

import com.pearson.app.model.Specunit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;


/**
 * Repository class for the SpecUnit entity
 */
@Repository
public class SpecUnitRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpecUnitRepository.class);

    @PersistenceContext
    private EntityManager em;

    /**
     * create new specUnit
     */
    public void addSpecUnit(Specunit specunit) {
        em.persist(specunit);
        LOGGER.debug("Added new Specunit[{}]", specunit.toString());

    }


    /**
     * list all specUnits
     * @return  list specUnits, or an empty collection if no match found
     */
    public List<Specunit> listSpecUnits() {
        CriteriaBuilder cb = em.getCriteriaBuilder();

        // the actual search query that returns one page of results
        CriteriaQuery<Specunit> searchQuery = cb.createQuery(Specunit.class);
        Root<Specunit> searchRoot = searchQuery.from(Specunit.class);
        searchQuery.select(searchRoot);

        List<Specunit> specunits = em.createQuery(searchQuery).getResultList();
        LOGGER.debug("Found {} specUnits", (specunits != null) ? specunits.size() : 0);
        return specunits;
    }


    /**
     * finds a specUnits given its id
     */
    public Specunit getSpecUnitById(Integer id) {
        Specunit specunit = em.find(Specunit.class, id);
        LOGGER.debug("Found matching Id[{}] ->  specunit[{}]", id, (specunit != null) ? specunit.toString() : "None");
        return specunit;
    }


    /**
     * finds a user given its username
     *
     * @param qanNo - the qanNo of the searched specUnit
     * @return  a matching specUnit, or null if no specUnit found.
     */
    public Specunit getSpecUnitByQanNo(String qanNo) {
        List<Specunit> specunits = em.createNamedQuery(Specunit.FIND_BY_QAN, Specunit.class)
                .setParameter("qanNo", qanNo)
                .getResultList();

        Specunit result = specunits.size() == 1 ? specunits.get(0) : null;
        LOGGER.debug("Found matching qanNo[{}] ->  specunit[{}]", qanNo, (result != null) ? result.toString() : "None");
        return result;
    }



    /**
     * save changes made to a specUnit, or insert it if its new
     * @param specunit
     */
    public void updateSpecUnit(Specunit specunit) {
        em.merge(specunit);
        LOGGER.debug("Updated User[{}]", specunit.toString());
    }


    /**
     * Delete a SpecUnit, given its identifier
     * @param id - the id of the SpecUnit to be deleted
     */
    public void removeSpecUnit(Integer id) {
        Specunit delete = em.find(Specunit.class, id);
        em.remove(delete);
        LOGGER.debug("Removed User[{}]", delete.toString());
    }



}
