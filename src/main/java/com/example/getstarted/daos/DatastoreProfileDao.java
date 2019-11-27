package com.example.getstarted.daos;

import com.example.getstarted.objects.Profile;
import com.example.getstarted.objects.Result;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * DatastoreProfileDao as storage type
 */
public class DatastoreProfileDao implements ProfileDao {

    // [START constructor]
    private DatastoreService datastore;
    static final String PROFILE_KIND = "Profile";

    /**
     * Constructor  to get Datastore service
     */
    public DatastoreProfileDao() {
        datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
    }
    // [END constructor]

    // [START entityToProfile]

    /**
        * To translate a entity to Profile Object
        * @param entity entity
        * @return Profile Object
    */
    public Profile entityToProfile(Entity entity) {
        return new Profile.Builder()                                     // Convert to Profile form
        .id(entity.getKey().getId())                                  // Convert to Profile form
        .first((String) entity.getProperty(Profile.FIRST))
        .last((String) entity.getProperty(Profile.LAST))
        .title((String) entity.getProperty(Profile.TITLE))
        .introduction((String) entity.getProperty(Profile.INTRODUCTION))
        .email((String) entity.getProperty(Profile.EMAIL))
        .phone((String) entity.getProperty(Profile.PHONE))
        .address((String) entity.getProperty(Profile.ADDRESS))
        .linkedin((String) entity.getProperty(Profile.LINKEDIN))
        .facebook((String) entity.getProperty(Profile.FACEBOOK))
        .twitter((String) entity.getProperty(Profile.TWITTER))
        .instagram((String) entity.getProperty(Profile.INSTAGRAM))
        .youtube((String) entity.getProperty(Profile.YOUTUBE))
        .website((String) entity.getProperty(Profile.WEBSITE))
        .description((String) entity.getProperty(Profile.DESCRIPTION))
        .createdBy((String) entity.getProperty(Profile.CREATED_BY))
        .createdById((String) entity.getProperty(Profile.CREATED_BY_ID))
        .publishedDate((Date) entity.getProperty(Profile.PUBLISHED_DATE))
        .imageUrl((String) entity.getProperty(Profile.IMAGE_URL))
        .build();
  }
  // [END entityToProfile]

  // [START create]

    /**
     * To create an entity and create key
     * @param profile Profile
     * @return Long, the id of the key
     */
  @Override
  public Long createProfile(Profile profile) {
      Entity incProfileEntity = new Entity(PROFILE_KIND);  // Key will be assigned once written
      incProfileEntity.setProperty(Profile.FIRST, profile.getFirst());
      incProfileEntity.setProperty(Profile.LAST, profile.getLast());
      incProfileEntity.setProperty(Profile.TITLE, profile.getTitle());
      incProfileEntity.setProperty(Profile.INTRODUCTION, profile.getIntroduction());
      incProfileEntity.setProperty(Profile.EMAIL, profile.getEmail());
      incProfileEntity.setProperty(Profile.PHONE, profile.getPhone());
      incProfileEntity.setProperty(Profile.ADDRESS, profile.getAddress());
      incProfileEntity.setProperty(Profile.LINKEDIN, profile.getLinkedin());
      incProfileEntity.setProperty(Profile.FACEBOOK, profile.getFacebook());
      incProfileEntity.setProperty(Profile.TWITTER, profile.getTwitter());
      incProfileEntity.setProperty(Profile.INSTAGRAM, profile.getInstagram());
      incProfileEntity.setProperty(Profile.YOUTUBE, profile.getYoutube());
      incProfileEntity.setProperty(Profile.WEBSITE, profile.getWebsite());
      incProfileEntity.setProperty(Profile.DESCRIPTION, profile.getDescription());
      incProfileEntity.setProperty(Profile.CREATED_BY, profile.getCreatedBy());
      incProfileEntity.setProperty(Profile.CREATED_BY_ID, profile.getCreatedById());
      incProfileEntity.setProperty(Profile.PUBLISHED_DATE, profile.getPublishedDate());
      incProfileEntity.setProperty(Profile.IMAGE_URL, profile.getImageUrl());

      Key profileKey = datastore.put(incProfileEntity); // Save the Entity
      return profileKey.getId();                     // The ID of the Key
  }
  // [END create]

  // [START read]

