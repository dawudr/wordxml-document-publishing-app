package com.pearson.app.dao;

import com.pearson.app.model.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ImageRepository implements ImageDAOInterface {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageRepository.class);

    @PersistenceContext
    private EntityManager em;

    public List<Image> list() {
        LOGGER.debug("List of images");

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Image> criteria = criteriaBuilder.createQuery( Image.class );
        Root<Image> image = criteria.from( Image.class );
        criteria.select(image);
        List<Image> images = em.createQuery( criteria ).getResultList(); //notice no downcasting is necessary
        return images;
    }

    public Image create(Image image) {
        LOGGER.debug("Creating image");
       em.persist(image);
        return image;
    }

    public Image get(int id) {
        LOGGER.debug("Getting image {}", id);
        return em.find(Image.class, id);
    }

    public Image update(Image image) {
        LOGGER.debug("Creating image");
        em.merge(image);
        return image;
    }

    public void delete(Image image) {
        LOGGER.debug("Deleting image {}", image.getName());
        em.remove(em.contains(image) ? image : em.merge(image));
    }
    
}
