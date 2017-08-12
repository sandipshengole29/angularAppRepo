package com.angularAppDemoRepo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "FIELD_XML_MAPPING")
public class FieldXmlMapping implements Serializable {
    private static final long serialVersionUID = -1900387839007401098L;

    @Id
    @Column(name = "FIELD_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FIELD_XML_MAPPING_SEQUENCE")
    @SequenceGenerator(name = "FIELD_XML_MAPPING_SEQUENCE", sequenceName = "FIELD_XML_MAPPING_SEQUENCE", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "EXCEPTION_TYPE", nullable = false)
    private String exceptionType;

    @Column(name = "FIELD_NAME", nullable = false)
    private String fieldName;

    @Column(name = "MAPPING_NAME", nullable = false)
    private String mappingName;

    @Column(name = "DATA_TYPE", nullable = false)
    private String dataType;

    @Column(name = "UI_VISIBILITY", nullable = false)
    private char uiVisibility;

    @Column(name = "HTML_TYPE")
    private String htmlType;

    @Column(name = "IS_EDITABLE", nullable = false)
    private char isEditable;

    public FieldXmlMapping() {
        super();
    }

    public FieldXmlMapping(final Long id, final String exceptionType, final String fieldName, final String mappingName,
        final String dataType, final char uiVisibility, final String htmlType) {
        super();
        this.id = id;
        this.exceptionType = exceptionType;
        this.fieldName = fieldName;
        this.mappingName = mappingName;
        this.dataType = dataType;
        this.uiVisibility = uiVisibility;
        this.htmlType = htmlType;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final Long id) {
        this.id = id;
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
     * @return the fieldName
     */
    public String getFieldName() {
        return this.fieldName;
    }

    /**
     * @param fieldName
     *            the fieldName to set
     */
    public void setFieldName(final String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the mappingName
     */
    public String getMappingName() {
        return this.mappingName;
    }

    /**
     * @param mappingName
     *            the mappingName to set
     */
    public void setMappingName(final String mappingName) {
        this.mappingName = mappingName;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return this.dataType;
    }

    /**
     * @param dataType
     *            the dataType to set
     */
    public void setDataType(final String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the uiVisibility
     */
    public char getUiVisibility() {
        return this.uiVisibility;
    }

    /**
     * @param uiVisibility
     *            the uiVisibility to set
     */
    public void setUiVisibility(final char uiVisibility) {
        this.uiVisibility = uiVisibility;
    }

    /**
     * @return the htmlType
     */
    public String getHtmlType() {
        return this.htmlType;
    }

    /**
     * @param htmlType
     *            the htmlType to set
     */
    public void setHtmlType(final String htmlType) {
        this.htmlType = htmlType;
    }

    /**
     * @return the isEditable
     */
    public char getIsEditable() {
        return this.isEditable;
    }

    /**
     * @param isEditable
     *            the isEditable to set
     */
    public void setIsEditable(final char isEditable) {
        this.isEditable = isEditable;
    }

}
