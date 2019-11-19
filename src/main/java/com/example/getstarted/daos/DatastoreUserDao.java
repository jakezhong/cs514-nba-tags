package com.example.getstarted.daos;

import com.example.getstarted.objects.OurUser;
import com.example.getstarted.objects.Result;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * DatastoreUserDao as storage type
 */
public class DatastoreUserDao implements UserDao {

    // [START constructor]
    private DatastoreService datastore;
    static final String USER_KIND = "User";

    /**
     * Constructor  to get Datastore service
     */
    public DatastoreUserDao() {
        datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
    }
    // [END constructor]

    // [START entityToUser]

    /**
        * To translate a entity to OurUser Object
        * @param entity entity
        * @return OurUser Object
    */
    public OurUser entityToUser(Entity entity) {
        return new OurUser.Builder()                                     // Convert to OurUser form
        .id(entity.getKey().getId())                                  // Convert to OurUser form
        .userId((String) entity.getProperty(OurUser.USER_ID))
        .userName((String) entity.getProperty(OurUser.USER_NAME))
        .first((String) entity.getProperty(OurUser.FIRST))
        .last((String) entity.getProperty(OurUser.LAST))
        .title((String) entity.getProperty(OurUser.TITLE))
        .introduction((String) entity.getProperty(OurUser.INTRODUCTION))
        .email((String) entity.getProperty(OurUser.EMAIL))
        .phone((String) entity.getProperty(OurUser.PHONE))
        .address((String) entity.getProperty(OurUser.ADDRESS))
        .linkedin((String) entity.getProperty(OurUser.LINKEDIN))
        .facebook((String) entity.getProperty(OurUser.FACEBOOK))
        .twitter((String) entity.getProperty(OurUser.TWITTER))
        .instagram((String) entity.getProperty(OurUser.INSTAGRAM))
        .youtube((String) entity.getProperty(OurUser.YOUTUBE))
        .website((String) entity.getProperty(OurUser.WEBSITE))
        .description((String) entity.getProperty(OurUser.DESCRIPTION))
        .createdDate((Date) entity.getProperty(OurUser.CREATED_DATE))
        .imageUrl((String) entity.getProperty(OurUser.IMAGE_URL))
        .build();
  }
  // [END entityToUser]

  // [START create]

    /**
     * To create an entity and create key
     * @param ourUser OurUser
     * @return Long, the id of the key
     */
  @Override
  public Long createUser(OurUser ourUser) {
      Entity incUserEntity = new Entity(USER_KIND);  // Key will be assigned once written
      incUserEntity.setProperty(OurUser.USER_ID, ourUser.getUserId());
      incUserEntity.setProperty(OurUser.USER_NAME, ourUser.getUserName());
      incUserEntity.setProperty(OurUser.FIRST, ourUser.getFirst());
      incUserEntity.setProperty(OurUser.LAST, ourUser.getLast());
      incUserEntity.setProperty(OurUser.TITLE, ourUser.getTitle());
      incUserEntity.setProperty(OurUser.INTRODUCTION, ourUser.getIntroduction());
      incUserEntity.setProperty(OurUser.EMAIL, ourUser.getEmail());
      incUserEntity.setProperty(OurUser.PHONE, ourUser.getPhone());
      incUserEntity.setProperty(OurUser.ADDRESS, ourUser.getAddress());
      incUserEntity.setProperty(OurUser.LINKEDIN, ourUser.getLinkedin());
      incUserEntity.setProperty(OurUser.FACEBOOK, ourUser.getFacebook());
      incUserEntity.setProperty(OurUser.TWITTER, ourUser.getTwitter());
      incUserEntity.setProperty(OurUser.INSTAGRAM, ourUser.getInstagram());
      incUserEntity.setProperty(OurUser.YOUTUBE, ourUser.getYoutube());
      incUserEntity.setProperty(OurUser.WEBSITE, ourUser.getWebsite());
      incUserEntity.setProperty(OurUser.CREATED_DATE, ourUser.getCreatedDate());
      incUserEntity.setProperty(OurUser.DESCRIPTION, ourUser.getDescription());
      incUserEntity.setProperty(OurUser.IMAGE_URL, ourUser.getImageUrl());

      Key userKey = datastore.put(incUserEntity); // Save the Entity
      return userKey.getId();                     // The ID of the Key
  }
  // [END create]

  // [START read]

    /**
     * Read OurUser according to userId
     * @param userId Long UserId
     * @return OurUser Object
     */
  @Override
  public OurUser readUser(Long userId) {
      try {
        Entity userEntity = datastore.get(KeyFactory.createKey(USER_KIND, userId));
        return entityToUser(userEntity);
      } catch (EntityNotFoundException e) {
        return null;
      }
  }
  // [END read]

