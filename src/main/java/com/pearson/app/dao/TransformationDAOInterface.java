package com.pearson.app.dao;

import com.pearson.app.model.Transformation;

import java.util.List;


/**
 * Created by dawud on 22/02/2015.
 */
public interface TransformationDAOInterface {

        public void addTransformation(Transformation transformation);
        public List<Transformation> listTransformations();
        public Transformation getTransformationById(Long id);
        public Transformation getTransformationByQan(String id);
        public void updateTransformation(Transformation transformation);
        public void removeTransformation(Long id);
}
