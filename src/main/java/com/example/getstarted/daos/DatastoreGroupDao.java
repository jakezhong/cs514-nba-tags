package com.example.getstarted.daos;

import com.example.getstarted.daos.interfaces.GroupDao;
import com.example.getstarted.objects.Group;
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

/**
 * DatastoreGroupDao as storage type
 */
public class DatastoreGroupDao implements GroupDao {

    // [START constructor]
    private DatastoreService datastore;
    private static final String GROUP_KIND = "Group";
    /**
     * Constructor  to get Datastore service
     */
    public DatastoreGroupDao() {
        datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
    }
    // [END constructor]

    // [START entityToPerson]
    /**
     * To translate a entity to group Object
     * @param entity entity
     * @return Group Object
     */
    public Group entityToGroup(Entity entity) {
        return new Group.Builder()
            .id(entity.getKey().getId())                   // Convert to Group form
            .name((String) entity.getProperty(Group.NAME))
            .introduction((String) entity.getProperty(Group.INTRODUCTION))
            .category((String) entity.getProperty(Group.CATEGORY))
            .type((String) entity.getProperty(Group.TYPE))
            .linkedin((String) entity.getProperty(Group.LINKEDIN))
            .facebook((String) entity.getProperty(Group.FACEBOOK))
            .twitter((String) entity.getProperty(Group.TWITTER))
            .instagram((String) entity.getProperty(Group.INSTAGRAM))
            .youtube((String) entity.getProperty(Group.YOUTUBE))
            .website((String) entity.getProperty(Group.WEBSITE))
            .description((String) entity.getProperty(Group.DESCRIPTION))
            .createdBy((String) entity.getProperty(Group.CREATED_BY))
            .createdById((String) entity.getProperty(Group.CREATED_BY_ID))
            .createdDate((Date) entity.getProperty(Group.CREATED_DATE))
            .imageUrl((String) entity.getProperty(Group.IMAGE_URL))
            .build();
    }
    // [END entityToPerson]

    // [START create]
    @Override
    /**
     * To create an entity and create key
     * @param Group group
     * @return Long, the id of the key
     */
    public Long createGroup(Group group) {
        Entity incGroupEntity = new Entity(GROUP_KIND);  // Key will be assigned once written
        incGroupEntity.setProperty(Group.NAME, group.getName());
        incGroupEntity.setProperty(Group.INTRODUCTION, group.getIntroduction());
        incGroupEntity.setProperty(Group.CATEGORY, group.getCategory());
        incGroupEntity.setProperty(Group.TYPE, group.getType());
        incGroupEntity.setProperty(Group.LINKEDIN, group.getLinkedin());
        incGroupEntity.setProperty(Group.FACEBOOK, group.getFacebook());
        incGroupEntity.setProperty(Group.TWITTER, group.getTwitter());
        incGroupEntity.setProperty(Group.INSTAGRAM, group.getInstagram());
        incGroupEntity.setProperty(Group.YOUTUBE, group.getYoutube());
        incGroupEntity.setProperty(Group.WEBSITE, group.getWebsite());
        incGroupEntity.setProperty(Group.DESCRIPTION, group.getDescription());
        incGroupEntity.setProperty(Group.CREATED_BY, group.getCreatedBy());
        incGroupEntity.setProperty(Group.CREATED_BY_ID, group.getCreatedById());
        incGroupEntity.setProperty(Group.CREATED_DATE, group.getCreatedDate());
        incGroupEntity.setProperty(Group.IMAGE_URL, group.getImageUrl());

        Key groupKey = datastore.put(incGroupEntity); // Save the Entity
        return groupKey.getId();                     // The ID of the Key
    }
    // [END create]

