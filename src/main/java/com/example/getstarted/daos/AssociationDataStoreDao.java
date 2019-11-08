/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *`
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.getstarted.daos;

import com.example.getstarted.objects.Association;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import com.example.getstarted.daos.PersonDao;

import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.QueryResultIterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// [START example]
public class AssociationDataStoreDao implements AssociationDao {

  // [START constructor]
  private DatastoreService datastore;
  private static final String ASSOCIATION_KIND = "Association";

  public AssociationDataStoreDao() {
    datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
  }
  // [END constructor]

  // [START entityToAssociation]
  public Association entityToAssociation(Entity entity) {
    return new Association.Builder()                                     // Convert to Association form
        .id(entity.getKey().getId())
        .personId((Long) entity.getProperty(Association.PERSON_ID))
        .groupId((Long) entity.getProperty(Association.GROUP_ID))
        .build();
  }
  // [END entityToAssociation]

  // [START create]
  @Override
  public Long createAssociation(Association association) {
    Entity incAssociationEntity = new Entity(ASSOCIATION_KIND);  // Key will be assigned once written
    incAssociationEntity.setProperty(Association.PERSON_ID, association.getPersonId());
    incAssociationEntity.setProperty(Association.GROUP_ID, association.getGroupId());

    Key associationKey = datastore.put(incAssociationEntity); // Save the Entity
    return associationKey.getId();                     // The ID of the Key
  }
  // [END create]

  // [START read]
  @Override
  public Association readAssociation(Long associationId) {
    try {
      Entity associationEntity = datastore.get(KeyFactory.createKey(ASSOCIATION_KIND, associationId));
      return entityToAssociation(associationEntity);
    } catch (EntityNotFoundException e) {
      return null;
    }
  }
  // [END read]

  // [START delete]
  @Override
  public void deleteAssociation(Long associationId) {
    Key key = KeyFactory.createKey(ASSOCIATION_KIND, associationId);        // Create the Key
    datastore.delete(key);                      // Delete the Entity
  }
  // [END delete]

  // [START entitiesToAssociations]
  public List<Association> entitiesToAssociations(Iterator<Entity> results) {
    List<Association> resultAssociations = new ArrayList<>();
    while (results.hasNext()) {  // We still have data
      resultAssociations.add(entityToAssociation(results.next()));      // Add the Association to the List
    }
    return resultAssociations;
  }
  // [END entitiesToAssociations]

  // [START listassociations]
  @Override
  public Result<Association> listPersonsByGroup(String groupId) {

    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10); // Only show 10 at a time
    Query query = new Query(ASSOCIATION_KIND); // We only care about Associations
//    .setFilter(new Query.FilterPredicate(
//            Association.GROUP_ID, Query.FilterOperator.EQUAL, groupId));
            // a custom datastore index is required since you are filtering by one property
    PreparedQuery preparedQuery = datastore.prepare(query);
    QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

    List<Association> resultAssociations = entitiesToAssociations(results);     // Retrieve and convert Entities

    List<Person> persons = null;
    for (Association association : resultAssociations) {
        PersonDataStoreDao personDao = new PersonDataStoreDao();
        Person person = personDao.readPerson(association.getPersonId());
        persons.add(person);
    }

    return new Result<>(resultAssociations);

//    List<Person> persons = null;
//    for (Association association : resultAssociations) {
//      Query personQuery = new Query(PERSON_KIND) // We only care about Persons
//              // Only for this user
//              .setFilter(new Query.FilterPredicate(
//                      Person.ID, Query.FilterOperator.EQUAL, association.getPersonId()));
//              // a custom datastore index is required since you are filtering by one property
//      PreparedQuery preparedPersonQuery = datastore.prepare(personQuery);
//      Entity entity = preparedPersonQuery.asQueryResultIterator();
//
//      Person person = new Person.Builder()
//          .last((String) entity.getProperty(Person.LAST))
//          .description((String) entity.getProperty(Person.DESCRIPTION))
//          .first((String) entity.getProperty(Person.FIRST))
//          .imageUrl((String) entity.getProperty(Person.IMAGE_URL))
//          // [START auth]
//          .createdBy((String) entity.getProperty(Person.CREATED_BY))
//          .createdById((String) entity.getProperty(Person.CREATED_BY_ID))
//          // [END auth]
//          .build();
//      persons.add(person);
//    }
  }
  // [END listassociations]

  // [START listassociationsbyuser]
//  @Override
//  public Result<Association> listGroups(String personId, String startCursorString) {
//    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10); // Only show 10 at a time
//    if (startCursorString != null && !startCursorString.equals("")) {
//      fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
//    }
//    Query query = new Query(GROUP_KIND) // We only care about Associations
//        // Only for this user
//        .setFilter(new Query.FilterPredicate(
//            Association.CREATED_BY_ID, Query.FilterOperator.EQUAL, userId))
//        // a custom datastore index is required since you are filtering by one property
//        // but ordering by another
//        .addSort(Association.NAME, SortDirection.ASCENDING);
//    PreparedQuery preparedQuery = datastore.prepare(query);
//    QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);
//
//    List<Association> resultAssociations = entitiesToAssociations(results);     // Retrieve and convert Entities
//    Cursor cursor = results.getCursor();              // Where to start next time
//    if (cursor != null && resultAssociations.size() == 10) {         // Are we paging? Save Cursor
//      String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
//      return new Result<>(resultAssociations, cursorString);
//    } else {
//      return new Result<>(resultAssociations);
//    }
//    return new Result<>(resultAssociations);
//  }
  // [END listassociationbyuser]
}
// [END example]
