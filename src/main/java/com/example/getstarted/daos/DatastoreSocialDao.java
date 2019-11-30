package com.example.getstarted.daos;

import com.example.getstarted.objects.SocialLink;
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
import java.util.Iterator;
import java.util.List;

/**
 * DatastorePersonDao as storage type
 */
public class DatastoreSocialDao implements SocialLinkDao {

    // [START constructor]
    private DatastoreService datastore;
    static final String SOCIAL__KIND = "SocialLink";

    /**
     * Constructor  to get Datastore service
     */
    public DatastoreSocialDao() {
        datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
    }
    // [END constructor]

    /**
     * To translate a entity to SocialLink Object
     * @param entity entity
     * @return SocialLink Object
     */
    public SocialLink entityToSocialLink(Entity entity) {
        return new SocialLink.Builder()                                     // Convert to SocialLink form
                .id(entity.getKey().getId())
                .socialLinkName((String) entity.getProperty(SocialLink.SOCIALLINKNAME))
                .socialLinkUrl((String) entity.getProperty(SocialLink.SOCIALLINKURL))
                .personId((Long) entity.getProperty(SocialLink.PERSONID))
                .build();
    }
    // [END entityToSocialLink]

    // [START create]

    /**
     * To create an entity and create key
     * @param socialLink SocialLink
     * @return Long, the id of the key
     */

    public Long createSocialLink(SocialLink socialLink) {
        Entity incSocialLinkEntity = new Entity(SOCIAL__KIND);  // Key will be assigned once written
        incSocialLinkEntity.setProperty(SocialLink.SOCIALLINKNAME, socialLink.getSocialLinkName());
        incSocialLinkEntity.setProperty(SocialLink.SOCIALLINKURL, socialLink.getSocialLinkUrl());
        incSocialLinkEntity.setProperty(SocialLink.PERSONID,socialLink.getPersonId());

        Key socialLinkKey = datastore.put(incSocialLinkEntity); // Save the Entity
        return socialLinkKey.getId();                     // The ID of the Key
    }
    // [END create]

    public SocialLink findSocialLink(SocialLink socialLink){
        Query.FilterPredicate filterSocialLinkName = new Query.FilterPredicate(SocialLink. SOCIALLINKNAME, Query.FilterOperator.EQUAL, socialLink.getSocialLinkName());
        Query.FilterPredicate filterSocialId = new Query.FilterPredicate(SocialLink. PERSONID, Query.FilterOperator.EQUAL, socialLink.getPersonId());
        Query.CompositeFilter filter =
                Query.CompositeFilterOperator.and(filterSocialLinkName,filterSocialId);

        Query query = new Query(SOCIAL__KIND).setFilter(filter);
        System.out.println(query);
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator();

        List<SocialLink> resultSocialLinks = entitiesToSocialLinks(results);
        if(resultSocialLinks.isEmpty()){
            return null;
        }else{
            return resultSocialLinks.get(0);
        }
    }


    // [START read]

    /**
     * Read SocialLink according to socialLinkId
     * @param socialLinkId Long SocialLinkId
     * @return SocialLink Object
     */
    @Override
    public SocialLink readSocialLink(Long socialLinkId) {
        try {
            Entity socialLinkEntity = datastore.get(KeyFactory.createKey(SOCIAL__KIND, socialLinkId));
            return entityToSocialLink(socialLinkEntity);
        } catch (EntityNotFoundException e) {
            return null;
        }
    }
    // [END read]

    // [START update]

    /**
     * To update socialLink info and store the updated info
     * @param socialLink SocialLink Object
     */
    @Override
    public void updateSocialLink(SocialLink socialLink,String socialLinkUrl) {
        Key key = KeyFactory.createKey(SOCIAL__KIND, socialLink.getId());  // From a socialLink, create a Key
        Entity entity = new Entity(key);         // Convert SocialLink to an Entity
        entity.setProperty(SocialLink.SOCIALLINKNAME, socialLink.getSocialLinkName());
        entity.setProperty(SocialLink.SOCIALLINKURL, socialLinkUrl);
        entity.setProperty(SocialLink.PERSONID, socialLink.getPersonId());

        datastore.put(entity);                   // Update the Entity
    }
    // [END update]

    // [START delete]

    /**
     * To delete SocialLink according to socialLinkId
     * @param socialLinkId
     */
    @Override
    public void deleteSocialLink(Long socialLinkId) {
        Key key = KeyFactory.createKey(SOCIAL__KIND, socialLinkId);        // Create the Key
        datastore.delete(key);                      // Delete the Entity
    }
    // [END delete]

    // [START entitiesToSocialLinks]

    /**
     * Loop through Iterator<Result> and call entity to socialLink
     * To translate all entities to socialLink Object
     * @param results  Iterator<Entity>
     * @return a lit of socialLink
     */
    public List<SocialLink> entitiesToSocialLinks(Iterator<Entity> results) {
        List<SocialLink> resultSocialLinks = new ArrayList<>();
        while (results.hasNext()) {  // We still have data
            resultSocialLinks.add(entityToSocialLink(results.next()));      // Add the SocialLink to the List
        }
        return resultSocialLinks;
    }
    // [END entitiesToSocialLinks]

    // [START listsocialLinks]

    /**
     * List all SocialLinks
     * @param startCursorString to display 10 per time
     * @return Result<SocialLink>
     */
    @Override
    public Result<SocialLink> listSocialLinks(String startCursorString) {
        FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
        if (startCursorString != null && !startCursorString.equals("")) {
            fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
        }
        Query query = new Query(SOCIAL__KIND) // We only care about SocialLinks
                .addSort(SocialLink.SOCIALLINKNAME, SortDirection.ASCENDING); // Use default Index "first"
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

        List<SocialLink> resultSocialLinks = entitiesToSocialLinks(results);     // Retrieve and convert Entities
        Cursor cursor = results.getCursor();              // Where to start next time
        if (cursor != null && resultSocialLinks.size() == 8) {         // Are we paging? Save Cursor
            String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
            return new Result<>(resultSocialLinks, cursorString);
        } else {
            return new Result<>(resultSocialLinks);
        }

        // EntityQuery.Builder queryBuilder = Query.newEntityQueryBuilder().setKind(PERSON_LINK);

    }
    // [END listsocialLinks]


    public Result<SocialLink> listAllSocialLinks() {

        Query query = new Query(SOCIAL__KIND) // We only care about SocialLinks
                .addSort(SocialLink.SOCIALLINKNAME, SortDirection.ASCENDING); // Use default Index "first"

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator();

        List<SocialLink> resultSocialLinks = entitiesToSocialLinks(results);     // Retrieve and convert Entities

        return new Result<>(resultSocialLinks);

    }

    // [START listbyuser]
    @Override
    /**
     * List all SocialLinks by specific socialLink
     * @param startCursorString to display 10 per time
     * @return Result<SocialLink>
     */
    public Result<SocialLink> listSocialLinksByPerson(Long personId) {
        Query query = new Query(SOCIAL__KIND) // We only care about SocialLinks
                // Only for this user
                .setFilter(new Query.FilterPredicate(
                        SocialLink.PERSONID, Query.FilterOperator.EQUAL, personId));
                // a custom datastore index is required since you are filtering by one property
                // but ordering by another
        System.out.println(query);
        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator();

        List<SocialLink> resultSocialLinks = entitiesToSocialLinks(results);     // Retrieve and convert Entities
        return new Result<>(resultSocialLinks);
    }
    // [END listbyuser]
}