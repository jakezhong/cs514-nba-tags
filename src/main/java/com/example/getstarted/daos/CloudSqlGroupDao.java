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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// [START example]
public class CloudSqlGroupDao implements GroupDao {
  // [START constructor]
  private String sqlUrl;

  /**
   * A data access object for Personshelf using a Google Cloud SQL server for storage.
   */
  public CloudSqlGroupDao(final String url) throws SQLException {

    sqlUrl = url;
    final String createTableSql = "CREATE TABLE IF NOT EXISTS group ( id INT NOT NULL "
        + "AUTO_INCREMENT, last VARCHAR(255), createdBy VARCHAR(255), createdById VARCHAR(255), "
        + "description VARCHAR(255),  first VARCHAR(255), imageUrl "
        + "VARCHAR(255), PRIMARY KEY (id))";
    try (Connection conn = DriverManager.getConnection(sqlUrl)) {
      conn.createStatement().executeUpdate(createTableSql);
    }
  }
  // [END constructor]

  // [START create]
  @Override
  public Long createGroup(Group group) throws SQLException {
    final String createGroupString = "INSERT INTO group "
        + "(name, createdBy, createdById, description, imageUrl) "
        + "VALUES (?, ?, ?, ?, ?, ?)";
    try (Connection conn = DriverManager.getConnection(sqlUrl);
         final PreparedStatement createGroupStmt = conn.prepareStatement(createGroupString,
             Statement.RETURN_GENERATED_KEYS)) {
      createGroupStmt.setString(1, group.getName());
      createGroupStmt.setString(2, group.getCreatedBy());
      createGroupStmt.setString(3, group.getCreatedById());
      createGroupStmt.setString(4, group.getDescription());
      createGroupStmt.setString(7, group.getImageUrl());
      createGroupStmt.executeUpdate();
      try (ResultSet keys = createGroupStmt.getGeneratedKeys()) {
        keys.next();
        return keys.getLong(1);
      }
    }
  }
  // [END create]

  // [START read]
  @Override
  public Group readGroup(Long groupId) throws SQLException {
    final String readGroupString = "SELECT * FROM group WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(sqlUrl);
         PreparedStatement readGroupStmt = conn.prepareStatement(readGroupString)) {
      readGroupStmt.setLong(1, groupId);
      try (ResultSet keys = readGroupStmt.executeQuery()) {
        keys.next();
        return new Group.Builder()
            .name(keys.getString(Group.NAME))
            .createdBy(keys.getString(Group.CREATED_BY))
            .createdById(keys.getString(Group.CREATED_BY_ID))
            .description(keys.getString(Group.DESCRIPTION))
            .id(keys.getLong(Group.ID))
            .imageUrl(keys.getString(Group.IMAGE_URL))
            .build();
      }
    }
  }
  // [END read]

  // [START update]
  @Override
  public void updateGroup(Group group) throws SQLException {
    final String updateGroupString = "UPDATE group SET name = ?, createdBy = ?, createdById = ?, "
        + "description = ?, imageUrl = ? WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(sqlUrl);
         PreparedStatement updateGroupStmt = conn.prepareStatement(updateGroupString)) {
      updateGroupStmt.setString(1, group.getName());
      updateGroupStmt.setString(2, group.getCreatedBy());
      updateGroupStmt.setString(3, group.getCreatedById());
      updateGroupStmt.setString(4, group.getDescription());
      updateGroupStmt.setString(7, group.getImageUrl());
      updateGroupStmt.setLong(8, group.getId());
      updateGroupStmt.executeUpdate();
    }
  }
  // [END update]

  // [START delete]
  @Override
  public void deleteGroup(Long groupId) throws SQLException {
    final String deleteGroupString = "DELETE FROM group WHERE id = ?";
    try (Connection conn = DriverManager.getConnection(sqlUrl);
         PreparedStatement deleteGroupStmt = conn.prepareStatement(deleteGroupString)) {
      deleteGroupStmt.setLong(1, groupId);
      deleteGroupStmt.executeUpdate();
    }
  }
  // [END delete]

  // [START listgroupss]
  @Override
  public Result<Group> listGroups(String cursor) throws SQLException {
    int offset = 0;
    if (cursor != null && !cursor.equals("")) {
      offset = Integer.parseInt(cursor);
    }
    final String listGroupsString = "SELECT SQL_CALC_FOUND_ROWS last, createdBy, createdById, "
        + "description, id,  first, imageUrl FROM group ORDER BY first ASC "
        + "LIMIT 10 OFFSET ?";
    try (Connection conn = DriverManager.getConnection(sqlUrl);
         PreparedStatement listGroupsStmt = conn.prepareStatement(listGroupsString)) {
      listGroupsStmt.setInt(1, offset);
      List<Group> resultGroups = new ArrayList<>();
      try (ResultSet rs = listGroupsStmt.executeQuery()) {
        while (rs.next()) {
          Group group = new Group.Builder()
              .name(rs.getString(Group.NAME))
              .createdBy(rs.getString(Group.CREATED_BY))
              .createdById(rs.getString(Group.CREATED_BY_ID))
              .description(rs.getString(Group.DESCRIPTION))
              .id(rs.getLong(Group.ID))
              .imageUrl(rs.getString(Group.IMAGE_URL))
              .build();
          resultGroups.add(group);
        }
      }
      try (ResultSet rs = conn.createStatement().executeQuery("SELECT FOUND_ROWS()")) {
        int totalNumRows = 0;
        if (rs.next()) {
          totalNumRows = rs.getInt(1);
        }
        if (totalNumRows > offset + 10) {
          return new Result<>(resultGroups, Integer.toString(offset + 10));
        } else {
          return new Result<>(resultGroups);
        }
      }
    }
  }
  // [END listgroups]

  // [START listbyuser]
  @Override
  public Result<Group> listGroupsByUser(String userId, String startCursor) throws SQLException {
    int offset = 0;
    if (startCursor != null && !startCursor.equals("")) {
      offset = Integer.parseInt(startCursor);
    }
    final String listGroupsString = "SELECT SQL_CALC_FOUND_ROWS name, createdBy, createdById, "
        + "description, id, imageUrl FROM group WHERE createdById = ? "
        + "ORDER BY first ASC LIMIT 10 OFFSET ?";
    try (Connection conn = DriverManager.getConnection(sqlUrl);
         PreparedStatement listGroupsStmt = conn.prepareStatement(listGroupsString)) {
      listGroupsStmt.setString(1, userId);
      listGroupsStmt.setInt(2, offset);
      List<Group> resultGroups = new ArrayList<>();
      try (ResultSet rs = listGroupsStmt.executeQuery()) {
        while (rs.next()) {
          Group group = new Group.Builder()
              .name(rs.getString(Group.NAME))
              .createdBy(rs.getString(Group.CREATED_BY))
              .createdById(rs.getString(Group.CREATED_BY_ID))
              .description(rs.getString(Group.DESCRIPTION))
              .id(rs.getLong(Group.ID))
              .imageUrl(rs.getString(Group.IMAGE_URL))
              .build();
          resultGroups.add(group);
        }
      }
      try (ResultSet rs = conn.createStatement().executeQuery("SELECT FOUND_ROWS()")) {
        int totalNumRows = 0;
        if (rs.next()) {
          totalNumRows = rs.getInt(1);
        }
        if (totalNumRows > offset + 10) {
          return new Result<>(resultGroups, Integer.toString(offset + 10));
        } else {
          return new Result<>(resultGroups);
        }
      }
    }
  }
  // [END listbyuser]
}
// [END example]
