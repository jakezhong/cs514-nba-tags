package com.example.getstarted.daos;

import com.example.getstarted.objects.Association;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;

import java.sql.SQLException;

public interface PersonGroupDao {

    Long createAssociation(Association association) throws SQLException;
    Result<Person> listPersonsByGroup(Long groupId, String startCursor) throws SQLException;

}
