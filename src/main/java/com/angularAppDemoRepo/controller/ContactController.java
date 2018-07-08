package com.angularAppDemoRepo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.angularAppDemoRepo.model.Contact;
import com.angularAppDemoRepo.service.ContactService;


@RestController
@RequestMapping(value = "/contactInfo")
public class ContactController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactController.class);
    
    @Autowired
    private ContactService contactService;
    
    @Value("${pageSize}")
    private Integer pageSize;

    @GetMapping(value = "/welCome")
    public String helloWorldName() {
        String returnString = null;
        try {
            returnString = "Wel-Come to Contact controller";
        } catch (Exception e) {
            ContactController.LOGGER.error("Exception occured in helloWorldName: " + e);
        }
        return "<Strong>" + returnString + "</Strong>";
    }

    

    @PostMapping(value = "/saveContact", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> saveContactInfo(@RequestBody final Contact contact) {
    	String contactCreationResponse = "FAILURE";
        try {
        	contactCreationResponse = "SUCCESS";
        	if(null != contact){
        		contactService.saveContactInfo(contact);
        	}
        } catch (Exception e) {
        	contactCreationResponse = "FAILURE";
            ContactController.LOGGER.error("Exception occured in getDataOfField: " + e);
        }
        return new ResponseEntity<String>(contactCreationResponse, HttpStatus.OK);
    }

    
}
