package com.angularAppDemoRepo.fileOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.angularAppDemoRepo.model.ExceptionXml;
import com.angularAppDemoRepo.service.ExceptionXmlService;

@Component
public class PrepareXMLObject {
    private static final Logger LOGGER = LoggerFactory.getLogger(PrepareXMLObject.class);

    @Autowired
    private ExceptionXmlService exceptionXmlService;

    @SuppressWarnings("unchecked")
    public List<ExceptionXml> designXML(final List<Map<String, String>> inputFileMapsList) {
        List<ExceptionXml> exceptionXMLPojos = new ArrayList<ExceptionXml>();
        ExceptionXml exceptionXml = null;
        try {
            for (Map<String, String> inputFileMap : inputFileMapsList) {

                XStream magicApi = new XStream(new DomDriver("UTF-8"));
                magicApi.registerConverter(new MapEntryConverter());
                magicApi.alias("exceptionFeed", Map.class);
                String xml = magicApi.toXML(inputFileMap);
                StringBuilder builder = new StringBuilder();
                builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                builder.append(xml);

                String uniqueKey = getUniqueKey(inputFileMap);
                exceptionXml = this.exceptionXmlService.getExceptionXmlByUniqueKey(uniqueKey);

                if (null != exceptionXml) {
                    Map<String, String> dbXMLMap = (Map<String, String>) magicApi.fromXML(exceptionXml.getExceptionFeed());
                    dbXMLMap.putAll(inputFileMap);
                    String updatedXML = magicApi.toXML(dbXMLMap);
                    PrepareXMLObject.LOGGER.info("updatedXML: " + updatedXML);
                    exceptionXml.setExceptionFeed(updatedXML);
                    exceptionXml.setExceptionKey(uniqueKey);
                } else {
                    exceptionXml = new ExceptionXml();
                    exceptionXml.setExceptionKey(uniqueKey);
                    exceptionXml.setExceptionFeed(xml);
                    exceptionXml.setExceptionType(inputFileMap.get("ExceptionType"));
                }

                exceptionXMLPojos.add(exceptionXml);
            }
        } catch (Exception e) {
            PrepareXMLObject.LOGGER.error("exception in creating xml string: ", e);
        }

        return exceptionXMLPojos;
    }

    public String getUniqueKey(final Map<String, String> inputDataMap) {
        StringBuilder keyBuilder = new StringBuilder();
        try {
            keyBuilder.append(inputDataMap.get("TradeId"));
            if (StringUtils.isNotEmpty(inputDataMap.get("BooKId"))) {
                keyBuilder.append("_");
                keyBuilder.append(inputDataMap.get("BooKId"));
            }
        } catch (Exception e) {
            PrepareXMLObject.LOGGER.error("exception in getUniqueKey string: ", e);
        }
        return keyBuilder.toString();
    }
}
