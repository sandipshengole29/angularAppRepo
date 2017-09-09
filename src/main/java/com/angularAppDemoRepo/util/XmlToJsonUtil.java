package com.angularAppDemoRepo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.angularAppDemoRepo.model.ExceptionXml;
import com.angularAppDemoRepo.model.FieldXmlMapping;
import com.angularAppDemoRepo.service.ExceptionXmlService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class XmlToJsonUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(XmlToJsonUtil.class);

    @Autowired
    private ExceptionXmlService exceptionXmlService;

    public List<Map<String, Object>> getXmlToJsonList(final List<ExceptionXml> exceptionXmls) {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        Map<String, Object> jsonDataMap = null;
        try {
            for (ExceptionXml exceptionXml : exceptionXmls) {
                Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
                dataMap.put("ID", exceptionXml.getExceptionId());

                InputStream inputStream =
                    new ByteArrayInputStream(exceptionXml.getExceptionFeed().getBytes(StandardCharsets.UTF_8));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                IOUtils.copy(inputStream, byteArrayOutputStream);
                String clobAsString = new String(byteArrayOutputStream.toByteArray(), "UTF-8");

                JSONObject xmlJSONObj = XML.toJSONObject(clobAsString);

                JSONObject jsonContent = xmlJSONObj.getJSONObject("exceptionFeed");
                XmlToJsonUtil.LOGGER.info("JsonObject: " + xmlJSONObj);

                jsonDataMap = new ObjectMapper().readValue(jsonContent.toString(), new TypeReference<Map<String, Object>>() {});
                dataMap.putAll(prepareMapToDisplay(jsonDataMap));

                maps.add(dataMap);
            }
        } catch (Exception e) {
            XmlToJsonUtil.LOGGER.error("Exception occured in getXmlToJsonList: " + e);
        }
        XmlToJsonUtil.LOGGER.info("maps_data: " + maps);
        return maps;
    }

    public Map<String, Object> prepareMapToDisplay(final Map<String, Object> dataMap) {
        List<FieldXmlMapping> displayList = new ArrayList<FieldXmlMapping>();
        Map<String, Object> returnMap = new LinkedHashMap<String, Object>();
        try {
            displayList = this.exceptionXmlService.getListOfFieldXmlMapping();
            if (null != displayList && !(displayList.isEmpty())) {
                for (FieldXmlMapping fieldXmlMapping : displayList) {
                    returnMap.put(fieldXmlMapping.getFieldName(), dataMap.get(fieldXmlMapping.getFieldName()));
                }
            }
        } catch (Exception e) {
            XmlToJsonUtil.LOGGER.error("Exception occured in prepareMapToDisplay: " + e);
        }
        return returnMap;
    }
}
