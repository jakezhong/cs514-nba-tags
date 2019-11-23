package com.example.getstarted.daos;
import com.example.getstarted.objects.PostTag;
import com.example.getstarted.objects.Result;
import com.google.appengine.api.datastore.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * DatastoreAssociationDao as storage type
 */
public class DatastorePostTagDao implements PostTagDao {
    private static final String POST_TAG_KIND = "Post Tag";
    private DatastoreService datastore;
    /**
     * Constructor  to get Datastore service
     */
    public DatastorePostTagDao() {
        datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
    }

    /**
     * To create an entity and create key
     * @param postTag PostTag
     * @return Long, the id of the key
     */
    @Override
    public Long createPostTag(PostTag postTag) {
        Entity incPostTagEntity = new Entity(POST_TAG_KIND);  // Key will be assigned once written
        incPostTagEntity.setProperty(PostTag.POST_ID, postTag.getPostId());
        incPostTagEntity.setProperty(PostTag.PERSON_ID, postTag.getPersonId());
        incPostTagEntity.setProperty(PostTag.GROUP_ID, postTag.getGroupId());

        Key postTagKey = datastore.put(incPostTagEntity); // Save the Entity
        return postTagKey.getId();                     // The ID of the Key
    }

    /**
     * To translate a entity to postTag Object
     * @param entity entity
     * @return postTag Object
     */
    public PostTag entityToPostTag(Entity entity) {
        return new PostTag.Builder()                                     // Convert to PostTag form
        .id(entity.getKey().getId())
        .postId((Long) entity.getProperty(PostTag.POST_ID))
        .personId((Long) entity.getProperty(PostTag.PERSON_ID))
        .groupId((Long) entity.getProperty(PostTag.GROUP_ID))
        .build();
    }

    /**
     * Loop through Iterator<Result> and call entity to group
     *  To translate all entities to postTag Object
     * @param results Iterator<Entity>
     * @return List<PostTag>
     */
    public List<PostTag> entitiesToPostTags(Iterator<Entity> results) {
        List<PostTag> resultPostTags = new ArrayList<>();
        while (results.hasNext()) {  // We still have data
            resultPostTags.add(entityToPostTag(results.next()));      // Add the PostTag to the List
        }
        return resultPostTags;
    }

    /**
     * Read group according to groupId
     * @param postTagId Long postTagId
     * @return postTag Object
     */
    @Override
    public PostTag readPostTag(Long postTagId) {
        try {
            Entity postTagEntity = datastore.get(KeyFactory.createKey(POST_TAG_KIND, postTagId));
            return entityToPostTag(postTagEntity);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

    /**
     * To delete PostTag according to postTagId
     * @param postTagId
     */
    @Override
    public void deletePostTag(Long postTagId) {
        Key key = KeyFactory.createKey(POST_TAG_KIND, postTagId);        // Create the Key
        datastore.delete(key);                      // Delete the Entity
    }

    /**
     * when deleting person also deleting postTag
     * @param postId postId
     */
    public void deletePostTagByPostId(Long postId){
        Query query = new Query(POST_TAG_KIND) // We only care about Persons
                // Only for this user
                .setFilter(new Query.FilterPredicate(PostTag. POST_ID, Query.FilterOperator.EQUAL, postId));

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> resultPostTags = preparedQuery.asQueryResultIterator();

        List<PostTag> resultPosts = entitiesToPostTags(resultPostTags);
        for(PostTag postTag: resultPosts){
            Long postTagId = postTag.getId();
            Key key = KeyFactory.createKey(POST_TAG_KIND, postTagId);// Create the Key
            datastore.delete(key);// Delete the Entity
        }
    }

    /**
     * when deleting person also deleting postTag
     * @param personId personId
     */
    public void deletePostTagByPersonId(Long personId){
        Query query = new Query(POST_TAG_KIND) // We only care about Persons
        // Only for this user
        .setFilter(new Query.FilterPredicate(PostTag. PERSON_ID, Query.FilterOperator.EQUAL, personId));

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> resultPostTags = preparedQuery.asQueryResultIterator();

        List<PostTag> resultPersons = entitiesToPostTags(resultPostTags);
        for(PostTag postTag: resultPersons){
            Long postTagId = postTag.getId();
            Key key = KeyFactory.createKey(POST_TAG_KIND, postTagId);// Create the Key
            datastore.delete(key);// Delete the Entity
        }
    }

    /**
     * when deleting group also deleting postTag
     * @param groupId personId
     */
    public void deletePostTagByGroupId(Long groupId){
        Query query = new Query(POST_TAG_KIND) // We only care about Persons
        // Only for this user
        .setFilter(new Query.FilterPredicate(PostTag. GROUP_ID, Query.FilterOperator.EQUAL, groupId));

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> resultPostTags = preparedQuery.asQueryResultIterator();

        List<PostTag> resultGroups = entitiesToPostTags(resultPostTags);
        for(PostTag postTag: resultGroups){
            Long postTagId = postTag.getId();
            Key key = KeyFactory.createKey(POST_TAG_KIND, postTagId);// Create the Key
            datastore.delete(key);// Delete the Entity
        }
    }

    /**
     * list all group member in a specific group
     * @param groupId groupId
     * @param startCursor startCursor
     * @return Result<Person>
     */
    @Override
    public Result<Long> listPostByGroup(Long groupId, String startCursor) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(6); // Only show 10 at a time
        if (startCursor != null && !startCursor.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor)); // Where we left off
        }
        Query query = new Query(POST_TAG_KIND) // We only care about Persons
            // Only for this user
            .setFilter(new Query.FilterPredicate(PostTag.GROUP_ID, Query.FilterOperator.EQUAL, groupId));
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<PostTag> resultTags = entitiesToPostTags(results);     // Retrieve and convert Entities
        List<Long> postIds= new ArrayList<>();

