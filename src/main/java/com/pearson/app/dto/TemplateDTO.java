package com.pearson.app.dto;


import com.pearson.app.model.Template;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * JSON serializable DTO containing Template data
 *
 */
public class TemplateDTO {

    private Long id;
    private String templateType;
    private String description;
    private String revision;
    private String styleType;
    private String styleTag;
    private String xsltScriptLocation;
    private String xQueryScriptLocation;
    private String xsdScriptLocation;

    public TemplateDTO() {
    }

    public TemplateDTO(Template template) {
        this.id = template.getId();
        this.templateType = template.getTemplateType();
        this.description = template.getDescription();
        this.revision = template.getRevision();
        this.styleType = template.getStyleType();
        this.styleTag = template.getStyleTag();
        this.xsltScriptLocation = template.getXsltScriptLocation();
        this.xQueryScriptLocation = template.getxQueryScriptLocation();
        this.xsdScriptLocation = template.getXsdScriptLocation();
    }

    public TemplateDTO(Long id, String templateType, String description, String revision,
                       String styleType, String styleTag,
                       String xsltScriptLocation, String xsdScriptLocation, String xQueryScriptLocation) {
        this.id = id;
        this.templateType = templateType;
        this.description = description;
        this.revision = revision;
        this.styleType = styleType;
        this.styleTag = styleTag;
        this.xsltScriptLocation = xsltScriptLocation;
        this.xsdScriptLocation = xsdScriptLocation;
        this.xQueryScriptLocation = xQueryScriptLocation;
    }

    public static TemplateDTO mapFromTemplateEntity(Template template) {
        return new TemplateDTO(
                template.getId(),
                template.getTemplateType(),
                template.getDescription(),
                template.getRevision(),
                template.getStyleType(),
                template.getStyleTag(),
                template.getXsltScriptLocation(),
                template.getxQueryScriptLocation(),
                template.getXsdScriptLocation());
    }


    public static List<TemplateDTO> mapFromTemplatesEntities(List<Template> templates) {
        return templates.stream().map((template) -> mapFromTemplateEntity(template)).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
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
}



