package com.pearson.app.dao;

import com.pearson.app.model.Image;

import java.util.List;


public interface ImageDAOInterface {
    
    public List<Image> list();
    public Image create(Image image);
    public Image get(int id);
    public void delete(Image image);
}
