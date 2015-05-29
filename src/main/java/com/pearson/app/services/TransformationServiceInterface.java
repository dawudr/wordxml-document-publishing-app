package com.pearson.app.services;

import com.pearson.app.model.Transformation;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by dawud on 22/02/2015.
 */
@Service
public interface TransformationServiceInterface {

        public void addTransformation(Transformation transformation);
        public List<Transformation> listTransformations();
        public Transformation getTransformationById(int id);
        public void updateTransformation(Transformation transformation);
        public void removeTransformation(int id);
}
