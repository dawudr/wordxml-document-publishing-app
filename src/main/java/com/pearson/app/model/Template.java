package com.pearson.app.model;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * The Template JPA entity.
 *
 */
@Entity
@Table(name = "template")
@NamedQueries({
        @NamedQuery(
                name = Template.FIND_BY_TEMPLATE_TYPE,
                query = "select t from Template t where templateType = :templateType"
        )
})
public class Template extends AbstractEntity {

    public static final String FIND_BY_TEMPLATE_TYPE = "template.findByTemplate";

    private String templateType;
    private String description;
    private String revision;
    private String styleType;
    private String styleTag;
    private String xsltScriptLocation;
    private String xsdScriptLocation;
    private String xQueryScriptLocation;


    public Template() {

    }

    public Template(String templateType, String description, String revision, String styleType, String styleTag, String xsltScriptLocation, String xQueryScriptLocation, String xsdScriptLocation) {
        this.templateType = templateType;
        this.description = description;
        this.revision = revision;
        this.styleType = styleType;
        this.styleTag = styleTag;
        this.xsltScriptLocation = xsltScriptLocation;
        this.xQueryScriptLocation = xQueryScriptLocation;
        this.xsdScriptLocation = xsdScriptLocation;
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStyleType() {
        return styleType;
    }

    public void setStyleType(String styleType) {
        this.styleType = styleType;
    }

    public String getStyleTag() {
        return styleTag;
    }

    public void setStyleTag(String styleTag) {
        this.styleTag = styleTag;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getXsltScriptLocation() {
        return xsltScriptLocation;
    }

    public void setXsltScriptLocation(String xsltScriptLocation) {
        this.xsltScriptLocation = xsltScriptLocation;
    }

    public String getXsdScriptLocation() {
        return xsdScriptLocation;
    }

    public void setXsdScriptLocation(String xsdScriptLocation) {
        this.xsdScriptLocation = xsdScriptLocation;
    }

    public String getxQueryScriptLocation() {
        return xQueryScriptLocation;
    }

    public void setxQueryScriptLocation(String xQueryScriptLocation) {
        this.xQueryScriptLocation = xQueryScriptLocation;
    }

    @Override
    public String toString() {
        return "Template{" +
                "templateType='" + templateType + '\'' +
                ", description='" + description + '\'' +
                ", revision='" + revision + '\'' +
                ", styleType='" + styleType + '\'' +
                ", styleTag='" + styleTag + '\'' +
                ", xsltScriptLocation='" + xsltScriptLocation + '\'' +
                ", xsdScriptLocation='" + xsdScriptLocation + '\'' +
                ", xQueryScriptLocation='" + xQueryScriptLocation + '\'' +
                '}';
    }
}
