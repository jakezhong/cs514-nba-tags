package com.example.getstarted.daos;

import com.example.getstarted.objects.Group;
import com.example.getstarted.objects.Result;

import java.sql.SQLException;

/**
 * Interface for GroupDao Class
 */
public interface GroupDao {
    Long createGroup(Group group) throws SQLException;

    Group readGroup(Long groupId) throws SQLException;

    void updateGroup(Group group) throws SQLException;

    void deleteGroup(Long groupId) throws SQLException;

    Result<Group> listGroups(String startCursor) throws SQLException;

    Result<Group> listGroupsByUser(String userId, String startCursor) throws SQLException;
}