    // [START read]
    /**
     * Read group according to groupId
     * @param groupId Long groupId
     * @return group Object
     */
    @Override
    public Group readGroup(Long groupId) {
        try {
            Entity groupEntity = datastore.get(KeyFactory.createKey(GROUP_KIND, groupId));
            return entityToGroup(groupEntity);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
    // [END read]

    // [START update]
    /**
     * To update group info and store the updated info
     * @param group Person Object
     */
    @Override
    public void updateGroup(Group group) {
        Key key = KeyFactory.createKey(GROUP_KIND, group.getId());  // From a group, create a Key
        Entity entity = new Entity(key);         // Convert Group to an Entity
        entity.setProperty(Group.NAME, group.getName());
        entity.setProperty(Group.INTRODUCTION, group.getIntroduction());
        entity.setProperty(Group.CATEGORY, group.getCategory());
        entity.setProperty(Group.TYPE, group.getType());
        entity.setProperty(Group.LINKEDIN, group.getLinkedin());
        entity.setProperty(Group.FACEBOOK, group.getFacebook());
        entity.setProperty(Group.TWITTER, group.getTwitter());
        entity.setProperty(Group.INSTAGRAM, group.getInstagram());
        entity.setProperty(Group.YOUTUBE, group.getYoutube());
        entity.setProperty(Group.WEBSITE, group.getWebsite());
        entity.setProperty(Group.DESCRIPTION, group.getDescription());
        entity.setProperty(Group.CREATED_BY, group.getCreatedBy());
        entity.setProperty(Group.CREATED_BY_ID, group.getCreatedById());
        entity.setProperty(Group.CREATED_DATE, group.getCreatedDate());
        entity.setProperty(Group.IMAGE_URL, group.getImageUrl());

        datastore.put(entity);                   // Update the Entity
    }
    // [END update]

    // [START delete]
    /**
     * To delete Group according to groupId
     * @param groupId
     */
    @Override
    public void deleteGroup(Long groupId) {
        Key key = KeyFactory.createKey(GROUP_KIND, groupId);        // Create the Key
        datastore.delete(key);                      // Delete the Entity
    }
    // [END delete]

    // [START entitiesToGroups]
    /**
     * Loop through Iterator<Result> and call entity to group
     * To translate all entities to group Object
     * @param results  Iterator<Entity>
     * @return a lit of group
     */
    public List<Group> entitiesToGroups(Iterator<Entity> results) {
        List<Group> resultGroups = new ArrayList<>();
        while (results.hasNext()) {  // We still have data
            resultGroups.add(entityToGroup(results.next()));      // Add the Group to the List
        }
        return resultGroups;
    }
    // [END entitiesToPersons]

    // [START listgroups]
    /**
     * List all groups
     * @param startCursorString to display 10 per time
     * @return Result<Group>
     */
    @Override
    public Result<Group> listGroups(String startCursorString) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
        if (startCursorString != null && !startCursorString.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
        }
        Query query = new Query(GROUP_KIND) // We only care about Groups
                .addSort(Group.NAME, SortDirection.ASCENDING); // Use default Index "first"
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<Group> resultGroups = entitiesToGroups(results);     // Retrieve and convert Entities
        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && resultGroups.size() == 8) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(resultGroups, cursorString);
        } else {
            return new Result<>(resultGroups);
        }
    }
    // [END listpersons]

    public Result<Group> listAllGroups() {
        Query query = new Query(GROUP_KIND) // We only care about Persons
                .addSort(Group.NAME, SortDirection.ASCENDING); // Use default Index "first"

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator();
        List<Group> resultGroups = entitiesToGroups(results);     // Retrieve and convert Entities

        return new Result<>(resultGroups);
    }

    // [START listbyuser]
    /**
     * List all groups by specific group
     * @param startCursorString to display 10 per time
     * @return Result<Group>
     */
    @Override
    public Result<Group> listGroupsByUser(String userId, String startCursorString) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
        if (startCursorString != null && !startCursorString.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
        }
        Query query = new Query(GROUP_KIND) // We only care about Groups
            // Only for this user
            .setFilter(new Query.FilterPredicate(Group.CREATED_BY_ID, Query.FilterOperator.EQUAL, userId))
            .addSort(Group.NAME, SortDirection.ASCENDING);
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<Group> resultGroups = entitiesToGroups(results);     // Retrieve and convert Entities
        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && resultGroups.size() == 8) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(resultGroups, cursorString);
        } else {
            return new Result<>(resultGroups);
        }
    }

    public Result<Group> listAllGroupsByUser(String userId) {
        Query query = new Query(GROUP_KIND) // We only care about Persons
                .setFilter(new Query.FilterPredicate(Group.CREATED_BY_ID, Query.FilterOperator.EQUAL, userId))
                .addSort(Group.NAME, SortDirection.ASCENDING); // Use default Index "first"

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator();
        List<Group> resultGroups = entitiesToGroups(results);     // Retrieve and convert Entities

        return new Result<>(resultGroups);
    }
    // [END listbyuser]
}