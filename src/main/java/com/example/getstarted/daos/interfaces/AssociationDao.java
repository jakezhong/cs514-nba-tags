package com.example.getstarted.daos.interfaces;

import com.example.getstarted.objects.Association;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import com.google.appengine.api.datastore.Entity;
import java.sql.SQLException;

/**
 * Interface for PersonGroup Classes
 */
public interface AssociationDao {

    Long createAssociation(Association association) throws SQLException;

    Association readAssociation(Long associationId) throws SQLException;

    void deleteAssociation(Long associationId) throws SQLException;

    void deleteAssociationByPersonId(Long personId) throws SQLException;

    void deleteAssociationByGroupId(Long personId) throws SQLException;

    Association entityToAssociation(Entity entity) throws SQLException;

    Result<Long> listPersonsByGroup(Long groupId, String startCursor) throws SQLException;

    Result<Long> listAllPersonsByGroup(Long groupId) throws SQLException;

    Result<Long> listGroupByPerson(Long personId, String startCursor) throws SQLException;

    Result<Long> listAllGroupByPerson(Long personId) throws SQLException;

}