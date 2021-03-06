package com.pearson.app.model;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Parameter;

/**
 *
 * The User JPA entity.
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 *
 */
@Entity
@Table(name = "specunit", uniqueConstraints = {
        @UniqueConstraint(columnNames = "id") })
@NamedQueries({
        @NamedQuery(
                name = Specunit.FIND_BY_ID,
                query = "select s from Specunit s where id = :id"
        ),
        @NamedQuery(
                name = Specunit.FIND_BY_QAN,
                query = "select s from Specunit s where qanNo = :qanNo"
        )
})
public class Specunit extends AbstractEntity {

    public static final String FIND_BY_ID = "specunit.findById";
    public static final String FIND_BY_QAN = "specunit.findByQanNo";

    private String qanNo;

    @JsonIgnore
    @Lob
    @Column(name = "VAL_XML")
    private String unitXML;

    @OneToOne (mappedBy="specunit")
    private Transformation transformation;


    public Specunit(String qanNo, String unitXML) {
        this.qanNo = qanNo;
        this.unitXML = unitXML;
    }

    public Specunit() {
    }

    public String getQanNo() {
        return qanNo;
    }

    public void setQanNo(String qanNo) {
        this.qanNo = qanNo;
    }

    public String getUnitXML() {
        return unitXML;
    }

    public void setUnitXML(String unitXML) {
        this.unitXML = unitXML;
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public void setTransformation(Transformation transformation) {
        this.transformation = transformation;
    }

    @Override
    public String toString() {
        return "SpecUnit{" +
                "qanNo='" + qanNo + '\'' +
                //", unitXML='" + truncate(unitXML,50) + '\'' +
                '}';
    }

    public static String truncate(String value, int length) {
        // Ensure String length is longer than requested size.
        if (value.length() > length) {
            return value.substring(0, length) + "\r\n ..... \r\n" + value.substring(value.length()- length,value.length()-1);

        } else {
            return value;
        }
    }
}
