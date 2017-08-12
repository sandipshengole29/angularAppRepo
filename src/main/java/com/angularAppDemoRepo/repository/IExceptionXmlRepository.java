package com.angularAppDemoRepo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.angularAppDemoRepo.model.ExceptionXml;

@Repository
public interface IExceptionXmlRepository extends JpaRepository<ExceptionXml, Long> {

    @Query(value = "SELECT ex FROM ExceptionXml AS ex ORDER BY ex.id DESC")
    public List<ExceptionXml> getListOfExceptions();

    @Query(value = "SELECT ex FROM ExceptionXml AS ex WHERE ex.id=:id ORDER BY ex.id DESC")
    public ExceptionXml getExceptionXmlInfo(@Param("id") Long longId);

    @Query(value = "SELECT ex FROM ExceptionXml AS ex WHERE ex.exceptionKey=:exceptionKey ORDER BY ex.id DESC")
    public ExceptionXml getExceptionXmlByExceptionKey(@Param("exceptionKey") String exceptionKey);

}
