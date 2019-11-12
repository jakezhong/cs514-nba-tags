package com.example.getstarted.daos;

import com.example.getstarted.objects.Association;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import com.google.appengine.api.datastore.Entity;

import java.sql.SQLException;

/**
 * Interface for PersonGroup Classes
 */
public interface PersonGroupDao {

    Long createAssociation(Association association) throws SQLException;

    Association readAssociation(Long associationId) throws SQLException;

    void deleteAssociation(Long associationId) throws SQLException;

    void deleteAssociationByPersonId(Long personId) throws SQLException;

    Association entityToAssociation(Entity entity) throws SQLException;

    Result<Person> listPersonsByGroup(Long groupId, String startCursor) throws SQLException;

}