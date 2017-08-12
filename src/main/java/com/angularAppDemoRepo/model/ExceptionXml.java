package com.angularAppDemoRepo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;


@Entity
@Table(name = "EXCEPTION_XML")
public class ExceptionXml implements Serializable {
    private static final long serialVersionUID = -8162130538909152998L;

    @Id
    @Column(name = "EXCEPTION_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EXCEPTION_XML_SEQUENCE")
    @SequenceGenerator(name = "EXCEPTION_XML_SEQUENCE", sequenceName = "EXCEPTION_XML_SEQUENCE", initialValue = 1, allocationSize = 1)
    private Long exceptionId;

    @ColumnTransformer(read = "to_clob(EXCEPTION_FEED)", write = "?")
    @Column(name = "EXCEPTION_FEED", nullable = false, columnDefinition = "XMLType")
    private String exceptionFeed;

    @Column(name = "EXCEPTION_TYPE", nullable = false)
    private String exceptionType;

    @Column(name = "EXCEPTION_KEY", nullable = false)
    private String exceptionKey;


    public ExceptionXml() {
        super();
    }

    public ExceptionXml(final Long exceptionId, final String exceptionFeed, final String exceptionType, final String exceptionKey) {
        super();
        this.exceptionId = exceptionId;
        this.exceptionFeed = exceptionFeed;
        this.exceptionType = exceptionType;
        this.exceptionKey = exceptionKey;
    }

    /**
     * @return the exceptionId
     */
    public Long getExceptionId() {
        return this.exceptionId;
    }

    /**
     * @param exceptionId
     *            the exceptionId to set
     */
    public void setExceptionId(final Long exceptionId) {
        this.exceptionId = exceptionId;
    }

    /**
     * @return the exceptionFeed
     */
    public String getExceptionFeed() {
        return this.exceptionFeed;
    }

    /**
     * @param exceptionFeed
     *            the exceptionFeed to set
     */
    public void setExceptionFeed(final String exceptionFeed) {
        this.exceptionFeed = exceptionFeed;
    }

    /**
     * @return the exceptionType
     */
    public String getExceptionType() {
        return this.exceptionType;
    }

    /**
     * @param exceptionType
     *            the exceptionType to set
     */
    public void setExceptionType(final String exceptionType) {
        this.exceptionType = exceptionType;
    }


    /**
     * @return the exceptionKey
     */
    public String getExceptionKey() {
        return this.exceptionKey;
    }

    /**
     * @param exceptionKey
     *            the exceptionKey to set
     */
    public void setExceptionKey(final String exceptionKey) {
        this.exceptionKey = exceptionKey;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[exceptionId=" + this.exceptionId + ", exceptionFeed=" + this.exceptionFeed + ", exceptionType="
            + this.exceptionType + ", exceptionKey=" + this.exceptionKey + "]";
    }


}
