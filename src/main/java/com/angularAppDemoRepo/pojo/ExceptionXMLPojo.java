package com.angularAppDemoRepo.pojo;

import java.util.Map;

public class ExceptionXMLPojo {
    private String xmlString;
    private String exceptionType;
    private String searchText;
    private String searchField;
    private String uniqueKeyValue;

    private Long exceptionId;
    private Map<String, String> dataMap;


    /**
     * @return the xmlString
     */
    public String getXmlString() {
        return this.xmlString;
    }

    /**
     * @param xmlString
     *            the xmlString to set
     */
    public void setXmlString(final String xmlString) {
        this.xmlString = xmlString;
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
     * @return the searchText
     */
    public String getSearchText() {
        return this.searchText;
    }

    /**
     * @param searchText
     *            the searchText to set
     */
    public void setSearchText(final String searchText) {
        this.searchText = searchText;
    }

    /**
     * @return the searchField
     */
    public String getSearchField() {
        return this.searchField;
    }

    /**
     * @param searchField
     *            the searchField to set
     */
    public void setSearchField(final String searchField) {
        this.searchField = searchField;
    }

    /**
     * @return the uniqueKeyValue
     */
    public String getUniqueKeyValue() {
        return this.uniqueKeyValue;
    }

    /**
     * @param uniqueKeyValue
     *            the uniqueKeyValue to set
     */
    public void setUniqueKeyValue(final String uniqueKeyValue) {
        this.uniqueKeyValue = uniqueKeyValue;
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
     * @return the dataMap
     */
    public Map<String, String> getDataMap() {
        return this.dataMap;
    }

    /**
     * @param dataMap
     *            the dataMap to set
     */
    public void setDataMap(final Map<String, String> dataMap) {
        this.dataMap = dataMap;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "[xmlString=" + this.xmlString + ", exceptionType=" + this.exceptionType + ", searchText=" + this.searchText
            + ", searchField=" + this.searchField + ", uniqueKeyValue=" + this.uniqueKeyValue + "]";
    }


}
