package com.example.getstarted.daos;
import com.example.getstarted.objects.Association;
import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Person;
import com.example.getstarted.objects.Result;
import com.google.appengine.api.datastore.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * DatastoreAssociationDao as storage type
 */
public class DatastoreAssociationDao implements AssociationDao {
    private static final String PERSON_KIND = "Person";
    private static final String GROUP_KIND = "Group";
    private static final String ASSOCIATION_KIND = "Association";
    private DatastoreService datastore;
    /**
     * Constructor  to get Datastore service
     */
    public DatastoreAssociationDao() {
        datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
    }

    /**
     * To create an entity and create key
     * @param association Association
     * @return Long, the id of the key
     */
    @Override
    public Long createAssociation(Association association) {
        Entity incAssociationEntity = new Entity(ASSOCIATION_KIND);  // Key will be assigned once written
        incAssociationEntity.setProperty(Association.PERSON_ID, association.getPersonId());
        incAssociationEntity.setProperty(Association.GROUP_ID, association.getGroupId());

        Key associationKey = datastore.put(incAssociationEntity); // Save the Entity
        return associationKey.getId();                     // The ID of the Key
    }

    /**
     * To translate a entity to association Object
     * @param entity entity
     * @return association Object
     */
    public Association entityToAssociation(Entity entity) {
        return new Association.Builder()                                     // Convert to Association form
        .id(entity.getKey().getId())
        .personId((Long) entity.getProperty(Association.PERSON_ID))
        .groupId((Long) entity.getProperty(Association.GROUP_ID))
        .build();
    }

    /**
     * Read group according to groupId
     * @param associationId Long associationId
     * @return association Object
     */
    @Override
    public Association readAssociation(Long associationId) {
        try {
            Entity associationEntity = datastore.get(KeyFactory.createKey(ASSOCIATION_KIND, associationId));
            return entityToAssociation(associationEntity);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    /**
     * To delete Association according to associationId
     * @param associationId
     */
    @Override
    public void deleteAssociation(Long associationId) {
        Key key = KeyFactory.createKey(ASSOCIATION_KIND, associationId);        // Create the Key
        datastore.delete(key);                      // Delete the Entity
    }

    /**
     * when deleting person also deleting association
     * @param personId personId
     */
    public void deleteAssociationByPersonId(Long personId){
        Query query = new Query(ASSOCIATION_KIND) // We only care about Persons
        // Only for this user
        .setFilter(new Query.FilterPredicate(Association. PERSON_ID, Query.FilterOperator.EQUAL, personId));

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> resultAssociations = preparedQuery.asQueryResultIterator();

        List<Association> resultPersons = entitiesToAssociations(resultAssociations);
        for(Association association: resultPersons){
            Long associationId = association.getId();
            Key key = KeyFactory.createKey(ASSOCIATION_KIND, associationId);// Create the Key
            datastore.delete(key);// Delete the Entity
        }
    }

    /**
     * when deleting group also deleting association
     * @param groupId personId
     */
    public void deleteAssociationByGroupId(Long groupId){
        Query query = new Query(ASSOCIATION_KIND) // We only care about Persons
        // Only for this user
        .setFilter(new Query.FilterPredicate(Association. GROUP_ID, Query.FilterOperator.EQUAL, groupId));

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> resultAssociations = preparedQuery.asQueryResultIterator();

        List<Association> resultGroups = entitiesToAssociations(resultAssociations);
        for(Association association: resultGroups){
            Long associationId = association.getId();
            Key key = KeyFactory.createKey(ASSOCIATION_KIND, associationId);// Create the Key
            datastore.delete(key);// Delete the Entity
        }
    }

    /**
     * Loop through Iterator<Result> and call entity to group
     *  To translate all entities to association Object
     * @param results Iterator<Entity>
     * @return List<Association>
     */
    public List<Association> entitiesToAssociations(Iterator<Entity> results) {
        List<Association> resultAssociations = new ArrayList<>();
        while (results.hasNext()) {  // We still have data
            resultAssociations.add(entityToAssociation(results.next()));      // Add the Association to the List
        }
        return resultAssociations;
    }

    /**
     * list all group member in a specific group
     * @param groupId groupId
     * @param startCursor startCursor
     * @return Result<Person>
     */
    @Override
    public Result<Person> listPersonsByGroup(Long groupId, String startCursor) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(6); // Only show 10 at a time
        if (startCursor != null && !startCursor.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor)); // Where we left off
        }
        Query query = new Query(ASSOCIATION_KIND) // We only care about Persons
                // Only for this user
                .setFilter(new Query.FilterPredicate(
                        Association.GROUP_ID, Query.FilterOperator.EQUAL, groupId));
                // a custom datastore index is required since you are filtering by one property
                // but ordering by another
//                .addSort(Person.LAST, Query.SortDirection.ASCENDING);
        System.out.println(query);
        PreparedQuery preparedQuery = datastore.prepare(query);
        System.out.println(preparedQuery);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);


        List<Association> resultPersons = entitiesToAssociations(results);     // Retrieve and convert Entities
        List<Person> member = new ArrayList<>();

        for(Association association: resultPersons){
            Person person = readPerson(association.getPersonId());
            member.add(person);
        }


        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && resultPersons.size() == 6) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(member, cursorString);
        } else {
            return new Result<>(member);
        }
    }

    /**
     * List all groups by specific person
     * @param peopelId peopelId
     * @param startCursor startCursor
     * @return Result<Group>
     */
    public Result<Group> listGroupByPerson(Long peopelId, String startCursor) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(6); // Only show 10 at a time
        if (startCursor != null && !startCursor.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor)); // Where we left off
        }
        Query query = new Query(ASSOCIATION_KIND) // We only care about Persons
                // Only for this user
                .setFilter(new Query.FilterPredicate(
                       Association.PERSON_ID, Query.FilterOperator.EQUAL, peopelId));
        // a custom datastore index is required since you are filtering by one property
        // but ordering by another
