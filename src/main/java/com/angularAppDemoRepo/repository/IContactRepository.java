package com.angularAppDemoRepo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.angularAppDemoRepo.model.Contact;

@RepositoryRestResource(collectionResourceRel = "contact", path = "contact")
public interface IContactRepository extends JpaRepository<Contact, Long> {

}
