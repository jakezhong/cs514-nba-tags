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

import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Result;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// [START example]
public class GroupDataStoreDao implements GroupDao {

  // [START constructor]
  private DatastoreService datastore;
  private static final String GROUP_KIND = "Group";

  public GroupDataStoreDao() {
    datastore = DatastoreServiceFactory.getDatastoreService(); // Lastized Datastore service
  }
  // [END constructor]

  // [START entityToGroup]
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
  // [END entityToGroup]

  // [START create]
  @Override
  public Long createGroup(Group group) {
    Entity incGroupEntity = new Entity(GROUP_KIND);  // Key will be assigned once written
    incGroupEntity.setProperty(Group.NAME, group.getName());
    incGroupEntity.setProperty(Group.DESCRIPTION, group.getDescription());
    incGroupEntity.setProperty(Group.IMAGE_URL, group.getImageUrl());
    incGroupEntity.setProperty(Group.CREATED_BY, group.getCreatedBy());
    incGroupEntity.setProperty(Group.CREATED_BY_ID, group.getCreatedById());

    Key groupKey = datastore.put(incGroupEntity); // Save the Entity
    return groupKey.getId();                     // The ID of the Key
  }
  // [END create]

  // [START read]
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
  @Override
  public void updateGroup(Group group) {
    Key key = KeyFactory.createKey(GROUP_KIND, group.getId());  // From a group, create a Key
    Entity entity = new Entity(key);         // Convert Group to an Entity
    entity.setProperty(Group.NAME, group.getName());
    entity.setProperty(Group.DESCRIPTION, group.getDescription());
    entity.setProperty(Group.IMAGE_URL, group.getImageUrl());
    entity.setProperty(Group.CREATED_BY, group.getCreatedBy());
    entity.setProperty(Group.CREATED_BY_ID, group.getCreatedById());

    datastore.put(entity);                   // Update the Entity
  }
  // [END update]

  // [START delete]
  @Override
  public void deleteGroup(Long groupId) {
    Key key = KeyFactory.createKey(GROUP_KIND, groupId);        // Create the Key
    datastore.delete(key);                      // Delete the Entity
  }
  // [END delete]

  // [START entitiesToGroups]
  public List<Group> entitiesToGroups(Iterator<Entity> results) {
    List<Group> resultGroups = new ArrayList<>();
    while (results.hasNext()) {  // We still have data
      resultGroups.add(entityToGroup(results.next()));      // Add the Group to the List
    }
    return resultGroups;
  }
  // [END entitiesToGroups]

  // [START listgroups]
  @Override
  public Result<Group> listGroups(String startCursorString) {
    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10); // Only show 10 at a time
    if (startCursorString != null && !startCursorString.equals("")) {
      fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
    }
    Query query = new Query(GROUP_KIND) // We only care about Groups
        .addSort(Group.NAME, SortDirection.ASCENDING); // Use default Index "first"
    PreparedQuery preparedQuery = datastore.prepare(query);
    QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

    List<Group> resultGroups = entitiesToGroups(results);     // Retrieve and convert Entities
    Cursor cursor = results.getCursor();              // Where to start next time
    if (cursor != null && resultGroups.size() == 10) {         // Are we paging? Save Cursor
      String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
      return new Result<>(resultGroups, cursorString);
    } else {
      return new Result<>(resultGroups);
    }
  }
  // [END listgroups]

  // [START listgroupsbyuser]
  @Override
  public Result<Group> listGroupsByUser(String userId, String startCursorString) {
    FetchOptions fetchOptions = FetchOptions.Builder.withLimit(10); // Only show 10 at a time
    if (startCursorString != null && !startCursorString.equals("")) {
      fetchOptions.startCursor(Cursor.fromWebSafeString(startCursorString)); // Where we left off
    }
    Query query = new Query(GROUP_KIND) // We only care about Groups
        // Only for this user
        .setFilter(new Query.FilterPredicate(
            Group.CREATED_BY_ID, Query.FilterOperator.EQUAL, userId))
        // a custom datastore index is required since you are filtering by one property
        // but ordering by another
        .addSort(Group.NAME, SortDirection.ASCENDING);
    PreparedQuery preparedQuery = datastore.prepare(query);
    QueryResultIterator<Entity> results = preparedQuery.asQueryResultIterator(fetchOptions);

    List<Group> resultGroups = entitiesToGroups(results);     // Retrieve and convert Entities
    Cursor cursor = results.getCursor();              // Where to start next time
    if (cursor != null && resultGroups.size() == 10) {         // Are we paging? Save Cursor
      String cursorString = cursor.toWebSafeString();               // Cursors are WebSafe
      return new Result<>(resultGroups, cursorString);
    } else {
      return new Result<>(resultGroups);
    }
  }
  // [END listgroupbyuser]
}
// [END example]
