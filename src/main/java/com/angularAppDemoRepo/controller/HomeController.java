package com.angularAppDemoRepo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/home")
public class HomeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

	@GetMapping(value = "/welCome")
	public String helloWorldName() {
		String returnString = null;
		try {
			returnString = "Wel-Come to UI service client";
		} catch (Exception e) {
			LOGGER.error("Exception occured in helloWorldName: " + e);
		}
		LOGGER.info("returnString: " + returnString);
		return "<Strong>"+ returnString + "</Strong>";
	}
}
