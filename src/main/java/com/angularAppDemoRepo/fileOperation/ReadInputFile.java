package com.angularAppDemoRepo.fileOperation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class ReadInputFile {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReadInputFile.class);

    public List<Map<String, String>> getInputFromFile(final String fileName) {
        List<Map<String, String>> inputFileMapList = new ArrayList<Map<String, String>>();
        Map<String, String> inputFileMap = null;
        List<String> headingList = new ArrayList<String>();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split("\\|");
                if (!(headingList.isEmpty()) && null != headingList) {
                    inputFileMap = new HashMap<String, String>();
                    for (int i = 0; i < fields.length; i++) {
                        inputFileMap.put(headingList.get(i), fields[i]);
                    }
                } else {
                    for (int i = 0; i < fields.length; i++) {
                        headingList.add(fields[i]);
                    }
                }
                if (null != inputFileMap) {
                    inputFileMapList.add(inputFileMap);
                }
            }
        } catch (Exception e) {
            ReadInputFile.LOGGER.error("Exception occured in getInputFromFile: ", e);
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    ReadInputFile.LOGGER.error("Exception occured while closing bufferedReader: ", e);
                }
            }
        }
        return inputFileMapList;
    }
}
