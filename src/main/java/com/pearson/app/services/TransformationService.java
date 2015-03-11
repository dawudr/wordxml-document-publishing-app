package com.pearson.app.services;


import com.pearson.app.dao.TemplateRepository;
import com.pearson.app.dao.TransformationRepository;
import com.pearson.app.dao.UserRepository;
import com.pearson.app.model.SearchResult;
import com.pearson.app.model.Transformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pearson.app.services.ValidationUtils.assertMinimumLength;
import static com.pearson.app.services.ValidationUtils.assertNotBlank;
import static org.springframework.util.Assert.notNull;

/**
 *
 * Business service for Transformation-related operations.
 *
 */
@Service
public class TransformationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransformationService.class);
    @Autowired
    TransformationRepository transformationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TemplateRepository templateRepository;
/*    @Autowired
    User user;*/

/*    public TransformationService(User user) {
        this.user = user;
    }*/


    @Transactional
    public void addTransformation(Transformation transformation) {
        assertNotBlank(transformation.getUser().getUsername(), "Username cannot be empty.");
        assertMinimumLength(transformation.getQanNo(), 6, "Qan No must have at least 6 characters.");
        assertNotBlank(transformation.getUnitNo(), "Unit No cannot be empty.");
        assertNotBlank(transformation.getUnitTitle(), "Unit Title cannot be empty.");
        assertNotBlank(transformation.getAuthor(), "Author cannot be empty.");
        assertNotBlank(transformation.getWordfilename(), "Word Filename cannot be empty.");

        LOGGER.debug("Adding Transformation[{}]", transformation);
        transformationRepository.addTransformation(transformation);
        LOGGER.debug("Successfully added Transformation[{}]", transformation);


/*        User user = transformation.getUser();
        if(user != null) {
            transformation.setUser(user);
            transformationRepository.addTransformation(transformation);
        } else {
            throw new IllegalArgumentException("A transformation was attempted to be saved for a non-existing user");
        }*/

    }

    @Transactional(readOnly = true)
    public List<Transformation> listTransformations() {
        List<Transformation> transformations = transformationRepository.listTransformations();
        return transformations;
    }

    @Transactional(readOnly = true)
    public List<Transformation> listUnreadTransformations() {
        List<Transformation> transformations = transformationRepository.listUnreadTransformations();
        return transformations;
    }

    /**
     * get transformation by id
     * @param id - the id of transform
     * @return - the found results
     */
    @Transactional(readOnly = true)
    public Transformation getTransformationById(Long id) {
        Transformation transformation = transformationRepository.getTransformationById(id);
        return transformation;
    }


    /**
     * get transformation by id
     * @param id - the id of transform
     * @return - the found results
     */
    @Transactional(readOnly = true)
    public Long getTransformationSpecunitById(Long id) {
        Long specunitById = transformationRepository.getTransformationSpecunitById(id);
        return specunitById;
    }


    /**
     * get transformation by qanNo
     * @param qanNo - the qanNo of transform
     * @return - the found results
     */
    @Transactional(readOnly = true)
    public Transformation getTransformationByQan(String qanNo) {
        Transformation transformation = transformationRepository.getTransformationByQan(qanNo);
        return transformation;
    }

    @Transactional
    public void updateTransformation(Transformation transformation) {
        assertNotBlank(transformation.getUser().getUsername(), "Username cannot be empty.");
        assertMinimumLength(transformation.getQanNo(), 6, "Qan No must have at least 6 characters.");
        assertNotBlank(transformation.getUnitNo(), "Unit No cannot be empty.");
        assertNotBlank(transformation.getUnitTitle(), "Unit Title cannot be empty.");
        assertNotBlank(transformation.getAuthor(), "Author cannot be empty.");
        assertNotBlank(transformation.getWordfilename(), "Word Filename cannot be empty.");

        transformationRepository.updateTransformation(transformation);


/*        User user = transformation.getUser();
        if(user != null) {
            transformation.setUser(user);
            transformationRepository.updateTransformation(transformation);
        } else {
            throw new IllegalArgumentException("A transformation was attempted to be saved for a non-existing user");
        }*/
    }

    @Transactional
    public void removeTransformation(Long id) {
        notNull(id, "id is mandatory");
        transformationRepository.removeTransformation(id);
    }

    @Transactional
    public void removeTransformation(List<Long> deletedTransformationIds) {
        notNull(deletedTransformationIds, "deletedTransformationsId is mandatory");
        deletedTransformationIds.stream().forEach((deletedTransformationId) -> transformationRepository.removeTransformation(deletedTransformationId));
    }

    /**
     *
     * saves a list of transformations (new or not) into the database
     *
     * @param  - the list of transformations to be saved
     * @return - the new versions of the saved transformations
     */
/*
    @Transactional
*/
/*    public List<Transformation> updateTransformation(List<TransformationDTO> transformations) {
        return transformations.stream()
                .map((transformation) -> updateTransformation(new Transformation(
                                transformation.getId(),
                                new User(transformation.getUser().getId(),
                                        transformation.getUser().getUsername(),
                                        transformation.getUser().getPassword(),
                                        transformation.getUser().getEmail(),
                                        transformation.getUser().getFirstname(),
                                        transformation.getUser().getLastname(),
                                        transformation.getUser().getRole()),
                                transformation.getDate(),
                                transformation.getQanNo(),
                                transformation.getWordfilename(),
                                transformation.getOpenxmlfilename(),
                                transformation.getIqsxmlfilename(),
                                transformation.getUnitNo(),
                                transformation.getUnitTitle(),
                                transformation.getAuthor(),
                                transformation.getTemplatename(),
                                transformation.getLastmodified(),
                                transformation.getTransformStatus(),
                                transformation.getMessage(),
                                transformation.getGeneralStatus()
                        )
                ))
                .collect(Collectors.toList());
    }*/


    /**
     * list all transformations with paginated results
     * @param pageNumber - the page number (each page has 10 entries)
     * @return - the found results
     */
    @Transactional(readOnly = true)
    public SearchResult<Transformation> listTransformations(int pageNumber) {
        Long resultsCount = transformationRepository.countAllTransformations();
        List<Transformation> transformations = transformationRepository.listTransformation(pageNumber);
        return new SearchResult<>(resultsCount, transformations);
    }
}
