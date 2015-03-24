package com.pearson.app.model;


import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 *
 * The Transformation JPA entity
 *
 */
@Entity
@Table(name = "transformation", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id")
})
@NamedQueries({
        @NamedQuery(
                name = Transformation.FIND_BY_QAN,
                query = "select t from Transformation t where qanNo = :qanNo"
        ),
        @NamedQuery(
                name = Transformation.LIST_WHERE_GENERAL_STATUS_UNREAD,
                query = "select t from Transformation t where generalStatus = 'GENERAL_STATUS_UNREAD'"
        ),
        @NamedQuery(
                name = Transformation.FIND_SPECUNIT_BY_ID,
                query = "select specunit from Transformation t where id = :id"
        )
})
public class Transformation extends AbstractEntity {

    public static final String FIND_BY_QAN = "transformation.findByQan";
    public static final String LIST_WHERE_GENERAL_STATUS_UNREAD = "transformation.listWhereGeneralStatusUnread";
    public static final String FIND_SPECUNIT_BY_ID = "transformation.findSpecunitIdById";

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="image_id")
    private Image image;

//    @OneToOne(mappedBy="transformation", cascade=CascadeType.ALL)
    @OneToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="specunit_id")
    private Specunit specunit;
    @ManyToOne
    @JoinColumn(name="template_id")
    private Template template;


    private Date date;
    @Column(name = "qanNo", unique = true)
    private String qanNo;
    private String wordfilename;
    private String openxmlfilename;
    private String iqsxmlfilename;
    private String unitNo;
    private String unitTitle;
    private String author;
    private Date lastmodified;
    private String transformStatus;
    private String message; // Error messages
    private String generalStatus; // Unread, Viewed, Deleted, Modified

    // TRANSFORM STATUS CONSTANTS

    // WORKFLOW PROCESS STATUSES
    public static final String TRANSFORM_STATUS_FILE_UPLOAD_IN_PROGRESS = "TRANSFORM_STATUS_FILE_UPLOAD_IN_PROGRESS";
    public static final String TRANSFORM_STATUS_EXTRACTION_IN_PROGRESS = "TRANSFORM_STATUS_EXTRACTION_IN_PROGRESS";
    public static final String TRANSFORM_STATUS_TRANSFORM_IN_PROGRESS = "TRANSFORM_STATUS_TRANSFORM_IN_PROGRESS";
    public static final String TRANSFORM_STATUS_SUCCESS = "TRANSFORM_STATUS_SUCCESS"; // final state
    public static final String TRANSFORM_STATUS_FAIL = "TRANSFORM_STATUS_FAIL";


    // WORKFLOW FAIL STATUSES
    public static final String TRANSFORM_STATUS_FAIL_VALIDATE_WORD = "TRANSFORM_STATUS_FAIL_VALIDATE_WORD"; // Word Document failed validation e.g missing UAN, unit number, unit title etc
    public static final String TRANSFORM_STATUS_FAIL_EXTRACT_WORD_TO_XML = "TRANSFORM_STATUS_FAIL_EXTRACT_WORD_TO_XML"; // Docx4J errors while extracting to OpenXML
    public static final String TRANSFORM_STATUS_FAIL_TRANSFORM = "TRANSFORM_STATUS_FAIL_TRANSFORM"; // Transform errors all
    public static final String TRANSFORM_STATUS_FAIL_TRANSFORM_XML_TO_IQSXML_XSLT = "TRANSFORM_STATUS_FAIL_TRANSFORM_XML_TO_IQSXML_XSLT"; // IQS XSLT Transform errors all
    public static final String TRANSFORM_STATUS_FAIL_TRANSFORM_XML_TO_IQSXML_XQUERY = "TRANSFORM_STATUS_XML_TO_IQSXML_XQUERY"; // IQS XQUERY Transform errors like Tables
    public static final String TRANSFORM_STATUS_FAIL_IQS_VALIDATION = "TRANSFORM_STATUS_FAIL_IQS_VALIDATION"; // IQS XML validation fails
    public static final String TRANSFORM_STATUS_FAIL_FILE_WRITE = "TRANSFORM_STATUS_FAIL_FILE_WRITE"; // Error while saving XML


    // GENERAL STATUS
    public static final String GENERAL_STATUS_UNREAD = "GENERAL_STATUS_UNREAD";
    public static final String GENERAL_STATUS_READ = "GENERAL_STATUS_READ";
    public static final String GENERAL_STATUS_MODIFIED = "GENERAL_STATUS_MODIFIED";
    public static final String GENERAL_STATUS_DELETED = "GENERAL_STATUS_DELETED";


    public Transformation() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
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

    public Specunit getSpecunit() {
        return specunit;
    }

    public void setSpecunit(Specunit openxmlfilename) {
        this.specunit = openxmlfilename;
    }

    public String getIqsxmlfilename() {
        return iqsxmlfilename;
    }

    public void setIqsxmlfilename(String iqsxmlfilename) {
        this.iqsxmlfilename = iqsxmlfilename;
    }

    public String getOpenxmlfilename() {
        return openxmlfilename;
    }

    public void setOpenxmlfilename(String openxmlfilename) {
        this.openxmlfilename = openxmlfilename;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Transformation{" +
                "user=" + user +
                ", date=" + date +
                ", qanNo='" + qanNo + '\'' +
                ", wordfilename='" + wordfilename + '\'' +
                ", openxmlfilename='" + specunit + '\'' +
                ", iqsxmlfilename='" + iqsxmlfilename + '\'' +
                ", unitNo='" + unitNo + '\'' +
                ", unitTitle='" + unitTitle + '\'' +
                ", author='" + author + '\'' +
                ", lastmodified=" + lastmodified +
                ", template='" + template + '\'' +
                ", transformStatus=" + transformStatus +
                ", message='" + message + '\'' +
                ", generalStatus=" + generalStatus +
                '}';
    }

}
