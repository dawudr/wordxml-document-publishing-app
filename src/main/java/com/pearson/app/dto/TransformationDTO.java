package com.pearson.app.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.pearson.app.model.Image;
import com.pearson.app.model.Specunit;
import com.pearson.app.model.Template;
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

    private Integer id;
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

    private String template;
    private int templateId;
    private int imageId;
    private String transformStatus;
    private String message; // Error messages
    private String generalStatus;

    private int user_id;
    private int specunit;

    public TransformationDTO() {
    }

    public TransformationDTO(Transformation transformation) {
        String templateName = "";
/*        Template t = transformation.getTemplate();
        if(t!=null) {
            templateName = t.getTemplateName();
            templateId = t.getId();
        }*/



        this.id = transformation.getId();
        this.user = UserInfoDTO.mapFromUserEntity(transformation.getUser());
        this.date = transformation.getDate();
        this.qanNo = transformation.getQanNo();

        this.wordfilename = transformation.getWordfilename();
        this.openxmlfilename = transformation.getOpenxmlfilename();
        this.iqsxmlfilename = transformation.getIqsxmlfilename();

        this.unitNo = transformation.getUnitNo();
        this.unitTitle = transformation.getUnitTitle();
        this.author = transformation.getAuthor();
        //this.template = templateName;
        //this.templateId = templateId;
        this.templateId = transformation.getTemplateId();

        this.lastmodified = transformation.getLastmodified();
        this.transformStatus = transformation.getTransformStatus();
        this.message = transformation.getMessage();
        this.generalStatus = transformation.getGeneralStatus();
/*        if(transformation.getImage() != null) {
            this.imageId = transformation.getImage().getId();
        }*/
        this.imageId = transformation.getImage_id();
        this.specunit = transformation.getSpecunit().getId();
    }

    public TransformationDTO(Integer id, UserInfoDTO user, Date date, String qanNo,
                             String wordfilename, String openxmlfilename, String iqsxmlfilename,
                             String unitNo, String unitTitle, String author, int templateId,
                             Date lastmodified, String transformStatus, String message, String generalStatus, int specunit, int imageId) {
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
        this.template = template;
        this.templateId = templateId;

        this.lastmodified = lastmodified;
        this.transformStatus = transformStatus;
        this.message = message;
        this.generalStatus = generalStatus;
        this.specunit = specunit;
        this.imageId = imageId;
    }


    public static TransformationDTO mapFromTransformationEntity(Transformation transformation) {

        String templateName = "";
/*        Template t = transformation.getTemplate();
        if(t!=null) {
            templateName = t.getTemplateName();
            templateId = t.getId();
        }*/

        int specunit= 0;
        Specunit s = transformation.getSpecunit();
        if(s!=null) {
            specunit = s.getId();
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        if(transformation.getUser() != null) {
            userInfoDTO = UserInfoDTO.mapFromUserEntity(transformation.getUser());
        }

/*        int imageId = 0;
        Image i = transformation.getImage();
        if(i!=null) {
            imageId = i.getId();
        }*/

        return new TransformationDTO(
                transformation.getId(),
                userInfoDTO,
                transformation.getDate(),
                transformation.getQanNo(),

                transformation.getWordfilename(),
                transformation.getOpenxmlfilename(),
                transformation.getIqsxmlfilename(),

                transformation.getUnitNo(),
                transformation.getUnitTitle(),
                transformation.getAuthor(),
                transformation.getTemplateId(),

                transformation.getLastmodified(),
                transformation.getTransformStatus(),
                transformation.getMessage(),
                transformation.getGeneralStatus(),
                specunit,
                transformation.getImage_id());
    }


    public static List<TransformationDTO> mapFromTransformationsEntities(List<Transformation> transformations) {
        return transformations.stream().map((transformation) -> mapFromTransformationEntity(transformation)).collect(Collectors.toList());
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getOpenxmlfilename() {
        return openxmlfilename;
    }

    public void setOpenxmlfilename(String openxmlfilename) {
        this.openxmlfilename = openxmlfilename;
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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public int getSpecunit() {
        return specunit;
    }

    public void setSpecunit(int specunit) {
        this.specunit = specunit;
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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "TransformationDTO{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", qanNo='" + qanNo + '\'' +
                ", wordfilename='" + wordfilename + '\'' +
                ", openxmlfilename='" + openxmlfilename + '\'' +
                ", iqsxmlfilename='" + iqsxmlfilename + '\'' +
                ", unitNo='" + unitNo + '\'' +
                ", unitTitle='" + unitTitle + '\'' +
                ", author='" + author + '\'' +
                ", lastmodified=" + lastmodified +
                ", template='" + template + '\'' +
                ", templateId=" + templateId +
                ", imageId=" + imageId +
                ", transformStatus='" + transformStatus + '\'' +
                ", message='" + message + '\'' +
                ", generalStatus='" + generalStatus + '\'' +
                ", user_id=" + user_id +
                ", specunit=" + specunit +
                '}';
    }
}