    /**
     * Read Profile according to userId
     * @param profileId Long ProfileId
     * @return Profile Object
     */
  @Override
  public Profile readProfile(Long profileId) {
      try {
        Entity userEntity = datastore.get(KeyFactory.createKey(PROFILE_KIND, profileId));
        return entityToProfile(userEntity);
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
    public Result<Profile> findProfile(String userId) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(1);
        Profile profile;
        Query query = new Query(PROFILE_KIND) // We only care about Groups
        // Only for this user
        .setFilter(new Query.FilterPredicate(Profile.CREATED_BY_ID, Query.FilterOperator.EQUAL, userId));

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<Profile> resultProfiles = entitiesToProfiles(results);     // Retrieve and convert Entities
        return new Result<>(resultProfiles);
    }
    // [END find]

    // [START update]

    /**
     * To update profile info and store the updated info
     * @param profile Profile Object
     */
  @Override
  public void updateProfile(Profile profile) {
      Key key = KeyFactory.createKey(PROFILE_KIND, profile.getId());  // From a profile, create a Key
      Entity entity = new Entity(key);         // Convert Profile to an Entity
      entity.setProperty(Profile.FIRST, profile.getFirst());
      entity.setProperty(Profile.LAST, profile.getLast());
      entity.setProperty(Profile.TITLE, profile.getTitle());
      entity.setProperty(Profile.INTRODUCTION, profile.getIntroduction());
      entity.setProperty(Profile.EMAIL, profile.getEmail());
      entity.setProperty(Profile.PHONE, profile.getPhone());
      entity.setProperty(Profile.ADDRESS, profile.getAddress());
      entity.setProperty(Profile.LINKEDIN, profile.getLinkedin());
      entity.setProperty(Profile.FACEBOOK, profile.getFacebook());
      entity.setProperty(Profile.TWITTER, profile.getTwitter());
      entity.setProperty(Profile.INSTAGRAM, profile.getInstagram());
      entity.setProperty(Profile.YOUTUBE, profile.getYoutube());
      entity.setProperty(Profile.WEBSITE, profile.getWebsite());
      entity.setProperty(Profile.DESCRIPTION, profile.getDescription());
      entity.setProperty(Profile.CREATED_BY, profile.getCreatedBy());
      entity.setProperty(Profile.CREATED_BY_ID, profile.getCreatedById());
      entity.setProperty(Profile.PUBLISHED_DATE, profile.getPublishedDate());
      entity.setProperty(Profile.IMAGE_URL, profile.getImageUrl());

      datastore.put(entity);                   // Update the Entity
  }
  // [END update]

  // [START delete]

    /**
     * To delete Profile according to userId
     * @param profileId
     */
  @Override
  public void deleteProfile(Long profileId) {
      Key key = KeyFactory.createKey(PROFILE_KIND, profileId);        // Create the Key
      datastore.delete(key);                      // Delete the Entity
  }
  // [END delete]

  // [START entitiesToProfiles]

    /**
     * Loop through Iterator<Result> and call entity to user
     * To translate all entities to user Object
     * @param results  Iterator<Entity>
     * @return a lit of user
     */
    public List<Profile> entitiesToProfiles(Iterator<Entity> results) {
        List<Profile> resultProfiles = new ArrayList<>();
        while (results.hasNext()) {  // We still have data
            resultProfiles.add(entityToProfile(results.next()));      // Add the Profile to the List
        }
    return resultProfiles;
}
  // [END entitiesToProfiles]

  // [START listusers]

    /**
     * List all Profiles
     * @param startCursorString to display 10 per time
     * @return Result<Profile>
     */
  @Override
  public Result<Profile> listProfiles(String startCursorString) {
      FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
      if (startCursorString != null && !startCursorString.equals("")) {
        fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
      }
      Query query = new Query(PROFILE_KIND) // We only care about Profiles
          .addSort(Profile.CREATED_BY, SortDirection.ASCENDING); // Use default Index "first"
      PreparedQuery preparedQuery = datastore.prepare(query);
      QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

      List<Profile> resultProfiles = entitiesToProfiles(results);     // Retrieve and convert Entities
      Cursor cursor = results.getCursor();              // Where to start next time
      if (cursor != null && resultProfiles.size() == 8) {         // Are we paging? Save Cursor
        String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
        return new Result<>(resultProfiles, cursorString);
      } else {
        return new Result<>(resultProfiles);
      }
  }
  // [END listusers]
}