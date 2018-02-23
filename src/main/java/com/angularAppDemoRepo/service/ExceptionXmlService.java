package com.angularAppDemoRepo.service;

import java.util.List;
import java.util.Map;

import com.angularAppDemoRepo.model.ExceptionXml;
import com.angularAppDemoRepo.model.FieldXmlMapping;
import com.angularAppDemoRepo.pojo.ExceptionXMLPojo;


public interface ExceptionXmlService {
    public List<ExceptionXml> getXmlPojoToSave(List<Map<String, String>> inputFileMapsList);

    public List<FieldXmlMapping> getListOfFieldXmlMapping();

    public boolean saveXmlInfo(List<ExceptionXml> xmlPojo);

    public List<ExceptionXml> getSavedXmlData();

    public List<String> getListOfDisplayName();

    public List<ExceptionXml> getSearchDataOfField(final String searchText, final String searchField);

    public ExceptionXml getExceptionXmlByUniqueKey(String uniqueKey);

    public ExceptionXml getExceptionForId(Long exceptionID);

    public String updateExceptionData(ExceptionXMLPojo exceptionXMLPojo);

    public List<FieldXmlMapping> getEditableFieldXmlMapping();

    public List<String> getDataOfDropDown();

    public List<String> getDataForAutoComplete(String field, String fieldVal);

    public List<String> getDataOfDropDownOnEdit(final String field, String componentType);
    
    public Map<String, Object> getSavedXmlData_Native(Integer pageNumber, String sortKey, String sortOrder);
}
