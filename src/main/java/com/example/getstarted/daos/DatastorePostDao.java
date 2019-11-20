package com.example.getstarted.daos;

import com.example.getstarted.objects.Post;
import com.example.getstarted.objects.Result;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * DatastorePostDao as storage type
 */
public class DatastorePostDao implements PostDao {

  // [START constructor]
  private DatastoreService datastore;
  static final String POST_KIND = "Post";

    /**
     * Constructor  to get Datastore service
     */
  public DatastorePostDao() {
    datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
  }
  // [END constructor]

  // [START entityToPost]

    /**
     * To translate a entity to Post Object
     * @param entity entity
     * @return Post Object
     */
  public Post entityToPost(Entity entity) {
      return new Post.Builder()                                     // Convert to Post form
          .id(entity.getKey().getId())
          .title((String) entity.getProperty(Post.TITLE))
          .introduction((String) entity.getProperty(Post.INTRODUCTION))
          .category((String) entity.getProperty(Post.CATEGORY))
          .type((String) entity.getProperty(Post.TYPE))
          .status((String) entity.getProperty(Post.STATUS))
          .personTag((String) entity.getProperty(Post.PERSON_TAG))
          .groupTag((String) entity.getProperty(Post.GROUP_TAG))
          .description((String) entity.getProperty(Post.DESCRIPTION))
          .createdBy((String) entity.getProperty(Post.CREATED_BY))
          .createdById((String) entity.getProperty(Post.CREATED_BY_ID))
          .publishedDate((Date) entity.getProperty(Post.PUBLISHED_DATE))
          .imageUrl((String) entity.getProperty(Post.IMAGE_URL))
          .build();
  }
  // [END entityToPost]

  // [START create]

    /**
     * To create an entity and create key
     * @param post Post
     * @return Long, the id of the key
     */
  @Override
  public Long createPost(Post post) {
      Entity incPostEntity = new Entity(POST_KIND);  // Key will be assigned once written
      incPostEntity.setProperty(Post.SLUG, post.getSlug());
      incPostEntity.setProperty(Post.TITLE, post.getTitle());
      incPostEntity.setProperty(Post.INTRODUCTION, post.getIntroduction());
      incPostEntity.setProperty(Post.CATEGORY, post.getCategory());
      incPostEntity.setProperty(Post.TYPE, post.getType());
      incPostEntity.setProperty(Post.DESCRIPTION, post.getDescription());
      incPostEntity.setProperty(Post.CREATED_BY, post.getCreatedBy());
      incPostEntity.setProperty(Post.CREATED_BY_ID, post.getCreatedById());
      incPostEntity.setProperty(Post.PUBLISHED_DATE, post.getPublishedDate());
      incPostEntity.setProperty(Post.IMAGE_URL, post.getImageUrl());

      Key postKey = datastore.put(incPostEntity); // Save the Entity
      return postKey.getId();                     // The ID of the Key
  }
  // [END create]

  // [START read]

    /**
     * Read Post according to postId
     * @param postId Long PostId
     * @return Post Object
     */
  @Override
  public Post readPost(Long postId) {
        try {
            Entity postEntity = datastore.get(KeyFactory.createKey(POST_KIND, postId));
            return entityToPost(postEntity);
        } catch (EntityNotFoundException e) {
            return null;
        }
  }
  // [END read]

  // [START update]

    /**
     * To update post info and store the updated info
     * @param post Post Object
     */
  @Override
  public void updatePost(Post post) {
      Key key = KeyFactory.createKey(POST_KIND, post.getId());  // From a post, create a Key
      Entity entity = new Entity(key);         // Convert Post to an Entity
      entity.setProperty(Post.SLUG, post.getSlug());
      entity.setProperty(Post.TITLE, post.getTitle());
      entity.setProperty(Post.INTRODUCTION, post.getIntroduction());
      entity.setProperty(Post.CATEGORY, post.getCategory());
      entity.setProperty(Post.TYPE, post.getType());
      entity.setProperty(Post.STATUS, post.getStatus());
      entity.setProperty(Post.PERSON_TAG, post.getPersonTag());
      entity.setProperty(Post.GROUP_TAG, post.getGroupTag());
      entity.setProperty(Post.DESCRIPTION, post.getDescription());
      entity.setProperty(Post.CREATED_BY, post.getCreatedBy());
      entity.setProperty(Post.CREATED_BY_ID, post.getCreatedById());
      entity.setProperty(Post.PUBLISHED_DATE, post.getPublishedDate());
      entity.setProperty(Post.IMAGE_URL, post.getImageUrl());

      datastore.put(entity);                   // Update the Entity
  }
  // [END update]

