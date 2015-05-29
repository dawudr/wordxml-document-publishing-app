package com.pearson.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
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
//    @JsonIgnore
//    @OneToMany(mappedBy="template")
//    private List<Transformation> transformation;

    /*
    TODO: Doc
    Persisting Collections
    https://www.safaribooksonline.com/library/view/just-hibernate/9781449334369/ch04.html


    Eager, not Lazy loading here
    http://stackoverflow.com/questions/11746499/solve-failed-to-lazily-initialize-a-collection-of-role-exception
    http://stackoverflow.com/questions/2990799/difference-between-fetchtype-lazy-and-eager-in-java-persistence
    If you know that you'll want to see all Comments every time you retrieve a Topic then change your field mapping for comments to:

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "topic", cascade = CascadeType.ALL)
    private Collection<Comment> comments = new LinkedHashSet<Comment>();
    Collections are lazy-loaded by default, take a look at this if you want to know more.
     */

    @JsonProperty(value = "templatesection")
    //@OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    //@OneToMany(cascade=CascadeType.ALL)
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "template_templatesection",
            joinColumns=@JoinColumn(name="template_id"))
    @GenericGenerator(strategy="hilo", name = "hilo-gen")
    @CollectionId(columns = { @Column(name="templateSection_id") }, generator = "hilo-gen", type = @Type(type="long"))
    private Collection<TemplateSection> templateSections = new ArrayList<TemplateSection>();


    public Template () {

    };

/*    public List<Transformation> getTransformation() {
        return transformation;
    }

    public void setTransformation(List<Transformation> transformation) {
        this.transformation = transformation;
    }*/

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

    public Collection<TemplateSection> getTemplateSections() {
        return templateSections;
    }

    public void setTemplateSections(Collection<TemplateSection> templateSections) {
        this.templateSections = templateSections;
    }

    @Override
    public String toString() {
        return "Template{" +
                "templateName='" + templateName + '\'' +
                ", description='" + description + '\'' +
                ", revision='" + revision + '\'' +
                ", xsltScriptLocation='" + xsltScriptLocation + '\'' +
                ", xsdScriptLocation='" + xsdScriptLocation + '\'' +
                ", xQueryScriptLocation='" + xQueryScriptLocation + '\'' +
//                ", transformation=" + transformation +
                ", templateSections=" + templateSections +
                '}';
    }
}
