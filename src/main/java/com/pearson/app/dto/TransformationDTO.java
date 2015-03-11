package com.pearson.app.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pearson.app.model.Transformation;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * JSON serializable DTO containing Transformation data
 *
 */
public class TransformationDTO {

    private Long id;
    private UserInfoDTO user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date date;

    private String qanNo;
    private String wordfilename;
    private String openxmlfilename;
    private String iqsxmlfilename;
    private String unitNo;
    private String unitTitle;
    private String author;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "CET")
    private Date lastmodified; // for Viewed transformStatus

    private String templatename;
    private String transformStatus;
    private String message; // Error messages
    private String generalStatus;

    private Long user_id;
    private Long specunit_id;

    public TransformationDTO() {
    }

    public TransformationDTO(Transformation transformation) {
        this.id = transformation.getId();
        this.user = UserInfoDTO.mapFromUserEntity(transformation.getUser());
        this.date = transformation.getDate();
        this.qanNo = transformation.getQanNo();

        this.wordfilename = transformation.getWordfilename();
        this.openxmlfilename = "";//transformation.getSpecunit().getId();
        this.iqsxmlfilename = transformation.getIqsxmlfilename();

        this.unitNo = transformation.getUnitNo();
        this.unitTitle = transformation.getUnitTitle();
        this.author = transformation.getAuthor();
        this.templatename = transformation.getTemplatename();

        this.lastmodified = transformation.getLastmodified();
        this.transformStatus = transformation.getTransformStatus();
        this.message = transformation.getMessage();
        this.generalStatus = transformation.getGeneralStatus();
    }

    public TransformationDTO(Long id, UserInfoDTO user, Date date, String qanNo,
                             String wordfilename, String openxmlfilename, String iqsxmlfilename,
                             String unitNo, String unitTitle, String author, String templatename,
                             Date lastmodified, String transformStatus, String message, String generalStatus, Long specunit_id) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.qanNo = qanNo;

        this.wordfilename = wordfilename;
        this.openxmlfilename = openxmlfilename;
        this.iqsxmlfilename = iqsxmlfilename;

        this.unitNo = unitNo;
        this.unitTitle = unitTitle;
        this.author = author;
        this.templatename = templatename;

        this.lastmodified = lastmodified;
        this.transformStatus = transformStatus;
        this.message = message;
        this.generalStatus = generalStatus;
        this.specunit_id = specunit_id;
    }


    public static TransformationDTO mapFromTransformationEntity(Transformation transformation) {
        return new TransformationDTO(
                transformation.getId(),
                UserInfoDTO.mapFromUserEntity(transformation.getUser()),
                transformation.getDate(),
                transformation.getQanNo(),

                transformation.getWordfilename(),
                "",//transformation.getSpecunit().getId(),
                transformation.getIqsxmlfilename(),

                transformation.getUnitNo(),
                transformation.getUnitTitle(),
                transformation.getAuthor(),
                transformation.getTemplatename(),

                transformation.getLastmodified(),
                transformation.getTransformStatus(),
                transformation.getMessage(),
                transformation.getGeneralStatus(),
                transformation.getSpecunit().getId());
    }


    public static List<TransformationDTO> mapFromTransformationsEntities(List<Transformation> transformations) {
        return transformations.stream().map((transformation) -> mapFromTransformationEntity(transformation)).collect(Collectors.toList());
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserInfoDTO getUser() {
        return user;
    }

    public void setUser(UserInfoDTO user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getQanNo() {
        return qanNo;
    }

    public void setQanNo(String qanNo) {
        this.qanNo = qanNo;
    }

    public String getWordfilename() {
        return wordfilename;
    }

    public void setWordfilename(String wordfilename) {
        this.wordfilename = wordfilename;
    }



    public String getIqsxmlfilename() {
        return iqsxmlfilename;
    }

    public void setIqsxmlfilename(String iqsxmlfilename) {
        this.iqsxmlfilename = iqsxmlfilename;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getUnitTitle() {
        return unitTitle;
    }

    public void setUnitTitle(String unitTitle) {
        this.unitTitle = unitTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    public String getTemplatename() {
        return templatename;
    }

    public void setTemplatename(String templatename) {
        this.templatename = templatename;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransformStatus() {
        return transformStatus;
    }

    public void setTransformStatus(String transformStatus) {
        this.transformStatus = transformStatus;
    }

    public String getGeneralStatus() {
        return generalStatus;
    }

    public void setGeneralStatus(String generalStatus) {
        this.generalStatus = generalStatus;
    }

}



