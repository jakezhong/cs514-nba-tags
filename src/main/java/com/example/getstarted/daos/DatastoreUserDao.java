package com.example.getstarted.daos;

import com.example.getstarted.objects.User;
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
    static final String PERSON_KIND = "User";

    /**
     * Constructor  to get Datastore service
     */
    public DatastoreUserDao() {
        datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
    }
    // [END constructor]

    // [START entityToUser]

    /**
        * To translate a entity to User Object
        * @param entity entity
        * @return User Object
    */
    public User entityToUser(Entity entity) {
        return new User.Builder()                                     // Convert to User form
        .id(entity.getKey().getId())
        .first((String) entity.getProperty(User.FIRST))
        .last((String) entity.getProperty(User.LAST))
        .title((String) entity.getProperty(User.TITLE))
        .introduction((String) entity.getProperty(User.INTRODUCTION))
        .email((String) entity.getProperty(User.EMAIL))
        .phone((String) entity.getProperty(User.PHONE))
        .address((String) entity.getProperty(User.ADDRESS))
        .linkedin((String) entity.getProperty(User.LINKEDIN))
        .facebook((String) entity.getProperty(User.FACEBOOK))
        .twitter((String) entity.getProperty(User.TWITTER))
        .instagram((String) entity.getProperty(User.INSTAGRAM))
        .youtube((String) entity.getProperty(User.YOUTUBE))
        .website((String) entity.getProperty(User.WEBSITE))
        .description((String) entity.getProperty(User.DESCRIPTION))
        .createdDate((Date) entity.getProperty(User.CREATED_DATE))
        .imageUrl((String) entity.getProperty(User.IMAGE_URL))
        .build();
  }
  // [END entityToUser]

  // [START create]

    /**
     * To create an entity and create key
     * @param user User
     * @return Long, the id of the key
     */
  @Override
  public Long createUser(User user) {
      Entity incUserEntity = new Entity(PERSON_KIND);  // Key will be assigned once written
      incUserEntity.setProperty(User.USERNAME, user.getUserName());
      incUserEntity.setProperty(User.FIRST, user.getFirst());
      incUserEntity.setProperty(User.LAST, user.getLast());
      incUserEntity.setProperty(User.TITLE, user.getTitle());
      incUserEntity.setProperty(User.INTRODUCTION, user.getIntroduction());
      incUserEntity.setProperty(User.EMAIL, user.getEmail());
      incUserEntity.setProperty(User.PHONE, user.getPhone());
      incUserEntity.setProperty(User.ADDRESS, user.getAddress());
      incUserEntity.setProperty(User.LINKEDIN, user.getLinkedin());
      incUserEntity.setProperty(User.FACEBOOK, user.getFacebook());
      incUserEntity.setProperty(User.TWITTER, user.getTwitter());
      incUserEntity.setProperty(User.INSTAGRAM, user.getInstagram());
      incUserEntity.setProperty(User.YOUTUBE, user.getYoutube());
      incUserEntity.setProperty(User.WEBSITE, user.getWebsite());
      incUserEntity.setProperty(User.CREATED_DATE, user.getCreatedDate());
      incUserEntity.setProperty(User.DESCRIPTION, user.getDescription());
      incUserEntity.setProperty(User.IMAGE_URL, user.getImageUrl());

      Key userKey = datastore.put(incUserEntity); // Save the Entity
      return userKey.getId();                     // The ID of the Key
  }
  // [END create]

  // [START read]

    /**
     * Read User according to userId
     * @param userId Long UserId
     * @return User Object
     */
  @Override
  public User readUser(Long userId) {
      try {
        Entity userEntity = datastore.get(KeyFactory.createKey(PERSON_KIND, userId));
        return entityToUser(userEntity);
      } catch (EntityNotFoundException e) {
        return null;
      }
  }
  // [END read]

  // [START update]

    /**
     * To update user info and store the updated info
     * @param user User Object
     */
  @Override
  public void updateUser(User user) {
      Key key = KeyFactory.createKey(PERSON_KIND, user.getId());  // From a user, create a Key
      Entity entity = new Entity(key);         // Convert User to an Entity
      entity.setProperty(User.USERNAME, user.getUserName());
      entity.setProperty(User.FIRST, user.getFirst());
      entity.setProperty(User.LAST, user.getLast());
      entity.setProperty(User.TITLE, user.getTitle());
      entity.setProperty(User.INTRODUCTION, user.getIntroduction());
      entity.setProperty(User.EMAIL, user.getEmail());
      entity.setProperty(User.PHONE, user.getPhone());
      entity.setProperty(User.ADDRESS, user.getAddress());
      entity.setProperty(User.LINKEDIN, user.getLinkedin());
      entity.setProperty(User.FACEBOOK, user.getFacebook());
      entity.setProperty(User.TWITTER, user.getTwitter());
      entity.setProperty(User.INSTAGRAM, user.getInstagram());
      entity.setProperty(User.YOUTUBE, user.getYoutube());
      entity.setProperty(User.WEBSITE, user.getWebsite());
      entity.setProperty(User.DESCRIPTION, user.getDescription());
      entity.setProperty(User.CREATED_DATE, user.getCreatedDate());
      entity.setProperty(User.IMAGE_URL, user.getImageUrl());

      datastore.put(entity);                   // Update the Entity
  }
  // [END update]

  // [START delete]

    /**
     * To delete User according to userId
     * @param userId
     */
  @Override
  public void deleteUser(Long userId) {
      Key key = KeyFactory.createKey(PERSON_KIND, userId);        // Create the Key
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
    public List<User> entitiesToUsers(Iterator<Entity> results) {
        List<User> resultUsers = new ArrayList<>();
        while (results.hasNext()) {  // We still have data
            resultUsers.add(entityToUser(results.next()));      // Add the User to the List
        }
    return resultUsers;
}
  // [END entitiesToUsers]

  // [START listusers]

    /**
     * List all Users
     * @param startCursorString to display 10 per time
     * @return Result<User>
     */
  @Override
  public Result<User> listUsers(String startCursorString) {
      FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
      if (startCursorString != null && !startCursorString.equals("")) {
        fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
      }
      Query query = new Query(PERSON_KIND) // We only care about Users
          .addSort(User.USERNAME, SortDirection.ASCENDING); // Use default Index "first"
      PreparedQuery preparedQuery = datastore.prepare(query);
      QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

      List<User> resultUsers = entitiesToUsers(results);     // Retrieve and convert Entities
      Cursor cursor = results.getCursor();              // Where to start next time
      if (cursor != null && resultUsers.size() == 8) {         // Are we paging? Save Cursor
        String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
        return new Result<>(resultUsers, cursorString);
      } else {
        return new Result<>(resultUsers);
      }
  }
  // [END listusers]
}