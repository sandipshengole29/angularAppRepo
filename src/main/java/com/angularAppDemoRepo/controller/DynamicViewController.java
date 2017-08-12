package com.angularAppDemoRepo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angularAppDemoRepo.pojo.DynamicViewPojo;

/**
 * <p>
 * <b> TODO : Insert description of the class's responsibility/role. </b>
 * </p>
 */

@RestController
@RequestMapping(value = "/dynaView")
public class DynamicViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicViewController.class);

    @GetMapping(value = "/welCome")
    public String welComeToDynamicController() {
        String returnString = null;
        try {
            returnString = "Wel-Come Dynamic Controller";
        } catch (Exception e) {
            DynamicViewController.LOGGER.error("Exception occured in welComeToDynamicController: " + e);
        }
        return "<Strong>" + returnString + "</Strong>";
    }

    @PostMapping(value = "/saveDynaViewData", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> saveDynaViewData(@RequestBody final List<DynamicViewPojo> dynamicViewPojos,
        final HttpSession httpSession) {
        String saveResponse = "FAILURE";
        try {
            for (DynamicViewPojo dynamicViewPojo : dynamicViewPojos) {
                DynamicViewController.LOGGER.info("dynamicViewPojo: " + dynamicViewPojo.toString());
            }
            if (null != dynamicViewPojos && !(dynamicViewPojos.isEmpty())) {
                httpSession.setAttribute("FUNCTIONAL_DATA", dynamicViewPojos);
                httpSession.setAttribute("IS_SAVED", 'Y');
                saveResponse = "SUCCESS";
            }
        } catch (Exception e) {
            saveResponse = "FAILURE";
            DynamicViewController.LOGGER.error("Exception occured in saveDynaViewData: " + e);
        }
        return new ResponseEntity<String>(saveResponse, HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @GetMapping(value = "/getDynaViewData/{type}/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DynamicViewPojo>> getDynaViewData(@PathVariable(value = "type") final String type,
        @PathVariable(value = "id") final Long id, final HttpSession httpSession) {
        List<DynamicViewPojo> dynamicViewPojos = new ArrayList<DynamicViewPojo>();
        try {
            if (0 != id && StringUtils.isNotEmpty(type)) {
                if (type.toUpperCase().equals("FG")) {
                    if (null != httpSession.getAttribute("FUNCTIONAL_DATA")) {
                        dynamicViewPojos = (List<DynamicViewPojo>) httpSession.getAttribute("FUNCTIONAL_DATA");
                    }
                }
            }
        } catch (Exception e) {
            DynamicViewController.LOGGER.error("Exception occured in getDynaViewData: " + e);
        }
        return new ResponseEntity<List<DynamicViewPojo>>(dynamicViewPojos, HttpStatus.OK);
    }

}