//                .addSort(Person.LAST, Query.SortDirection.ASCENDING);
        System.out.println(query);
        PreparedQuery preparedQuery = datastore.prepare(query);
        System.out.println(preparedQuery);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);


        List<Association> resultPersons = entitiesToAssociations(results);     // Retrieve and convert Entities
        List<Group> groups = new ArrayList<>();

        for(Association association: resultPersons){
            Group group = readGroup(association.getGroupId());
            groups.add(group);
        }


        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && resultPersons.size() == 6) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(groups, cursorString);
        } else {
            return new Result<>(groups);
        }
    }

    /**
     * To translate a entity to Person Object
     * @param entity entity
     * @return Person Object
     */
    private Person entityToPerson(Entity entity) {
        return new Person.Builder()                                     // Convert to Person form
        .last((String) entity.getProperty(Person.LAST))
        .description((String) entity.getProperty(Person.DESCRIPTION))
        .id(entity.getKey().getId())
        .imageUrl((String) entity.getProperty(Person.IMAGE_URL))
        .createdBy((String) entity.getProperty(Person.CREATED_BY))
        .createdById((String) entity.getProperty(Person.CREATED_BY_ID))
        .first((String) entity.getProperty(Person.FIRST))
        .build();
    }

    /**
     * Read Person according to personId
     * @param personId Long PersonId
     * @return Person Object
     */
    public Person readPerson(Long personId) {
        try {
            Entity personEntity = datastore.get(KeyFactory.createKey(PERSON_KIND, personId));
            return entityToPerson(personEntity);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    /**
     * Read group according to groupId
     * @param groupId Long groupId
     * @return group Object
     */
    public Group readGroup(Long groupId) {
        try {
            Entity groupEntity = datastore.get(KeyFactory.createKey(GROUP_KIND, groupId));
            return entityToGroup(groupEntity);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    /**
     * To create an entity and create key
     * @param entity entity
     * @return Long, the id of the key
     */
    public Group entityToGroup(Entity entity) {
        return new Group.Builder()                                     // Convert to Group form
        .name((String) entity.getProperty(Group.NAME))
        .description((String) entity.getProperty(Group.DESCRIPTION))
        .id(entity.getKey().getId())
        .imageUrl((String) entity.getProperty(Group.IMAGE_URL))
        .createdBy((String) entity.getProperty(Group.CREATED_BY))
        .createdById((String) entity.getProperty(Group.CREATED_BY_ID))
        .build();
    }
}