package com.angularAppDemoRepo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.angularAppDemoRepo.fileOperation.ReadInputFile;
import com.angularAppDemoRepo.model.ExceptionXml;
import com.angularAppDemoRepo.model.FieldXmlMapping;
import com.angularAppDemoRepo.pojo.ExceptionXMLPojo;
import com.angularAppDemoRepo.service.ExceptionXmlService;
import com.angularAppDemoRepo.util.XmlToJsonUtil;


@RestController
@RequestMapping(value = "/xmlPoc")
public class XmlPocController {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlPocController.class);

    @Autowired
    private ReadInputFile readInputFile;

    @Autowired
    private ExceptionXmlService exceptionXmlService;

    @Autowired
    private XmlToJsonUtil xmlToJsonUtil;
    
    @Value("${pageSize}")
    private Integer pageSize;

    @GetMapping(value = "/welCome")
    public String helloWorldName() {
        String returnString = null;
        try {
            returnString = "Wel-Come to XML Poc controller";
        } catch (Exception e) {
            XmlPocController.LOGGER.error("Exception occured in helloWorldName: " + e);
        }
        return "<Strong>" + returnString + "</Strong>";
    }

    @GetMapping(value = "/readInputFile", produces = MediaType.TEXT_HTML_VALUE)
    public String readInputFile() {
        String returnString = "FAILURE";
        List<Map<String, String>> inputFileMapsList = new ArrayList<Map<String, String>>();
        List<ExceptionXml> xmlPojos = new ArrayList<ExceptionXml>();
        try {
            inputFileMapsList = this.readInputFile.getInputFromFile("FlatFile.txt");
            XmlPocController.LOGGER.info("inputFileMapsList : " + inputFileMapsList);
            if (null != inputFileMapsList && !(inputFileMapsList.isEmpty())) {
                xmlPojos = this.exceptionXmlService.getXmlPojoToSave(inputFileMapsList);
                if (null != xmlPojos && !(xmlPojos.isEmpty())) {
                    boolean saveResponse = this.exceptionXmlService.saveXmlInfo(xmlPojos);
                    if (saveResponse) {
                        returnString = "SUCCESS";
                    }
                }
            }
        } catch (Exception e) {
            returnString = "FAILURE";
            XmlPocController.LOGGER.error("Exception occured in readInputFile: " + e);
        }
        return returnString;
    }

    @SuppressWarnings("unchecked")
	@GetMapping(value = "/viewFileData/{pageNumber}/{sortKey}/{sortOrder}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> viewFileData(@PathVariable(name="pageNumber") Integer pageNumber, @PathVariable(name="sortKey") String sortKey,
    														@PathVariable(name="sortOrder") String sortOrder) {
    	
        List<ExceptionXml> exceptionXmls = new ArrayList<ExceptionXml>();
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        List<FieldXmlMapping> fieldsList = new ArrayList<FieldXmlMapping>();
        Map<String, Object> exceptionMap = new HashMap<String, Object>();
        try {
        	LOGGER.info("--PageNumber: " + pageNumber);
        	LOGGER.info("--SortKey: " + sortKey);
        	LOGGER.info("--SortOrder: " + sortOrder);
        	
        	exceptionMap = this.exceptionXmlService.getSavedXmlData_Native(pageNumber, sortKey, sortOrder);
            if (null != exceptionMap && !(exceptionMap.isEmpty())) {
            	exceptionXmls = (List<ExceptionXml>) exceptionMap.get("XML_MAP");
                maps = this.xmlToJsonUtil.getXmlToJsonList(exceptionXmls);
                
                fieldsList = this.exceptionXmlService.getListOfFieldXmlMapping();
                map.put("EXCEPTION_DATA", maps);
                map.put("FIELD_LIST", fieldsList);
                map.put("TOTAL_COUNT", exceptionMap.get("TOTAL_COUNT"));
                map.put("PAGE_SIZE", pageSize);
            }
            XmlPocController.LOGGER.info("xmlmaps : " + maps);
        } catch (Exception e) {
            XmlPocController.LOGGER.error("Exception occured in viewFileData: " + e);
        }
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @GetMapping(value = "/getFieldList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldXmlMapping>> getFieldList() {
        List<FieldXmlMapping> fieldsList = new ArrayList<FieldXmlMapping>();
        try {
            fieldsList = this.exceptionXmlService.getListOfFieldXmlMapping();
            XmlPocController.LOGGER.info("fieldsList : " + fieldsList);
        } catch (Exception e) {
            XmlPocController.LOGGER.error("Exception occured in getFieldList: " + e);
        }
        return new ResponseEntity<List<FieldXmlMapping>>(fieldsList, HttpStatus.OK);
    }

    @PostMapping(value = "/searchFieldData", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getSearchDataOfField(@RequestBody final ExceptionXMLPojo exceptionXMLPojo) {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        List<ExceptionXml> exceptionXmls = new ArrayList<ExceptionXml>();
        try {
            XmlPocController.LOGGER.info("serachText: " + exceptionXMLPojo.getSearchText());
            XmlPocController.LOGGER.info("serachField: " + exceptionXMLPojo.getSearchField());
            if (StringUtils.isNotBlank(exceptionXMLPojo.getSearchText())
                && StringUtils.isNotBlank(exceptionXMLPojo.getSearchField())) {
                exceptionXmls = this.exceptionXmlService.getSearchDataOfField(exceptionXMLPojo.getSearchText(),
                    exceptionXMLPojo.getSearchField());
            } else {
                exceptionXmls = this.exceptionXmlService.getSavedXmlData();
            }

            if (null != exceptionXmls && !(exceptionXmls.isEmpty())) {
                maps = this.xmlToJsonUtil.getXmlToJsonList(exceptionXmls);
                map.put("EXCEPTION_DATA", maps);
            }
        } catch (Exception e) {
            XmlPocController.LOGGER.error("Exception occured in getDataOfField: " + e);
        }
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @GetMapping(value = "/getEditableFieldList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FieldXmlMapping>> getEditableFieldXmlMapping() {
        List<FieldXmlMapping> fieldsList = new ArrayList<FieldXmlMapping>();
        try {
            fieldsList = this.exceptionXmlService.getEditableFieldXmlMapping();
            XmlPocController.LOGGER.info("fieldsList : " + fieldsList);
        } catch (Exception e) {
            XmlPocController.LOGGER.error("Exception occured in getFieldList: " + e);
        }
        return new ResponseEntity<List<FieldXmlMapping>>(fieldsList, HttpStatus.OK);
    }

    @GetMapping(value = "/getExceptionForId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getExceptionForId(@PathVariable(value = "id") final Long exceptionID) {
        ExceptionXml exceptionXml = null;
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        List<ExceptionXml> exceptionXmls = new ArrayList<ExceptionXml>();
        try {
            exceptionXml = this.exceptionXmlService.getExceptionForId(exceptionID);
            if (null != exceptionXml) {
                exceptionXmls.add(exceptionXml);
                maps = this.xmlToJsonUtil.getXmlToJsonList(exceptionXmls);
                map.put("EXCEPTION_DATA", maps);
            }
        } catch (Exception e) {
            XmlPocController.LOGGER.error("Exception occured in getExceptionForId: " + e);
        }
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    @PostMapping(value = "/updateExceptionData", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> updateExceptionData(@RequestBody final ExceptionXMLPojo exceptionXMLPojo) {
        String updateResponse = "FAILURE";
        try {
            XmlPocController.LOGGER.info("getDataMap: " + exceptionXMLPojo.getDataMap());
            updateResponse = this.exceptionXmlService.updateExceptionData(exceptionXMLPojo);
        } catch (Exception e) {
            updateResponse = "FAILURE";
            XmlPocController.LOGGER.error("Exception occured in updateExceptionData: " + e);
        }
        return new ResponseEntity<String>(updateResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/getDataOfDropDown", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDataOfDropDown() {
        List<String> currencyList = new ArrayList<String>();
        try {
            currencyList = this.exceptionXmlService.getDataOfDropDown();
            XmlPocController.LOGGER.info("currencyList : " + currencyList);
        } catch (Exception e) {
            XmlPocController.LOGGER.error("Exception occured in getCurrencyData: " + e);
        }
        return new ResponseEntity<List<String>>(currencyList, HttpStatus.OK);
    }

    @GetMapping(value = "/getDataForAutoComplete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDataForAutoComplete(@RequestParam(value = "field") final String field,
        @RequestParam(value = "fieldVal") final String fieldVal) {
        List<String> autoPopulateData = new ArrayList<String>();
        try {
            XmlPocController.LOGGER.info("field: " + field);
            XmlPocController.LOGGER.info("fieldVal: " + fieldVal);
            if (StringUtils.isNotEmpty(field) && StringUtils.isNotEmpty(fieldVal)) {
                autoPopulateData = this.exceptionXmlService.getDataForAutoComplete(field, fieldVal);
            }
        } catch (Exception e) {
            XmlPocController.LOGGER.error("Exception occured in getDataForAutoComplete: " + e);
        }
        return new ResponseEntity<List<String>>(autoPopulateData, HttpStatus.OK);
    }

    @GetMapping(value = "/getDataOfDropDownOnEdit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> getDataOfDropDownOnEdit(@RequestParam(value = "field") final String field,
        @RequestParam(value = "componentType") final String componentType) {
        List<String> currencyList = new ArrayList<String>();
        try {
            currencyList = this.exceptionXmlService.getDataOfDropDownOnEdit(field, componentType);
        } catch (Exception e) {
            XmlPocController.LOGGER.error("Exception occured in getCurrencyData: " + e);
        }
        return new ResponseEntity<List<String>>(currencyList, HttpStatus.OK);
    }
}
