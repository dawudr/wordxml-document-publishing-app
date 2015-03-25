package com.pearson.app.model;

//import org.codehaus.jackson.annotate.JsonIgnoreProperties;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "image")
@NamedQueries({
        @NamedQuery(name = "image", query = "select i from Image i")
})
//@JsonIgnoreProperties({"id","newFilename","contentType","dateCreated","lastUpdated"})
public class Image extends AbstractEntity {

    public static final String LIST_IMAGES = "image.listImages";

    private String name;
    private String newFilename;
    private String contentType;
    @Column(name = "size_")
    private Long size;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    @Transient
    private String url;
    @Transient
    private String deleteUrl;
    @Transient
    private String deleteType;

    @JsonIgnore
    @OneToOne (mappedBy="image")
    private Transformation transformation;


    public Image() {}

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the newFilename
     */
    public String getNewFilename() {
        return newFilename;
    }

    /**
     * @param newFilename the newFilename to set
     */
    public void setNewFilename(String newFilename) {
        this.newFilename = newFilename;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the size
     */
    public Long getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(Long size) {
        this.size = size;
    }

    /**
     * @return the dateCreated
     */
    public Date getDateCreated() {
        return dateCreated;
    }

    /**
     * @param dateCreated the dateCreated to set
     */
    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * @return the lastUpdated
     */
    public Date getLastUpdated() {
        return lastUpdated;
    }

    /**
     * @param lastUpdated the lastUpdated to set
     */
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the deleteUrl
     */
    public String getDeleteUrl() {
        return deleteUrl;
    }

    /**
     * @param deleteUrl the deleteUrl to set
     */
    public void setDeleteUrl(String deleteUrl) {
        this.deleteUrl = deleteUrl;
    }

    /**
     * @return the deleteType
     */
    public String getDeleteType() {
        return deleteType;
    }

    /**
     * @param deleteType the deleteType to set
     */
    public void setDeleteType(String deleteType) {
        this.deleteType = deleteType;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public String toString() {
        return "Image{" +
                ", name='" + name + '\'' +
                ", newFilename='" + newFilename + '\'' +
                ", contentType='" + contentType + '\'' +
                ", size=" + size +
                ", dateCreated=" + dateCreated +
                ", lastUpdated=" + lastUpdated +
                ", url='" + url + '\'' +
                ", deleteUrl='" + deleteUrl + '\'' +
                ", deleteType='" + deleteType + '\'' +
                '}';
    }
}