  // [START delete]

    /**
     * To delete Post according to postId
     * @param postId
     */
  @Override
  public void deletePost(Long postId) {
      Key key = KeyFactory.createKey(POST_KIND, postId);        // Create the Key
      datastore.delete(key);                      // Delete the Entity
  }
  // [END delete]

  // [START entitiesToPosts]

    /**
     * Loop through Iterator<Result> and call entity to post
     * To translate all entities to post Object
     * @param results  Iterator<Entity>
     * @return a lit of post
     */
  public List<Post> entitiesToPosts(Iterator<Entity> results) {
      List<Post> resultPosts = new ArrayList<>();
      while (results.hasNext()) {  // We still have data
        resultPosts.add(entityToPost(results.next()));      // Add the Post to the List
      }
      return resultPosts;
  }
  // [END entitiesToPosts]

  // [START listposts]

    /**
     * List all Posts
     * @param startCursorString to display 10 per time
     * @return Result<Post>
     */
  @Override
  public Result<Post> listPosts(String startCursorString) {
      FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
      if (startCursorString != null && !startCursorString.equals("")) {
        fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
      }
      Query query = new Query(POST_KIND) // We only care about Posts
          .addSort(Post.TITLE, SortDirection.ASCENDING); // Use default Index "first"
      PreparedQuery preparedQuery = datastore.prepare(query);
      QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

      List<Post> resultPosts = entitiesToPosts(results);     // Retrieve and convert Entities
      Cursor cursor = results.getCursor();              // Where to start next time
      if (cursor != null && resultPosts.size() == 8) {         // Are we paging? Save Cursor
        String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
        return new Result<>(resultPosts, cursorString);
      } else {
        return new Result<>(resultPosts);
      }
  }
  // [END listposts]


    public Result<Post> listAllPosts() {

        Query query = new Query(POST_KIND) // We only care about Posts
                .addSort(Post.TITLE, SortDirection.ASCENDING); // Use default Index "first"

        PreparedQuery preparedQuery = datastore.prepare(query);
        QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator();

        List<Post> resultPosts = entitiesToPosts(results);     // Retrieve and convert Entities

        return new Result<>(resultPosts);

    }

  // [START listbyuser]
  @Override
  /**
   * List all Posts by specific post
   * @param startCursorString to display 10 per time
   * @return Result<Post>
   */
  public Result<Post> listPostsByUser(String userId, String startCursorString) {
      FetchOptions fetchOptions = FetchOptions.Builder.withLimit(8); // Only show 10 at a time
      if (startCursorString != null && !startCursorString.equals("")) {
        fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
      }
      Query query = new Query(POST_KIND) // We only care about Posts
          // Only for this user
          .setFilter(new Query.FilterPredicate(
              Post.CREATED_BY_ID, Query.FilterOperator.EQUAL, userId))
          // a custom datastore index is required since you are filtering by one property
          // but ordering by another
          .addSort(Post.TITLE, SortDirection.ASCENDING);
      PreparedQuery preparedQuery = datastore.prepare(query);
      QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

      List<Post> resultPosts = entitiesToPosts(results);     // Retrieve and convert Entities
      Cursor cursor = results.getCursor();              // Where to start next time
      if (cursor != null && resultPosts.size() == 8) {         // Are we paging? Save Cursor
        String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
        return new Result<>(resultPosts, cursorString);
      } else {
        return new Result<>(resultPosts);
      }
  }
  // [END listbyuser]
}