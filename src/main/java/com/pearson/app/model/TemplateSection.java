package com.pearson.app.model;

import javax.persistence.*;

/**
 *
 * The Template JPA entity.
 *
 */
/*
@Entity
@Table(name = "templatesection")
*/

/*@Table(name = "templatesection", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id") })
@NamedQueries({
        @NamedQuery(
                name = TemplateSection.FIND_BY_TEMPLATE_SECTION_NAME,
                query = "select t from TemplateSection t where id = :id"
        )
})*/
//public class TemplateSection extends AbstractEntity {
@Embeddable
public class TemplateSection {

    public static final String FIND_BY_TEMPLATE_SECTION_NAME = "templatesection.findByTemplateSectionName";
    public static final String SECTION_TYPE_HEADER = "HEADER";
    public static final String SECTION_TYPE_META = "META";
    public static final String SECTION_TYPE_SECTION = "SECTION";
    public static final String SECTION_TYPE_PARAGRAPH = "PARAGRAPH";
    public static final String SECTION_TYPE_TABLE = "TABLE";
    public static final String SECTION_TYPE_ROW = "ROW";
    public static final String SECTION_TYPE_COLUMN = "COLUMN";

//    @Id
    //@Column(name="templateSection_id")
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
   // private int templateSection_Id;
//    @Column(name="id")
//    private int templateId;
    private String sectionType; // SECTION, META, sub SECTION, PARAGRAPH
    private String sectionStyle;
    private String sectionName;
    private String sectionValue;
    private boolean showSection;
    private boolean validateInWordDoc;

    //@JsonIgnore
    //@ManyToOne (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    //@ManyToOne (cascade=CascadeType.ALL)
    //private Template template;
    public TemplateSection() {
    }

    public String getSectionType() {
        return sectionType;
    }

    public void setSectionType(String sectionType) {
        this.sectionType = sectionType;
    }

    public String getSectionStyle() {
        return sectionStyle;
    }

    public void setSectionStyle(String sectionStyle) {
        this.sectionStyle = sectionStyle;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSectionValue() {
        return sectionValue;
    }

    public void setSectionValue(String sectionValue) {
        this.sectionValue = sectionValue;
    }

    public boolean isShowSection() {
        return showSection;
    }

    public void setShowSection(boolean showSection) {
        this.showSection = showSection;
    }

    public boolean isValidateInWordDoc() {
        return validateInWordDoc;
    }

    public void setValidateInWordDoc(boolean validateInWordDoc) {
        this.validateInWordDoc = validateInWordDoc;
    }


/*    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

  /*
    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }
*/


    @Override
    public String toString() {
        return "TemplateSection{" +
//                "id=" + id +
                ", sectionType='" + sectionType + '\'' +
                ", sectionStyle='" + sectionStyle + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", sectionValue='" + sectionValue + '\'' +
                ", showSection=" + showSection +
                ", validateInWordDoc=" + validateInWordDoc +
                '}';
    }
}
