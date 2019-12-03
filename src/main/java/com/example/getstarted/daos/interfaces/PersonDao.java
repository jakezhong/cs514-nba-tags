package com.example.getstarted.daos.interfaces;

import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

/**
 * Interface for PersonDao Class
 */
public interface PersonDao {
  Long createPerson(Person person) throws SQLException;

  Person readPerson(Long personId) throws SQLException;

  void updatePerson(Person person) throws SQLException;

  void deletePerson(Long personId) throws SQLException;

  Result<Person> listPersons(String startCursor) throws SQLException;

  Result<Person> listPersonsBySearch(Hashtable search, String startCursor) throws SQLException;

  Result<Person> listAllPersons() throws SQLException;

  Result<Person> listPersonsByUser(String userId, String startCursor) throws SQLException;

  Result<Person> listAllPersonsByUser(String userId) throws SQLException;

  Map<String,String> listSocialLink(long personId)throws SQLException;
}
