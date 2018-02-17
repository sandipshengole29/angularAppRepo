package com.angularAppDemoRepo.service;

import java.io.InputStream;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.angularAppDemoRepo.fileOperation.MapEntryConverter;
import com.angularAppDemoRepo.fileOperation.PrepareXMLObject;
import com.angularAppDemoRepo.model.ExceptionXml;
import com.angularAppDemoRepo.model.FieldXmlMapping;
import com.angularAppDemoRepo.pojo.ExceptionXMLPojo;
import com.angularAppDemoRepo.repository.IExceptionXmlRepository;
import com.angularAppDemoRepo.repository.IFieldXmlMappingRepository;


@Service
public class ExceptionXmlServiceImpl implements ExceptionXmlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionXmlServiceImpl.class);
    @Autowired
    private IExceptionXmlRepository iExceptionXmlRepository;

    @Autowired
    private IFieldXmlMappingRepository iFieldXmlMappingRepository;

    @Autowired
    private PrepareXMLObject prepareXMLObject;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Value("${pageSize}")
    private Integer pageSize;

    /*
     * (non-Javadoc)
     *
     * @see
     * com.angularAppDemoRepo.service.ExceptionXmlService#saveExceptionXml()
     */
    public List<ExceptionXml> getXmlPojoToSave(final List<Map<String, String>> inputFileMapsList) {
        List<ExceptionXml> xmlPojos = new ArrayList<ExceptionXml>();
        try {
            if (!(inputFileMapsList.isEmpty())) {
                xmlPojos = this.prepareXMLObject.designXML(inputFileMapsList);
                ExceptionXmlServiceImpl.LOGGER.info("xmlPojos : " + xmlPojos);
            }
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in saveExceptionXml: ", e);
        }
        return xmlPojos;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.angularAppDemoRepo.service.ExceptionXmlService#
     * getListOfFieldXmlMapping()
     */
    public List<FieldXmlMapping> getListOfFieldXmlMapping() {
        List<FieldXmlMapping> fieldsList = new ArrayList<FieldXmlMapping>();
        try {
            fieldsList = this.iFieldXmlMappingRepository.getListOfFieldXmlMapping();
            ExceptionXmlServiceImpl.LOGGER.info("fieldsList: " + fieldsList);
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getListOfFieldXmlMapping: ", e);
        }
        return fieldsList;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.angularAppDemoRepo.service.ExceptionXmlService#saveXmlInfo(java.util
     * .List)
     */
    public boolean saveXmlInfo(final List<ExceptionXml> xmlPojo) {
        boolean saveResponse = false;
        try {
            for (ExceptionXml exceptionXMLPojo : xmlPojo) {
                ExceptionXmlServiceImpl.LOGGER.info("exceptionXMLPojo: " + exceptionXMLPojo.toString());
                this.iExceptionXmlRepository.save(exceptionXMLPojo);
                saveResponse = true;
            }
        } catch (Exception e) {
            saveResponse = false;
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in saveXmlInfo: ", e);
        }
        return saveResponse;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.angularAppDemoRepo.service.ExceptionXmlService#getSavedXmlData()
     */
    public List<ExceptionXml> getSavedXmlData() {
        List<ExceptionXml> exceptionXmls = new ArrayList<ExceptionXml>();
        try {
            exceptionXmls = this.iExceptionXmlRepository.getListOfExceptions();
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in saveXmlInfo: ", e);
        }
        return exceptionXmls;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.angularAppDemoRepo.service.ExceptionXmlService#getListOfDisplayName(
     * )
     */
    public List<String> getListOfDisplayName() {
        List<String> displayList = new ArrayList<String>();
        try {
            ExceptionXmlServiceImpl.LOGGER.info("fieldsList: " + displayList);
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getListOfDisplayName: ", e);
        }
        return displayList;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.angularAppDemoRepo.service.ExceptionXmlService#getSearchDataOfField(
     * )
     */
    @SuppressWarnings("unchecked")
    public List<ExceptionXml> getSearchDataOfField(final String searchText, final String searchField) {
        List<ExceptionXml> exceptionXmls = new ArrayList<ExceptionXml>();
        List<Object[]> detailsTestResult = new ArrayList<Object[]>();
        StringBuilder designXpath = new StringBuilder();
        try {
            designXpath.append(
                "SELECT EXCEPTION_ID, TO_CLOB(EXCEPTION_FEED) AS EXCEPTION_FEED, EXCEPTION_TYPE FROM EXCEPTION_XML WHERE existsNode(EXCEPTION_FEED, ");
            designXpath.append("'/exceptionFeed[");
            designXpath.append(searchField);
            designXpath.append("=\"");
            designXpath.append(searchText);
            designXpath.append("\"");
            designXpath.append("]");
            designXpath.append("')");
            designXpath.append(" > 0 ORDER BY EXCEPTION_ID DESC");


            EntityManager entityManager = this.entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            ExceptionXmlServiceImpl.LOGGER.info("designXpath_sql: " + designXpath.toString());
            Query query = entityManager.createNativeQuery(designXpath.toString());
            detailsTestResult = query.getResultList();
            entityManager.getTransaction().commit();

            exceptionXmls = getListOfObject(detailsTestResult);

            ExceptionXmlServiceImpl.LOGGER.info("xPath: " + designXpath.toString());
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in saveXmlInfo: ", e);
        }
        return exceptionXmls;
    }

    @SuppressWarnings("deprecation")
    private List<ExceptionXml> getListOfObject(final List<Object[]> detailsTestResult) {
        List<ExceptionXml> exceptionXmls = new ArrayList<ExceptionXml>();
        try {
            for (Object[] objects : detailsTestResult) {
                ExceptionXml exceptionXml = new ExceptionXml();
                exceptionXml.setExceptionId(Long.parseLong((objects[0] == null ? "0" : objects[0].toString())));
                exceptionXml.setExceptionType(objects[2] == null ? "0" : objects[2].toString());

                Clob clob = (Clob) objects[1] == null ? null : (Clob) objects[1];
                InputStream in = clob.getAsciiStream();
                StringWriter w = new StringWriter();
                IOUtils.copy(in, w);
                String clobAsString = w.toString();
                exceptionXml.setExceptionFeed(clobAsString);

                exceptionXmls.add(exceptionXml);
            }
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getUserTestInfoMap @Service: ", e);
        }
        return exceptionXmls;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.angularAppDemoRepo.service.ExceptionXmlService#
     * getExceptionXmlByUniqueKey(java.lang.String)
     */
    public ExceptionXml getExceptionXmlByUniqueKey(final String uniqueKey) {
        ExceptionXml exceptionXml = null;
        try {
            exceptionXml = this.iExceptionXmlRepository.getExceptionXmlByExceptionKey(uniqueKey);
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getExceptionXmlByUniqueKey: ", e);
        }
        return exceptionXml;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.angularAppDemoRepo.service.ExceptionXmlService#getExceptionForId(
     * java.lang.Long)
     */
    public ExceptionXml getExceptionForId(final Long exceptionID) {
        ExceptionXml exceptionXml = null;
        try {
            exceptionXml = this.iExceptionXmlRepository.getExceptionXmlInfo(exceptionID);
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getExceptionForId: ", e);
        }
        return exceptionXml;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.angularAppDemoRepo.service.ExceptionXmlService#updateExceptionData(
     * com.angularAppDemoRepo.pojo.ExceptionXMLPojo)
     */
    @SuppressWarnings("unchecked")
    public String updateExceptionData(final ExceptionXMLPojo exceptionXMLPojo) {
        String updateResponse = "FAILURE";
        try {
            if (null != exceptionXMLPojo) {
                String uniqueKey = this.prepareXMLObject.getUniqueKey(exceptionXMLPojo.getDataMap());
                XStream magicApi = new XStream(new DomDriver("UTF-8"));
                magicApi.registerConverter(new MapEntryConverter());
                magicApi.alias("exceptionFeed", Map.class);
                ExceptionXml exceptionXml = this.iExceptionXmlRepository.getExceptionXmlInfo(exceptionXMLPojo.getExceptionId());
                Map<String, String> dbXMLMap = (Map<String, String>) magicApi.fromXML(exceptionXml.getExceptionFeed());
                dbXMLMap.putAll(exceptionXMLPojo.getDataMap());
                String updatedXML = magicApi.toXML(dbXMLMap);
                ExceptionXmlServiceImpl.LOGGER.info("updatedXML: " + updatedXML);
                exceptionXml.setExceptionFeed(updatedXML);
                exceptionXml.setExceptionType(exceptionXMLPojo.getExceptionType());
                exceptionXml.setExceptionKey(uniqueKey);
                this.iExceptionXmlRepository.save(exceptionXml);
                updateResponse = "SUCCESS";
            }
        } catch (Exception e) {
            updateResponse = "FAILURE";
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getExceptionForId: ", e);
        }
        return updateResponse;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.angularAppDemoRepo.service.ExceptionXmlService#
     * getEditableFieldXmlMapping()
     */
    public List<FieldXmlMapping> getEditableFieldXmlMapping() {
        List<FieldXmlMapping> fieldsList = new ArrayList<FieldXmlMapping>();
        try {
            fieldsList = this.iFieldXmlMappingRepository.getEditableFieldXmlMapping();
            ExceptionXmlServiceImpl.LOGGER.info("fieldsList: " + fieldsList);
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getListOfFieldXmlMapping: ", e);
        }
        return fieldsList;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.angularAppDemoRepo.service.ExceptionXmlService#getCurrencyData()
     */
    @SuppressWarnings("unchecked")
    public List<String> getDataOfDropDown() {
        List<String> currencyData = new ArrayList<String>();
        List<Object[]> detailsTestResult = new ArrayList<Object[]>();
        try {
            EntityManager entityManager = this.entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(
                "SELECT distinct(extractValue(exception_feed, '/exceptionFeed/Currency')) as currency FROM EXCEPTION_XML");
            detailsTestResult = query.getResultList();
            entityManager.getTransaction().commit();

            for (Object object : detailsTestResult) {
                currencyData.add(object.toString());
            }
            ExceptionXmlServiceImpl.LOGGER.info("currencyData: " + currencyData);
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getCurrencyData: ", e);
        }
        return currencyData;
    }

    @SuppressWarnings("unchecked")
    public List<String> getDataForAutoComplete(final String field, final String fieldVal) {
        List<String> autoCompleteData = new ArrayList<String>();
        List<FieldXmlMapping> fieldXmlMappings = new ArrayList<FieldXmlMapping>();
        List<Object[]> detailsTestResult = new ArrayList<Object[]>();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fieldXmlMappings = getListOfFieldXmlMapping();
            for (FieldXmlMapping fieldXmlMapping : fieldXmlMappings) {
                if (StringUtils.isNotEmpty(field) && fieldXmlMapping.getFieldName().equalsIgnoreCase(field)) {
                    stringBuilder.append("SELECT DISTINCT(EXTRACTVALUE(EXCEPTION_FEED, '/exceptionFeed/");
                    stringBuilder.append(fieldXmlMapping.getFieldName());
                    stringBuilder.append("')) FROM EXCEPTION_XML WHERE ");
                    stringBuilder.append("EXTRACTVALUE(EXCEPTION_FEED, '/exceptionFeed/");
                    stringBuilder.append(fieldXmlMapping.getFieldName());
                    stringBuilder.append("') LIKE ('");
                    stringBuilder.append("%");
                    stringBuilder.append(fieldVal.toUpperCase());
                    stringBuilder.append("%')");
                }
            }

            ExceptionXmlServiceImpl.LOGGER.info("field_sql: " + stringBuilder.toString());
            EntityManager entityManager = this.entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(stringBuilder.toString());
            detailsTestResult = query.getResultList();
            entityManager.getTransaction().commit();

            for (Object object : detailsTestResult) {
                autoCompleteData.add(object.toString());
            }
            ExceptionXmlServiceImpl.LOGGER.info("autoCompleteData: " + autoCompleteData);
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getCurrencyData: ", e);
        }
        return autoCompleteData;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.angularAppDemoRepo.service.ExceptionXmlService#
     * getDataOfDropDownOnEdit()
     */
    @SuppressWarnings("unchecked")
    public List<String> getDataOfDropDownOnEdit(final String field, final String componentType) {
        List<String> autoCompleteData = new ArrayList<String>();
        List<FieldXmlMapping> fieldXmlMappings = new ArrayList<FieldXmlMapping>();
        List<Object[]> detailsTestResult = new ArrayList<Object[]>();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fieldXmlMappings = getListOfFieldXmlMapping();
            for (FieldXmlMapping fieldXmlMapping : fieldXmlMappings) {
                if (StringUtils.isNotEmpty(field) && fieldXmlMapping.getFieldName().equalsIgnoreCase(field)) {
                    stringBuilder.append("SELECT DISTINCT(EXTRACTVALUE(EXCEPTION_FEED, '/exceptionFeed/");
                    stringBuilder.append(fieldXmlMapping.getFieldName());
                    stringBuilder.append("')) FROM EXCEPTION_XML");
                }
            }

            ExceptionXmlServiceImpl.LOGGER.info("field_sql: " + stringBuilder.toString());
            EntityManager entityManager = this.entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            Query query = entityManager.createNativeQuery(stringBuilder.toString());
            detailsTestResult = query.getResultList();
            entityManager.getTransaction().commit();

            for (Object object : detailsTestResult) {
                if (null != object) {
                    autoCompleteData.add(object.toString());
                }
            }
            ExceptionXmlServiceImpl.LOGGER.info("autoCompleteData: " + autoCompleteData);
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getCurrencyData: ", e);
        }
        return autoCompleteData;

    }
    
    @SuppressWarnings("unchecked")
	public Map<String, Object> getSavedXmlData_Native(Integer pageNumber) {
        List<ExceptionXml> exceptionXmls = new ArrayList<ExceptionXml>();
        Integer totalExceptionXmlCount = 0;
        Map<String, Object> exceptionMap = new HashMap<String, Object>();
        List<Object[]> detailsOfResult = new ArrayList<Object[]>();
        try {
        	EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        	StringBuilder stringBuilder = new StringBuilder("SELECT EXCEPTION_ID, TO_CLOB(EXCEPTION_FEED) AS EXCEPTION_FEED, EXCEPTION_TYPE FROM EXCEPTION_XML");
        	
            ExceptionXmlServiceImpl.LOGGER.info("designXpath_sql: " + stringBuilder.toString());
            Query query = entityManager.createNativeQuery(stringBuilder.toString());
            query.setFirstResult((pageNumber-1) * pageSize); 
            query.setMaxResults(pageSize);
            
            detailsOfResult = query.getResultList();
            exceptionXmls = getListOfObject(detailsOfResult);
            totalExceptionXmlCount = getTotalCount_Native();

        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getSavedXmlData_Native: ", e);
        }
        exceptionMap.put("XML_MAP", exceptionXmls);
        exceptionMap.put("TOTAL_COUNT", totalExceptionXmlCount);
        return exceptionMap;
    }
    
	private Integer getTotalCount_Native() {
    	Integer totalCount = 0;
        try {
        	EntityManager entityManager = this.entityManagerFactory.createEntityManager();

        	StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(EXCEPTION_ID) FROM EXCEPTION_XML");
        	
            ExceptionXmlServiceImpl.LOGGER.info("designXpath_sql: " + stringBuilder.toString());
            Query query = entityManager.createNativeQuery(stringBuilder.toString());
            Object object = query.getSingleResult();
            if(null != object){
                BigDecimal temp_totalCount = new BigDecimal(object.toString());
                totalCount = temp_totalCount.intValue();
            }
        } catch (Exception e) {
            ExceptionXmlServiceImpl.LOGGER.error("Exception occured in getTotalCount_Native: ", e);
        }
        return totalCount;
    }

}
