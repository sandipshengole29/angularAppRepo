/**
 * 
 */
package com.angularAppDemoRepo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.angularAppDemoRepo.model.Contact;
import com.angularAppDemoRepo.repository.IContactRepository;

/**
 * @author sshengole
 *
 */

@Service
public class ContactServiceImpl implements ContactService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);
	
    @Autowired
    private IContactRepository iContactRepository;
    
	@Override
	public void saveContactInfo(Contact contact) {
		try {
			iContactRepository.save(contact);
		} catch (Exception e) {
			LOGGER.error("Exception occured in saveContactInfo @ContactServiceImpl: " + e);
		}
	}

}
