/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.getstarted.daos;

import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;

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
import java.util.Date;
import java.util.Iterator;
import java.util.List;

// [START example]
public class DatastoreDao implements PersonDao {

  // [START constructor]
  private DatastoreService datastore;
  static final String PERSON_KIND = "Person";

  public DatastoreDao() {
    datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
  }
  // [END constructor]

  // [START entityToPerson]
  public Person entityToPerson(Entity entity) {
      return new Person.Builder()                                     // Convert to Person form
          .id(entity.getKey().getId())
          .first((String) entity.getProperty(Person.FIRST))
          .last((String) entity.getProperty(Person.LAST))
          .title((String) entity.getProperty(Person.TITLE))
          .introduction((String) entity.getProperty(Person.INTRODUCTION))
          .email((String) entity.getProperty(Person.EMAIL))
          .phone((String) entity.getProperty(Person.PHONE))
          .address((String) entity.getProperty(Person.ADDRESS))
          .category((String) entity.getProperty(Person.CATEGORY))
          .type((String) entity.getProperty(Person.TYPE))
          .linkedin((String) entity.getProperty(Person.LINKEDIN))
          .facebook((String) entity.getProperty(Person.FACEBOOK))
          .twitter((String) entity.getProperty(Person.TWITTER))
          .instagram((String) entity.getProperty(Person.INSTAGRAM))
          .youtube((String) entity.getProperty(Person.YOUTUBE))
          .website((String) entity.getProperty(Person.WEBSITE))
          .description((String) entity.getProperty(Person.DESCRIPTION))
          .createdBy((String) entity.getProperty(Person.CREATED_BY))
          .createdById((String) entity.getProperty(Person.CREATED_BY_ID))
          .publishedDate((Date) entity.getProperty(Person.PUBLISHED_DATE))
          .imageUrl((String) entity.getProperty(Person.IMAGE_URL))
          .build();
  }
  // [END entityToPerson]

  // [START create]
  @Override
  public Long createPerson(Person person) {
      Entity incPersonEntity = new Entity(PERSON_KIND);  // Key will be assigned once written
      incPersonEntity.setProperty(Person.FIRST, person.getFirst());
      incPersonEntity.setProperty(Person.LAST, person.getLast());
      incPersonEntity.setProperty(Person.TITLE, person.getTitle());
      incPersonEntity.setProperty(Person.INTRODUCTION, person.getIntroduction());
      incPersonEntity.setProperty(Person.EMAIL, person.getEmail());
      incPersonEntity.setProperty(Person.PHONE, person.getPhone());
      incPersonEntity.setProperty(Person.ADDRESS, person.getAddress());
      incPersonEntity.setProperty(Person.CATEGORY, person.getCategory());
      incPersonEntity.setProperty(Person.TYPE, person.getType());
      incPersonEntity.setProperty(Person.LINKEDIN, person.getLinkedin());
      incPersonEntity.setProperty(Person.FACEBOOK, person.getFacebook());
      incPersonEntity.setProperty(Person.TWITTER, person.getTwitter());
      incPersonEntity.setProperty(Person.INSTAGRAM, person.getInstagram());
      incPersonEntity.setProperty(Person.YOUTUBE, person.getYoutube());
      incPersonEntity.setProperty(Person.WEBSITE, person.getWebsite());
      incPersonEntity.setProperty(Person.DESCRIPTION, person.getDescription());
      incPersonEntity.setProperty(Person.CREATED_BY, person.getCreatedBy());
      incPersonEntity.setProperty(Person.CREATED_BY_ID, person.getCreatedById());
      incPersonEntity.setProperty(Person.PUBLISHED_DATE, person.getPublishedDate());
      incPersonEntity.setProperty(Person.IMAGE_URL, person.getImageUrl());

      Key personKey = datastore.put(incPersonEntity); // Save the Entity
      return personKey.getId();                     // The ID of the Key
  }
  // [END create]

  // [START read]
  @Override
  public Person readPerson(Long personId) {
      try {
        Entity personEntity = datastore.get(KeyFactory.createKey(PERSON_KIND, personId));
        return entityToPerson(personEntity);
      } catch (EntityNotFoundException e) {
        return null;
      }
  }
  // [END read]

