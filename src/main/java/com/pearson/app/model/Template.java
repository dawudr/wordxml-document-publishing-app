package com.pearson.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

/**
 *
 * The Template JPA entity.
 *
 */
@JsonRootName(value = "template")
@Entity
@Table(name = "template", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")})
@NamedQueries({
        @NamedQuery(
                name = Template.FIND_BY_TEMPLATE_NAME,
                query = "select t from Template t where templateName = :templateName"
        )
})
public class Template extends AbstractEntity {

    public static final String FIND_BY_TEMPLATE_NAME = "template.findByTemplateName";

    private String templateName;
    private String description;
    private String revision;
    private String xsltScriptLocation;
    private String xsdScriptLocation;
    private String xQueryScriptLocation;

/*    @OneToOne(mappedBy="template")
    private Transformation transformation;*/
    @JsonIgnore
    @OneToMany(mappedBy="template")
    private List<Transformation> transformation;

    @JsonProperty(value = "templatesection")
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="template_id")
    private List<TemplateSection> templateSections;


    public Template () {

    };

    public List<Transformation> getTransformation() {
        return transformation;
    }

    public void setTransformation(List<Transformation> transformation) {
        this.transformation = transformation;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<TemplateSection> getTemplateSections() {
        return templateSections;
    }

    public void setTemplateSections(List<TemplateSection> templateSections) {
        this.templateSections = templateSections;
    }

    @Override
    public String toString() {
        return "Template{" +
                "templateType='" + templateName + '\'' +
                ", description='" + description + '\'' +
                ", revision='" + revision + '\'' +
                ", xsltScriptLocation='" + xsltScriptLocation + '\'' +
                ", xsdScriptLocation='" + xsdScriptLocation + '\'' +
                ", xQueryScriptLocation='" + xQueryScriptLocation + '\'' +
                '}';
    }
}
