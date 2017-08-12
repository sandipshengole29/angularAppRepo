package com.angularAppDemoRepo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.angularAppDemoRepo.model.FieldXmlMapping;

@Repository
public interface IFieldXmlMappingRepository extends JpaRepository<FieldXmlMapping, Long> {

    @Query(value = "SELECT fxm FROM FieldXmlMapping fxm WHERE fxm.uiVisibility='Y' ORDER BY id ASC")
    public List<FieldXmlMapping> getListOfFieldXmlMapping();

    @Query(value = "SELECT fxm FROM FieldXmlMapping fxm WHERE fxm.isEditable='Y' ORDER BY id ASC")
    public List<FieldXmlMapping> getEditableFieldXmlMapping();
}