        for(PostTag postTag: resultTags){
            Long postId = postTag.getPostId();
            postIds.add(postId);
        }

        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && resultTags.size() == 6) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(postIds, cursorString);
        } else {
            return new Result<>(postIds);
        }
    }

    /**
     * List all groups by specific person
     * @param personId personId
     * @param startCursor startCursor
     * @return Result<Group>
     */
    public Result<Long> listPostByPerson(Long personId, String startCursor) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(6); // Only show 10 at a time
        if (startCursor != null && !startCursor.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor)); // Where we left off
        }
        Query query = new Query(POST_TAG_KIND) // We only care about Persons
        // Only for this user
        .setFilter(new Query.FilterPredicate(PostTag.PERSON_ID, Query.FilterOperator.EQUAL, personId));
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<PostTag> resultTags = entitiesToPostTags(results);     // Retrieve and convert Entities
        List<Long> postIds = new ArrayList<>();

        for(PostTag postTag: resultTags){
            Long postId = postTag.getPostId();
            postIds.add(postId);
        }

        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && resultTags.size() == 6) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(postIds, cursorString);
        } else {
            return new Result<>(postIds);
        }
    }

    /**
     * list all persons in a specific post
     * @param postId groupId
     * @param startCursor startCursor
     * @return Result<Person>
     */
    @Override
    public Result<Long> listPersonByPost(Long postId, String startCursor) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(6); // Only show 10 at a time
        if (startCursor != null && !startCursor.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor)); // Where we left off
        }
        Query query = new Query(POST_TAG_KIND) // We only care about Persons
        // Only for this user
        .setFilter(new Query.FilterPredicate(PostTag.POST_ID, Query.FilterOperator.EQUAL, postId));
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<PostTag> resultTags = entitiesToPostTags(results);     // Retrieve and convert Entities
        List<Long> memberIds= new ArrayList<>();

        for(PostTag tag: resultTags){
            Long person = tag.getPersonId();
            memberIds.add(person);
        }

        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && memberIds.size() == 6) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(memberIds, cursorString);
        } else {
            return new Result<>(memberIds);
        }
    }

    /**
     * list all groups in a specific post
     * @param postId groupId
     * @param startCursor startCursor
     * @return Result<Group>
     */
    @Override
    public Result<Long> listGroupByPost(Long postId, String startCursor) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(6); // Only show 10 at a time
        if (startCursor != null && !startCursor.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursor)); // Where we left off
        }
        Query query = new Query(POST_TAG_KIND) // We only care about Persons
        // Only for this user
        .setFilter(new Query.FilterPredicate(PostTag.POST_ID, Query.FilterOperator.EQUAL, postId));
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<PostTag> resultTags = entitiesToPostTags(results);     // Retrieve and convert Entities
        List<Long> groupIds= new ArrayList<>();

        for(PostTag tag: resultTags){
            Long group = tag.getGroupId();
            groupIds.add(group);
        }

        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && groupIds.size() == 6) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(groupIds, cursorString);
        } else {
            return new Result<>(groupIds);
        }
    }
}