  // [START update]
  @Override
  public void updatePerson(Person person) {
      Key key = KeyFactory.createKey(PERSON_KIND, person.getId());  // From a person, create a Key
      Entity entity = new Entity(key);         // Convert Person to an Entity
      entity.setProperty(Person.FIRST, person.getFirst());
      entity.setProperty(Person.LAST, person.getLast());
      entity.setProperty(Person.TITLE, person.getLast());
      entity.setProperty(Person.INTRODUCTION, person.getIntroduction());
      entity.setProperty(Person.EMAIL, person.getEmail());
      entity.setProperty(Person.PHONE, person.getPhone());
      entity.setProperty(Person.ADDRESS, person.getAddress());
      entity.setProperty(Person.CATEGORY, person.getCategory());
      entity.setProperty(Person.TYPE, person.getType());
      entity.setProperty(Person.LINKEDIN, person.getLinkedin());
      entity.setProperty(Person.FACEBOOK, person.getFacebook());
      entity.setProperty(Person.TWITTER, person.getTwitter());
      entity.setProperty(Person.INSTAGRAM, person.getInstagram());
      entity.setProperty(Person.YOUTUBE, person.getYoutube());
      entity.setProperty(Person.WEBSITE, person.getWebsite());
      entity.setProperty(Person.DESCRIPTION, person.getDescription());
      entity.setProperty(Person.CREATED_BY, person.getCreatedBy());
      entity.setProperty(Person.CREATED_BY_ID, person.getCreatedById());
      entity.setProperty(Person.PUBLISHED_DATE, person.getPublishedDate());
      entity.setProperty(Person.IMAGE_URL, person.getImageUrl());

      datastore.put(entity);                   // Update the Entity
  }
  // [END update]

  // [START delete]
  @Override
  public void deletePerson(Long personId) {
      Key key = KeyFactory.createKey(PERSON_KIND, personId);        // Create the Key
      datastore.delete(key);                      // Delete the Entity
  }
  // [END delete]

  // [START entitiesToPersons]
  public List<Person> entitiesToPersons(Iterator<Entity> results) {
      List<Person> resultPersons = new ArrayList<>();
      while (results.hasNext()) {  // We still have data
        resultPersons.add(entityToPerson(results.next()));      // Add the Person to the List
      }
      return resultPersons;
  }
  // [END entitiesToPersons]

  // [START listpersons]
  @Override
  public Result<Person> listPersons(String startCursorString) {
      FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10); // Only show 10 at a time
      if (startCursorString != null && !startCursorString.equals("")) {
        fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
      }
      Query query = new Query(PERSON_KIND) // We only care about Persons
          .addSort(Person.FIRST, SortDirection.ASCENDING); // Use default Index "first"
      PreparedQuery preparedQuery = datastore.prepare(query);
      QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

      List<Person> resultPersons = entitiesToPersons(results);     // Retrieve and convert Entities
      Cursor cursor = results.getCursor();              // Where to start next time
      if (cursor != null && resultPersons.size() == 10) {         // Are we paging? Save Cursor
        String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
        return new Result<>(resultPersons, cursorString);
      } else {
        return new Result<>(resultPersons);
      }
  }
  // [END listpersons]

  // [START listbyuser]
  @Override
  public Result<Person> listPersonsByUser(String userId, String startCursorString) {
      FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10); // Only show 10 at a time
      if (startCursorString != null && !startCursorString.equals("")) {
        fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
      }
      Query query = new Query(PERSON_KIND) // We only care about Persons
          // Only for this user
          .setFilter(new Query.FilterPredicate(
              Person.CREATED_BY_ID, Query.FilterOperator.EQUAL, userId))
          // a custom datastore index is required since you are filtering by one property
          // but ordering by another
          .addSort(Person.LAST, SortDirection.ASCENDING);
      PreparedQuery preparedQuery = datastore.prepare(query);
      QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

      List<Person> resultPersons = entitiesToPersons(results);     // Retrieve and convert Entities
      Cursor cursor = results.getCursor();              // Where to start next time
      if (cursor != null && resultPersons.size() == 10) {         // Are we paging? Save Cursor
        String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
        return new Result<>(resultPersons, cursorString);
      } else {
        return new Result<>(resultPersons);
      }
  }
  // [END listbyuser]
}
// [END example]
