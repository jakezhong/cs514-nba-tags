package com.example.getstarted.daos;

import com.example.getstarted.daos.interfaces.PersonDao;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.io.IOException;
import java.util.*;

/**
 * DatastorePersonDao as storage type
 */
public class DatastorePersonDao implements PersonDao {

  // [START constructor]
  private DatastoreService datastore;
  static final String PERSON_KIND = "Person";

    /**
     * Constructor  to get Datastore service
     */
  public DatastorePersonDao() {
    datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
  }
  // [END constructor]

  // [START entityToPerson]

    /**
     * To translate a entity to Person Object
     * @param entity entity
     * @return Person Object
     */
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
          .status((String) entity.getProperty(Person.STATUS))
          .description((String) entity.getProperty(Person.DESCRIPTION))
          .createdBy((String) entity.getProperty(Person.CREATED_BY))
          .createdById((String) entity.getProperty(Person.CREATED_BY_ID))
          .publishedDate((Date) entity.getProperty(Person.PUBLISHED_DATE))
          .imageUrl((String) entity.getProperty(Person.IMAGE_URL))
          .socialLink((String)entity.getProperty(Person.SOCIAL_LINK))
          .build();
  }
  // [END entityToPerson]

  // [START create]

    /**
     * To create an entity and create key
     * @param person Person
     * @return Long, the id of the key
     */
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
      incPersonEntity.setProperty(Person.STATUS, person.getStatus());
      incPersonEntity.setProperty(Person.DESCRIPTION, person.getDescription());
      incPersonEntity.setProperty(Person.CREATED_BY, person.getCreatedBy());
      incPersonEntity.setProperty(Person.CREATED_BY_ID, person.getCreatedById());
      incPersonEntity.setProperty(Person.PUBLISHED_DATE, person.getPublishedDate());
      incPersonEntity.setProperty(Person.IMAGE_URL, person.getImageUrl());
      incPersonEntity.setProperty(Person.SOCIAL_LINK,person.getSocialLink());

      Key personKey = datastore.put(incPersonEntity); // Save the Entity
      return personKey.getId();                     // The ID of the Key
  }
  // [END create]

  // [START read]

    /**
     * Read Person according to personId
     * @param personId Long PersonId
     * @return Person Object
     */
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

    /**
     * To update person info and store the updated info
     * @param person Person Object
     */
  @Override
  public void updatePerson(Person person) {
      Key key = KeyFactory.createKey(PERSON_KIND, person.getId());  // From a person, create a Key
      Entity entity = new Entity(key);         // Convert Person to an Entity
      entity.setProperty(Person.FIRST, person.getFirst());
      entity.setProperty(Person.LAST, person.getLast());
      entity.setProperty(Person.TITLE, person.getTitle());
      entity.setProperty(Person.INTRODUCTION, person.getIntroduction());
      entity.setProperty(Person.EMAIL, person.getEmail());
      entity.setProperty(Person.PHONE, person.getPhone());
      entity.setProperty(Person.ADDRESS, person.getAddress());
      entity.setProperty(Person.CATEGORY, person.getCategory());
      entity.setProperty(Person.STATUS, person.getStatus());
      entity.setProperty(Person.DESCRIPTION, person.getDescription());
      entity.setProperty(Person.CREATED_BY, person.getCreatedBy());
      entity.setProperty(Person.CREATED_BY_ID, person.getCreatedById());
      entity.setProperty(Person.PUBLISHED_DATE, person.getPublishedDate());
      entity.setProperty(Person.IMAGE_URL, person.getImageUrl());
      entity.setProperty(Person.SOCIAL_LINK, person.getSocialLink());

      datastore.put(entity);                   // Update the Entity
  }
  // [END update]

  // [START delete]
    /**
     * To delete Person according to personId
     * @param personId
     */
  @Override
  public void deletePerson(Long personId) {
      Key key = KeyFactory.createKey(PERSON_KIND, personId);        // Create the Key
      datastore.delete(key);                      // Delete the Entity
  }
  // [END delete]

  // [START entitiesToPersons]

    /**
     * Loop through Iterator<Result> and call entity to person
     * To translate all entities to person Object
     * @param results  Iterator<Entity>
     * @return a lit of person
     */
  public List<Person> entitiesToPersons(Iterator<Entity> results) {
      List<Person> resultPersons = new ArrayList<>();
      while (results.hasNext()) {  // We still have data
        resultPersons.add(entityToPerson(results.next()));      // Add the Person to the List
      }
      return resultPersons;
  }
  // [END entitiesToPersons]

  // [START listpersons]

    /**
     * List all Persons
     * @param startCursorString to display 10 per time
     * @return Result<Person>
     */
  @Override
  public Result<Person> listPersons(String startCursorString) {
      FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
      if (startCursorString != null && !startCursorString.equals("")) {
        fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
      }
      Query query = new Query(PERSON_KIND) // We only care about Persons
      .addSort(Person.FIRST, SortDirection.ASCENDING); // Use default Index "first"

      PreparedQuery preparedQuery = datastore.prepare(query);
      QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

      List<Person> resultPersons = entitiesToPersons(results);     // Retrieve and convert Entities
      Cursor cursor = results.getCursor();              // Where to start next time

      if (cursor != null && resultPersons.size() == 8) {         // Are we paging? Save Cursor
        String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
        return new Result<>(resultPersons, cursorString);
      } else {
        return new Result<>(resultPersons);
      }
  }
  // [END listpersons]

    // [START listPostsBySearch]
    /**
     * List all Posts by Search
     * @param startCursorString to display 10 per time
     * @return Result<Post>
     */
    @Override
    public Result<Person> listPersonsBySearch(Hashtable search, String startCursorString) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
        if (startCursorString != null && !startCursorString.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
        }
        Query query = new Query(PERSON_KIND) // We only care about Persons
        .addSort(Person.FIRST, SortDirection.ASCENDING); // Use default Index "first"

        if (search.size() > 0) {
            Enumeration keys;
            String key;
            ArrayList<Query.Filter> filters = new ArrayList<Query.Filter>();

            keys = search.keys();

            while(keys.hasMoreElements()) {
                key = (String) keys.nextElement();
                Query.FilterPredicate filter = new Query.FilterPredicate(key, Query.FilterOperator.EQUAL, search.get(key));
                filters.add(filter);
            }

            if (filters.size() > 1) {
                Query.CompositeFilter filter = Query.CompositeFilterOperator.and(filters);
                System.out.println(filter.toString());
                query.setFilter(filter);
            } else {
                query.setFilter(filters.get(0));
            }
        }

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<Person> resultPersons = entitiesToPersons(results);     // Retrieve and convert Entities
        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && resultPersons.size() == 8) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(resultPersons, cursorString);
        } else {
            return new Result<>(resultPersons);
        }
    }
    // [END listPersonsBySearch]
    
    public Result<Person> listAllPersons() {
        Query query = new Query(PERSON_KIND) // We only care about Persons
        .addSort(Person.FIRST, SortDirection.ASCENDING); // Use default Index "first"

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator();
        List<Person> resultPersons = entitiesToPersons(results);     // Retrieve and convert Entities

        return new Result<>(resultPersons);
    }

  // [START listbyuser]
    @Override
    /**
    * List all Persons by specific person
    * @param startCursorString to display 10 per time
    * @return Result<Person>
    */
    public Result<Person> listPersonsByUser(String userId, String startCursorString) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
        if (startCursorString != null && !startCursorString.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
        }
        Query query = new Query(PERSON_KIND) // We only care about Persons
        // Only for this user
        .setFilter(new Query.FilterPredicate(Person.CREATED_BY_ID, Query.FilterOperator.EQUAL, userId))
        // a custom datastore index is required since you are filtering by one property
        // but ordering by another
        .addSort(Person.FIRST, SortDirection.ASCENDING);
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<Person> resultPersons = entitiesToPersons(results);     // Retrieve and convert Entities
        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && resultPersons.size() == 8) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(resultPersons, cursorString);
        } else {
            return new Result<>(resultPersons);
        }
    }

    // [END listbyuser]
    @Override
    /**
     * List all persons for a specific user
     * @return Result<Person>
     */
    public Result<Person> listAllPersonsByUser(String userId) {
        Query query = new Query(PERSON_KIND) // We only care about Persons
        .setFilter(new Query.FilterPredicate(Person.CREATED_BY_ID, Query.FilterOperator.EQUAL, userId))
        .addSort(Person.FIRST, SortDirection.ASCENDING); // Use default Index "first"

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator();
        List<Person> resultPersons = entitiesToPersons(results);     // Retrieve and convert Entities

        return new Result<>(resultPersons);
    }

    @Override
    /**
     * List all social links for a specific person
     * @return Result<Person>
     */
    public void addSocialLinkInPerson(long personId, String socialLinkName, String socialLinkUrl){
        ObjectMapper mapper = new ObjectMapper();
        try{
            Entity personEntity = datastore.get(KeyFactory.createKey(PERSON_KIND, personId));
            String jsonString = (String) personEntity.getProperty(Person.SOCIAL_LINK);
            Map<String,String> map;
            //convert string to a map
            if(jsonString.length()!=0){
                map = mapper.readValue(jsonString, Map.class);
            }else {
                map = new HashMap<>();
            }
            //convert jsonString to map, and fetch existed content
            //add new sociallink
            map.put(socialLinkName,socialLinkUrl);
            // convert to string
            String newJsonString  = mapper.writeValueAsString(map);
            //System.out.println(newJsonString);
            //update entity
            personEntity.setProperty(Person.SOCIAL_LINK,newJsonString);

            datastore.put(personEntity);
        }catch(IOException e){
            e.printStackTrace();
        }catch (EntityNotFoundException e){
            System.out.println("error adding socialLink");
        }
    }

    /**
     * use hashmap since the key is unique, so when key is same, will update value
     * @param personId personId
     * @return map
     */
    public Map<String,String> listSocialLink(long personId){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = null;
        try{
            Entity personEntity = datastore.get(KeyFactory.createKey(PERSON_KIND, personId));
            String jsonString = (String) personEntity.getProperty(Person.SOCIAL_LINK);

            //convert string to a map
            if(jsonString.length()!=0){
                map = mapper.readValue(jsonString, Map.class);
            }else{
                map = new HashMap<>();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        catch (EntityNotFoundException e){
            System.out.println("error listing socialLink");
        }
        return map;
    }

    /**
     * Delete the selected social link
     * @param postId
     * @param commentKey
     */
    public void deleteSocialLink( long postId, String commentKey){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = null;
        try{
            Entity personEntity = datastore.get(KeyFactory.createKey(PERSON_KIND, postId));
            String jsonString = (String) personEntity.getProperty(Person.SOCIAL_LINK);

            //convert string to a map
            if(jsonString.length()!=0){
                map = mapper.readValue(jsonString, Map.class);
                map.remove(commentKey);
                String newJsonString  = mapper.writeValueAsString(map);
                personEntity.setProperty(Person.SOCIAL_LINK,newJsonString);
                datastore.put(personEntity);

            }else{
                return;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        catch (EntityNotFoundException e){
            System.out.println("error deleting  socialLink");
        }
    }
}