    // [START find]

    /**
     * Read OurUser according to userId
     * @param userId Long UserId
     * @return OurUser Object
     */
    @Override
    public OurUser findUser(String userId) {
        try {
            OurUser currentUser = null;
            Query query = new Query(USER_KIND) // We only care about Groups
                    // Only for this user
                    .setFilter(new Query.FilterPredicate(OurUser.USER_ID, Query.FilterOperator.EQUAL, userId));
            PreparedQuery preparedQuery = datastore.prepare(query);
            QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator();

            List<OurUser> resultUsers = entitiesToUsers(results);     // Retrieve and convert Entities
            for (OurUser user: resultUsers) {
                currentUser = readUser(user.getId());
            }
            return currentUser;
        } catch (Exception e) {
            return null;
        }
    }
    // [END find]

  // [START update]

    /**
     * To update ourUser info and store the updated info
     * @param ourUser OurUser Object
     */
  @Override
  public void updateUser(OurUser ourUser) {
      Key key = KeyFactory.createKey(USER_KIND, ourUser.getId());  // From a ourUser, create a Key
      Entity entity = new Entity(key);         // Convert OurUser to an Entity
      entity.setProperty(OurUser.USER_NAME, ourUser.getUserName());
      entity.setProperty(OurUser.FIRST, ourUser.getFirst());
      entity.setProperty(OurUser.LAST, ourUser.getLast());
      entity.setProperty(OurUser.TITLE, ourUser.getTitle());
      entity.setProperty(OurUser.INTRODUCTION, ourUser.getIntroduction());
      entity.setProperty(OurUser.EMAIL, ourUser.getEmail());
      entity.setProperty(OurUser.PHONE, ourUser.getPhone());
      entity.setProperty(OurUser.ADDRESS, ourUser.getAddress());
      entity.setProperty(OurUser.LINKEDIN, ourUser.getLinkedin());
      entity.setProperty(OurUser.FACEBOOK, ourUser.getFacebook());
      entity.setProperty(OurUser.TWITTER, ourUser.getTwitter());
      entity.setProperty(OurUser.INSTAGRAM, ourUser.getInstagram());
      entity.setProperty(OurUser.YOUTUBE, ourUser.getYoutube());
      entity.setProperty(OurUser.WEBSITE, ourUser.getWebsite());
      entity.setProperty(OurUser.DESCRIPTION, ourUser.getDescription());
      entity.setProperty(OurUser.CREATED_DATE, ourUser.getCreatedDate());
      entity.setProperty(OurUser.IMAGE_URL, ourUser.getImageUrl());

      datastore.put(entity);                   // Update the Entity
  }
  // [END update]

  // [START delete]

    /**
     * To delete OurUser according to userId
     * @param userId
     */
  @Override
  public void deleteUser(Long userId) {
      Key key = KeyFactory.createKey(USER_KIND, userId);        // Create the Key
      datastore.delete(key);                      // Delete the Entity
  }
  // [END delete]

  // [START entitiesToUsers]

    /**
     * Loop through Iterator<Result> and call entity to user
     * To translate all entities to user Object
     * @param results  Iterator<Entity>
     * @return a lit of user
     */
    public List<OurUser> entitiesToUsers(Iterator<Entity> results) {
        List<OurUser> resultOurUsers = new ArrayList<>();
        while (results.hasNext()) {  // We still have data
            resultOurUsers.add(entityToUser(results.next()));      // Add the OurUser to the List
        }
    return resultOurUsers;
}
  // [END entitiesToUsers]

  // [START listusers]

    /**
     * List all Users
     * @param startCursorString to display 10 per time
     * @return Result<OurUser>
     */
  @Override
  public Result<OurUser> listUsers(String startCursorString) {
      FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
      if (startCursorString != null && !startCursorString.equals("")) {
        fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
      }
      Query query = new Query(USER_KIND) // We only care about Users
          .addSort(OurUser.USER_NAME, SortDirection.ASCENDING); // Use default Index "first"
      PreparedQuery preparedQuery = datastore.prepare(query);
      QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

      List<OurUser> resultOurUsers = entitiesToUsers(results);     // Retrieve and convert Entities
      Cursor cursor = results.getCursor();              // Where to start next time
      if (cursor != null && resultOurUsers.size() == 8) {         // Are we paging? Save Cursor
        String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
        return new Result<>(resultOurUsers, cursorString);
      } else {
        return new Result<>(resultOurUsers);
      }
  }
  // [END listusers]
}